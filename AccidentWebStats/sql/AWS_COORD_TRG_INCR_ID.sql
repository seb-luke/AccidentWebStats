CREATE OR REPLACE TRIGGER AWS_COORD_TRG_INCR_ID
  BEFORE INSERT ON AWS_COORDINATES
  FOR EACH ROW
BEGIN
  :new.LOC_ID := INCREMENT_ID.NEXTVAL;
END;