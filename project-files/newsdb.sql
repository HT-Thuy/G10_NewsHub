-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2025 at 07:20 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `newsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `article`
--

CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `create_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `article`
--

INSERT INTO `article` (`id`, `category_id`, `title`, `image`, `content`, `create_at`) VALUES
(1, 1, 'Ex-UK cyber chief says asking Apple to break encryption was \'naive\'', 'images/article/NAI-1.webp', 'A former cyber security chief has called the UK government “naive” for demanding that Apple add a backdoor to its software – allowing the UK’s intelligence agencies to snoop on customers’ data – and expecting the request to remain secret.\r\nCiaran Martin was head of cyber security at the UK’s Government Communications Headquarters (GCHQ) and served as the first chief executive of the National Cyber Security Centre (NCSC) before joining the University of Oxford in 2020. He spoke to New Scientist about reports that the UK government has made an unprecedented demand for Apple to grant it access to data stored by any customer, anywhere in the world, even if it is encrypted.', '2025-03-27'),
(2, 2, 'Find out how our minds and bodies are inextricably linked', 'images/article/NAI-2.webp', 'EARLY BIRD DISCOUNT ENDS ON SUNDAY 6 APRIL\r\nScience is more aware than ever of the myriad complex links between our physical and the mental worlds; from the strange effects of placebos and nocebos, to the explosion of research into interoception (how the brain perceives the body).\r\nAt the heart of these discussions is the mystery of consciousness – how can a physical object like a brain be aware of its own existence?\r\nThere’s also the Enteric nervous system, a mesh-like system of neurons that governs the function of the gastrointestinal tract, often nicknamed our ‘second brain’.\r\nJoin six world-leading experts to discover how our minds and bodies are inextricably linked, and how leading-edge neuroscience is revealing what this means for our future health and wellbeing.\r\nTALK: What is interoception? \r\nJennifer Murphy, Senior Lecturer in Psychology, University of Surrey\r\nExplore the complexities of interoception including how we perceive our body’s internal signals and the challenges of measuring it, while revealing its important connections to health and cognitive function.\r\nInteroception is described as the perception of the internal state of one’s body and includes sensations such as cardiac or respiratory signals. Despite great interest in interoception, there remains uncertainty regarding the measurement and conceptualization of interoception, as well as its relationship with health and higher order cognition. This talk will provide an overview of work that has scrutinised the measurement of interoception and sought to develop solutions to the challenges of interoception measurement that may be used to understand the relevance of interoception for health and cognition. Together these findings suggest that interoception is a multifaceted construct with relevance for health and cognition, though further work is required to adequately measure individual differences in interoceptive ability. \r\nTALK: How the human brain thinks about itself \r\nStephen Fleming, Professor of Cognitive Neuroscience, University College London\r\nDiscover how the brain’s ability to reflect on its own thinking shapes our success, with major implications for mental health, education, and AI.\r\nThe human brain has a remarkable ability to monitor and evaluate its own thinking, known as metacognition. Metacognition is crucial to success, enabling us to recognise gaps in our knowledge and collaborate effectively. Problems with metacognition are linked to maladaptive behaviours, such as endorsing false beliefs or being unaware of our own limitations. Stephen will explore the tools we can use to isolate how this extraordinary capacity for self-reflection is supported by the functions of the human brain. By combining mathematical models of human behaviour with cutting-edge brain imaging techniques, Stephen and his team are discovering the building blocks of metacognition, and asking how these pieces come together to support a rich awareness of own skills and capabilities. This work is uncovering the neurobiology of a core aspect of what makes us human, with wide-ranging implications for mental health, education and AI.\r\nTALK: What the science of consciousness can teach us about our place in the universe\r\nAdam Barrett, Deputy Director, Sussex Centre for Consciousness Science, University of Sussex\r\nFind out how panpsychism and eternalism reshape our understanding of consciousness and our place in the universe\r\nIn this talk, Adam explains two major insights that science provides about our place in the universe. The first of these is ‘panpsychism’ – consciousness is a fundamental property of matter, and we’re all in some sense part of the same being. After arguing for this, there follows a discussion on where we’re at in understanding what is the fundamental physical substrate of consciousness, via an accessible account of the much-misunderstood integrated information theory. The second insight is ‘eternalism’ – that past, present and future exist equally. It is explained how this follows from Einstein’s theory of relativity. The talk concludes with a discussion on how these two insights can provide spiritual comfort, with knock-on benefits for mental health and society.', '2025-03-31'),
(3, 4, 'MMA fighters to compete at famous Angel\'s Fighting Championship', 'images/article/NAI-3.jpg', 'HÀ NỘI — International mixed martial art fighters will compete at the Angel\'s Fighting Championship 25 (AFC 25) at The Grand Ho Tram Strip in Bà Rịa - Vũng Tàu Province on June 10.\nThere will be six elite matches between competitors from Brazil, South Korea and Việt Nam.\n\nAmong them, the most anticipated bout will see Brazilian jujitsu champion Robson de Olivera Soares takes on Filipino Rene Catalan Jr in the flyweight (56kg) category.\nSoares is a three-time national champion. He has made a name for himself on the international stage with his impressive jiu-jitsu skills. The black belt became famous after a controversial defeat to Vietnamese champion Trần Ngọc Lượng in the Lion Championship 5 on April 22 in HCM City. \nSoares was a better fighter on the night but lost to a judges\' decision who then were fined because of wrong decisions. A rematch between the two is planned in the near future..\n“This match is in my schedule.\n Personally, I love to be challenged. Challenges and being able to compete make me feel alive. I don\'t care about the past. I need to let it go and move on,” said Soares.\nCatalan will be a big obstacle for Soares. The Filipino has won nine out of 10 matches.\n“Rene Catalan is a powerful rival who is considered stronger than me,\" Soares added.\n\"I have trained hard to prepare for this match. I have received a remarkable assistance from my teammates and coaches. I believe that the match will be great, a match of lions.\"\nAnother key match of the AFC25 is between Phan Huy Hoàng of Việt Nam and Pablo Eduardo from Brazil in the bantamweight (61kg) class.\nEduardo has recorded a series of three wins in a row with his excellent Brazil jujitsu (BJJ) skills which helped him make an earthquake win over Uzbek Norov Azizbek by submission at the AFC 23 in March.\nHoàng\'s best result was the semi-final round of the Lion Championship 2022. He took two knock-out wins on the way to finishing in the top four of the tournament.\n“I have prepared both striking and grappling techniques for this high-quality event. My experience at the professional Lion Championship will help me in this match,\" said Hoàng.\n\"My coaching board and I watched several of Eduardo\'s videos. We all agreed that he was strong with submission, wrestling and grappling. I have to find a way to avoid his wrestling technique while making use of my striking skills. Currently, I am in good condition and flexibility ahead of the bout.”\nOther matches will be between South Korean Han Gil Choi and Brazilian Felipe Negochadle in the bantamweight (61kg); Nosherwan Khanzada from Hong Kong and Jang Bum Seok of South Korea in the light heavyweight (90kg); Rodrigo Caporal of Brazil and Burenzorig Batmunkh from Mongolian in the welterweight (77kg) and Mhar John Manahan of the Philippines and Kim Eui Jong of South Korea in the lightweight (70kg).\nAFC 25 is organised by Cocky Buffalo, a leading gym in Việt Nam in managing and training fighters and organising tournaments, and AFC, a South Korean mixed martial arts promotion. All matches will be broadcast live and executively on VTVcab channels, apps and platforms. VNS', '2025-04-01'),
(4, 3, 'The Police aiming for a come back to qualify regional championship ', 'images/article/NAI-4.png', 'Coach Mano Polking said his Hà Nội Police side would make a strong comeback against PSM Makassar to advance to the final of the ASEAN Club Championship Shopee Cup.\r\nHÀ NỘI — Coach Mano Polking said his Hà Nội Police side will be fighting back against PSM Makassar to advance to the final of the ASEAN Club Championship Shopee Cup.\r\nYuran Fernandes’ header secured a 1-0 win for PSM Makassar of Indonesia to help the hosts hold a slim advantage over Hà Nội Police of Việt Nam after the April 2 keenly contested first leg clash in South Sulawesi.\r\nPSM Makassar shaded their match against Hà Nội Police, after the visitors squandered the opportunity to take a 15th minute lead at the Gelora B.J. Habibie Stadium.\r\nPSM Makassar captain Fernandes upended his counterpart Nguyễn Quang Hải of Hà Nội Police in the penalty area, but goalkeeper Reza Arya pushed Alan Grafite’s spot-kick onto his left post to keep the scores level.\r\nThere was nothing to separate the teams until the 80th minute when Fernandes was given the time and space in the area to meet Victor Luiz’s corner with a header that the central defender powered beyond Filip Nguyễn.\r\n“It’s just the first match, we still have the second match in Việt Nam” said Fernandes. “I’m very happy to help the team with the goal and now we focus on the second game. We have to do the same as we did today, try to win.”\r\nSpeaking to reporters after the match Polking said it was a good game played in a great atmosphere. Both teams played positive and dedicated football.\r\n\"The Police were better in the first half. Alan missed a penalty after we created many opportunities. It is pity that we couldn\'t take advantage of it to score but that\'s football. When you attack a lot but can\'t convert your chances, you will be punished.\r\n\"PSM Makassar played better in the second half. They were patient and wait for the opposing side to make a mistake and they deserved to have that goal. However, overall, the Police were still the dominant team in terms of statistics.\r\n\"Regarding the goal, it is disappointing after our instructions to players prior to the match, as PSM Makassar are always very strong in set pieces.\r\n\"In the second leg, we need to score more goals and improve our finishing. We will have passionate fans on our side. We will leave this loss behind and focus on the match at home and I believe we can turn the situation around. One goal is not too big a gap,\" he said.', '2025-04-03'),
(5, 1, 'Global Technology Outage Caused by Failed Software Update Shuts Down Flights', 'images/article/NAI-5.webp', 'Multiple major U.S. airlines — including American, Delta and United — grounded their flights on the morning of Friday, July 19 amid a global technical outage.\nThe massive, worldwide IT disruption is affecting Microsoft Windows and all computer systems that use it, leaving those using the technology with what social media has long dubbed the dreaded \"blue screen of death,\" reported multiple outlets including NBC News, CNN and BBC News.\nIt\'s said to not stem from a cyberattack but a bug in an update by American cybersecurity firm CrowdStrike, one of the world\'s largest providers whose software is used by scores of global industries, the outlets reported.\nThe downage has affected far more than airlines. It\'s wreaked havoc worldwide in a number of other industries including banks, stock exchanges, police and fire stations, hospitals, news broadcasters, and other public transit systems worldwide like trains and busses, according to multiple outlets. Other businesses have been hit too, from major Fortune 500 companies to local coffee shops using mobile ordering.\nEven those using a Microsoft Windows operating system on their personal PCs have been reporting problems, PEOPLE has confirmed.\nMicrosoft didn\'t immediately respond when contacted by PEOPLE but issued a public statement Friday morning. \"We acknowledge how impactful this is for our customers, and we\'re working to still receiving obstructions as soon as possible,\" the company said.\nCrowdStrike noted they have identified the issue and that a fix has been deployed but is slowly taking into effect.\n\"The system was sent an update and that update had a software bug in it and caused an issue with the Microsoft operating system. And we identified this very quickly and remediated the issue. As systems come back online and they\'re rebooted, they\'re working,\" George Kurtz, the founder and CEO of CrowdStrike, added in a live interview on the Today show.\nIrish low-cost airline Ryanair added in a message shared on X (formerly known as Twitter), \"We’re currently experiencing disruption across the network due to a Global 3rd party IT outage which is out of our control.\"\nPer a message shared on the Melbourne Airport Facebook page, Jetstar Airways had also been \"experiencing a significant outage,\" while Qantas and Virgin were said to be \"slowly processing passengers.\"\nA statement on the Azure cloud software status report site read on the morning of July 19, \"We\'ve been made aware of an issue impacting Virtual Machines running Windows, running the CrowdStrike Falcon agent, which may encounter a bug check (BSOD) and get stuck in a restarting state. We\'re aware of this issue and are currently investigating potential options Azure customers can take for mitigation.\"', '2025-04-05'),
(16, 4, 'Daily Skimm: NBA Protest, RNC Night Three', 'images/article/NAI-6.webp', 'Solidarity\r\nThe Story\r\nMajor sports leagues are throwing their weight behind the fight for racial justice.\r\n\r\nTell me more.\r\nYesterday, the Milwaukee Bucks boycotted an NBA playoff game in protest of the police shooting of Jacob Blake – which occurred in their home state, and is now under a federal civil rights investigation. Earlier this week, Blake (who was reportedly trying to break up a fight) was shot in the back seven times by an officer later identified as Rusten Sheskey in Kenosha, WI. Investigators said officers tried using a taser first, but it failed. Now, Blake is in the hospital, paralyzed from the waist down. Cellphone video of the shooting was released and reignited protests across the country calling for justice, respect, equality, humanity, agency (the list goes on) for Black lives.\r\n\r\nSo the Bucks said \'enough\'?\r\nYep. Yesterday, they were scheduled to play Game 5 in the playoffs against the Orlando Magic. But they never came out on the court. Instead, they called for justice, demanded that the \"officers be held accountable,\" and denounced Wisconsin lawmakers for \"months of inaction\" on police reform. The team\'s owners said they weren\'t aware of the boycott beforehand, but supported the decision. Then the momentum spread. The NBA and the National Basketball Players Association postponed all other playoff games scheduled yesterday. Then other leagues took notice. The WNBA and some MLB teams joined in the boycott – sitting out their games in solidarity and calling for change.\r\n\r\nDare I say...a movement?\r\nYou may. But activism in the leagues is not new. To date, the NBA and WNBA have been more supportive of its players taking part. (See: the WNBA working with players to dedicate this season to social justice.) Then...there\'s the NFL – which was vocally against Colin Kaepernick\'s 2016 #TakeAKnee movement and subsequently banned players from kneeling during the national anthem. The league has since admitted it was wrong for not listening to its players.\r\n\r\nSo will this boycott lead to change?\r\nUnclear. But it\'s helping the issue get even more attention. Meanwhile, protests continued for a fourth night in Kenosha, WI. The city is still reeling after two protesters were shot and killed on Tuesday night. The suspect: 17-year-old Kyle Rittenhouse – who apparently considered himself a member of a militia. Yesterday, authorities arrested and charged him with first-degree intentional homicide after he\'d fled Wisconsin to avoid the charges. Rittenhouse\'s being held without bond and a hearing tomorrow will determine if he\'ll be sent back to Wisconsin.\r\n\r\ntheSkimm\r\nAugust 26, 2016: The day Colin Kaepernick sat for the national anthem in a peaceful protest against racial inequality. Exactly four years later, these players are uniting to say \'we need change more than we need sports.\'', '2025-04-13'),
(17, 3, 'Recycling and Composting, Skimm\'d', 'images/article/NAI-8.webp', 'Skimm\'d by Jane Ackermann, Carly Mallenbaum and Becky Murray.\r\nThe StoryYou’ve heard that recycling and composting are good for the environment. But you might need some advice on how to be a better eco friend. \r\n\r\nLet’s start with the basics.Recycling is when something that could be thrown away is instead collected and turned into something new. If you count creatively reusing products as recycling, then people have been doing it for hundreds of years. At first, it was mostly done because new materials were scarce. Recycling then became a major part of the environmental movement in the 20th century, when landfills were filling up. Waste needed to be managed, and in 1970, the EPA (which now regulates recycling) and Earth Day were created. In 2018 (the latest EPA data available), 69 million tons of garbage was recycled out of about 292 million tons of total waste (that’s 4.9 pounds of trash per person per day...ew). There aren\'t any federal recycling laws, so state and local govs are responsible for it (see: plastic bag fees and plastic straw bans). Thing to know: several states are considering laws that would have the burden (see: cost) of waste management fall on manufacturers. \r\n\r\nHow do I recycle? One study estimates that about half of Americans have access to curbside recycling. That’s when you bring recyclables to the curb to be picked up and dropped off at sorting facilities. You can also bring stuff to a drop-off center. In some states, some bottles and/or cans can be taken to a redemption center, sometimes in exchange for a few cents per container. Search the Earth911 database for info on ways to recycle near you. \r\n\r\nWhat gets recycled?That’s complicated. But typically a curbside program will accept paper, flattened cardboard boxes, cartons, aluminum cans, glass bottles, and plastics that are clean and dry and have the numbers 1 or 2 and sometimes 5 on them. Heads up: numbers 3, 4, 6, and 7 typically can’t be recycled through curbside pickup, even if they have those arrows we associate with recycling. Eye roll. Styrofoam and egg cartons could also go either way so check with your recycling center. Pizza boxes can be fine unless they’re greasy and cheesy. Rip them in half and recycle only the clean top part in order to avoid contaminating what else is in the bin. Typically dirty napkins/paper towels (which are sometimes compostable) and mirror glass don’t pass go. And to make things even more confusing, you’ll probably need to separately sort and drop off things like plastic bags and film (use this link or this one to find a location), ink cartridges (some spots are listed here) and batteries (search for local collectors here).\r\n\r\nI’m taking notes. Anything else?China used to take a lot of America’s recycling. But they announced restrictions on what waste they’d import in 2017. Since then, the price tag to recycle in the U.S. has gone way up, and some local govs have had their recycling incinerated or sent to landfills to save money. But most people agree that when it’s done right, recycling is clean and green. That’s because it reduces the amount of landfill material and carbon emissions. However, if you lose yourself and toss your Starbucks cup into the wrong recycling bin, there are consequences. Incorrectly recycled items can contaminate otherwise fine recyclables and can damage sorting machines. According to one estimate, 25% of what’s put in recycling bins shouldn’t be. And that number has probably gotten worse in the pandemic. When you want to be a good recycler in theory, but produce extra waste instead, that’s called wish-cycling. We wish we didn’t have to tell you that. \r\n\r\nNow explain composting.Compost, Aka black gold, is decayed matter, often a combo of food scraps and yard waste, that can be added to soil. People have used organic material for farming since at least the Stone Age, through the American Revolution (George Washington was a known composter) until today. Now, a number of individuals, home gardeners, cities with municipal operations, and private facilities compost. In 2018, nearly 25 million tons of material was composted. But the EPA estimates about 3x that could have been. ', '2025-04-13');

