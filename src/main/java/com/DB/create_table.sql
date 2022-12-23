USE simpledb;
CREATE TABLE users
(
    PersonID    int primary key,
    lastname    varchar(255),
    firstname   varchar(255),
    Address     varchar(255),
    City        varchar(255),
    DateOfBirth date
);

CREATE TABLE accounts
(
    AccountID int,
    PersonID  int,
    FOREIGN KEY (PersonID) REFERENCES users (PersonID)
);
CREATE TABLE creditcards
(
    CreditCardID int,
    PersonID     int,

    FOREIGN KEY (PersonID) REFERENCES users (PersonID)
);

-- insert some mock data
INSERT INTO users (PersonID, lastname, firstname, Address, City, DateOfBirth)
VALUES (1, 'Doe', 'John', '123 Main St', 'New York', '1980-01-01');
INSERT INTO users (PersonID, lastname, firstname, Address, City, DateOfBirth)
VALUES (2, 'HOe', 'Jane', '123 Main rtrtrtr', 'HCM', '1980-04-01');
INSERT INTO users (PersonID, lastname, firstname, Address, City, DateOfBirth)
VALUES (3, 'Jenny', 'Kay', '123 asdsad St', 'HN', '1980-02-01');

insert into accounts (AccountID, PersonID)
values (1, 1);
insert into accounts (AccountID, PersonID)
values (2, 2);
insert into accounts (AccountID, PersonID)
values (3, 3);

insert into creditcards (CreditCardID, PersonID)
values (1, 1);
insert into creditcards (CreditCardID, PersonID)
values (2, 1);

select *
from users;
select *
from accounts;
select *
from creditcards;
ALTER TABLE users
    ADD AccountID varchar(255);

UPDATE users
SET AccountID = '1'
WHERE PersonID = 1;

UPDATE users
SET AccountID = '2'
WHERE PersonID = 2;

UPDATE users
SET AccountID = '3'
WHERE PersonID = 3;

