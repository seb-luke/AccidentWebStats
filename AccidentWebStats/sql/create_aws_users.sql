CREATE TABLE AWS_USERS 
(
  USR_ID NUMBER(38) NOT NULL 
, EMAIL VARCHAR2(30) NOT NULL 
, NAME VARCHAR2(30) NOT NULL 
, SURNAME VARCHAR2(15) NOT NULL 
, USERNAME VARCHAR2(20) NOT NULL 
, PASSWD VARCHAR2(256) NOT NULL 
, SALT VARCHAR2(256) NOT NULL 
, PERMISSIONS VARCHAR2(5) NOT NULL 
, BIRTHDATE DATE NOT NULL 
, REGISTER_DATE TIMESTAMP DEFAULT sysdate 
, SECQ_ID NUMBER(38) NOT NULL 
, TOK_ID NUMBER(38) 
, CONSTRAINT AWS_USERS_PK PRIMARY KEY 
  (
    USR_ID 
  )
  ENABLE 
);

ALTER TABLE AWS_USERS
ADD CONSTRAINT AWS_USERS_UK1 UNIQUE 
(
  EMAIL 
, USERNAME 
)
ENABLE;

ALTER TABLE AWS_USERS
ADD CONSTRAINT AWS_USERS_FK_SECQ FOREIGN KEY
(
  SECQ_ID 
)
REFERENCES AWS_SEC_Q
(
  SECQ_ID 
)
ENABLE;

ALTER TABLE AWS_USERS
ADD CONSTRAINT AWS_USERS_FK_TOKENS FOREIGN KEY
(
  TOK_ID 
)
REFERENCES AWS_TOKENS
(
  TOK_ID 
)
ENABLE;
