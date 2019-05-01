# DOM parser

## DOMParser.java
### mains243.xml

- assume the id is unique in the movies, movie with same id will only be added once. The following records will be ignored
- the movie must have a title, year, director, and it is optional to have a genre or not

### actors63.xml
- if the records' firstname and familyname are all empty, just skip this record.

### cast124.xml
- make sure stage name and movieId is always valid

# GenerateCSV.java
- using hashmap to do it.

# LoadData.java
- This file is used to load the csv files to the database.


## improvement
- using csv to load file
- using hashmap to store temp result to improve performance
- can do parsing and inserting very quickly
