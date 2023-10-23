CREATE TABLE user
(
first_name  VARCHAR(20),
last_name  VARCHAR(20),
nationalID VARCHAR (10)  NOT NULL,
phone_number VARCHAR (12),
password VARCHAR (20),
CONSTRAINT nationalID_PK PRIMARY KEY (nationalID)
);
