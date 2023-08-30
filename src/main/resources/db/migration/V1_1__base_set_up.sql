DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
       `id` INT(11) NOT NULL AUTO_INCREMENT,
       `first_name` VARCHAR(45) NULL,
       `lastname` VARCHAR(45) NULL,
       `email` VARCHAR(150) NULL,
       `password` VARCHAR(500) NULL,
       `user_type` INT NULL,
       `is_enabled` SMALLINT NULL DEFAULT '0',
       `created_on`         timestamp NULL DEFAULT CURRENT_TIMESTAMP,
       `updated_on`         timestamp NULL DEFAULT CURRENT_TIMESTAMP,
       `created_by`         varchar(150)   DEFAULT NULL,
       `updated_by`         varchar(150)   DEFAULT NULL,

       PRIMARY KEY (`id`),

       INDEX `id` (`id` ASC) INVISIBLE,
       INDEX `firstname` (`first_name` ASC) ,
       INDEX `lastname` (`lastname` ASC) ,
       INDEX `email` (`email` ASC) );
