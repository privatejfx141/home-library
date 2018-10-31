-- -----------------------------------------------------
-- Table `Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Book` (
  `ISBN` INT NOT NULL,
  `Title` VARCHAR(64) NOT NULL,
  `PublisherName` VARCHAR(64) NOT NULL,
  `NumPages` INT NOT NULL,
  `PublicationYear` DATE NOT NULL,
  `EditionNumber` INT NULL,
  `Description` VARCHAR(128) NULL,
  PRIMARY KEY (`ISBN`));


-- -----------------------------------------------------
-- Table `BookKeyword`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookKeyword` (
  `BookISBN` INT NOT NULL,
  `Keyword` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`BookISBN`),
  CONSTRAINT `FK_BookISBN`
    FOREIGN KEY (`BookISBN`)
    REFERENCES `Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Person` (
  `PersonId` INT NOT NULL,
  `FirstName` VARCHAR(64) NOT NULL,
  `MiddleName` VARCHAR(64) NULL,
  `LastName` VARCHAR(64) NOT NULL,
  `PersonType` CHAR(1) NOT NULL,
  PRIMARY KEY (`PersonId`));


-- -----------------------------------------------------
-- Table `Disk`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Disk` (
  `DiskId` INT NOT NULL,
  `Name` VARCHAR(64) NOT NULL,
  `ReleaseYear` INT NOT NULL,
  `DiskType` CHAR(1) NOT NULL,
  PRIMARY KEY (`DiskId`));


-- -----------------------------------------------------
-- Table `Album`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Album` (
  `DiskId` INT NOT NULL,
  `ProducerId` INT NOT NULL,
  PRIMARY KEY (`DiskId`),
  INDEX `Fk_ProducerId_idx` (`ProducerId` ASC) VISIBLE,
  CONSTRAINT `Fk_DiskId`
    FOREIGN KEY (`DiskId`)
    REFERENCES `Disk` (`DiskId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Fk_ProducerId`
    FOREIGN KEY (`ProducerId`)
    REFERENCES `Person` (`PersonId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Movie` (
  `DiskId` INT NOT NULL,
  PRIMARY KEY (`DiskId`),
  CONSTRAINT `Fk_DiskId`
    FOREIGN KEY (`DiskId`)
    REFERENCES `Disk` (`DiskId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Soundtrack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Soundtrack` (
  `AlbumId` INT NOT NULL,
  `TrackId` INT NOT NULL,
  `Name` VARCHAR(64) NOT NULL,
  `Language` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`AlbumId`, `TrackId`),
  CONSTRAINT `Fk_AlbumId`
    FOREIGN KEY (`AlbumId`)
    REFERENCES `Album` (`DiskId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `FilmCrew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FilmCrew` (
  `PersonId` INT NOT NULL,
  `MovieId` INT NOT NULL,
  `Gender` VARCHAR(64) NOT NULL,
  `Role` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`PersonId`),
  INDEX `Fk_MovieId_idx` (`MovieId` ASC) VISIBLE,
  CONSTRAINT `Fk_PersonId`
    FOREIGN KEY (`PersonId`)
    REFERENCES `Person` (`PersonId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Fk_MovieId`
    FOREIGN KEY (`MovieId`)
    REFERENCES `Movie` (`DiskId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `MusicCrew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MusicCrew` (
  `PersonId` INT NOT NULL,
  `SoundtrackId` INT NOT NULL,
  `Role` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`PersonId`),
  INDEX `Fk_SoundtrackId_idx` (`SoundtrackId` ASC) VISIBLE,
  CONSTRAINT `Fk_PersonId`
    FOREIGN KEY (`PersonId`)
    REFERENCES `Person` (`PersonId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Fk_SoundtrackId`
    FOREIGN KEY (`SoundtrackId`)
    REFERENCES `Soundtrack` (`TrackId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Author` (
  `PersonId` INT NOT NULL,
  `Address` VARCHAR(64) NULL,
  `PhoneNumber` VARCHAR(64) NULL,
  `EmailAddress` VARCHAR(64) NULL,
  PRIMARY KEY (`PersonId`),
  CONSTRAINT `Fk_PersonId`
    FOREIGN KEY (`PersonId`)
    REFERENCES `Person` (`PersonId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `Publishing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Publishing` (
  `AuthorId` INT NOT NULL,
  `BookISBN` INT NOT NULL,
  PRIMARY KEY (`AuthorId`, `BookISBN`),
  INDEX `fk_Author_has_Book_Book1_idx` (`BookISBN` ASC) VISIBLE,
  INDEX `fk_Author_has_Book_Author1_idx` (`AuthorId` ASC) VISIBLE,
  CONSTRAINT `fk_Author_has_Book_Author1`
    FOREIGN KEY (`AuthorId`)
    REFERENCES `Author` (`PersonId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Author_has_Book_Book1`
    FOREIGN KEY (`BookISBN`)
    REFERENCES `Book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `FilmAward`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FilmAward` (
  `FilmAwardId` INT NOT NULL,
  `FilmCrewId` INT NOT NULL,
  `Award` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`FilmAwardId`),
  INDEX `Fk_FilmCrewId_idx` (`FilmCrewId` ASC) VISIBLE,
  CONSTRAINT `Fk_FilmCrewId`
    FOREIGN KEY (`FilmCrewId`)
    REFERENCES `FilmCrew` (`PersonId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
