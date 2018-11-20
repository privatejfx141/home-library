drop schema if exists HL;
create schema HL;
use HL;

-- -----------------------------------------------------
-- Table Book
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS Book (
  ISBN CHAR(13) NOT NULL,
  Title VARCHAR(45) NOT NULL,
  Publisher VARCHAR(45) NOT NULL,
  NumberOfPages INT NOT NULL,
  YearOfPublication INT NOT NULL,
  EditionNumber INT NULL,
  Abstract MEDIUMTEXT NULL,
  PRIMARY KEY (ISBN));


-- -----------------------------------------------------
-- Table PeopleInvolved
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS PeopleInvolved (
  ID INT NOT NULL AUTO_INCREMENT,
  FirstName VARCHAR(45) NOT NULL,
  MiddleName VARCHAR(45) NULL,
  FamilyName VARCHAR(45) NOT NULL,
  Gender TINYINT NULL,
  PRIMARY KEY (ID));


-- -----------------------------------------------------
-- Table BookAuthor
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS BookAuthor (
  ISBN CHAR(13) NOT NULL,
  Author_ID INT NOT NULL,
  PRIMARY KEY (ISBN, Author_ID),
  INDEX fk_Book_has_Author_Author1_idx (Author_ID ASC),
  INDEX fk_Book_has_Author_Book_idx (ISBN ASC),
  CONSTRAINT fk_Book_has_Author_Book
    FOREIGN KEY (ISBN)
    REFERENCES Book (ISBN)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Book_has_Author_Author1
    FOREIGN KEY (Author_ID)
    REFERENCES PeopleInvolved (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table Keyword
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS Keyword (
  ID INT NOT NULL AUTO_INCREMENT,
  Tag VARCHAR(45) NOT NULL,
  PRIMARY KEY (ID));


-- -----------------------------------------------------
-- Table BookKeyword
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS BookKeyword (
  ISBN CHAR(13) NOT NULL,
  Keyword_ID INT NOT NULL,
  PRIMARY KEY (ISBN, Keyword_ID),
  INDEX fk_Book_has_Keywords_Keywords1_idx (Keyword_ID ASC),
  INDEX fk_Book_has_Keywords_Book1_idx (ISBN ASC),
  CONSTRAINT fk_Book_has_Keywords_Book1
    FOREIGN KEY (ISBN)
    REFERENCES Book (ISBN)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Book_has_Keywords_Keywords1
    FOREIGN KEY (Keyword_ID)
    REFERENCES Keyword (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table Movie
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS Movie (
  MovieName VARCHAR(45) NOT NULL,
  Year INT NOT NULL,
  PRIMARY KEY (MovieName, Year));


-- -----------------------------------------------------
-- Table Music
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS Music (
  AlbumName VARCHAR(45) NOT NULL,
  Year INT NOT NULL,
  MusicName VARCHAR(45) NOT NULL,
  Language VARCHAR(45) NULL,
  DiskType TINYINT NULL COMMENT '\"C\" for CD, \"V\" for Vinyl',
  Producer_ID INT NOT NULL,
  PRIMARY KEY (AlbumName, Year, MusicName),
  INDEX fk_Album_PeopleInvolved1_idx (Producer_ID ASC),
  CONSTRAINT fk_Album_PeopleInvolved1
    FOREIGN KEY (Producer_ID)
    REFERENCES PeopleInvolved (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table MusicSinger
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS MusicSinger (
  AlbumName VARCHAR(45) NOT NULL,
  Year INT NOT NULL,
  MusicName VARCHAR(45) NOT NULL,
  PeopleInvolved_ID INT NOT NULL,
  PRIMARY KEY (AlbumName, Year, MusicName, PeopleInvolved_ID),
  INDEX fk_Album_has_PeopleInvolved_PeopleInvolved2_idx (PeopleInvolved_ID ASC),
  INDEX fk_Album_has_PeopleInvolved_Album2_idx (AlbumName ASC, Year ASC, MusicName ASC),
  CONSTRAINT fk_Album_has_PeopleInvolved_Album2
    FOREIGN KEY (AlbumName , Year , MusicName)
    REFERENCES Music (AlbumName , Year , MusicName)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Album_has_PeopleInvolved_PeopleInvolved2
    FOREIGN KEY (PeopleInvolved_ID)
    REFERENCES PeopleInvolved (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table PeopleInvolvedMusic
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS PeopleInvolvedMusic (
  AlbumName VARCHAR(45) NOT NULL,
  Year INT NOT NULL,
  MusicName VARCHAR(45) NOT NULL,
  PeopleInvolved_ID INT NOT NULL,
  IsSongwriter TINYINT NULL COMMENT '\"S\" for Song writer, \"C\" for Composer,\"A\" for Arranger',
  IsComposer TINYINT NULL,
  IsArranger TINYINT NULL,
  PRIMARY KEY (AlbumName, Year, MusicName, PeopleInvolved_ID),
  INDEX fk_Album_has_PeopleInvolved_PeopleInvolved3_idx (PeopleInvolved_ID ASC),
  INDEX fk_Album_has_PeopleInvolved_Album3_idx (AlbumName ASC, Year ASC, MusicName ASC),
  CONSTRAINT fk_Album_has_PeopleInvolved_Album3
    FOREIGN KEY (AlbumName , Year , MusicName)
    REFERENCES Music (AlbumName , Year , MusicName)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Album_has_PeopleInvolved_PeopleInvolved3
    FOREIGN KEY (PeopleInvolved_ID)
    REFERENCES PeopleInvolved (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table Role
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS Role (
  ID INT NOT NULL AUTO_INCREMENT,
  Description VARCHAR(45) NULL,
  PRIMARY KEY (ID));


-- -----------------------------------------------------
-- Table CrewMember
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS CrewMember (
  PeopleInvolved_ID INT NOT NULL,
  MovieName VARCHAR(45) NOT NULL,
  ReleaseYear INT NOT NULL,
  Role_ID INT NOT NULL COMMENT '\"C\" for Cast, \"D\" for directors, script writers, cast, producers, composers, editors, costume designers ',
  PRIMARY KEY (PeopleInvolved_ID, MovieName, ReleaseYear, Role_ID),
  INDEX fk_PeopleInvolved_has_Movie_Movie1_idx (MovieName ASC, ReleaseYear ASC),
  INDEX fk_PeopleInvolved_has_Movie_PeopleInvolved1_idx (PeopleInvolved_ID ASC),
  INDEX fk_CrewMember_Role1_idx (Role_ID ASC),
  CONSTRAINT fk_PeopleInvolved_has_Movie_PeopleInvolved1
    FOREIGN KEY (PeopleInvolved_ID)
    REFERENCES PeopleInvolved (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PeopleInvolved_has_Movie_Movie1
    FOREIGN KEY (MovieName , ReleaseYear)
    REFERENCES Movie (MovieName , Year)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CrewMember_Role1
    FOREIGN KEY (Role_ID)
    REFERENCES Role (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table Award
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS Award (
  PeopleInvolved_ID INT NOT NULL,
  MovieName VARCHAR(45) NOT NULL,
  Year INT NOT NULL,
  Award TINYINT NULL,
  PRIMARY KEY (PeopleInvolved_ID, MovieName, Year),
  INDEX fk_PeopleInvolved_has_Movie_Movie2_idx (MovieName ASC, Year ASC),
  INDEX fk_PeopleInvolved_has_Movie_PeopleInvolved2_idx (PeopleInvolved_ID ASC),
  CONSTRAINT fk_PeopleInvolved_has_Movie_PeopleInvolved2
    FOREIGN KEY (PeopleInvolved_ID)
    REFERENCES PeopleInvolved (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_PeopleInvolved_has_Movie_Movie2
    FOREIGN KEY (MovieName , Year)
    REFERENCES Movie (MovieName , Year)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


