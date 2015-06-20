SELECT c.LOC_ID, t.x latitude, t.y longitude, c.TIMESTAMP, c.HITS, 
  sdo_nn_distance (1) distance
FROM AWS_COORDINATES c, TABLE(SDO_UTIL.GETVERTICES(c.coord)) t
WHERE
  sdo_nn(C.COORD,
      SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE (45.805110899999995,21.2523475, NULL), NULL, NULL),
      'sdo_num_res=5 unit=KM', 1) = 'TRUE' 
  ORDER BY distance ASC;