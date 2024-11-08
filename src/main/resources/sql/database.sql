-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: library_management
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `username` varchar(100) DEFAULT NULL,
    `password` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK
TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin`
VALUES (1, 'admin', 'admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `document`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document`
(
    `id`               varchar(255) NOT NULL,
    `title`            varchar(255) DEFAULT NULL,
    `author`           varchar(255) DEFAULT NULL,
    `category`         varchar(255) DEFAULT NULL,
    `image`            text,
    `quantity`         int          DEFAULT NULL,
    `link_description` varchar(300) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK
TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
INSERT INTO `document`
VALUES ('0URjAwAAQBAJ', 'Story of League of Legends', 'Atinon Book', 'Games & Activities',
        'http://books.google.com/books/content?id=0URjAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        10, 'http://books.google.com.vn/books?id=0URjAwAAQBAJ&dq=league+of+legend&hl=&source=gbs_api'),
       ('7gnJCQAAQBAJ', 'Cristiano Ronaldo', 'Guillem Balague', 'Sports & Recreation',
        'http://books.google.com/books/content?id=7gnJCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        6, 'https://play.google.com/store/books/details?id=7gnJCQAAQBAJ&source=gbs_api'),
       ('jvCCDwAAQBAJ', 'Lionel Messi', 'David Machajewski', 'Juvenile Nonfiction',
        'http://books.google.com/books/content?id=jvCCDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        4, 'http://books.google.com.vn/books?id=jvCCDwAAQBAJ&dq=messi&hl=&source=gbs_api'),
       ('NgXeDwAAQBAJ', 'Marvel Encyclopedia New Edition', 'Stephen Wiacek', 'Comics & Graphic Novels',
        'http://books.google.com/books/content?id=NgXeDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        5, 'http://books.google.com.vn/books?id=NgXeDwAAQBAJ&dq=Mavel&hl=&source=gbs_api'),
       ('QiJlCQAAQBAJ', 'Get Rich In Spite of Yourself Collection - An \"If You Can Count to Four...\" Reference',
        'Wallace D. Wattles', 'Business & Economics',
        'http://books.google.com/books/content?id=QiJlCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        4, 'http://books.google.com.vn/books?id=QiJlCQAAQBAJ&dq=get&hl=&source=gbs_api'),
       ('WaWWNrh67aUC', 'A History of the World Cup, 1930-2010', 'Clemente Angelo Lisi', 'Sports & Recreation',
        'http://books.google.com/books/content?id=WaWWNrh67aUC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        3, 'http://books.google.com.vn/books?id=WaWWNrh67aUC&dq=world+cup&hl=&source=gbs_api'),
       ('yVwLEQAAQBAJ', 'Lionel Messi', 'Leslie Holleran', 'Juvenile Nonfiction',
        'http://books.google.com/books/content?id=yVwLEQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api',
        15, 'https://play.google.com/store/books/details?id=yVwLEQAAQBAJ&source=gbs_api');
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `transaction`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `userId`     bigint       DEFAULT NULL,
    `documentId` varchar(255) DEFAULT NULL,
    `borrowDate` date         DEFAULT NULL,
    `returnDate` date         DEFAULT NULL,
    `status`     varchar(20)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY          `transaction_ibfk_1` (`userId`),
    KEY          `transaction_ibfk_2` (`documentId`),
    CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`documentId`) REFERENCES `document` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK
TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction`
VALUES (8, 11, 'jvCCDwAAQBAJ', '2024-09-29', NULL, 'borrowed'),
       (9, 11, '7gnJCQAAQBAJ', '2024-10-08', '2024-10-29', 'returned'),
       (10, 11, 'WaWWNrh67aUC', '2024-10-11', '2024-11-11', 'returned');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `firstName`   varchar(100) NOT NULL,
    `lastName`    varchar(100) NOT NULL,
    `dateOfBirth` date         NOT NULL,
    `gender`      varchar(10)  NOT NULL,
    `image`       varchar(250) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK
TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user`
VALUES (4, 'Boby', 'Williams', '1991-07-19', 'Female', 'image/user/anh-avatar-facebook-38.jpg'),
       (5, 'Charlie', 'Brown', '1987-12-03', 'Female', 'image/user/anh-avatar-facebook-39.jpg'),
       (6, 'Emily', 'Davis', '1993-05-30', 'Female', 'image/user/anh-avatar-facebook-40.jpg'),
       (7, 'Michael', 'Wilson', '1989-08-21', 'Male', 'image/user/anh-avatar-facebook-41.jpg'),
       (8, 'Sara', 'Taylor', '1990-11-11', 'Female', 'image/user/anh-avatar-facebook-42.jpg'),
       (9, 'David', 'Anderson', '1986-02-14', 'Male', 'image/user/anh-avatar-facebook-43.jpg'),
       (10, 'Sophia', 'Moore', '1992-09-20', 'Female', 'image/user/anh-avatar-facebook-44.jpg'),
       (11, 'Chris', 'Lee', '1991-04-17', 'Male', 'image/user/anh-avatar-facebook-45.jpg'),
       (12, 'Emma', 'Thomas', '1988-06-29', 'Female', 'image/user/anh-avatar-facebook-47.jpg'),
       (13, 'James', 'Martinez', '1984-10-05', 'Male', 'image/user/anh-avatar-facebook-48.jpg'),
       (14, 'Isabella', 'Rodriguez', '1994-01-09', 'Female', 'image/user/anh-avatar-facebook-49.jpg'),
       (15, 'Oliver', 'Hernandez', '1989-12-12', 'Male', 'image/user/anh-avatar-facebook-50.jpg'),
       (16, 'Mia', 'Lopez', '1990-07-18', 'Female', 'image/user/anh-avatar-facebook-51.jpg'),
       (17, 'Ethan', 'Gonzalez', '1985-03-24', 'Male', 'image/user/anh-avatar-facebook-52.jpg'),
       (18, 'Ava', 'Perez', '1991-09-06', 'Female', 'image/user/anh-avatar-facebook-53.jpg'),
       (19, 'William', 'Clark', '1987-04-13', 'Male', 'image/user/anh-avatar-facebook-54.jpg'),
       (21, 'Hi', 'Ha', '2024-10-01', 'Male', 'image/user/school_management.png'),
       (22, 'Cris', 'Ronaldo', '2024-09-30', 'Male', 'image/user/image4.jpg'),
       (23, 'Neymar', 'Jr', '2024-10-16', 'Male', 'image/user/image5.jpg'),
       (24, 'Joker', 'Joker', '2024-10-15', 'Male', 'image/user/tải xuống.jpg'),
       (25, 'Abc', 'abc', '2024-10-08', 'Female', 'image/user/image9.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Dumping routines for database 'library_management'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-08 17:34:58
