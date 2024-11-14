SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
create database if not exists javafxTest;
use javafxTest;


CREATE TABLE IF NOT EXISTS `roles` (
  `RoleID` int NOT NULL,
  `RoleName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `schedule` (
  `ScheduleID` int NOT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  PRIMARY KEY (`ScheduleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `training_sections` (
  `SectionID` int NOT NULL,
  `SectionName` varchar(100) DEFAULT NULL,
  `SectionDescription` varchar(255) DEFAULT NULL,
  `SectionExercises` varchar(150) DEFAULT NULL,
  `SectionPrice` decimal(19,2) DEFAULT NULL,
  `ScheduleID` int NOT NULL,
  PRIMARY KEY (`SectionID`),
  KEY `fk_Training_sections_Schedule1` (`ScheduleID`),
  CONSTRAINT `fk_Training_sections_Schedule1` FOREIGN KEY (`ScheduleID`) REFERENCES `schedule` (`ScheduleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `users` (
  `UserID` int NOT NULL,
  `UserName` varchar(100) DEFAULT NULL,
  `UserSurname` varchar(100) DEFAULT NULL,
  `UserPassport` varchar(100) DEFAULT NULL,
  `UserBorn` date DEFAULT NULL,
  `UserPassword` varchar(100) DEFAULT NULL,
  `UserLogin` varchar(100) DEFAULT NULL,
  `RoleID` int NOT NULL,
  PRIMARY KEY (`UserID`),
  KEY `fk_Users_Roles1` (`RoleID`),
  CONSTRAINT `fk_Users_Roles1` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`RoleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `subscription` (
  `SubscriptionID` int NOT NULL,
  `Price` int DEFAULT NULL,
  `UserID` int NOT NULL,
  PRIMARY KEY (`SubscriptionID`),
  KEY `fk_Subscription_Users1` (`UserID`),
  CONSTRAINT `fk_Subscription_Users1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `sections_subs` (
  `SubscriptionID` int NOT NULL,
  `SectionID` int NOT NULL,
  `Quantity` int DEFAULT NULL,
  PRIMARY KEY (`SubscriptionID`,`SectionID`),
  KEY `fk_Subscription_has_Training_sections_Training_sections1` (`SectionID`),
  CONSTRAINT `fk_Subscription_has_Training_sections_Subscription1` FOREIGN KEY (`SubscriptionID`) REFERENCES `subscription` (`SubscriptionID`),
  CONSTRAINT `fk_Subscription_has_Training_sections_Training_sections1` FOREIGN KEY (`SectionID`) REFERENCES `training_sections` (`SectionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles` VALUES (1,'Клиент'),(2,'Сотрудник');
INSERT INTO `schedule` VALUES (1,'2023-12-10','12:00:00'),(2,'2024-01-01','08:00:00'),(3,'2024-01-02','11:00:00'),(4,'2024-01-02','13:00:00'),(5,'2024-01-03','09:01:00'),(6,'2023-11-05','13:05:00'),(7,'2023-11-03','10:03:00'),(8,'2023-11-05','11:04:00'),(9,'2023-12-03','11:03:00');
INSERT INTO `training_sections` VALUES (1,'Силовая','Направления, которые прокачивают силу, мышечную массу и наращивают рельеф.','Жим лежа, присед со штангой, становая тяга.',300.00,1),(2,'Аэробная','Выполнение различных движений под музыку в группе.','Тай-бо, тренировки на батуте или степ-платформе',1000.00,2),(3,'Оздоровительная','Упражнения с весом собственного тела подходят людям с проблемами позвоночника и суставов.','Пилатес, гимнастика, шейпинг, фитбол.',400.00,3),(4,'Танцевальная','На тренировках можно не только проработать осанку, пластичность и гибкость, но и поддерживать физическую форму.','Зумба, поул-дэнс, стрип-пластика, танец живота и боди-балет.',500.00,4);
INSERT INTO `users` VALUES (1,'Иван','Коверигин','2220-029191','2005-10-07','123','Madl',2),(2,'Матвей','Дубков','2220-032561','2005-02-03','Zerf','Zerf',1);
INSERT INTO `subscription` VALUES (1,300,2),(2,1500,2);
INSERT INTO `sections_subs` VALUES (1,1,1),(2,1,5);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

