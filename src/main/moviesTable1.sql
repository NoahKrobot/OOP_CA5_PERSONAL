-- Main Author: Mariela Machuca Palmeros
-- Second table: Sean Mooney

DROP DATABASE IF EXISTS `hollywood_database`;
CREATE DATABASE `hollywood_database`;
USE `hollywood_database`;
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
  `MOVIE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MOVIE_NAME` varchar(255) NOT NULL,
  `DIRECTOR_NAME` varchar(50) NOT NULL,
  `GENRE` varchar(50) NOT NULL,
  `STUDIO` varchar(255) NOT NULL,
  `YEAR` int(11) NOT NULL,
  `BOXOFFICE_GAIN` FLOAT NOT NULL,

  PRIMARY KEY  (`MOVIE_ID`)
  );
DROP TABLE IF EXISTS `actors`;


CREATE TABLE actors (
                         ACTOR_ID INT(11) NOT NULL AUTO_INCREMENT,
                         ACTOR_NAME VARCHAR(255) NOT NULL,
                         ACTOR_AGE INT(11) NOT NULL,
                         MOVIE_ID_FK INT,
                         FOREIGN KEY (MOVIE_ID_FK) REFERENCES movies(MOVIE_ID),
                         PRIMARY KEY  (`ACTOR_ID`)
);

INSERT INTO movies VALUES (null, "Dune: Part One", "Denis Villenueve", "Sci-Fi", "Warner Bros. Picture", 2021, 434.8),
  			(null, "Blade Runner 2049", "Denis Villenueve", "Sci-Fi", "Warner Bros. Picture", 2017, 267.7),
  			(null, "Poor Things", "Yorgos Lanthimos", "Comedy", "Searchlight Pictures", 2023, 104),
            (null, "Interstellar", "Cristopher Nolan", "Sci-Fi", "Paramount Pictures", 2014, 701.7),
            (null, "Barbie", "Greta Gerwig", "Comedy", "Warner Bros. Picture", 2023, 1446),
            (null, "The Lovely Bones", "Peter Jackson", "Drama", "Paramount Pictures", 2010, 93.6),
            (null, "Roma", "Alfonso Cuarón", "Drama", "	Espectáculos Fílmicos El Coyúl Pimienta Films", 2018, 5.1),
            (null, "Aftersun", "Charlotte Wells", "Drama", "A24", 2022, 8.4),
            (null, "Lost in translation", "Sofia Copolla", "Romance", "Focus Features", 2003, 118.7),
            (null, "Hereditary", "Ari Aster", "Horror", "A24", 2018, 82.8);

INSERT INTO actors (ACTOR_NAME, ACTOR_AGE, MOVIE_ID_FK) VALUES
                                                ('Timothée Chalamet', 27, 1),
                                                ('Rebecca Ferguson', 39, 1),
                                                ('Ryan Gosling', 42, 2),
                                                ('Harrison Ford', 80, 2),
                                                ('Emma Stone', 34, 6),
                                                ('Mark Wahlberg', 51, 6),
                                                ('Yalitza Aparicio', 30, 7),
                                                ('Marina de Tavira', 49, 7),
                                                ('Bill Murray', 72, 9),
                                                ('Scarlett Johansson', 38, 9),
                                                ('Toni Collette', 50, 10),
                                                ('Milly Shapiro', 21, 10),
                                                ('Margot Robbie', 32, 5),
                                                ('Ryan Gosling', 42, 5);

