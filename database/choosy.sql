DROP DATABASE IF EXISTS choosy;

CREATE DATABASE choosy;

USE choosy;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS permissions;

CREATE TABLE permissions (
  permission_id int(11) NOT NULL DEFAULT '0',
  permission_name VARCHAR(35) NOT NULL DEFAULT '',
  PRIMARY KEY(permission_id)
);


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
  role_id int(11) NOT NULL DEFAULT '0',
  role_name VARCHAR(35) NOT NULL DEFAULT '',
  PRIMARY KEY(role_id)
);

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS role_permission;

CREATE TABLE role_permission (
  role_id int(11) NOT NULL DEFAULT '0',
  permission_id int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (role_id,permission_id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
  CONSTRAINT permission_fk FOREIGN KEY (permission_id) REFERENCES permissions (permission_id)
);

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  username VARCHAR(25) NOT NULL DEFAULT '',
  fname VARCHAR(25) NOT NULL DEFAULT '',
  lname VARCHAR(25) NOT NULL DEFAULT '',
  password VARCHAR(64) NOT NULL DEFAULT '',
  PRIMARY KEY (username)
);

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role (
  username varchar(25) NOT NULL DEFAULT '',
  role_id int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (role_id,username),
  CONSTRAINT role_user_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
  CONSTRAINT user_fk FOREIGN KEY (username) REFERENCES users (username)
);


-- GENRES TABLE 


DROP TABLE IF EXISTS genres;

CREATE TABLE genres(
	genre_id INT(11) NOT NULL DEFAULT '0',
    genre_name VARCHAR(45) DEFAULT '',
	PRIMARY KEY(genre_id)
);


-- ARTIST TABLE


DROP TABLE IF EXISTS artist;

CREATE TABLE artist(
	artist_id INT(11) NOT NULL DEFAULT '0',
    artist_name VARCHAR(25),
	PRIMARY KEY(artist_id)
);


-- ALBUM TABLE


DROP TABLE IF EXISTS albums;

CREATE TABLE albums(
	album_id INT(11) NOT NULL DEFAULT '0',
    album_name VARCHAR(30) NOT NULL DEFAULT '',
	release_year YEAR(4) DEFAULT '0',
    album_length DOUBLE(5,2) DEFAULT '0.0',
	num_of_songs INT(11),
    artist_id INT(11) NOT NULL DEFAULT '0',
    CONSTRAINT artist_album_id_fk FOREIGN KEY (artist_id) REFERENCES artist (artist_id),
    PRIMARY KEY(album_id)
);


-- PUBLISHER TABLE


DROP TABLE IF EXISTS publisher;

CREATE TABLE publisher(
	publisher_id INT(11) NOT NULL DEFAULT '0',
    publisher_name VARCHAR(25) NOT NULL DEFAULT 'undefined',
    publisher_state VARCHAR(25) DEFAULT 'undefined',
    PRIMARY KEY(publisher_id)
);


-- SONGS TABLE


DROP TABLE IF EXISTS songs;

CREATE TABLE songs(
	song_id INT(11) NOT NULL DEFAULT '0',
    song_title VARCHAR(50) DEFAULT '',
    song_bpm INT(3) DEFAULT '0',
    song_key CHAR(2) DEFAULT '0A',
    song_duration DOUBLE(4,2) DEFAULT '0.0',
	release_year YEAR(4) DEFAULT '0',
	genre_id INT(11) NOT NULL DEFAULT '0',
	publisher_id INT(11) NOT NULL DEFAULT '0',
	album_id INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY(song_id),
    CONSTRAINT song_genre_id FOREIGN KEY (genre_id) REFERENCES genres (genre_id),
    CONSTRAINT song_publisher_id FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id),
	CONSTRAINT song_album_id FOREIGN KEY (album_id) REFERENCES albums (album_id)
);


-- PLAYLIST TABLE 


DROP TABLE IF EXISTS playlist;

CREATE TABLE playlist(
	playlist_id INT(11) NOT NULL DEFAULT '0',
	playlist_name VARCHAR(25) DEFAULT '',
    playlist_description VARCHAR(25) DEFAULT '',
    num_of_songs INT(3) DEFAULT '0',
	length_of_playlist DOUBLE(3,2) DEFAULT '0',
	username VARCHAR(25) NOT NULL DEFAULT '',
    PRIMARY KEY(playlist_id,username),
    CONSTRAINT user_playlist FOREIGN KEY (username) REFERENCES users (username)
);

