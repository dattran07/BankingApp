CREATE TABLE BankUser (
    UserName VARCHAR2(25) NOT NULL,
    UserPassword VARCHAR2(25) NOT NULL,
    UserEmail VARCHAR2(50) NOT NULL UNIQUE,
    CONSTRAINT PK_BankUser PRIMARY KEY (UserName)
);

CREATE TABLE BankAccount (
    AccountName VARCHAR2(50) NOT NULL,
    AccountType VARCHAR2(15) NOT NULL,
    AccountBalance NUMBER(25,2) NOT NULL,
    CONSTRAINT PK_BankAccount PRIMARY KEY (AccountName)
);

CREATE TABLE UserAccount (
    UserName VARCHAR2(50) NOT NULL,
    AccountName VARCHAR2(50) NOT NULL,
    CONSTRAINT PK_UserAccount PRIMARY KEY (UserName, AccountName)
);

ALTER TABLE UserAccount ADD CONSTRAINT FK_UAUser
    FOREIGN KEY (UserName) REFERENCES BankUser (UserName);
    
ALTER TABLE UserAccount ADD CONSTRAINT FK_UAAccount
    FOREIGN KEY (AccountName) REFERENCES BankAccount (AccountName);