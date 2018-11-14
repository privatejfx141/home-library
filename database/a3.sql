CREATE TABLE IF NOT EXISTS PeopleInvolved (
    id                  INTEGER PRIMARY KEY,
    first_name          VARCHAR(45) NOT NULL,
    middle_name         VARCHAR(45),
    family_name         VARCHAR(45) NOT NULL,
    gender              TINYINT
);

CREATE TABLE IF NOT EXISTS Book (
    isbn                CHAR(13) PRIMARY KEY NOT NULL,
    title               VARCHAR(45) NOT NULL,
    publisher           VARCHAR(45) NOT NULL,
    number_of_pages     INTEGER NOT NULL,
    year_of_publication INTEGER NOT NULL,
    edition_number      INTEGER,
    abstract            MEDIUMTEXT
);

CREATE TABLE IF NOT EXISTS Keyword (
    id                  INTEGER PRIMARY KEY NOT NULL,
    tag                 VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS BookKeyword (
    isbn                CHAR(13) NOT NULL,
    keyword_id          INTEGER NOT NULL,
    PRIMARY KEY (isbn, keyword_id),
    CONSTRAINT fk_BookKeyword_ISBN
        FOREIGN KEY (isbn) REFERENCES Book(isbn)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_BookKeyword_Keyword_ID
        FOREIGN KEY (keyword_id) REFERENCES Keyword(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS BookAuthor (
    isbn                CHAR(13) NOT NULL,
    author_id           INTEGER NOT NULL,
    PRIMARY KEY (isbn, author_id),
    CONSTRAINT fk_BookAuthor_ISBN
        FOREIGN KEY (isbn) REFERENCES Book(isbn)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_BookAuthor_ID
        FOREIGN KEY (author_id) REFERENCES PeopleInvolved(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Music (
    album_name          VARCHAR(45) NOT NULL,
    year                INTEGER NOT NULL,    
    music_name          VARCHAR(45) NOT NULL,
    language            VARCHAR(45),
    disk_type           TINYINT,
    producer_id         INTEGER,
    PRIMARY KEY (album_name, year, music_name)
);

CREATE TABLE IF NOT EXISTS PeopleInvolvedMusic (
    album_name          VARCHAR(45) NOT NULL,
    year                INTEGER NOT NULL,
    music_name          VARCHAR(45) NOT NULL,
    people_involved_id  INTEGER NOT NULL,
    is_songwriter       TINYINT,
    is_composer         TINYINT,
    is_arranger         TINYINT,
    PRIMARY KEY (album_name, year, music_name, people_involved_id),
    CONSTRAINT fk_PIM_Album
        FOREIGN KEY (album_name, year, music_name)
        REFERENCES Music(album_name, year, music_name)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_PIM_ID
        FOREIGN KEY (people_involved_id) REFERENCES PeopleInvolved(id)
);

CREATE TABLE IF NOT EXISTS MusicSinger (
    album_name          VARCHAR(45) NOT NULL,
    year                INTEGER NOT NULL,
    music_name          VARCHAR(45) NOT NULL,
    people_involved_id  INTEGER NOT NULL,
    PRIMARY KEY (album_name, year, music_name, people_involved_id),
    CONSTRAINT fk_Singer_Album
        FOREIGN KEY (album_name, year, music_name)
        REFERENCES Music(album_name, year, music_name)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_Singer_ID
        FOREIGN KEY (people_involved_id) REFERENCES PeopleInvolved(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Movie (
    movie_name          VARCHAR(45) NOT NULL,
    year                INTEGER NOT NULL,
    PRIMARY KEY (movie_name, year)
);

CREATE TABLE IF NOT EXISTS Role (
    id                  INTEGER PRIMARY KEY NOT NULL,
    description         VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS CrewMember (
    people_involved_id  INTEGER NOT NULL,
    movie_name          VARCHAR(45) NOT NULL,
    release_year        INTEGER NOT NULL,
    role_id             INTEGER NOT NULL,
    PRIMARY KEY (people_involved_id, movie_name, release_year, role_id),
    CONSTRAINT fk_Crew_ID
        FOREIGN KEY (people_involved_id) REFERENCES PeopleInvolved(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Crew_Movie
        FOREIGN KEY (movie_name, release_year) REFERENCES Movie(movie_name, year)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_Crew_Role_ID
        FOREIGN KEY (role_id) REFERENCES Role(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Award (
    people_involved_id  INTEGER NOT NULL,
    movie_name          VARCHAR(45) NOT NULL,
    year                INTEGER NOT NULL,
    award               TINYINT DEFAULT 0,
    PRIMARY KEY (people_involved_id, movie_name, year),
    CONSTRAINT fk_Award_People_ID
        FOREIGN KEY (people_involved_id) REFERENCES PeopleInvolved(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_Award_Movie
        FOREIGN KEY (movie_name, year) REFERENCES Movie(movie_name, year)
        ON DELETE CASCADE ON UPDATE CASCADE
);