DROP TABLE IF EXISTS playlist_song;

CREATE TABLE playlist_song(
	song_id INT(11) NOT NULL DEFAULT '0',
	playlist_id INT(11) NOT NULL DEFAULT '0',
    CONSTRAINT song_id_fk FOREIGN KEY (song_id) REFERENCES songs (song_id),
    CONSTRAINT playlist_id FOREIGN KEY (playlist_id) REFERENCES playlist (playlist_id)
);


-- FAVORITES TABLE


DROP TABLE IF EXISTS favorites;

CREATE TABLE favorites(
	username varchar(25) NOT NULL DEFAULT '',
	song_id INT(11) NOT NULL DEFAULT '0',
    PRIMARY KEY(username,song_id),
    CONSTRAINT username_fav_fk FOREIGN KEY (username) REFERENCES users (username),
    CONSTRAINT song_id_fav_fk FOREIGN KEY (song_id) REFERENCES songs (song_id)
);


DROP TABLE IF EXISTS artist_song;

CREATE TABLE artist_song(
	artist_id INT(11) NOT NULL DEFAULT '0',
	song_id INT(11) NOT NULL DEFAULT '0',
	CONSTRAINT artist_song_id_fk FOREIGN KEY (artist_id) REFERENCES artist (artist_id),
	CONSTRAINT song_artist_id_fk FOREIGN KEY (song_id) REFERENCES songs (song_id)
);


-- INSERT ROLES

INSERT INTO roles (role_id, role_name) VALUES (1, 'Admin');
INSERT INTO roles (role_id, role_name) VALUES (2, 'Editor');
INSERT INTO roles (role_id, role_name) VALUES (3, 'General');
INSERT INTO roles (role_id, role_name) VALUES (4, 'Viewer');


-- INSERTIONS

-- Null insertions, this is what the default constructors should store
INSERT INTO genres VALUES(0,'');
INSERT INTO publisher VALUES(0,'','');
INSERT INTO artist VALUES(0,'');
INSERT INTO albums VALUES(0,'',0,0.0,0,0);

INSERT INTO songs VALUES(0,'',0,'0A','0.0','0','0','0','0');

-- Genre insertions

INSERT INTO genres VALUES(1,'Pop');
INSERT INTO genres VALUES(2,'Rock');
INSERT INTO genres VALUES(3,'RnB');
INSERT INTO genres VALUES(4,'Rap');
INSERT INTO genres VALUES(5,'Country');
INSERT INTO genres VALUES(6,'Turbofolk');

-- Britney Spears -> 1

INSERT INTO publisher VALUES(1,'Jive Records','New York US');
INSERT INTO artist VALUES(1,'Britney Spears');
INSERT INTO albums VALUES(1,'...Baby One More Time',1999,'42.20',11,1);

INSERT INTO songs VALUES(1,'...Baby One More Time',93, '5A', '3.31',1999,1,1,1);
INSERT INTO artist_song VALUES(1,1);

-- Macklemore -> 2

INSERT INTO publisher VALUES(2,'Bendo LLC','California US');
INSERT INTO artist VALUES(2,'Macklemore');
INSERT INTO albums VALUES(2,'Gemini',2017,'53.92',16,2);

INSERT INTO songs VALUES(2,'Corner Store',146, '1B', '4.33',2017,4,2,2);
INSERT INTO artist_song VALUES(2,2);

-- 50 Cent -> 3

INSERT INTO publisher VALUES(3,'Aftermath Records','California US');
INSERT INTO artist VALUES(3,'50 Cent');
INSERT INTO albums VALUES(3,'Get Rich or Die Tryin',2003,'53.44',16,3);

INSERT INTO songs VALUES(3,'In Da Club',90,'1A', '3.13',2003,4,3,3);
INSERT INTO artist_song VALUES(3,3);

-- Gunna -> 4

INSERT INTO publisher VALUES(4,'YSL 300','California US');
INSERT INTO artist VALUES(4,'Gunna');
INSERT INTO albums VALUES(4,'DS4EVER',2022,'58.07',19,4);

