import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GenerateCSV {
	
	public int starId;

    public int genreId;

    public int movieId; 

    public HashMap<String, String> moviesList;
    
    public HashMap<String, String> oldNewMovieIdList;
    
    public HashMap<String, Integer> newGenreIdList;
    
    public HashMap<String, String> newStarIdList;
    
    public DOMParser dpe;
    
    public GenerateCSV () {
    	moviesList = new HashMap<String, String>();
        oldNewMovieIdList = new HashMap<String, String>();
        newGenreIdList = new HashMap<String, Integer>();
        newStarIdList = new HashMap<String, String>();
        dpe = new DOMParser();
        
        getMoviesList();
        getMaxStarId();
        getMaxGenreId();
        getMaxMovieId();
        
        dpe.run();
    }

	public static void main(String[] args) {
		GenerateCSV csv = new GenerateCSV();
		csv.generateMoviesCSV();
		System.out.println("finish generating movies.csv");
		csv.generateStarsCSV();
		System.out.println("finish generating stars.csv");
		csv.generateGenresCSV();
		System.out.println("finish generating genres.csv");
		csv.generateGenreInMoviesCSV();
		System.out.println("finish generating genres_in_movies.csv");
		csv.generateStarInMoviesCSV();
		System.out.println("finish generating stars_in_movies.csv");
	}

	
	public void run() {
		generateMoviesCSV();
		System.out.println("finish generating movies.csv");
		generateStarsCSV();
		System.out.println("finish generating stars.csv");
		generateGenresCSV();
		System.out.println("finish generating genres.csv");
		generateGenreInMoviesCSV();
		System.out.println("finish generating genres_in_movies.csv");
		generateStarInMoviesCSV();
		System.out.println("finish generating stars_in_movies.csv");
		
	}
	
	private void generateMoviesCSV() {
		HashMap<String, Movie> movies = dpe.movies;
		 try {
		        File csv = new File("movies.csv");
		        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
		        int count = 0;
		        Iterator<Map.Entry<String, Movie>> entries = movies.entrySet().iterator();
		        while (entries.hasNext()) {
		            Map.Entry<String, Movie> entry = entries.next();
		            Movie movie = entry.getValue();
		            if (moviesList.containsKey(movie.getTitle())) {
		            	// link old movie id to the list
		            	oldNewMovieIdList.put(movie.getId(), moviesList.get(movie.getTitle()));
		            }
		            bw.newLine();
		            String id = "tt0" + movieId;
		            // keep a record of old new movie id
		            oldNewMovieIdList.put(movie.getId(), id);
		            String title = movie.getTitle();
		            Integer year = movie.getYear();
		            String director = movie.getDirector();
		            bw.write(id+ "," + title + "," + year + "," + director);
		            count++;
		            movieId++;
		        }
		        System.out.println("total number of movie records: " + count);
		        bw.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}
	
	
	private void generateStarsCSV() {
		HashMap<String, Star> stars = dpe.stars;
		try {
			int count = 0;
	        File csv = new File("stars.csv");
	        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
	        Iterator<Map.Entry<String, Star>> entries = stars.entrySet().iterator();
	        while (entries.hasNext()) {
	            Map.Entry<String, Star> entry = entries.next();
	            Star star = entry.getValue();
	            bw.newLine();
	            String id = "nm" + starId;
	            newStarIdList.put(star.getId(), id);
	            String name = star.getName();
	            Integer birthYear = star.getBirthYear();
	            bw.write(id+ "," + name + "," + birthYear);
	            count++;
	            starId++;
	        }
	        System.out.println("total number of star records: " + count);
	        bw.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void generateGenresCSV() {
		HashMap<String, Integer> genres = dpe.genres;
		try {
			int count = 0;
	        File csv = new File("genres.csv");
	        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
	        Iterator<Map.Entry<String, Integer>> entries = genres.entrySet().iterator();
	        while (entries.hasNext()) {
	            Map.Entry<String, Integer> entry = entries.next();
	            // genreid2 is the new id generated
	            int genreid2 = genreId;
	            String genre = entry.getKey();
	            newGenreIdList.put(genre, genreid2);
	            bw.newLine();
	            bw.write(genreid2+ "," + genre);
	            count++;
	            genreId++;
	        }
	        System.out.println("total number of genre records: " + count);
	        bw.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}
	
	private void generateGenreInMoviesCSV() {
		HashMap<String, Movie> movies = dpe.movies;
		try {
			int count = 0;
	        File csv = new File("genres_in_movies.csv");
	        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
	        Iterator<Map.Entry<String, Movie>> entries = movies.entrySet().iterator();
	        while (entries.hasNext()) {
	            Map.Entry<String, Movie> entry = entries.next();
	            Movie movie = entry.getValue();
	            List<String> genres = movie.getGenres();
	            if (genres != null) {
	            	for (String genre : genres) {
		            	bw.newLine();
		            	Integer newGenreId = newGenreIdList.get(genre);
		            	String movieId = oldNewMovieIdList.get(movie.getId());
			            bw.write(newGenreId + "," + movieId);
			            count++;
		            }
	            }
	        }
	        System.out.println("total number of genres_in_movies records: " + count);
	        bw.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}
	
	private void generateStarInMoviesCSV() {
		HashMap<String, Star> stars = dpe.stars;
		try {
			int count = 0;
	        File csv = new File("stars_in_movies.csv");
	        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
	        Iterator<Map.Entry<String, Star>> entries = stars.entrySet().iterator();
	        while (entries.hasNext()) {
	            Map.Entry<String, Star> entry = entries.next();
	            Star star = entry.getValue();
	            List<String> movieIdList = star.getMovieId();
	            if (movieIdList != null) {
	            	for (String movieId : movieIdList) {
		            	String movieId2 = oldNewMovieIdList.get(movieId);
		            	String starId = newStarIdList.get(star.getId());
		            	bw.newLine();
		            	bw.write(starId+ "," + movieId2);
		            	count++;
		            }
	            }
	        }
	        System.out.println("total number of stars_in_movies records: " + count);
	        bw.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void getMaxStarId() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false", "mytestuser", "mypassword");
            PreparedStatement pstmt = connection.prepareStatement("select max(id) from stars");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                String id = result.getString(1);
                if (id != null) {
                    id = id.substring(2);
                    starId = Integer.parseInt(id) + 1;
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private void getMaxGenreId() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false", "mytestuser", "mypassword");
            PreparedStatement pstmt = connection.prepareStatement("select max(id) from genres");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                genreId = result.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private void getMaxMovieId() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false", "mytestuser", "mypassword");
            PreparedStatement pstmt = connection.prepareStatement("select max(id) from movies");
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                String id = result.getString(1);
                if (id != null) {
                    id = id.substring(2);
                    movieId = Integer.parseInt(id) + 1;
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private void getMoviesList() {
        try {
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false", "mytestuser", "mypassword");
           PreparedStatement pstmt = connection.prepareStatement("select title, id from movies");
           ResultSet result = pstmt.executeQuery();
           moviesList.clear();

           while (result.next()) {
               String title = result.getString(1);
               String id = result.getString(2);
               moviesList.put(title, id);
           }
           result.close();
           pstmt.close();
           connection.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
	
}
