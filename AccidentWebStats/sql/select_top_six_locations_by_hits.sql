SELECT c.LOC_ID, t.x latitude, t.x longitude, TO_CHAR (c.timestamp, 'YYYY-MON-DD HH24:MI:SS') thedate, c.HITS 
FROM (
    SELECT * FROM aws_coordinates ORDER BY hits desc) c,
    TABLE(SDO_UTIL.GETVERTICES(c.coord)) t
WHERE ROWNUM < 7;