INSERT INTO songs VALUES(4,'private island',107, '1B', '2.12',2022,4,4,4);
INSERT INTO artist_song VALUES(4,4);

-- Magazin -> 5

INSERT INTO publisher VALUES(5,'Jugoton','Yugoslavia');
INSERT INTO artist VALUES(5,'Magazin');
INSERT INTO albums VALUES(5,'Kokolo',1983,'36.16',10,5);

INSERT INTO songs VALUES(5,'Kokolo',151, '9B', '3.37',1983,1,5,5);
INSERT INTO artist_song VALUES(5,5);

-- Doja Cat -> 6

INSERT INTO publisher VALUES(6,'Kemosabe and RCA Records','California US');
INSERT INTO artist VALUES(6,'Doja Cat');
INSERT INTO albums VALUES(6,'Planet Her',2021,'44.06',13,6);

INSERT INTO songs VALUES(6,'Get Into It (Yuh)',92, '1A', '2.18',2021,4,6,6);
INSERT INTO artist_song VALUES(6,6);

-- Michael Jackson -> 7

INSERT INTO publisher VALUES(7,'Epic records','New York US');
INSERT INTO artist VALUES(7,'Michael Jackson');
INSERT INTO albums VALUES(7,'Off the Wall',1979,'42.28',10,7);

INSERT INTO songs VALUES(7,'Off the Wall',119, '2A', '4.06',1979,1,7,7);
INSERT INTO artist_song VALUES(7,7);

-- Drake -> 8

INSERT INTO publisher VALUES(8,'Young Money Entertainment','Louisiana US');
INSERT INTO artist VALUES(8,'Drake');
INSERT INTO albums VALUES(8,'Scorpion',2018,'89.44',20,8);

INSERT INTO songs VALUES(8,'Emotionless',173, '3B', '5.02',2018,4,8,8);
INSERT INTO artist_song VALUES(8,8);

-- Lil Wayne -> 9
-- Duplicate entry '8' for key 'PRIMARY' 
-- INSERT INTO publisher VALUES(8,'Young Money Entertainment','Louisiana US');
INSERT INTO artist VALUES(9,'Lil Wayne');
INSERT INTO albums VALUES(9,'Tha Carter V',2018,'87.43',23,9);

INSERT INTO songs VALUES(9,'Dedicate',118, '1B', '3.09',2018,4,8,9);
INSERT INTO artist_song VALUES(9,9);

-- Queen -> 10

INSERT INTO publisher VALUES(10,'Rockfield','London');
INSERT INTO artist VALUES(10,'Queen');
INSERT INTO albums VALUES(10,'A Night at the Opera',1975,'43.08',12,10);

INSERT INTO songs VALUES(10,'Bohemian Rhapsody',144, '5A', '5.55',1975,2,10,10);
INSERT INTO artist_song VALUES(10,10);

-- Chuck Berry -> 11

INSERT INTO publisher VALUES(11,'Dualtone','Chicago US');
INSERT INTO artist VALUES(11,'Chuck Berry');
-- INSERT INTO albums VALUES(0,'',0000,0.0,0,0);

INSERT INTO songs VALUES(11,'Johnny B. Goode',168, '6B', '2.41',1958,2,11,0);
INSERT INTO artist_song VALUES(11,11);


-- Nirvana -> 12

INSERT INTO publisher VALUES(12,'DGC','California US');
INSERT INTO artist VALUES(12,'Nirvana');
INSERT INTO albums VALUES(12,'Nevermind',1991,42.36,12,12);

INSERT INTO songs VALUES(12,'Smells Like Teen Spirit',117, '3B', '5.01',1991,2,12,12);
INSERT INTO artist_song VALUES(12,12);


-- AC/DC -> 13

INSERT INTO publisher VALUES(13,'Atlantic Records','Nassau US');
INSERT INTO artist VALUES(13,'AC/DC');
INSERT INTO albums VALUES(13,'Back In Black',1980,42.11,10,13);

INSERT INTO songs VALUES(13,'Back In Black',188, '1B', '4.15',1980,2,13,13);
INSERT INTO artist_song VALUES(13,13);


-- Eagles -> 14

