CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `taxi_service`.`manufacturers` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(225) NOT NULL,
    `country` VARCHAR(225) NOT NULL,
    `is_deleted` BIT NOT NULL DEFAULT 0,
PRIMARY KEY (`id`));

CREATE TABLE `taxi_service`.`drivers` (
     `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(225) NOT NULL,
     `licence_number` VARCHAR(225) NOT NULL,
     `is_deleted` TINYINT NOT NULL DEFAULT 0,
     `login` VARCHAR(225) NOT NULL,
     `password` VARCHAR(225) NOT NULL,
UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
PRIMARY KEY (`id`));

CREATE TABLE `taxi_service`.`cars` (
      `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
      `model` VARCHAR(225) NOT NULL,
      `is_deleted` TINYINT NOT NULL DEFAULT 0,
      `manufacturer_id` BIGINT(11) NOT NULL,
PRIMARY KEY (`id`),
   INDEX `cars_manufacturer_fk_idx` (`manufacturer_id` ASC) VISIBLE,
   CONSTRAINT `cars_manufacturer_fk`
   FOREIGN KEY (`manufacturer_id`)
         REFERENCES `taxi_service`.`manufacturers` (`id`)
         ON DELETE NO ACTION
         ON UPDATE NO ACTION);

CREATE TABLE `taxi_service`.`cars_drivers` (
`car_id` BIGINT(11) NOT NULL,
`driver_id` BIGINT(11) NOT NULL,
INDEX `cars_id_fk_idx` (`car_id` ASC) VISIBLE,
INDEX `drivers_id_fk_idx` (`driver_id` ASC) VISIBLE,
CONSTRAINT `cars_id_fk`
    FOREIGN KEY (`car_id`)
        REFERENCES `taxi_service`.`cars` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
CONSTRAINT `drivers_id_fk`
    FOREIGN KEY (`driver_id`)
        REFERENCES `taxi_service`.`drivers` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);
