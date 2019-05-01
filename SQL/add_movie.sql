-- store procedure for add movie
-- current version supports the add new movie function, update function to be finished

--  first drop the procedure to debug, if needed
-- DROP PROCEDURE add_movie;
use moviedb;
-- change delimiter to $$
DELIMITER $$

CREATE procedure add_movie (
IN type INT,
IN movieId VARCHAR(10),
IN title VARCHAR(100),
IN year INT,
IN director VARCHAR(100), 
IN existingGenre INT,
IN genreId INT,
IN genreName VARCHAR(32),
IN existingStar INT,
IN starId VARCHAR(10), 
IN starName VARCHAR(100),
IN birthYear INT,
OUT newMovieId VARCHAR(10))
BEGIN
	IF (type = 1) THEN -- ADD NEW MOVIE
		-- INSERT A NEW MOVIE
		INSERT INTO movies values(movieId, title, year, director);

        -- IF THE GENRE IS A EXISTING GENRE
        IF(existingGenre = 1) THEN
			INSERT INTO genres_in_movies(genreId, movieId) values(genreId, movieId);
		ELSE
		-- IF THE GENRE NEEDS TO BE CREATED
			INSERT INTO genres(id, name) values(genreId, genreName);
            INSERT INTO genres_in_movies(genreId, movieId) values(genreId, movieId);
		END IF;
        
        -- IF THE STAR IS A EXISTING GENRE
        IF(existingStar = 1) THEN
			INSERT INTO stars_in_movies(starId, movieId) values(starId, movieId);
		ELSE
        -- IF THE STAR NEEDS TO BE CREATED
			INSERT INTO stars(id, name, birthYear) values(starId, starName, birthYear);
            INSERT INTO stars_in_movies(starId, movieId) values(starId, movieId);
		END IF;
        
		SET newMovieId=movieId;
    
    END IF;
END
$$
-- Change back DELIMITER to ;
DELIMITER ; 