INSERT INTO publisher VALUES(14,'Asylum','Los Angeles, CA, US');
INSERT INTO artist VALUES(14,'Eagles');
INSERT INTO albums VALUES(14,'Hotel California',1976,43.28,9,14);

INSERT INTO songs VALUES(14,'Hotel California',147, '1B', '6.31',1976,2,14,14);
INSERT INTO artist_song VALUES(14,14);


-- Little Richard -> 15

INSERT INTO publisher VALUES(15,'CEDOS Music','New Orleans, US');
INSERT INTO artist VALUES(15,'Little Richard');
INSERT INTO albums VALUES(15,'Rock And Roll Rebellion',1958,42.36,12,15);

INSERT INTO songs VALUES(15,'Good Golly Miss Molly',164, '9B', '2.09',1958,2,15,15);
INSERT INTO artist_song VALUES(15,15);

-- Elton John -> 16

INSERT INTO publisher VALUES(16,'Shawnee Press','London, UK');
INSERT INTO artist VALUES(16,'Elton John');
INSERT INTO albums VALUES(16,'Too Low for Zero',1983,44.23,13,16);

INSERT INTO songs VALUES(16,"I'm Still Standing",177, '2B', '3.03',1983,2,16,16);
INSERT INTO artist_song VALUES(16,16);


-- KISS -> 17

INSERT INTO publisher VALUES(17,'Electric Lady Studios','New York, US');
INSERT INTO artist VALUES(17,'KISS');
INSERT INTO albums VALUES(17,'Dynasty',1979,39.19,9,17);

INSERT INTO songs VALUES(17,"I Was Made for Lovin' You",128, '1B', '4.31',1979,2,17,17);
INSERT INTO artist_song VALUES(17,17);

-- Elvis Presley -> 18

INSERT INTO publisher VALUES(18,'RCA Victor','California, US');
INSERT INTO artist VALUES(18,'Elvis Presley');
INSERT INTO albums VALUES(18,'Jailhouse Rock',1957,10.36,5,18);

INSERT INTO songs VALUES(18,'Jailhouse Rock',167, '3A', '2.26',1957,2,18,18);
INSERT INTO artist_song VALUES(18,18);


-- Blue Swede -> 19

INSERT INTO publisher VALUES(19,'EMI','Memphis, US');
INSERT INTO artist VALUES(19,'Blue Swede');
INSERT INTO albums VALUES(19,'Hooked On The Feeling',1974,47.53,10,19);

INSERT INTO songs VALUES(19,'Hooked On The Feeling',118, '4B', '2.52',1973,2,19,19);
INSERT INTO artist_song VALUES(19,19);


-- Survivor -> 20

INSERT INTO publisher VALUES(20,'Heinemann','California, US');
INSERT INTO artist VALUES(20,'Survivor');
INSERT INTO albums VALUES(20,'Eye of the Tiger',1982,38.11,9,20);

INSERT INTO songs VALUES(20,'Eye of the Tiger',109, '5A', '4.05',1985,2,20,20);
INSERT INTO artist_song VALUES(20,20);


-- Snoop Dogg -> 21

INSERT INTO publisher VALUES(21,'Hustler','New York, US');
INSERT INTO artist VALUES(21,'Snoop Dogg');
INSERT INTO albums VALUES(21,'Doggystyle',1993,54.44,19,21);

INSERT INTO songs VALUES(21,'Gin and Juice',95, '9B', '3.32',1993,4,21,21);
INSERT INTO artist_song VALUES(21,21);


-- Tupac Shakur -> 22

INSERT INTO publisher VALUES(22,'Interscope Records','New York, US');
INSERT INTO artist VALUES(22,'Tupac Shakur');
INSERT INTO albums VALUES(22,'All Eyes on Me',1996,132.20,27,21);

INSERT INTO songs VALUES(22,'All Eyes on Me',93, '3B', '5.07',1996,4,22,22);
INSERT INTO artist_song VALUES(22,22);



-- Jay Z -> 23

INSERT INTO publisher VALUES(23,'Roc-A-Fella','New York US');
INSERT INTO artist VALUES(23,'Jay-Z');
INSERT INTO albums VALUES(23,'Life and Times of S. Carter',1999,71.05,16,23);
INSERT INTO albums VALUES(24,'The Black Album',2003,'55.32',14,23);

