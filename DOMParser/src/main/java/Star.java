import java.util.ArrayList;
import java.util.List;

public class Star {

	private List<String> movieId;

    private String id;

    private String name;

    private Integer birthYear;

    public Star(String id, String name, Integer birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.movieId = new ArrayList<String>();
    }

    public Star() {
        this.movieId = new ArrayList<String>();
    }

	public List<String> getMovieId() {
		return movieId;
	}

	// add one movieId
	public void setMovieId(String movieId) {
		this.movieId.add(movieId);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
}
