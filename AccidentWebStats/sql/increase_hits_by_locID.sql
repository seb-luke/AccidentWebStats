UPDATE AWS_COORDINATES SET HITS = (SELECT HITS+1 HITS FROM AWS_COORDINATES WHERE loc_id = 3) WHERE loc_id = 3;