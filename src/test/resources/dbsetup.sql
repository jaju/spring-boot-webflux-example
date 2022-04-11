CREATE TABLE AIRCRAFTS
(
    aircraft_code varchar(3) NOT NULL,
    model         varchar(256),
    range         integer
);

INSERT INTO AIRCRAFTS
values ('773', 'Boeing 777-300', 11100);