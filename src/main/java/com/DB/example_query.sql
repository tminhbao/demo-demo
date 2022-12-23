
-- find the latest person
SELECT *
FROM users u
GROUP By u.PersonID
Having  u.PersonID >= all ( select u2.PersonID from users u2 );

SELECT * from users;