INSERT INTO address (country, country_code, city,street,street_number,postal_code) VALUES
('Serbia', 'SR', 'Uzice', 'Dimitrija Tucovica', '28 a', 31000),
('Serbia', 'SR', 'Uzice', 'Dimitrija Tucovica', '55', 31000),
('Serbia', 'SR', 'Uzice', 'Mage Magazinovica', '10', 31000),
('Serbia', 'SR', 'Uzice', 'Uzickih Heroja', '25 a', 31000);


INSERT INTO client (first_name, last_name, address_id, date_created,date_changed ,active) VALUES
('John', 'Doe', 1,CURRENT_DATE(),CURRENT_DATE(),1 ),
('Mark', 'Johnson', 2,CURRENT_DATE(),CURRENT_DATE(),1  ),
('Jason', 'Statham', 3,CURRENT_DATE(),CURRENT_DATE(),1  ),
('Robert', 'Downey', 4 ,CURRENT_DATE(),CURRENT_DATE(),1 );

INSERT INTO meter (serial_number, client_id, active) VALUES
(51648464, 1,1 ),
(46468487, 2 ,1),
(48468486, 3,1 ),
(13548987, 4,1 );

INSERT INTO meter_readings (date_created,date_changed, value, meter_id, active) VALUES
('2021-01-12 15:00:00','2021-01-12 15:00:00', 1560.1, 1,1 ),
('2021-02-10 16:00:00','2021-02-10 16:00:00', 1320.5, 1,1 ),
('2021-03-16 12:00:00','2021-03-16 00:00:00', 565, 1 ,1),
('2021-01-12 16:00:00','2021-01-12 16:00:00', 1560.1, 2,1 ),
('2021-02-10 17:00:00','2021-02-10 17:00:00', 1320.5, 2,1 ),
('2021-01-16 16:00:00','2021-01-16 16:00:00', 565, 3 ,1),
('2021-01-17 16:00:00','2021-01-17 16:00:00', 785, 4 ,1),
('2021-02-17 16:00:00','2021-02-17 16:00:00', 128, 4 ,1),
('2021-03-17 16:00:00','2021-03-17 16:00:00', 256, 4 ,1);