-- --------------------------------------------------------

--
-- Table structure for table `bookmark`
--

CREATE TABLE `bookmark` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `create_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookmark`
--

INSERT INTO `bookmark` (`id`, `user_id`, `article_id`, `create_at`) VALUES
(11, 3, 5, '2025-04-09'),
(17, 4, 3, '2025-04-10'),
(26, 4, 4, '2025-04-11'),
(33, 3, 3, '2025-04-12'),
(42, 16, 3, '2025-04-14');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `create_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `category_name`, `image`, `create_at`) VALUES
(1, 'Technology', 'images/category/technology.jpg', '2025-04-01'),
(2, 'Education', 'images/category/education.jpg', '2025-04-01'),
(3, 'Environment', 'images/category/environment.jpg', '2025-04-04'),
(4, 'Sports', 'images/category/sports.jpg', '2025-04-02');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `create_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `article_id`, `user_id`, `content`, `create_at`) VALUES
(1, 2, 4, 'This article is really helpful', '2025-04-01'),
(2, 2, 3, 'I agree', '2025-04-01'),
(3, 1, 4, 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', '2025-04-01'),
(4, 3, 3, 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', '2025-04-02'),
(12, 5, 3, '66', '2025-04-11'),
(13, 3, 3, '000', '2025-04-11'),
(14, 1, 4, '1', '2025-04-11'),
(15, 4, 4, '2222', '2025-04-11'),
(16, 1, 16, '2', '2025-04-11'),
(17, 1, 16, 't', '2025-04-12'),
(18, 1, 3, '2', '2025-04-12'),
(20, 17, 16, '1', '2025-04-14'),
(21, 16, 16, '2', '2025-04-14'),
(22, 5, 16, '2', '2025-04-15');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `role` varchar(50) NOT NULL,
  `create_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `fullname`, `email`, `phone`, `password`, `image`, `role`, `create_at`) VALUES
(3, 'john', 'John Doeha', 'john@example.com', '12322222558', '$2a$10$FZSLpBWiHgc39CnT6ozDme/nm/alEwuPvxDGyv2JFlySEeMwa/9Cu', 'images/user/user_3_1744449905612.jpg', 'user', '2025-04-03'),
(4, 'mary', 'Mary Jane', 'mary@example.com', '123456789', '$2a$10$IJKG4dtZUYqll/I3IqDSvOLRkjYIGzP6fTNu51OV94wxbb0pOYc3a', 'images/user/avatar.png', 'user', '2025-04-03'),
(6, 'phillips', NULL, 'phillips@gmail.com', NULL, '$2y$10$Jd3k1WCXJwWBBxdfHB7Fnu0r4vC2VOLNu2K8SEIeVPk3LzFFX/xaS', 'images/user/avatar.png', 'user', '2025-04-06'),
(16, 'tuananh', 'tuan anh', '64cntt.ttanh@aptech.vn', '0966436204', '$2a$10$eMsg2J0RfgtPkuKQCBuLT.kmmWuih/C6Az87GDadPthyAYN2LjM4u', 'images/user/user_16_1744398102215.jpg', 'user', '2025-04-10'),
(19, 'admin', 'TDA', 'admin@gmail.com', '0123456789', '$2a$10$A.lLjsvACeuQvAYfbnR45uFequ23SOwehNwwfqqefWH5tEaVVJLSO', 'images/user/user.png', 'admin', '2025-04-13'),
(20, 'admin1', 'TTA', 'admin1@gmail.com', NULL, '$2a$10$Wp6KrT4LhXpgc3mCwc5lA.ChbTFtkkpkxxG038FDdYfrDlyTzvEzi', 'images/user/avatar.png', 'admin', '2025-04-13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoryId` (`category_id`);

--
-- Indexes for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`user_id`),
  ADD KEY `articleId` (`article_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `categoryName` (`category_name`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `articleId` (`article_id`),
  ADD KEY `userId` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `article`
--
ALTER TABLE `article`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `bookmark`
--
ALTER TABLE `bookmark`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `article_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD CONSTRAINT `bookmark_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bookmark_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
