CREATE DATABASE hotel;
-- DROP DATABASE hotel;
use hotel;

CREATE TABLE typ (
 typ varchar(10) NOT NULL PRIMARY KEY
);

INSERT INTO typ(typ) VALUES 
('basic'),
('comfort'),
('deluxe')
;

CREATE TABLE rezerwacja (
 id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 imie varchar(50)NOT NULL,
 nazwisko varchar(50)NOT NULL,
 typ varchar(10)NOT NULL,
 email varchar(50)NOT NULL,
 cena decimal(6,2) NOT NULL,
 dataOd date NOT NULL,
 dataDo date NOT NULL,
 FOREIGN KEY(typ) REFERENCES typ(typ)
);

INSERT INTO rezerwacja(imie, nazwisko, typ,email,cena,dataOd,dataDo) VALUES 
('Tomasz','Ryba','basic', 'tomasz.ryba@mail.com' , 1200,'2020-01-20','2020-01-22'),
('Adrianna','Sokół','basic', 'ada.sokol@mail.com', 1500,'2020-02-15','2020-02-19'),
('Maria','Kot','deluxe','maria.kot@mailcom',6000,'2020-02-1','2020-01-11')
;
SELECT * FROM rezerwacja;

drop table rezerwacja;
drop table typ;

-- dodanie uzytkownika

SET SQL_SAFE_UPDATES = 0;
CREATE USER 'user'@'localhost' IDENTIFIED BY 'user'; 
GRANT INSERT ON `rezerwacja` TO 'user'@'localhost';
