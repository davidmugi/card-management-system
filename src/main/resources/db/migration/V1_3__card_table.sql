CREATE TABLE `cards` (
       `id` INT(11) NOT NULL AUTO_INCREMENT,
       `name` VARCHAR(100) NULL,
       `color` VARCHAR(45) NULL,
       `description` VARCHAR(150) NULL,
       `user_id` INT(11) NULL,
       `status` INT NULL DEFAULT 0,
       `status_desc` VARCHAR(45) NULL,
       `created_on`         timestamp NULL DEFAULT CURRENT_TIMESTAMP,
       `updated_on`         timestamp NULL DEFAULT CURRENT_TIMESTAMP,
       `created_by`         varchar(150)   DEFAULT NULL,
       `updated_by`         varchar(150)   DEFAULT NULL,

       PRIMARY KEY (`id`),

       INDEX `id` (`id` ASC) INVISIBLE,
       INDEX `index2` (`user_id` ASC),
       CONSTRAINT `fk_new_table_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`));

