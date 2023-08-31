-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema university
-- -----------------------------------------------------
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Campus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Campus` (
  `CampusID` INT NOT NULL,
  `Name` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  PRIMARY KEY (`CampusID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Research and Innovation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Research and Innovation` (
  `ResearchID` INT NOT NULL,
  `Research Name` VARCHAR(45) NULL,
  `Research Description` VARCHAR(45) NULL,
  `Research Lead` VARCHAR(45) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`ResearchID`),
  INDEX `fk_Research and Innovation_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_Research and Innovation_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Faculty` (
  `FacultyID` INT NOT NULL,
  `FacultyName` VARCHAR(45) NULL,
  `FacultyOffice` VARCHAR(45) NULL,
  `FacultyDean` VARCHAR(45) NULL,
  `FacultyEmail` VARCHAR(45) NULL,
  `FacultyPhone` INT(10) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`FacultyID`),
  INDEX `fk_Faculty_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_Faculty_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Student` (
  `StudentID` INT NOT NULL,
  `email` VARCHAR(45) NULL,
  `First Name` VARCHAR(45) NULL,
  `Middle Name` VARCHAR(45) NULL,
  `Last Names` VARCHAR(45) NULL,
  `Phone` INT(10) NULL,
  `Address` VARCHAR(45) NULL,
  `Faculty_FacultyID` INT NOT NULL,
  PRIMARY KEY (`StudentID`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_Student_Faculty1_idx` (`Faculty_FacultyID` ASC) VISIBLE,
  CONSTRAINT `fk_Student_Faculty1`
    FOREIGN KEY (`Faculty_FacultyID`)
    REFERENCES `mydb`.`Faculty` (`FacultyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Employee/Staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Employee/Staff` (
  `EmployeeID` INT NOT NULL,
  `First Name` VARCHAR(45) NULL,
  `Last Name` VARCHAR(45) NULL,
  `EmployeeEmail` VARCHAR(45) NULL,
  `Phone` INT(10) NULL,
  `Address` VARCHAR(45) NULL,
  `Faculty_FacultyID` INT NOT NULL,
  PRIMARY KEY (`EmployeeID`),
  UNIQUE INDEX `EmployeeEmail_UNIQUE` (`First Name` ASC) VISIBLE,
  INDEX `fk_Employee/Staff_Faculty1_idx` (`Faculty_FacultyID` ASC) VISIBLE,
  CONSTRAINT `fk_Employee/Staff_Faculty1`
    FOREIGN KEY (`Faculty_FacultyID`)
    REFERENCES `mydb`.`Faculty` (`FacultyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Library`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Library` (
  `BuildingNumber` INT NOT NULL,
  `BuildingName` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `Timings` VARCHAR(45) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`BuildingNumber`),
  INDEX `fk_Library_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_Library_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Accomodation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Accomodation` (
  `BuildingNo` INT NOT NULL,
  `BuildingName` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`BuildingNo`),
  INDEX `fk_Accomodation_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_Accomodation_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Clubs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Clubs` (
  `ClubID` INT NOT NULL,
  `ClubName` VARCHAR(45) NULL,
  `ClubType` VARCHAR(45) NULL,
  `ClubHead` VARCHAR(45) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`ClubID`),
  INDEX `fk_Clubs_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_Clubs_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Events`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Events` (
  `EventName` VARCHAR(45) NULL,
  `Event Description` VARCHAR(45) NULL,
  `Event Date` DATE NULL,
  `ClubID` INT NOT NULL,
  PRIMARY KEY (`ClubID`),
  CONSTRAINT `fk_Events_Clubs1`
    FOREIGN KEY (`ClubID`)
    REFERENCES `mydb`.`Clubs` (`ClubID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Courses` (
  `CourseID` INT NOT NULL,
  `CourseName` VARCHAR(45) NULL,
  `CourseDescription` VARCHAR(45) NULL,
  `Faculty_FacultyID` INT NOT NULL,
  PRIMARY KEY (`CourseID`),
  INDEX `fk_Courses_Faculty1_idx` (`Faculty_FacultyID` ASC) VISIBLE,
  CONSTRAINT `fk_Courses_Faculty1`
    FOREIGN KEY (`Faculty_FacultyID`)
    REFERENCES `mydb`.`Faculty` (`FacultyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Gym`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Gym` (
  `BuildingNo` INT NOT NULL,
  `BuildingName` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `Timings` VARCHAR(45) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`BuildingNo`),
  INDEX `fk_Gym_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_Gym_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Food Outlets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Food Outlets` (
  `RestaurantID` INT NOT NULL,
  `RestaurantName` VARCHAR(45) NULL,
  `Address` VARCHAR(45) NULL,
  `Timings` VARCHAR(45) NULL,
  `Campus_CampusID` INT NOT NULL,
  PRIMARY KEY (`RestaurantID`),
  INDEX `fk_FoodOptions_Campus1_idx` (`Campus_CampusID` ASC) VISIBLE,
  CONSTRAINT `fk_FoodOptions_Campus1`
    FOREIGN KEY (`Campus_CampusID`)
    REFERENCES `mydb`.`Campus` (`CampusID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Menu` (
  `Food Item` VARCHAR(45) NULL,
  `Price` INT(10) NULL,
  `RestaurantID` INT NOT NULL,
  PRIMARY KEY (`RestaurantID`),
  CONSTRAINT `fk_Menu_FoodOptions1`
    FOREIGN KEY (`RestaurantID`)
    REFERENCES `mydb`.`Food Outlets` (`RestaurantID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
