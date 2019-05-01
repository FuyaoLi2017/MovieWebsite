
import java.sql.*;
import java.lang.String;

public class LoadData {

	public GenerateCSV generateCSV;

	public LoadData() {
		generateCSV = new GenerateCSV();
		generateCSV.run();
	}


	public static void main(String[] args) {
		
		// generate the csv files
		LoadData db = new LoadData();
		
		PreparedStatement pstmt =null;
		try {
	           Class.forName("com.mysql.jdbc.Driver").newInstance();
	           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false", "mytestuser", "mypassword");
	           
	           pstmt = connection.prepareStatement("SET GLOBAL local_infile = 1");
	           pstmt.executeQuery();
	           System.out.println("Set the global variable, enable to read local file.");
	           
	           pstmt = connection.prepareStatement("LOAD DATA LOCAL INFILE 'movies.csv' INTO TABLE movies FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS");
	           pstmt.executeUpdate();
	           System.out.println("finish importing movies.csv");
	           
	           pstmt = connection.prepareStatement("LOAD DATA LOCAL INFILE 'stars.csv' INTO TABLE stars FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS");
	           pstmt.executeUpdate();
	           System.out.println("finish importing stars.csv");
	           
	           pstmt = connection.prepareStatement("LOAD DATA LOCAL INFILE 'genres.csv' INTO TABLE genres FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS");
	           pstmt.executeUpdate();
	           System.out.println("finish importing genres.csv");
	           
	           pstmt = connection.prepareStatement("LOAD DATA LOCAL INFILE 'genres_in_movies.csv' INTO TABLE genres_in_movies FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS");
	           pstmt.executeUpdate();
	           System.out.println("finish importing genres_in_movies.csv");
	           
	           pstmt = connection.prepareStatement("LOAD DATA LOCAL INFILE 'stars_in_movies.csv' INTO TABLE stars_in_movies FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS");
	           pstmt.executeUpdate();
	           System.out.println("finish importing stars_in_movies.csv");
	           pstmt.close();
	           connection.close();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	}
};
