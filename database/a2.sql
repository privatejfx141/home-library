-- DROP TABLE IF EXISTS FilmAward;
-- DROP TABLE IF EXISTS FilmCrew;
-- DROP TABLE IF EXISTS Movie;

-- DROP TABLE IF EXISTS MusicCrew;
-- DROP TABLE IF EXISTS Soundtrack;
-- DROP TABLE IF EXISTS Album;

-- DROP TABLE IF EXISTS BookKeyword;
-- DROP TABLE IF EXISTS Publishing;
-- DROP TABLE IF EXISTS Book;
-- DROP TABLE IF EXISTS Author;

-- DROP TABLE IF EXISTS Person;
-- DROP TABLE IF EXISTS Disk;


CREATE TABLE IF NOT EXISTS Person (
    id                  INTEGER PRIMARY KEY NOT NULL,
    first_name          VARCHAR(32) NOT NULL,
    middle_name         VARCHAR(32) NOT NULL,
    last_name           VARCHAR(32) NOT NULL,
    gender              VARCHAR(16) NOT NULL
    CHECK (gender IN ('Male', 'Female')),
    is_author           BOOLEAN NOT NULL DEFAULT 0,
    is_audio_crew       BOOLEAN NOT NULL DEFAULT 0,
    is_film_crew        BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Book (
    isbn                BIGINT PRIMARY KEY NOT NULL,
    title               VARCHAR(64) NOT NULL,
    publisher_name      VARCHAR(64) NOT NULL,
    number_of_pages     INTEGER NOT NULL,
    publication_year    INTEGER NOT NULL,
    edition_number      INTEGER,
    description         VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS BookKeyword (
    book_isbn           BIGINT NOT NULL,
    keyword             VARCHAR(64) NOT NULL,
    PRIMARY KEY(book_isbn, keyword),
    CONSTRAINT Keyword_Fk_Book_ISBN
        FOREIGN KEY(book_isbn)
        REFERENCES Book(isbn)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Author (
    person_id           INTEGER PRIMARY KEY NOT NULL,
    address             VARCHAR(64),
    phone_number        VARCHAR(64),
    email_address       VARCHAR(64),
    CONSTRAINT Author_Fk_Person_ID
        FOREIGN KEY(person_id)
        REFERENCES Person(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Publishing (
    author_id           INTEGER NOT NULL,
    book_isbn           BIGINT NOT NULL,
    PRIMARY KEY(author_id, book_isbn),
    CONSTRAINT Publishing_Fk_Author_ID
        FOREIGN KEY(author_id)
        REFERENCES Author(person_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT Publishing_Fk_Book_ISBN
        FOREIGN KEY(book_isbn)
        REFERENCES Book(isbn)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Disk (
    id                  INTEGER PRIMARY KEY NOT NULL,
    name                VARCHAR(64) NOT NULL,
    release_year        INTEGER NOT NULL,
    disk_type           CHAR(1) NOT NULL
    CHECK (disk_type IN ('A', 'M'))
);

CREATE TABLE IF NOT EXISTS Album (
    disk_id             INTEGER PRIMARY KEY NOT NULL,
    producer_id         INTEGER NOT NULL,
    CONSTRAINT Album_Fk_Disk_ID
        FOREIGN KEY(disk_id)
        REFERENCES Disk(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	CONSTRAINT Album_Fk_Producer_ID
        FOREIGN KEY(producer_id)
        REFERENCES Person(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Soundtrack (
    album_id            INTEGER NOT NULL,
    soundtrack_id       INTEGER NOT NULL,
    name                VARCHAR(32) NOT NULL,
    language            VARCHAR(16) NOT NULL,
    PRIMARY KEY(album_id, soundtrack_id),
    CONSTRAINT Soundtrack_Fk_Album_ID
        FOREIGN KEY(album_id)
        REFERENCES Album(disk_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS MusicCrew (
    person_id           INTEGER NOT NULL,
    album_id            INTEGER NOT NULL,
    soundtrack_id       INTEGER NOT NULL,
    role                VARCHAR(16) NOT NULL
    CHECK (role IN ('Singer', 'Songwriter', 'Composer', 'Arranger')),
    PRIMARY KEY(person_id, soundtrack_id),
    CONSTRAINT MusicCrew_Fk_Person_ID
        FOREIGN KEY(person_id)
        REFERENCES Person(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	CONSTRAINT MusicCrew_Fk_Soundtrack_ID
        FOREIGN KEY(album_id, soundtrack_id)
        REFERENCES Soundtrack(album_id, soundtrack_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Movie (
    disk_id             INTEGER PRIMARY KEY NOT NULL,
    CONSTRAINT Movie_Fk_Disk_ID
        FOREIGN KEY(disk_id)
        REFERENCES Disk(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS FilmCrew (
    person_id           INTEGER NOT NULL,
    movie_id            INTEGER NOT NULL,
    role                VARCHAR(16) NOT NULL
    CHECK (role IN ('Director', 'Scriptwriter', 'Cast', 'Producer', 'Composer', 'Editor', 'Costume designer')),
    PRIMARY KEY(person_id, movie_id)
);

CREATE TABLE IF NOT EXISTS FilmAward (
    film_award_id       INTEGER PRIMARY KEY NOT NULL,
    film_crew_id        INTEGER NOT NULL,
    award               VARCHAR(64) NOT NULL,
    CONSTRAINT FilmAward_Fk_FilmCrew_ID
        FOREIGN KEY(film_crew_id)
        REFERENCES FilmCrew(person_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
