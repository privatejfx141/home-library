-- r1
SELECT bk.ISBN, bk.Title, bk.YearOfPublication FROM Book bk
INNER JOIN BookAuthor ba USING (ISBN) INNER JOIN PeopleInvolved pi ON (ba.Author_ID = pi.ID)
WHERE pi.FirstName = 'Tom' ORDER BY bk.ISBN ASC;

-- r2
SELECT bk.ISBN, bk.Title, bk.YearOfPublication, pi.FamilyName, pi.FirstName FROM Book bk
INNER JOIN BookAuthor ba USING (ISBN) INNER JOIN PeopleInvolved pi ON (ba.Author_ID = pi.ID)
WHERE bk.YearOfPublication = 2003 ORDER BY bk.Title ASC;

-- r3
SELECT DISTINCT bk.ISBN, bk.Title, bk.YearOfPublication FROM Book bk
INNER JOIN BookKeyword bkw ON (bk.ISBN = bkw.ISBN)
INNER JOIN Keyword kw ON (bkw.Keyword_ID = kw.ID)
WHERE bk.Title LIKE '%Thriller%' OR bk.Abstract LIKE '%Thriller%'
OR kw.Tag LIKE '%Thriller%' ORDER BY bk.ISBN;

-- r4
DROP VIEW IF EXISTS FrequentAuthors;

CREATE VIEW FrequentAuthors AS
SELECT DISTINCT ba1.Author_ID AS ID FROM BookAuthor ba1
INNER JOIN BookAuthor ba2 ON ba1.Author_ID = ba2.Author_ID
INNER JOIN Book b1 ON b1.ISBN = ba1.ISBN
INNER JOIN Book b2 ON b2.ISBN = ba2.ISBN
WHERE ba1.ISBN != ba2.ISBN
AND b1.YearOfPublication = b2.YearOfPublication + 1;

SELECT bk.ISBN, bk.Title, FullName(pi.ID) as `Author`,
bk.YearOfPublication FROM Book bk
INNER JOIN BookAuthor ba ON bk.ISBN = ba.ISBN
INNER JOIN PeopleInvolved pi ON pi.ID = ba.Author_ID
WHERE pi.ID IN (SELECT ID FROM FrequentAuthors)
ORDER BY pi.FamilyName, bk.YearOfPublication;

-- r5
DROP VIEW IF EXISTS TagFrequency;

CREATE VIEW TagFrequency AS
SELECT kw.Tag, COUNT(ISBN) AS Frequency FROM BookKeyword bk
INNER JOIN Keyword kw ON (bk.Keyword_ID = kw.ID)
GROUP BY bk.Keyword_ID ORDER BY Frequency DESC;

SELECT Tag, Frequency AS Frequency FROM TagFrequency
WHERE Frequency = (SELECT MAX(Frequency) FROM TagFrequency)
ORDER BY Tag;

-- r6
DROP FUNCTION IF EXISTS MovieRole;
SET GLOBAL log_bin_trust_function_creators = 1;
DELIMITER $$
CREATE FUNCTION MovieRole(roleCode VARCHAR(4)) RETURNS VARCHAR(32)
BEGIN
    DECLARE roleName VARCHAR(32);
    SET roleName = (CASE
        WHEN roleCode = 'C' THEN 'Cast'
        WHEN roleCode = 'D' THEN 'Director'
        WHEN roleCode = 'P' THEN 'Producer'
        WHEN roleCode = 'S' THEN 'Script writer'
        WHEN roleCode = 'E' THEN 'Editor'
        WHEN roleCode = 'CO' THEN 'Composer'
        WHEN roleCode = 'CD' THEN 'Costume designer'
    END);
    RETURN roleName;
END $$
DELIMITER ;
SET GLOBAL log_bin_trust_function_creators = 0;

DROP VIEW IF EXISTS MultiSkillsMovieRoleCount;
CREATE VIEW MultiSkillsMovieRoleCount AS
SELECT PeopleInvolved_ID, COUNT(Role_ID) FROM CrewMember cw 
GROUP BY PeopleInvolved_ID
HAVING COUNT(Role_ID) > 1
ORDER by PeopleInvolved_ID;

SELECT pi.FamilyName, MovieRole(r.Description) AS `Role`, cw.MovieName FROM PeopleInvolved pi
JOIN MultiSkillsMovieRoleCount rc ON (pi.ID = rc.PeopleInvolved_ID)
JOIN CrewMember cw ON (pi.ID = cw.PeopleInvolved_ID)
JOIN Role r ON (r.ID = cw.Role_ID) ORDER BY pi.FamilyName;

-- r7
DROP FUNCTION IF EXISTS FullName;
SET GLOBAL log_bin_trust_function_creators = 1;
DELIMITER $$
CREATE FUNCTION FullName(PeopleInvolved_ID INT) RETURNS VARCHAR(135)
BEGIN 
    DECLARE fullName VARCHAR(135);
    SET fullName = (SELECT CONCAT(FirstName, ' ', IF(ISNULL(MiddleName), '', CONCAT(MiddleName, ' ')),
        FamilyName) FROM PeopleInvolved WHERE ID = PeopleInvolved_ID);
    RETURN fullName;