INSERT INTO songs VALUES(23,"Big Pimpin'",138, '1A', '4.43',1999,4,23,23);
INSERT INTO artist_song VALUES(23,23);
INSERT INTO songs VALUES(24,'99 Problems',90, '1A', '3.54',2003,4,23,24);
INSERT INTO artist_song VALUES(23,24);


-- Alicia Keys -> 24

INSERT INTO publisher VALUES(25,'Columbia Records','New York, US');
INSERT INTO artist VALUES(25,'Alicia Keys');
INSERT INTO albums VALUES(25,'Girl on Fire',2012,61.14,13,25);
INSERT INTO albums VALUES(26,'Keys',2021,93.24,26,25);

INSERT INTO songs VALUES(25,'Girl on Fire',92, '1B', '3.44',2012,3,25,25);
INSERT INTO artist_song VALUES(25,25);
INSERT INTO songs VALUES(26,'Best of Me',80, '7B', '3.58',2021,3,25,26);
INSERT INTO artist_song VALUES(25,26);


-- Cardi B -> 26 
-- issue with the same primary key 13
-- INSERT INTO publisher VALUES(13,'Atlantic Records','Nassau US');
INSERT INTO artist VALUES(27,'Cardi B');
INSERT INTO albums VALUES(27,'Invasion Of Privacy',2018,48.18,13,27);

INSERT INTO songs VALUES(27,'Get Up 10',93, '3B', '3.51',2018,4,13,27);
INSERT INTO artist_song VALUES(27,27);
INSERT INTO songs VALUES(28,'Drip',130, '1B', '4.21',2018,4,13,27);
INSERT INTO artist_song VALUES(27,28);
INSERT INTO songs VALUES(29,'Bickenhead',156, '9B', '3.01',2018,4,13,27);
INSERT INTO artist_song VALUES(27,29);
INSERT INTO songs VALUES(30,'Bodak Yellow',125, '1A', '3.42',2018,4,13,27);
INSERT INTO artist_song VALUES(27,30);
INSERT INTO songs VALUES(31,'Be Careful',152, '1A', '3.30',2018,4,13,27);
INSERT INTO artist_song VALUES(27,31);


INSERT INTO songs VALUES(54,'Best Life',168, '1B', '4.44',2018,4,13,27);
INSERT INTO artist_song VALUES(27,54);
INSERT INTO songs VALUES(55,'I like it',136, '4A', '4.13',2018,4,13,27);
INSERT INTO artist_song VALUES(27,55);
INSERT INTO songs VALUES(56,'Ring',106, '1A', '2.57',2018,4,13,27);
INSERT INTO artist_song VALUES(27,56);
INSERT INTO songs VALUES(57,'Money Bag',130, '3A', '3.49',2018,4,13,27);
INSERT INTO artist_song VALUES(27,57);
INSERT INTO songs VALUES(58,'Bartier Cardi',138, '3B', '3.44',2018,4,13,27);
INSERT INTO artist_song VALUES(27,58);
INSERT INTO songs VALUES(59,'She Bad',160, '6B', '3.50',2018,4,13,27);
INSERT INTO artist_song VALUES(27,59);
INSERT INTO songs VALUES(60,'Thru Your Phone',122, '8B', '3.08',2018,4,13,27);
INSERT INTO artist_song VALUES(27,60);
INSERT INTO songs VALUES(61,'I do',135, '6A', '3.20',2018,4,13,27);
INSERT INTO artist_song VALUES(27,61);

-- Dolly Parton -> 31

INSERT INTO publisher VALUES(31,'Owe-Par Company','Tennessee US');
INSERT INTO artist VALUES(31,'Dolly Parton');
INSERT INTO albums VALUES(31,'Jolene',1974,24.16,10,31);

