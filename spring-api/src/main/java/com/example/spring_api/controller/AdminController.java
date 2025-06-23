package com.example.spring_api.controller;
import org.springframework.beans.factory.annotation.Value;

import com.example.spring_api.model.User;
import com.example.spring_api.repository.ArticleRepository;
import com.example.spring_api.repository.CategoryRepository;
import com.example.spring_api.repository.CommentRepository;
import com.example.spring_api.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring_api.dto.ArticleDTO;
import com.example.spring_api.model.Article;
import com.example.spring_api.model.Category;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String activePage = "";

    @GetMapping("/login")
    public String home(Model model) {
        model.addAttribute("activePage", activePage);
        return "login/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword()) && "admin".equals(user.getRole())) {
                session.setAttribute("role", "admin");
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("image", user.getImage());
                return "redirect:/admin/dashboard";
            }
        }

        model.addAttribute("error", "Incorrect email or password");
        return "login/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login/index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        activePage = "dashboard";
        model.addAttribute("activePage", activePage);

        model.addAttribute("totalAccounts", userRepository.count());
        model.addAttribute("totalCategories", categoryRepository.count());
        model.addAttribute("totalArticles", articleRepository.count());
        model.addAttribute("totalComments", commentRepository.count());

        List<Category> categories = categoryRepository.findAll();
        Map<String, Integer> articleCountByCategory = new LinkedHashMap<>();

        for (Category category : categories) {
            int articleCount = articleRepository.countByCategoryId(category.getId());
            articleCountByCategory.put(category.getCategoryName(), articleCount);
        }

        model.addAttribute("articleCountByCategory", articleCountByCategory);

        return "dashboard/dashboard";
    }

    @GetMapping("/admin_infomation")
    public String adminInformation(HttpSession session, Model model) {
        activePage = "admin_infomation";
        model.addAttribute("activePage", activePage);

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            optionalUser.ifPresent(user -> model.addAttribute("user", user));
        }

        return "admin_infomation/index";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") Integer id,
            @RequestParam("fullname") String fullname,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("avatar") MultipartFile avatar,
            RedirectAttributes redirectAttributes,
            Model model) {

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (password != null && !password.isEmpty()) {
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
            }

            user.setFullname(fullname);
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);

            if (avatar != null && !avatar.isEmpty()) {
                try {
                    String projectDir = System.getProperty("user.dir");
                    String uploadDir = projectDir + "/src/main/resources/static/images/user/";

                    String originalFilename = avatar.getOriginalFilename();
                    if (originalFilename != null && !originalFilename.isEmpty()) {
                        String fileName = originalFilename;

                        Path path = Paths.get(uploadDir + fileName);
                        Files.createDirectories(path.getParent());
                        Files.write(path, avatar.getBytes());

                        user.setImage("images/user/" + fileName);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/admin/dashboard";
                }
            }
            userRepository.save(user);
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/account_management")
    public String accountManagement(Model model) {
        activePage = "account_management";
        model.addAttribute("activePage", activePage);

        List<User> adminUsers = userRepository.findByRole("admin");
        model.addAttribute("adminUsers", adminUsers);

        return "account_management/index";
    }

    @PostMapping("/account_management/add_account")
    public String addAccount(
            @RequestParam("fullname") String fullname,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {

        User newUser = new User();
        newUser.setFullname(fullname);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
        newUser.setRole("admin");
        newUser.setImage("images/user/avatar.png");

        userRepository.save(newUser);

        return "redirect:/admin/account_management";
    }

    @PostMapping("/account_management/delete/{id}")
    public String deleteAccount(@PathVariable("id") Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        }
        return "redirect:/admin/account_management";
    }

    @GetMapping("/account_management/add_account")
    public String addAccount(Model model) {
        activePage = "account_management";
        model.addAttribute("activePage", activePage);
        return "account_management/add_account";
    }

    @GetMapping("/articles_management")
    public String articlesManagement(Model model) {
        activePage = "articles_management";
        model.addAttribute("activePage", activePage);

        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for (Article article : articles) {
            Category category = categoryRepository.findById(article.getCategoryId()).orElse(null);
            String categoryName = category != null ? category.getCategoryName() : "Unknown";

            ArticleDTO articleDTO = new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);

            articleDTOList.add(articleDTO);
        }
        model.addAttribute("articles", articleDTOList);

        return "articles_management/index";
    }

    @GetMapping("/articles_management/detail_news")
    public String viewDetail(@RequestParam("id") Integer id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            Category category = categoryRepository.findById(article.getCategoryId()).orElse(null);
            String categoryName = category != null ? category.getCategoryName() : "Unknown";

            ArticleDTO articleDTO = new ArticleDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getImage(),
                    article.getContent(),
                    article.getCreateAt().toString(),
                    categoryName);

            model.addAttribute("article", articleDTO);
            return "articles_management/detail_news";
        } else {
            return "articles_management/index";
        }
    }

    @GetMapping("/articles_management/add_news")
    public String addArticles(Model model) {
        activePage = "articles_management";
        model.addAttribute("activePage", activePage);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "articles_management/add_news";
    }

    @PostMapping("/articles_management/add_news")
    public String addArticle(@RequestParam("title") String title,
            @RequestParam("category") Integer categoryId,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            Model model) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();

            Article article = new Article();
            article.setTitle(title);
            article.setCategoryId(category.getId());
            article.setContent(description);
            article.setCreateAt(new java.sql.Date(System.currentTimeMillis()));

            if (image != null && !image.isEmpty()) {
                try {
                    String projectDir = System.getProperty("user.dir");
                    String uploadDir = projectDir + "/src/main/resources/static/images/article/";

                    String originalFilename = image.getOriginalFilename();
                    if (originalFilename != null && !originalFilename.isEmpty()) {
                        String fileName = originalFilename;

                        Path path = Paths.get(uploadDir + fileName);
                        Files.createDirectories(path.getParent());
                        Files.write(path, image.getBytes());

                        article.setImage("images/article/" + fileName);
                    }

                } catch (IOException e) {
                    return "articles_management/add_news";
                }
            }

            articleRepository.save(article);
        }
        return "redirect:/admin/articles_management";
    }

    @GetMapping("/articles_management/edit_news/{id}")
    public String showEditArticleForm(@PathVariable("id") Integer id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            model.addAttribute("article", article);

            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);

            activePage = "articles_management";
            model.addAttribute("activePage", activePage);

            return "articles_management/edit_news";
        } else {
            return "articles_management/index";
        }
    }

    @PostMapping("/articles_management/edit_news/{id}")
    public String updateArticle(@PathVariable("id") Integer id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String categoryName,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Model model) {

        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setTitle(title);
            article.setContent(description);

            Category category = categoryRepository.findByCategoryName(categoryName);
            if (category != null) {
                article.setCategoryId(category.getId());
            }
            if (image != null && !image.isEmpty()) {
                try {
                    String projectDir = System.getProperty("user.dir");
                    String uploadDir = projectDir + "/src/main/resources/static/images/article/";

                    String originalFilename = image.getOriginalFilename();
                    if (originalFilename != null && !originalFilename.isEmpty()) {
                        String fileName = originalFilename;

                        Path path = Paths.get(uploadDir + fileName);
                        Files.createDirectories(path.getParent());
                        Files.write(path, image.getBytes());

                        article.setImage("images/article/" + fileName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("error", "Failed to upload image.");
                    return "articles_management/index";
                }
            }

            articleRepository.save(article);
            return "redirect:/admin/articles_management";
        }

        model.addAttribute("error", "Article not found");
        return "articles_management/index";
    }

    @PostMapping("/articles_management/delete/{id}")
    public String deleteArticle(@PathVariable("id") Integer id) {
        articleRepository.deleteById(id);
        return "redirect:/admin/articles_management";
    }

    @GetMapping("/category_management")
    public String categoryManagement(Model model) {
        activePage = "category_management";
        model.addAttribute("activePage", activePage);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "category_management/index";
    }

    @GetMapping("/category_management/add_category")
    public String addCategory(Model model) {
        activePage = "category_management";
        model.addAttribute("activePage", activePage);
        return "category_management/add_category";
    }

    @PostMapping("/category_management/add_category")
    public String saveCategory(@RequestParam("categoryName") String categoryName,
            @RequestParam("image") MultipartFile image,
            Model model) {

        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            try {
                String projectDir = System.getProperty("user.dir");
                String uploadDir = projectDir + "/src/main/resources/static/images/category/";

                String originalFilename = image.getOriginalFilename();
                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String fileName = originalFilename;

                    Path path = Paths.get(uploadDir + fileName);
                    Files.createDirectories(path.getParent());
                    Files.write(path, image.getBytes());

                    imagePath = "images/category/" + fileName;
                }

            } catch (IOException e) {
                return "category_management/add_category";
            }
        }

        Category newCategory = new Category(categoryName, imagePath, new java.sql.Date(System.currentTimeMillis()));
        categoryRepository.save(newCategory);

        return "redirect:/admin/category_management";
    }

    @GetMapping("/category_management/edit_category/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        activePage = "category_management";
        Category category = categoryRepository.findById(id).orElse(null);

        model.addAttribute("activePage", activePage);
        model.addAttribute("category", category);
        return "category_management/edit_category";
    }

    @PostMapping("/category_management/edit_category/{id}")
    public String updateCategory(@PathVariable("id") Integer id,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) {

        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Category not found");
            return "redirect:/admin/category_management";
        }

        category.setCategoryName(categoryName);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String projectDir = System.getProperty("user.dir");
                String uploadDir = projectDir + "/src/main/resources/static/images/category/";

                String originalFilename = imageFile.getOriginalFilename();
                if (originalFilename != null && !originalFilename.isEmpty()) {
                    String fileName = originalFilename;

                    Path path = Paths.get(uploadDir + fileName);
                    Files.createDirectories(path.getParent());
                    Files.write(path, imageFile.getBytes());

                    String imagePath = "images/category/" + fileName;
                    category.setImage(imagePath);
                }

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("category", category);
                model.addAttribute("error", "Failed to upload image");
                return "category_management/edit_category";
            }
        }

        categoryRepository.save(category);
        return "redirect:/admin/category_management";
    }

    @PostMapping("/category_management/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category_management";
    }
}
