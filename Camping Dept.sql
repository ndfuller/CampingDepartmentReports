
-- CSC_243 FINAL PROJECT
-- CAMPING DEPARTMENT DATABASE WITH PRIMARY AND FOREIGN KEYS
-- AUTHOR NICHOLAS FULLER
-- UPDATED MAY 5, 2016 

-- THIS CREATES DATABASE

CREATE DATABASE CAMPINGDEPT;

-- OPEN DATABASE TO INSERT DATA

USE CAMPINGDEPT;

-- CREATE TABLE PONUMBER

CREATE TABLE PONUMBER
(PONUM int(5) PRIMARY KEY,
ODATE DATE)
ENGINE = INNODB DEFAULT CHARSET=UTF8;

insert into PONumber values(10001, '2014-1-5');
insert into PONumber values(10002, '2016-1-15');
insert into PONumber values(10003, '2016-1-31');
insert into PONumber values(10004, '2016-2-14');
insert into PONumber values(10005, '2016-2-28');
insert into PONumber values(10006, '2016-3-14');
insert into PONumber values(10007, '2016-3-28');
insert into PONumber values(10008, '2016-4-14');


-- CREATE TABLE CATEGORIES

CREATE TABLE CATEGORIES
(CATID CHAR(4) PRIMARY KEY NOT NULL,
CATDESC VARCHAR(40))
ENGINE = INNODB DEFAULT CHARSET=UTF8;

insert into Categories values('tent', 'tent');
insert into Categories values('sbag', 'sleeping bag');
insert into Categories values('spad', 'sleeping pad');
insert into Categories values('bp', 'backpacking packs');
insert into Categories values('dp', 'day packs');
insert into Categories values('st', 'stoves');
insert into Categories values('wf', 'water filters');
insert into Categories values('wb', 'water bottles');


-- CREATE TABLE MANUFACTURERS

CREATE TABLE MANUFACTURERS
(MFGID CHAR(3) PRIMARY KEY NOT NULL,
MFGNAME VARCHAR(30),
ADDRESS VARCHAR(40),
CITY CHAR(30),
ST CHAR(2),
ZIP CHAR(5),
PHONE VARCHAR(12),
CONTACTFIRST CHAR(30),
CONTACTLAST CHAR(30),
EMAIL VARCHAR(40))
ENGINE = INNODB DEFAULT CHARSET=UTF8;

insert into manufacturers values('CD', 'Cascade Designs', '1200 Hiking Dr', 'Seatle', 'WA', 98101, '800-245-9855', 'Jason', 'Livingston', 'jason_l@earthlink.net');
insert into manufacturers values('OSP', 'Osprey Packs', '115 Progress Circle', 'Cortex', 'CO', 81303, '866-514-6062', 'Leta ', 'Sharpe', 'leta_s@ospreypacks.com');
insert into manufacturers values('MAR', 'Marmot', '1600 Alpine Rd', 'Rhonert Park', 'CA', 94926, '800-316-9792', 'Mark ', 'Wildman', 'mark_w@mmp.com');
insert into manufacturers values('DEU', 'Deuter', '2200 S 1400 E', 'Salt Lake City', 'UT', 84110, '800-917-6592', 'Nick ', 'Peterson', 'nick_p@deuterusa.com');
insert into manufacturers values('KAT', 'Katadyn', '95 industrial Rd', 'Newark', 'NJ', 07101, '800-714-1245', 'Chappy ', 'Stewart', 'chappy_s@katadynusa.com');
insert into manufacturers values('EXP', 'Exped', '2613 Imports Lane', 'Kent', 'WA', 98030, '800-210-6831', 'Jessica ', 'Lory', 'jessica_l@expedusa.net');


-- CREATE TABLE INVENTORY

CREATE TABLE INVENTORY
(STYLE INT(5) PRIMARY KEY NOT NULL,
UPC VARCHAR(12) UNIQUE,
MFGID CHAR(3),
CATID CHAR(4),
DECSRIPTION VARCHAR(30),
COST decimal(6,2),
RETAIL decimal(6,2),
MARGIN DECIMAL(3,2),
ONHAND INT,
MINWANT INT,
MAXWANT INT,
foreign key (MFGID) REFERENCES MANUFACTURERS (MFGID)
ON DELETE CASCADE ON UPDATE CASCADE,
foreign key (CATID) REFERENCES CATEGORIES (CATID)
ON DELETE cascade ON UPDATE CASCADE)
ENGINE = INNODB DEFAULT CHARSET=UTF8;

