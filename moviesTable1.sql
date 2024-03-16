-- Main Author: Mariela Machuca Palmeros

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

