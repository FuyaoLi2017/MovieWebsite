### Project 2 substring matching
In the search function, our team use the LIKE '%CONTENT%' to warp up all valid input fields and use AND to connect these conditions.
The backend will do the query based on the search criteria.

- the query for search is
```sql
-- without rating
"Select distinct m.id, m.title, m.year, m.director from movies m, stars_in_movies sm, stars s"
					+ " WHERE m.id = sm.movieId AND sm.starId = s.id AND m.title LIKE \"%" + title
					+ "%\" AND m.year LIKE \"%" + year + "%\" AND m.director LIKE \"%" + director
					+ "%\" AND s.name LIKE \"%" + star + "%\"" + " ORDER BY " + sequenceBase + " " + ascOrDes + " LIMIT " + limit
					+ " OFFSET " + offset;
-- with rating    
"SELECT distinct m.id, m.title, m.year, m.director, r.rating, r.numVotes from movies m,  stars_in_movies sm, stars s, "
					+ "(select movieId, rating, numVotes from ratings) as r where r.movieId = m.id AND m.id = sm.movieId AND sm.starId = s.id AND m.title LIKE \"%"
					+ title + "%\" AND m.year LIKE \"%" + year + "%\" AND m.director LIKE \"%" + director
					+ "%\" AND s.name LIKE \"%" + star + "%\"" + " order by " + sequenceBase + " " + ascOrDes
					+ " limit " + limit + " OFFSET " + offset;
```

- Browse function are implemented in a similar way
