package com.example.spring_api.controller;

import java.time.LocalDate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring_api.config.CustomUserDetails;
import com.example.spring_api.dto.ArticleDTO;
import com.example.spring_api.dto.BookmarkDTO;
import com.example.spring_api.dto.CategoryDTO;
import com.example.spring_api.dto.CommentDTO;
import com.example.spring_api.dto.UserDTO;
import com.example.spring_api.model.Article;
import com.example.spring_api.model.Bookmark;
import com.example.spring_api.model.Category;
import com.example.spring_api.model.User;
import com.example.spring_api.model.Comment;
import com.example.spring_api.model.LoginRequest;
import com.example.spring_api.repository.ArticleRepository;
import com.example.spring_api.repository.CategoryRepository;
import com.example.spring_api.repository.CommentRepository;
import com.example.spring_api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.spring_api.repository.BookmarkRepository;

@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> otpMap = new HashMap<>();

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("user");
        user.setCreateAt(Date.valueOf(LocalDate.now()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            if ("admin".equals(user.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied for admin");
            }
            return ResponseEntity.ok(user);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return ResponseEntity.ok().body("Logout successful");
    }

    @GetMapping("get-user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    UserDTO userDTO = new UserDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getFullname(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getRole(),
                            user.getImage());
                    return ResponseEntity.ok(userDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("update-profile/{id}")
    public ResponseEntity<?> updateUserWithImage(
            @PathVariable Integer id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO;

        try {
            userDTO = objectMapper.readValue(userJson, UserDTO.class);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user JSON");
        }

        User user = optionalUser.get();
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());

        if (image != null && !image.isEmpty()) {
            try {
                String projectDir = System.getProperty("user.dir");
                String uploadDir = projectDir + "/src/main/resources/static/images/user/";

                String originalFilename = image.getOriginalFilename();
                String extension = "";
                int dotIndex = originalFilename.lastIndexOf(".");
                if (dotIndex >= 0) {
                    extension = originalFilename.substring(dotIndex);
                }
                String fileName = "user_" + id + "_" + System.currentTimeMillis() + extension;

                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, image.getBytes());

                user.setImage("images/user/" + fileName);

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Email does not exist");
        }

        String otp = String.format("%04d", new Random().nextInt(10000));
        otpMap.put(email, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Verification Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);

        return ResponseEntity.ok("OTP sent to your email");
    }

    @PostMapping("verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        String savedOtp = otpMap.get(email);

        if (savedOtp != null && savedOtp.equals(otp)) {
            otpMap.remove(email);
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP code");
        }
    }

    @PostMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body("Email does not exist");
        }
        user.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user.get());

        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("get-all-categories")
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            return new CategoryDTO(
                    category.getId(),
                    category.getCategoryName(),
                    category.getImage(),
                    category.getCreateAt().toString());
        }).collect(Collectors.toList());
    }

    @GetMapping("get-articles-by-category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        List<Article> articles = articleRepository.findByCategoryId(id);
        List<ArticleDTO> articleDTOs = articles.stream().map(article -> {
            String categoryName = categoryOpt.get().getCategoryName();
            return new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }

    @GetMapping("get-all-articles")
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(article -> {
            Optional<Category> categoryOptional = categoryRepository.findById(article.getCategoryId());
            String categoryName = categoryOptional.map(Category::getCategoryName).orElse("Unknown");

            return new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);
        }).collect(Collectors.toList());
    }

    @GetMapping("get-articles/{id}")
    public ArticleDTO getArticleById(@PathVariable("id") Integer id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();

            Optional<Category> categoryOptional = categoryRepository.findById(article.getCategoryId());
            String categoryName = categoryOptional.map(Category::getCategoryName).orElse("Unknown");

            return new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);
        }
        return null;
    }

    @GetMapping("article-latest")
    public List<ArticleDTO> getLatestArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByIdDesc();

        return articles.stream().map(article -> {
            Optional<Category> categoryOptional = categoryRepository.findById(article.getCategoryId());
            String categoryName = categoryOptional.map(Category::getCategoryName).orElse("Unknown");

            return new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);
        }).collect(Collectors.toList());
    }

    @GetMapping("article-slider")
    public List<ArticleDTO> getSliderArticles() {
        List<Article> allArticles = articleRepository.findAll();
        List<Article> sliderArticles = allArticles.stream()
                .sorted((a1, a2) -> Integer.compare(
                        commentRepository.countByArticleId(a2.getId()),
                        commentRepository.countByArticleId(a1.getId())))
                .limit(3)
                .collect(Collectors.toList());

        return sliderArticles.stream().map(article -> {
            Optional<Category> categoryOptional = categoryRepository.findById(article.getCategoryId());
            String categoryName = categoryOptional.map(Category::getCategoryName).orElse("Unknown");

            return new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);
        }).collect(Collectors.toList());
    }

    @GetMapping("{id}/article-count")
    public ResponseEntity<Integer> getArticleCountByCategory(@PathVariable Integer id) {
        int count = articleRepository.countByCategoryId(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("get-comments/{articleId}")
    public List<CommentDTO> getCommentsByArticleId(@PathVariable Integer articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream().map(comment -> {
            Optional<User> userOptional = userRepository.findById(comment.getUserId());
            UserDTO userDTO = userOptional.map(user -> new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getFullname(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getRole(),
                    user.getImage())).orElse(null);

            return new CommentDTO(
                    comment.getId(),
                    comment.getArticleId(),
                    userDTO,
                    comment.getContent(),
                    comment.getCreateAt().toString());
        }).collect(Collectors.toList());
    }

    @GetMapping("get-bookmark/{user_id}")
    public ResponseEntity<?> getBookmarksByUserId(@PathVariable("user_id") Integer userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getImage());

        List<BookmarkDTO> bookmarkDTO = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            Optional<Article> articleOptional = articleRepository.findById(bookmark.getArticleId());

            if (articleOptional.isPresent()) {
                Article article = articleOptional.get();
                Optional<Category> categoryOptional = categoryRepository.findById(article.getCategoryId());
                String categoryName = categoryOptional.map(Category::getCategoryName).orElse("Unknown");
                ArticleDTO articleDTO = new ArticleDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getImage(),
                        article.getContent(),
                        article.getCreateAt().toString(),
                        categoryName);
                BookmarkDTO dto = new BookmarkDTO(
                        bookmark.getId(),
                        userDTO,
                        articleDTO,
                        new SimpleDateFormat("yyyy-MM-dd").format(bookmark.getCreateAt()));
                bookmarkDTO.add(dto);
            }
        }
        return ResponseEntity.ok(bookmarkDTO);
    }

    @PostMapping("sendBookmark")
    public Bookmark createBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @DeleteMapping("deleteBookmark/{id}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Integer id) {
        if (!bookmarkRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookmarkRepository.deleteById(id);
        return ResponseEntity.ok("Bookmark deleted successfully");
    }

    @PostMapping("{articleId}/comments")
    public Comment addComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

}
