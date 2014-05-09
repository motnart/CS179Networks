CREATE TABLE Users(UserID CHAR(16) NOT NULL, Password CHAR(16) NOT NULL, access int,  PRIMARY KEY(UserID));

INSERT INTO Users(UserID, Password, access) VALUES ('user1','1234', 1);
INSERT INTO Users(UserID, Password, access) VALUES ('user2','1234', 2);
INSERT INTO Users(UserID, Password, access) VALUES ('admin','1234', 3);

CREATE TABLE GenDoors(access int, DoorID int, PRIMARY KEY(access,DoorID), FOREIGN KEY(access) REFERENCES Users(access) ON DELETE NO ACTION, FOREIGN KEY(DoorID) REFERENCES Doors(DoorID) ON DELETE CASCADE);

INSERT INTO GenDoors(access, DoorID) VALUES (1,100);
INSERT INTO GenDoors(access, DoorID) VALUES (1,101);
INSERT INTO GenDoors(access, DoorID) VALUES (1,102);
INSERT INTO GenDoors(access, DoorID) VALUES (1,103);
INSERT INTO GenDoors(access, DoorID) VALUES (1,104);

INSERT INTO GenDoors(access, DoorID) VALUES (2,100);
INSERT INTO GenDoors(access, DoorID) VALUES (2,101);
INSERT INTO GenDoors(access, DoorID) VALUES (2,102);
INSERT INTO GenDoors(access, DoorID) VALUES (2,103);
INSERT INTO GenDoors(access, DoorID) VALUES (2,104);
INSERT INTO GenDoors(access, DoorID) VALUES (2,105);
INSERT INTO GenDoors(access, DoorID) VALUES (2,106);
INSERT INTO GenDoors(access, DoorID) VALUES (2,107);
INSERT INTO GenDoors(access, DoorID) VALUES (2,108);
INSERT INTO GenDoors(access, DoorID) VALUES (2,109);

INSERT INTO GenDoors(access, DoorID) VALUES (3,100);
INSERT INTO GenDoors(access, DoorID) VALUES (3,101);
INSERT INTO GenDoors(access, DoorID) VALUES (3,102);
INSERT INTO GenDoors(access, DoorID) VALUES (3,103);
INSERT INTO GenDoors(access, DoorID) VALUES (3,104);
INSERT INTO GenDoors(access, DoorID) VALUES (3,105);
INSERT INTO GenDoors(access, DoorID) VALUES (3,106);
INSERT INTO GenDoors(access, DoorID) VALUES (3,107);
INSERT INTO GenDoors(access, DoorID) VALUES (3,108);
INSERT INTO GenDoors(access, DoorID) VALUES (3,109);
INSERT INTO GenDoors(access, DoorID) VALUES (3,200);
INSERT INTO GenDoors(access, DoorID) VALUES (3,201);
INSERT INTO GenDoors(access, DoorID) VALUES (3,202);
INSERT INTO GenDoors(access, DoorID) VALUES (3,203);
INSERT INTO GenDoors(access, DoorID) VALUES (3,204);
INSERT INTO GenDoors(access, DoorID) VALUES (3,205);
INSERT INTO GenDoors(access, DoorID) VALUES (3,206);
INSERT INTO GenDoors(access, DoorID) VALUES (3,207);
INSERT INTO GenDoors(access, DoorID) VALUES (3,208);
INSERT INTO GenDoors(access, DoorID) VALUES (3,209);


CREATE TABLE SpecDoors(UserID CHAR(16), DoorID int, PRIMARY KEY(UserID,DoorID), FOREIGN KEY(UserID) REFERENCES Users(UserID) ON DELETE CASCADE, FOREIGN KEY(DoorID) REFERENCES Doors(DoorID) ON DELETE CASCADE);

INSERT INTO SpecDoors(UserID, DoorID) VALUES ('user1',200);
INSERT INTO SpecDoors(UserID, DoorID) VALUES ('user1',201);
INSERT INTO SpecDoors(UserID, DoorID) VALUES ('user2',202);
INSERT INTO SpecDoors(UserID, DoorID) VALUES ('user2',203);


CREATE TABLE Doors(DoorID int, PRIMARY KEY(DoorID));
INSERT INTO Doors(DoorID) VALUES (100);
INSERT INTO Doors(DoorID) VALUES (101);
INSERT INTO Doors(DoorID) VALUES (102);
INSERT INTO Doors(DoorID) VALUES (103);
INSERT INTO Doors(DoorID) VALUES (104);
INSERT INTO Doors(DoorID) VALUES (105);
INSERT INTO Doors(DoorID) VALUES (106);
INSERT INTO Doors(DoorID) VALUES (107);
INSERT INTO Doors(DoorID) VALUES (108);
INSERT INTO Doors(DoorID) VALUES (109);
INSERT INTO Doors(DoorID) VALUES (200);
INSERT INTO Doors(DoorID) VALUES (201);
INSERT INTO Doors(DoorID) VALUES (202);
INSERT INTO Doors(DoorID) VALUES (203);
INSERT INTO Doors(DoorID) VALUES (204);
INSERT INTO Doors(DoorID) VALUES (205);
INSERT INTO Doors(DoorID) VALUES (206);
INSERT INTO Doors(DoorID) VALUES (207);
INSERT INTO Doors(DoorID) VALUES (208);
INSERT INTO Doors(DoorID) VALUES (209);


