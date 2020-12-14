CREATE DATABASE INST
GO
USE INST
GO
CREATE TABLE Users(
UserId UNIQUEIDENTIFIER PRIMARY KEY NOT NULL,
Username NVARCHAR(20) NOT NULL,
Email NVARCHAR(20) NOT NULL,
Password NVARCHAR(max) NOT NULL,
Constraint username_unique Unique(username)
);

CREATE TABLE Posts(
PostId INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
UserId UNIQUEIDENTIFIER NOT NULL,
Caption NVARCHAR(200),
Image nvarchar(max) NOT NULL,
DateCreated DATETIME NOT NULL,
FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

CREATE TABLE UserData(
IdData int identity(1,1) primary key,
UserId UNIQUEIDENTIFIER,
Firstname NVARCHAR(20),
Lastname NVARCHAR(20),
Age INT,
Description NVARCHAR(30),
City NVARCHAR(20),
Followers int not null, 
follows int not null,
FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

SELECT * FROM USERS

