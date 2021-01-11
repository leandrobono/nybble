USE `nybble` ;

CREATE TABLE IF NOT EXISTS `nybble`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `apellido` VARCHAR(100) NOT NULL,
  `email` VARCHAR(80) NOT NULL,
  `password` VARCHAR(80) NOT NULL,
  `rol` INT NOT NULL,
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `nybble`.`proveedores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
  )
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `nybble`.`eventos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `proveedor` INT NOT NULL,
  `limite` INT NOT NULL,
  `fecha` DATETIME NOT NULL,
  `ciudad` VARCHAR(200) NOT NULL,
  `pais` VARCHAR(100) NOT NULL,
  `costo` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
    CONSTRAINT `proveedor`
        FOREIGN KEY (`proveedor`)
        REFERENCES `nybble`.`proveedores` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `proveedor_idx` ON `nybble`.`eventos` (`proveedor` ASC);


CREATE TABLE IF NOT EXISTS `nybble`.`reservas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `usuario` INT NOT NULL,
  `evento` INT NOT NULL,
  PRIMARY KEY (`id`),
    CONSTRAINT `usuario_reservas`
        FOREIGN KEY (`usuario`)
        REFERENCES `nybble`.`usuarios` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
        CONSTRAINT `evento`
            FOREIGN KEY (`evento`)
            REFERENCES `nybble`.`eventos` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
  )
ENGINE = InnoDB;

COMMIT;