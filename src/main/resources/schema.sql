CREATE TABLE city (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  city_centre_latitude DOUBLE,
  city_centre_longitude DOUBLE
);

CREATE TABLE hotel (
  id INT PRIMARY KEY,
  name VARCHAR(255),
  rating DOUBLE,
  latitude DOUBLE,
  longitude DOUBLE,
  address VARCHAR(255),
  city_id INT,
  deleted BOOLEAN
);
