import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {

	public HashMap<String, Movie> movies;
	public HashMap<String, Star> stars;
	public HashMap<String, Integer> genres;
	public Document dom;

	public DOMParser() {
		// create maps to hold dom objects
		// key: movieId, value: Movie object
		movies = new HashMap<String, Movie>();
		// key: stageName, value: Star object
		stars = new HashMap<String, Star>();
		// key: genreName, value: number of appearance of that genre
		genres = new HashMap<String, Integer>();
		// all stars with same stagename but not consider as same star
	}
	
	public static void main(String args[]) {
		DOMParser d = new DOMParser();
		d.run();
	}

	public void run() {

		// parse main file
		parseXmlFile("mains243.xml");
		parseDocumentMain();

		// parse actors file
		parseXmlFile("actors63.xml");
		parseDocumentActor();

		// parse cast file
		parseXmlFile("casts124.xml");
		parseDocumentCast();
		
		System.out.println("movie size:" + movies.size());
		System.out.println("stars size:" + stars.size());
//		System.out.println("otherStars size:" + otherStars.size());
		System.out.println("genres size:" + genres.size());
	}

	private void parseXmlFile(String fileName) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(fileName);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseDocumentMain() {
		// get the root elememt
		Element docEle = dom.getDocumentElement();

		// get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("film");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// get the film element
				Element el = (Element) nl.item(i);

				// get the Employee object
				String id = getTextValue(el, "fid").size() == 0 ? null : getTextValue(el, "fid").get(0);
				String director = getTextValue(el, "dirn").size() == 0 ? null : getTextValue(el, "dirn").get(0);
				int year = getIntValue(el, "year");
				String title = getTextValue(el, "t").size() == 0 ? null : getTextValue(el, "t").get(0);

				// remove the leading and trailing spaces
				if (id != null) {
					id = id.trim();
				}
				if (director != null) {
					director = director.trim();
				}
				if (title != null) {
					title = title.trim();
				}

				// get the genres element
				List<String> curGenreTag = getTextValue(el, "cat");
				for (int k = 0; k < curGenreTag.size(); ++k) {
					String s = curGenreTag.get(k);
					if (s != null) {
						s = s.trim();
						curGenreTag.set(k, s.toLowerCase());
					}
					genres.put(curGenreTag.get(k), genres.getOrDefault(curGenreTag.get(k), 0) + 1);
				}

				// ignore movie with duplicate id
				if (movies.containsKey(id)) {
					System.out.println("duplicate movieid, ignored");
					continue;
				}

				// create a Movie object
				Movie curMovie = null;
				if (title == null) {
					System.out.println("movie title is null, movie(" + id + ") can't be added");
				} else if (year == -1) {
					System.out.println("movie year is null, movie(" + id + ") can't be added");
				} else if (director == null) {
					System.out.println("movie director is null, movie(" + id + ") can't be added");
				} else {
					if (curGenreTag.size() == 0) {
						System.out.println("The movie " + title + " has no genres data.");
						curMovie = new Movie(id, title, year, director, null);
						movies.put(id, curMovie);
					} else {
						curMovie = new Movie(id, title, year, director, curGenreTag);
						movies.put(id, curMovie);
					}
				}

			}
		}
		System.out.println("Complete parsing the main file!");
	}

	private void parseDocumentActor() {
		// get the root elememt
		Element docEle = dom.getDocumentElement();

		// get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("actor");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); ++i) {

				Element el = (Element) nl.item(i);

				String stageName = getSingleTextValue(el, "stagename");
				String firstName = getSingleTextValue(el, "firstname");
				String lastName = getSingleTextValue(el, "familyname");
				int birthYear = getIntValue(el, "dob");
				
				String name = null;
				if (firstName == null && lastName == null) {
					System.out.println("The actor don't have a name, can't add stageName/name cast.");
					continue;
				}
				if (firstName == null) {
					name = lastName.trim();
				}
				if (lastName == null) {
					name = firstName.trim();
				}
				
				name = firstName + " " + lastName;
				
				if (birthYear == -1) {
					System.out.println("The birth year of the actor with stage name: " + stageName + " is unknown or invalid.");
				}

				if (stars.containsKey(stageName)) {
					continue;
				} else {
					if (birthYear == -1) {
						Star star = new Star(stageName, name, null);
						stars.put(stageName, star);
					} else {
						Star star = new Star(stageName, name, birthYear);
						stars.put(stageName, star);
					}
				}
			}
		}
		System.out.println("Complete parsing the casts file!");
	}

	private void parseDocumentCast(){
		Element docEle = dom.getDocumentElement();
		
		NodeList nl = docEle.getElementsByTagName("m");
		if(nl != null && nl.getLength() > 0) {
			for (int i = 0 ; i < nl.getLength(); ++ i) {

				Element el = (Element)nl.item(i);
				
				String movieId = getSingleTextValue(el, "f");
				String stageName = getSingleTextValue(el, "a");
				
				// exclude null fields
				if (movieId == null) {
					System.out.println("Can not add star/movie cast without movieId");
				}
				
				if (stageName == null) {
					System.out.println("Can not add star/movie cast without starId");
				}
				
				if (movieId != null) {
					movieId = movieId.trim();
				}
				if (stageName != null) {
					stageName = stageName.trim();
				}
				if (stars.containsKey(stageName) && movies.containsKey(movieId)) {
					stars.get(stageName).setMovieId(movieId);
				} else {
					if (!movies.containsKey(movieId)) {
						System.out.println("The movie with movieId " + movieId + " is not found.");
					} else {
						System.out.println("The actor with stage name " + stageName + " could not be found.");
					}
				}
				
			}
		}
		System.out.println("Complete parsing the cast file!");
	}
	
	// get a list text values for the required fields
	private List<String> getTextValue(Element ele, String tagName) {
		List<String> textVal = new ArrayList<String>();
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); ++i) {
				Element el = (Element) nl.item(i);
				if (el.getFirstChild() != null) {
					textVal.add(el.getFirstChild().getNodeValue());
				}
			}
		}
		return textVal;
	}

	private String getSingleTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			if (el.getFirstChild() != null) {
				textVal = el.getFirstChild().getNodeValue();
			}
		}

		return textVal;
	}

	private int getIntValue(Element ele, String tagName) {
		// match a string
		String pattern = "^\\d+$";
		// in production application you would catch the exception
		String content;
		if (getSingleTextValue(ele, tagName) == null || getSingleTextValue(ele, tagName).length() == 0) {
			content = null;
		} else {
			content = getSingleTextValue(ele, tagName).trim();
		}
		if (content != null && Pattern.matches(pattern, content)) {
			return Integer.parseInt(content);
		}
		return -1;
	}
}