INSERT INTO songs VALUES(32,'Jolene',111, '1A', '2.41',1974,5,31,31);
INSERT INTO artist_song VALUES(31,32);
INSERT INTO songs VALUES(33,'When Someone Wants to Leave',98, '4B', '2.05',1974,5,31,31);
INSERT INTO artist_song VALUES(31,33);
INSERT INTO songs VALUES(34,'River of Happiness',104, '8B', '2.18',1974,5,31,31);
INSERT INTO artist_song VALUES(31,34);
INSERT INTO songs VALUES(35,'Early Morning Breeze',88, '7B', '2.46',1974,5,31,31);
INSERT INTO artist_song VALUES(31,35);
INSERT INTO songs VALUES(36,'Highlight of My Life',92, '7B', '2.16',1974,5,31,31);
INSERT INTO artist_song VALUES(31,36);
INSERT INTO songs VALUES(37,'I Will Always Love You',136, '1B', '2.56',1974,5,31,31);
INSERT INTO artist_song VALUES(31,37);
INSERT INTO songs VALUES(38,'Randy',94, '1B', '1.52',1974,5,31,31);
INSERT INTO artist_song VALUES(31,38);
INSERT INTO songs VALUES(39,'Living On Memories of You',87, '8B', '2.45',1974,5,31,31);
INSERT INTO artist_song VALUES(31,39);
INSERT INTO songs VALUES(40,'Lonely Comin Down',163, '1B', '3.13',1974,5,31,31);
INSERT INTO artist_song VALUES(31,40);
INSERT INTO songs VALUES(41,'It Must Be You',93, '1B', '1.51',1974,5,31,31);
INSERT INTO artist_song VALUES(31,41);


-- Lepa Brena -> 42

INSERT INTO publisher VALUES(42,'Zabava miliona','Yugoslavia');
INSERT INTO artist VALUES(42,'Lepa Brena');
INSERT INTO albums VALUES(42,'Luda za Tobom',1996,41.45,12,42);

INSERT INTO songs VALUES(42,'Sta jej bilo, bilo je',138, '8B', '3.44',1996,6,42,42);
INSERT INTO artist_song VALUES(42,42);
INSERT INTO songs VALUES(43,'Ti si moj greh',173, '1A', '3.23',1996,6,42,42);
INSERT INTO artist_song VALUES(42,43);
INSERT INTO songs VALUES(44,'Lagarija lagara',101, '2B', '3.36',1996,6,42,42);
INSERT INTO artist_song VALUES(42,44);
INSERT INTO songs VALUES(45,'Sve Mi Dobro Ide Osim Ljubavi',128, '7A', '3.37',1996,6,42,42);
INSERT INTO artist_song VALUES(42,45);
INSERT INTO songs VALUES(46,'Luda za Tobom',157, '2B', '3.11',1996,6,42,42);
INSERT INTO artist_song VALUES(42,46);
INSERT INTO songs VALUES(47,'Takve i Bog cuva',116, '1B', '2.45',1996,6,42,42);
INSERT INTO artist_song VALUES(42,47);
INSERT INTO songs VALUES(48,'Ljubav je...',105, '8B', '3.27',1996,6,42,42);
INSERT INTO artist_song VALUES(42,48);
INSERT INTO songs VALUES(49,'Otvori se nebo',130, '1A', '3.19',1996,6,42,42);
INSERT INTO artist_song VALUES(42,49);
INSERT INTO songs VALUES(50,'Dominantan',115, '1B', '2.36',1996,6,42,42);
INSERT INTO artist_song VALUES(42,50);
INSERT INTO songs VALUES(51,'Ne mogu da te prebolim',183, '1B', '3.25',1996,6,42,42);
INSERT INTO artist_song VALUES(42,51);
INSERT INTO songs VALUES(52,'Pariski lokal',75, '9B', '5.04',1996,6,42,42);
INSERT INTO artist_song VALUES(42,52);
INSERT INTO songs VALUES(53,'Prokleti zlatnici',123, '5A', '3.37',1996,6,42,42);
INSERT INTO artist_song VALUES(42,53);

-- QUERIES

SELECT artist_song.song_id, songs.song_title, artist.artist_name,songs.song_bpm, songs.song_key, 
songs.song_duration, songs.release_year, genres.genre_name, 
publisher.publisher_name, albums.album_name
FROM songs
JOIN genres ON songs.genre_id = genres.genre_id
JOIN publisher ON songs.publisher_id = publisher.publisher_id
JOIN albums ON songs.album_id = albums.album_id 
JOIN artist_song ON artist_song.song_id = songs.song_id
JOIN artist ON artist.artist_id = artist_song.artist_id;