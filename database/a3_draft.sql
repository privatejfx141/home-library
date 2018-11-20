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

-- r5
SELECT Tag, MAX(Frequency) AS Frequency
FROM (SELECT kw.Tag, COUNT(ISBN) AS Frequency FROM BookKeyword bk
INNER JOIN Keyword kw ON (bk.Keyword_ID = kw.ID)
GROUP BY bk.Keyword_ID ORDER BY Frequency DESC) TagFrequency ORDER BY Tag;

-- r6
SELECT pi.FamilyName, COUNT(r.ID) AS Roles, cw.MovieName
FROM PeopleInvolved pi JOIN CrewMember cw ON (pi.ID = cw.PeopleInvolved_ID)
JOIN Role r ON (r.ID = cw.Role_ID)
GROUP BY pi.FamilyName, cw.MovieName
HAVING COUNT(r.ID) > 1;

-- r7
SELECT CONCAT(pi.FirstName, ' ', IF(ISNULL(pi.MiddleName), '', CONCAT(pi.MiddleName, ' ')), pi.FamilyName) AS `Director Name`,
TRIM(cm.MovieName) AS `Movie Name`, `Awards` FROM PeopleInvolved pi
INNER JOIN CrewMember cm ON (pi.ID = cm.PeopleInvolved_ID)
INNER JOIN Role r ON (cm.Role_ID = r.ID)
INNER JOIN (SELECT cm.MovieName, COUNT(Award) AS `Awards` FROM CrewMember cm
INNER JOIN Award a USING (PeopleInvolved_ID, MovieName)
WHERE a.Award GROUP BY MovieName HAVING COUNT(Award) >= 1) AwardCount
USING (MovieName) WHERE r.Description = 'D';

-- r8
SELECT * FROM PeopleInvolvedMusic
