----------------
-- Warehouses --
----------------
insert into Warehouses (id_Warehouse, address, location, province) values (1, '07 Daystar Court', 'Milwaukee', 'Porto');
insert into Warehouses (id_Warehouse, address, location, province) values (2, '10673 Mifflin Park', 'Clyde Gallagher', 'Porto');
insert into Warehouses (id_Warehouse, address, location, province) values (3, '31806 Roth Parkway', 'Marquette', 'Porto');
insert into Warehouses (id_Warehouse, address, location, province) values (4, '7 Randy Circle', 'Anhalt', 'Porto');

----------------
--  Sections  --
----------------
insert into SECTIONS (section_id, BATCH_QUANTITY, MAX_SIZE, MIN_SIZE, WAREHOUSE_CODE, STATE) values (1, 36, 72, 31, 2, 1);
insert into SECTIONS (section_id, BATCH_QUANTITY, MAX_SIZE, MIN_SIZE, WAREHOUSE_CODE, STATE) values (2, 7, 92, 82, 1, 3);
insert into SECTIONS (section_id, BATCH_QUANTITY, MAX_SIZE, MIN_SIZE, WAREHOUSE_CODE, STATE) values (3, 49, 95, 91, 2, 1);
insert into SECTIONS (section_id, BATCH_QUANTITY, MAX_SIZE, MIN_SIZE, WAREHOUSE_CODE, STATE) values (4, 23, 71, 44, 4, 2);

----------------------
--     Products     --
----------------------
insert into PRODUCTS (PRODUCT_ID, ADDITIONAL_INFO, NAME, STATE, PRICE) values (1, 'nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue etiam justo etiam', 'Beauty', 1, 5799.23);
insert into PRODUCTS (PRODUCT_ID, ADDITIONAL_INFO, NAME, STATE, PRICE) values (2, 'curabitur at ipsum ac tellus semper interdum mauris ullamcorper purus sit amet nulla quisque arcu libero', 'Clothing', 0, 2117.94);
insert into PRODUCTS (PRODUCT_ID, ADDITIONAL_INFO, NAME, STATE, PRICE) values (3, 'venenatis lacinia aenean sit amet justo morbi ut odio cras', 'Automotive', 2, 4264.78);
insert into PRODUCTS (PRODUCT_ID, ADDITIONAL_INFO, NAME, STATE, PRICE) values (4, 'eget orci vehicula condimentum curabitur in libero ut massa volutpat convallis morbi odio odio elementum eu interdum eu tincidunt', 'Toys', 1, 3633.37);
INSERT INTO PRODUCTS (ADDITIONAL_INFO,NAME,STATE,PRICE) VALUES ('Bananas de colombia','BANANOS',1, 10000.0)
INSERT INTO PRODUCTS (ADDITIONAL_INFO,NAME,STATE,PRICE) VALUES ('Manzanas de Uruguay','MANZANAS',1, 43.5)
INSERT INTO PRODUCTS (ADDITIONAL_INFO,NAME,STATE,PRICE) VALUES ('Mangos de Argentina','MANGOS',1, 1500.0)

----------------------
--     Batches     --
----------------------
insert into BATCHES (BATCH_NUMBER, INITIAL_QUANTITY, CURRENT_QUANTITY, MANUFACTURING_TIME, MANUFACTURING_DATE, CURRENT_TEMPERATURE, MINIMUM_TEMPERATURE, FK_PRODUCT, DUE_DATE) values (1, 734, 229, '2021-08-04 02:19:34', '2021-04-04', 3, 3, 1, '2021-08-30');
insert into BATCHES (BATCH_NUMBER, INITIAL_QUANTITY, CURRENT_QUANTITY, MANUFACTURING_TIME, MANUFACTURING_DATE, CURRENT_TEMPERATURE, MINIMUM_TEMPERATURE, FK_PRODUCT, DUE_DATE) values (2, 69, 352, '2021-08-29 01:53:42', '2021-03-29', -9, 9, 2, '2021-08-03');


