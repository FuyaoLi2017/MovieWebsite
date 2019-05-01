-- test script for add movie
-- you can only use this script to test once!!!
set @test_newMovieId = "fake_id";
call add_movie(1, 'tt0499473', 'test_movie2', 2019, 'Fuyao Li', 0, 26, 'test_genre2', 0, 'nm9423084', 'test_star2', 1191, @test_newMovieId);
