CREATE TABLE AWS_USR_HISTORY 
(
  HIST_ID NUMBER(38) NOT NULL 
, USR_ID NUMBER(38) NOT NULL 
, LAST_LOGIN TIMESTAMP DEFAULT sysdate 
, LAST_LOGIN_IP VARCHAR2(15) 
, CONSTRAINT AWS_USR_HISTORY_PK PRIMARY KEY 
  (
    HIST_ID 
  )
  ENABLE 
);

ALTER TABLE AWS_USR_HISTORY
ADD CONSTRAINT AWS_USR_HISTORY_FK1 FOREIGN KEY
(
  USR_ID 
)
REFERENCES AWS_USERS
(
  USR_ID 
)
ENABLE;
