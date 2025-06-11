-- Insert sample cities
INSERT INTO city (id, name, city_centre_latitude, city_centre_longitude) VALUES 
(9, 'Amsterdam', 52.36878, 4.903303),
(8, 'Paris', 48.8566, 2.3522),
(7, 'London', 51.5074, -0.1278);

-- Insert sample hotels
INSERT INTO hotel (id, name, rating, latitude, longitude, address, city_id, deleted) VALUES 
(31, 'The Bank Hotel Amsterdam', 9.2, 52.37276, 4.893604, '10 Bank Street, 10110 AB', 9, false),
(32, 'Amsterdam Central Hotel', 8.5, 52.378900, 4.900200, '15 Central Square, 10111 AC', 8, false),
(42, 'Canal View Inn', 7.8, 52.365000, 4.905000, '22 Canal Street, 10112 AD', 7, false),
(33, 'Downtown Amsterdam', 8.9, 52.370000, 4.895000, '8 Downtown Ave, 10113 AE', 9, false),
(34, 'Harbor Hotel', 9.1, 52.375000, 4.910000, '33 Harbor Road, 10114 AF', 8, false),

(50, 'Paris Grand Hotel', 9.5, 48.8600, 2.3500, '1 Champs Elysees, 75001', 7, false),
(51, 'Eiffel Tower Inn', 8.7, 48.8584, 2.2945, '7 Eiffel Street, 75007', 9, false),

(60, 'London Bridge Hotel', 8.8, 51.5045, -0.0865, '10 London Bridge St, SE1', 8, false),
(61, 'Thames View Hotel', 9.0, 51.5085, -0.1257, '25 Thames Walk, SW1', 7, false);