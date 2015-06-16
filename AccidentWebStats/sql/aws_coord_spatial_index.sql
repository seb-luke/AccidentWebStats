CREATE INDEX aws_coord_sidx ON aws_coordinates(coord)
  INDEXTYPE IS mdsys.spatial_index;