insert into Inventory values(21113, 914407876319, 'CD', 'tent', 'Hubba', 113.4, 189, 0.4, 2, 2, 3);
insert into Inventory values(21137, 822665287414, 'CD', 'tent', 'Hubba Hubba', 137.4, 229, 0.4, 1, 3, 5);
insert into Inventory values(25149, 871328635606, 'MAR', 'tent', 'Limelight 2P', 149.4, 249, 0.4, 0, 1, 3);
insert into Inventory values(25251, 826466975034, 'MAR', 'tent', 'Force UL 2P', 251.4, 419, 0.4, 3, 1, 2);
insert into Inventory values(11159, 483329882612, 'OSP', 'bp', 'Aether 70', 159.5, 290, 0.45, 5, 4, 6);
insert into Inventory values(11126, 617481609107, 'OSP', 'bp', 'Atmos AG 50', 126.5, 230, 0.45, 2, 3, 5);
insert into Inventory values(13114, 161040545208, 'DEU', 'bp', 'ACT Lite 65', 114.95, 209, 0.45, 0, 2, 4);
insert into Inventory values(15103, 435853009810, 'MAR', 'bp', 'Eiger 42', 103.95, 189, 0.45, 2, 1, 3);
insert into Inventory values(32039, 468309419695, 'KAT', 'wf', 'Hiker', 39.75, 75, 0.47, 7, 5, 11);
insert into Inventory values(32050, 997125745983, 'KAT', 'wf', 'Vario', 50.32, 94.95, 0.47, 12, 6, 12);
insert into Inventory values(32052, 716005837777, 'KAT', 'wf', 'Base Camp Pro', 52.97, 99.95, 0.47, 2, 3, 6);
insert into Inventory values(31557, 201108005829, 'CD', 'wf', 'Hyperflow', 57.97, 99.95, 0.42, 4, 3, 6);
insert into Inventory values(31549, 573784040519, 'CD', 'wf', 'Sweetwater', 49.47, 89.95, 0.45, 0, 3, 6);
insert into Inventory values(41049, 952266066334, 'CD', 'st', 'Whisperlite', 49.47, 89.95, 0.45, 2, 2, 4);
insert into Inventory values(41131, 692347813630, 'CD', 'st', 'Reactor', 131.97, 219.95, 0.4, 2, 1, 2);
insert into Inventory values(14058, 140317190671, 'DEU', 'dp', 'ACT Trail 24', 58, 100, 0.42, 5, 2, 4);
insert into Inventory values(14034, 264924084302, 'DEU', 'dp', 'speed Lite 10', 34.22, 59, 0.42, 4, 2, 4);
insert into Inventory values(12055, 680764656280, 'OSP', 'dp', 'talon 22', 55, 100, 0.45, 6, 6, 8);
insert into Inventory values(12081, 97411685390, 'OSP', 'dp', 'stratos 24', 81.95, 149, 0.45, 2, 4, 6);
insert into Inventory values(16025, 32680850476, 'MAR', 'dp', 'Kompressor', 25, 50, 0.5, 3, 2, 4);
insert into Inventory values(17083, 569667017087, 'EXP', 'dp', 'Torrent 30L', 83.4, 139, 0.4, 0, 1, 3);
insert into Inventory values(55064, 46267639380, 'EXP', 'spad', 'Synmat 7', 64.5, 129, 0.5, 2, 3, 5);
insert into Inventory values(55095, 866571431746, 'EXP', 'spad', 'UL Synmat 7', 95.4, 159, 0.4, 4, 4, 6);
insert into Inventory values(51059, 299732474843, 'CD', 'spad', 'Prolite Plus', 59.97, 99.95, 0.4, 0, 2, 4);
insert into Inventory values(51041, 175932206446, 'CD', 'spad', 'Trail Lite', 41.97, 69.95, 0.4, 7, 6, 8);
insert into Inventory values(51095, 418955964036, 'CD', 'spad', 'Neo Air', 95.97, 159.95, 0.4, 6, 4, 6);
insert into Inventory values(61299, 727322989143, 'CD', 'sbag', 'Antares', 299.97, 499.95, 0.4, 1, 1, 2);
insert into Inventory values(65144, 380598325980, 'MAR', 'sbag', 'Never Summer', 144.5, 289, 0.5, 0, 4, 6);
insert into Inventory values(65104, 882092569954, 'MAR', 'sbag', 'Never Winter', 104.5, 209, 0.5, 5, 3, 5);
insert into Inventory values(65251, 715565006714, 'MAR', 'sbag', 'Helium', 251.4, 419, 0.4, 3, 2, 4);


-- CREATE TABLE POPLACED

CREATE TABLE POPLACED 
(PONUM INTEGER(5),
ODATE DATE,
MFGID CHAR(3),
STYLE INTEGER(5),
QTY INT,
foreign key (PONUM) references PONUMBER (PONUM)
ON DELETE CASCADE ON UPDATE CASCADE,
foreign key (MFGID) references MANUFACTURERS (MFGID)
ON DELETE CASCADE ON UPDATE CASCADE,
foreign key (STYLE) references INVENTORY (STYLE)
ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = INNODB DEFAULT CHARSET=UTF8;

insert into POPlaced  values(10001, '2016-1-5', 'CD', 21113, 2);
insert into POPlaced  values(10001, '2016-1-5', 'CD', 31549, 1);
insert into POPlaced  values(10001, '2016-1-5', 'CD', 51095, 2);
insert into POPlaced  values(10002, '2016-1-15', 'DEU', 13114, 1);
insert into POPlaced  values(10002, '2016-1-15', 'DEU', 14034, 3);
insert into POPlaced  values(10003, '2016-1-31', 'EXP', 17083, 1);
insert into POPlaced  values(10003, '2016-1-31', 'EXP', 55095, 1);
insert into POPlaced  values(10004, '2016-2-14', 'KAT', 32050, 6);
insert into POPlaced  values(10004, '2016-2-14', 'KAT', 32052, 2);
insert into POPlaced  values(10005, '2016-2-28', 'MAR', 15103, 1);
insert into POPlaced  values(10005, '2016-2-28', 'MAR', 16025, 1);
insert into POPlaced  values(10005, '2016-2-28', 'MAR', 25149, 1);
insert into POPlaced  values(10005, '2016-2-28', 'MAR', 25251, 1);
insert into POPlaced  values(10005, '2016-2-28', 'MAR', 65104, 1);
insert into POPlaced  values(10005, '2016-2-28', 'MAR', 65251, 1);
insert into POPlaced  values(10006, '2016-3-14', 'OSP', 11126, 1);
insert into POPlaced  values(10006, '2016-3-14', 'OSP', 11159, 2);
insert into POPlaced  values(10006, '2016-3-14', 'OSP', 12055, 6);
insert into POPlaced  values(10006, '2016-3-14', 'OSP', 12081, 4);