END $$
DELIMITER ;
SET GLOBAL log_bin_trust_function_creators = 0;

DROP VIEW IF EXISTS AwardCount;
CREATE VIEW AwardCount AS
SELECT cm.MovieName, COUNT(Award) AS `Awards` FROM CrewMember cm
INNER JOIN Award a USING (PeopleInvolved_ID, MovieName)
WHERE a.Award GROUP BY MovieName HAVING COUNT(Award) >= 1;

DROP VIEW IF EXISTS Directors;
CREATE VIEW Directors AS
SELECT pi.ID, pi.FamilyName,
cm.MovieName FROM PeopleInvolved pi
INNER JOIN CrewMember cm ON (pi.ID = cm.PeopleInvolved_ID)
INNER JOIN Role r ON (cm.Role_ID = r.ID)
WHERE r.Description = 'D';

SELECT FullName(d.ID) AS `Director Name`,
d.MovieName AS `Movie Name`, aw.Awards FROM Directors d
INNER JOIN AwardCount aw USING (MovieName) ORDER by d.FamilyName ASC;

-- r8
SELECT ms.AlbumName, ms.MusicName, FullName(pi.ID) AS `Singer`, ms.Year FROM
MusicSinger ms INNER JOIN PeopleInvolved pi ON (ms.PeopleInvolved_ID = pi.ID)
WHERE LOWER(ms.MusicName) IN (SELECT LOWER(m.MusicName) FROM Music m
GROUP BY m.MusicName HAVING COUNT(m.AlbumName) > 1);

-- r9
SELECT pi.FamilyName AS `Family Name`, LEFT(pi.FirstName, 1) AS `Initial`,
pim.MusicName AS `Music Name`, pim.AlbumName AS `Album Name`, pim.Year
FROM PeopleInvolved pi JOIN PeopleInvolvedMusic pim ON (pi.ID = pim.PeopleInvolved_ID)
WHERE pim.isSongwriter AND pim.isComposer AND NOT pim.isArranger
ORDER BY pim.Year, pim.MusicName DESC;

-- r10
DROP VIEW IF EXISTS BookFamilyNames;
DROP VIEW IF EXISTS MusicFamilyNames;
DROP VIEW IF EXISTS MovieFamilyNames;

CREATE VIEW BookFamilyNames AS
SELECT pi.FamilyName, 'Author' AS Role FROM PeopleInvolved pi JOIN BookAuthor ba
ON (pi.ID = ba.Author_ID) GROUP BY pi.FirstName, pi.FamilyName;

CREATE VIEW MusicFamilyNames AS
SELECT pi.FamilyName, 'Songwriter' AS Role FROM PeopleInvolved pi
JOIN (SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE isSongwriter) pim
ON (pi.ID = pim.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName
UNION ALL SELECT pi.FamilyName, 'Composer' AS Role FROM PeopleInvolved pi
JOIN (SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE isComposer) pim
ON (pi.ID = pim.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName
UNION ALL SELECT pi.FamilyName, 'Arranger' AS Role FROM PeopleInvolved pi
JOIN (SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE isArranger) pim
ON (pi.ID = pim.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName
UNION ALL SELECT pi.FamilyName, 'Singer' AS Role FROM PeopleInvolved pi
JOIN MusicSinger ms ON (pi.ID = ms.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName;

CREATE VIEW MovieFamilyNames AS
SELECT pi.FamilyName, MovieRole(r.Description) AS Role
FROM PeopleInvolved pi JOIN CrewMember cm
ON (pi.ID = cm.PeopleInvolved_ID) JOIN Role r ON (cm.Role_ID = r.ID)
GROUP BY cm.MovieName, pi.FirstName, pi.FamilyName;

DROP VIEW IF EXISTS AllFamilyNames;
CREATE VIEW AllFamilyNames AS
SELECT * FROM BookFamilyNames
UNION ALL SELECT * FROM MusicFamilyNames
UNION ALL SELECT * FROM MovieFamilyNames;

DROP VIEW IF EXISTS CommonFamilyNames;
CREATE VIEW CommonFamilyNames AS
SELECT FamilyName FROM AllFamilyNames GROUP BY FamilyName HAVING COUNT(FamilyName) > 1;

SELECT FamilyName, Role FROM AllFamilyNames
WHERE FamilyName IN (SELECT FamilyName FROM CommonFamilyNames)
ORDER BY FamilyName;

SELECT * FROM BookFamilyNames WHERE FamilyName IN
(SELECT DISTINCT FamilyName FROM MusicFamilyNames
UNION SELECT DISTINCT FamilyName FROM MovieFamilyNames)
UNION SELECT * FROM MusicFamilyNames WHERE FamilyName IN
(SELECT DISTINCT FamilyName FROM BookFamilyNames UNION
SELECT DISTINCT FamilyName FROM MovieFamilyNames)
UNION SELECT * FROM MovieFamilyNames WHERE FamilyName IN
(SELECT DISTINCT FamilyName FROM BookFamilyNames UNION
SELECT DISTINCT FamilyName FROM MusicFamilyNames) ORDER BY FamilyName;