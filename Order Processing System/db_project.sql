-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema book_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema book_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `book_store` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `book_store` ;

-- -----------------------------------------------------
-- Table `book_store`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`publisher` (
  `Name` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(60) NULL DEFAULT NULL,
  `Phone_Number` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`Name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`book` (
  `ISBN` INT NOT NULL,
  `Title` VARCHAR(45) NOT NULL,
  `Publication_year` YEAR NOT NULL,
  `selling_price` DECIMAL(10,4) NOT NULL,
  `category` ENUM('Science', 'Art', 'Religion', 'History', 'Geography') NOT NULL,
  `quantity` INT NOT NULL,
  `Minimum_quantity` INT NOT NULL,
  `publisher_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ISBN`),
  UNIQUE INDEX `Title_UNIQUE` (`Title` ASC) VISIBLE,
  INDEX `publisher of book_idx` (`publisher_name` ASC) INVISIBLE,
  INDEX `year_idx` (`Publication_year` ASC) INVISIBLE,
  INDEX `category_idx` (`category` ASC) INVISIBLE,
  INDEX `price_idx` (`selling_price` ASC) VISIBLE,
  CONSTRAINT `publisher of book`
    FOREIGN KEY (`publisher_name`)
    REFERENCES `book_store`.`publisher` (`Name`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`book_author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`book_author` (
  `ISBN` INT NOT NULL,
  `author_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ISBN`, `author_name`),
  CONSTRAINT `book fk`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`book_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`book_order` (
  `ISBN` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`ISBN`),
  CONSTRAINT `book of the order`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`user` (
  `user_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `first_name` VARCHAR(30) NOT NULL,
  `phone_number` VARCHAR(15) NOT NULL,
  `shipping_address` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE INDEX `e-mail_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`manager`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`manager` (
  `user_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_name`),
  CONSTRAINT `manager fk`
    FOREIGN KEY (`user_name`)
    REFERENCES `book_store`.`user` (`user_name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`sale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`sale` (
  `ISBN` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`ISBN`, `username`, `Date`),
  INDEX `buyer_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `buyer`
    FOREIGN KEY (`username`)
    REFERENCES `book_store`.`user` (`user_name`)
    ON UPDATE CASCADE,
  CONSTRAINT `sold book`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `book_store`.`shopping_cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`shopping_cart` (
  `ISBN` INT NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`ISBN`, `user_name`),
  INDEX `shopping cart holder_idx` (`user_name` ASC) VISIBLE,
  CONSTRAINT `book in shopping cart`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`),
  CONSTRAINT `shopping cart holder`
    FOREIGN KEY (`user_name`)
    REFERENCES `book_store`.`user` (`user_name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `book_store` ;

-- -----------------------------------------------------
-- procedure add_new_user
-- -----------------------------------------------------

DELIMITER $$
USE `book_store`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_new_user`(in user_name varchar(45),in mail varchar(255),in pass_word varchar(30),in last_name varchar(30),in first_name varchar(30),in phone_number varchar(15),in shipping_address varchar(255),in is_manager int)
BEGIN
	start transaction;
	insert into user values (user_name,mail,md5(pass_word),last_name,first_name,phone_number,shipping_address);
    if(is_manager = 1) then
		insert into manager values (user_name);
    end if;
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure modify_user
-- -----------------------------------------------------

DELIMITER $$
USE `book_store`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `modify_user`(in new_user_name varchar(45),in new_email varchar(255),in new_pass_word varchar(30),in new_last_name varchar(30),in new_first_name varchar(30),in new_phone_number varchar(15),in new_shipping_address varchar(255),in is_manager int,in old_username varchar(45))
BEGIN
	start transaction;
	update user set user_name = new_user_name,email = new_email,password = md5(new_pass_word),last_name = new_last_name,first_name = new_first_name,phone_number = new_phone_number,shipping_address = new_shipping_address
    where user_name = old_username;
    if(is_manager = 1 and (select count(*) from manager where user_name = new_user_name) = 0) then
		insert into manager values (new_user_name);
    end if;
    commit;
END$$

DELIMITER ;
USE `book_store`;

DELIMITER $$
USE `book_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `book_store`.`book_AFTER_UPDATE`
AFTER UPDATE ON `book_store`.`book`
FOR EACH ROW
BEGIN
	if(new.quantity < new.minimum_quantity)then
		insert into book_order VALUES (new.isbn,new.minimum_quantity);
	end if;
END$$

USE `book_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `book_store`.`book_BEFORE_UPDATE`
BEFORE UPDATE ON `book_store`.`book`
FOR EACH ROW
BEGIN
	if(new.quantity < 0)then
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "there is not enough quantity";
	end if;
END$$

USE `book_store`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `book_store`.`book_order_BEFORE_DELETE`
BEFORE DELETE ON `book_store`.`book_order`
FOR EACH ROW
BEGIN
UPDATE book
SET quantity = quantity + old.quantity
WHERE isbn = old.isbn;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
