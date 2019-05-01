# cs122b-winter19-team-10
Repository for CS122B project(Fuyao Li, Ananya Hosabeedu Somappa)

# Project 5 report: [link](Project5_report.md)



# Project 1
## Backend
### Servlet
1. MovieListServlet
- This servlet will map the movieList jsonArray to the URL: /api/movies
- The json format of the jsonArray: each object in the json array follows the following format.
- id field should not be presented in the frontend(movie_id, genreId, starId), it is used for finding information and make queries.
```json
[
	{
		"movie_id": "",
		"movie_title" : "",
		"movie_year" : "",
		"movie_director" : "",
		"genresList" :
		[
			{
				"genreId" : "id1",
				"genreName" : "name1"
			}
			{
				"genreId" : "id2",
				"genreName" : "name2"
			}
		],
		"starsList" : [
			{
				"starId" : "id1",
				"starName" : "name1"
			}
			{
				"starId" : "id2",
				"starName" : "name2"
			}
		],
		"rating" : ""
	}
]

```

2. SingleMovieServlet
- This servlet will map the singleMovieObject jsonObject to the URL: /api/single-movie
- The json format of the singleMovieObject is shown below.
- movieId should be presented. Other id field should not be presented in the frontend(genreId, starId), it is used for finding information and make queries.
```json
[
    {
        "movie_id": "",
        "movie_title" : "",
        "movie_year" : "",
        "movie_director" : "",
        "genresList" :
        [
            {
                "genreId" : "id1",
                "genreName" : "name1"
            }
            {
                "genreId" : "id2",
                "genreName" : "name2"
            }
        ],
        "starsList" : [
            {
                "starId" : "id1",
                "starName" : "name1"
            }
            {
                "starId" : "id2",
                "starName" : "name2"
            }
        ],
        "rating" : ""
    }
]

```
3. SingleStarServlet
- This servlet will map the singleStarObject jsonObject to the URL: /api/single-star
- The json format of the singleStarObject is shown as follows.(example)
- id field should not be presented in the frontend(star_id, movie_id), it is used for finding information and make queries.
```json
[
    {
        "starId" : "",
        "star_name" : "",
        "star_dob" : "",
        "moviesList" : [
            {
                "movie_id" : "id1",
                "movie_title" : "title1",
                "movie_year" : "year1",
                "movie_director" : "director1"
            }
            {
                "movie_id" : "id2",
                "movie_title" : "title2",
                "movie_year" : "year2",
                "movie_director" : "director2"
            }
        ]
    }
]
```
### Deploy
The project is deployed in the following link on AWS instance:
[link](http://ec2-52-53-197-246.us-west-1.compute.amazonaws.com:8080/cs122b-project/)



# Project 2
## Backend
**Remember to login before you make any other operations**
### Servlet

1. LoginServlet(api/login)

- **request parameter:**
    - email
    - password

- **return object**
    - status
    - message
    - data (will disappear if the status is ERROR)

2. BrowseServlet(api/browse)
- **request parameter**
    - browse by genre
        - browseType (should be: genre)
        - category (genre name)
        - sequenceBase (the sequence of the movies,default choice: title)
        - ascOrDes (can be null(default)/asc/dsc)
        - limit (the records per page)
        - offset (the offset position, should be (pageNumber - 1) * limit )

    - browse by firstLetter
        - browseType (should be: firstLetter)
        - category (the start letter, 0 - 9, A - Z)
        - sequenceBase (the sequence of the movies,default choice: title)
        - ascOrDes (can be null(default)/asc/dsc)
        - limit (the records per page)
        - offset (the offset position, should be (pageNumber - 1) * limit )

        - **Be careful!!!!: don't send empty fields to the backend, if the field doesn't contain anything, just don't add that attribute**

- **return object**
    - status
    - message
    - data (a a MovieListVo object, you can check the format in here: https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/blob/backend3/cs122b-project/src/com/cs122b/fablix/entity/vo/MovieListVo.java)


3. SearchServlet(api/search)
- **request parameter**
    - title: use %parameter% to match here
    - year: use %parameter% to match here
    - director: use %parameter% to match here
    - star: use %parameter% to match here
    - sequenceBase (the sequence of the movies,default choice: title)
    - ascOrDes (can be null(default)/asc/dsc)
    - limit (the records per page)
    - offset (the offset position, should be (pageNumber - 1) * limit )
    - **Be careful!!!!: don't send empty fields to the backend, if the field doesn't contain anything, just don't add that attribute**

- **return object**
    - status
    - message
    - data (a MovieListVo object, you can check the format in here: https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/blob/backend3/cs122b-project/src/com/cs122b/fablix/entity/vo/MovieListVo.java)

4. SelectAllGenresServlet(api/genres)
- **request parameter**
    - no parameter for this api

- **return object**
    - status
    - message
    - data (a list of Genre object, you can check the format in here: https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/blob/master/cs122b-project/src/com/cs122b/fablix/entity/pojo/Genre.java)


5. TopMoviesServlet(api/topMovies)
- **request parameter**
    - numOfMovies: number of top rating movies, default is 20, if the frontend don't give any parameter
    - **Be careful!!!!: don't send empty fields to the backend, if the field doesn't contain anything, just don't add that attribute**

- **return object**
    - status
    - message
    - data (a list of MovieVo object, you can check the format in here: https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/blob/master/cs122b-project/src/com/cs122b/fablix/entity/vo/MovieVo.java)


6. AddOrUpdateShoppingCartServlet(api/addOrUpdateShoppingCart)
- **request parameter**
    - movieId: the movie's movieId
    - count: number of the number (updated value)
    - frontend pre-check: there should be a update button to trigger this api, the count should be a integer between 1 - 20. number larger than or smaller than this should just give a tip to the user. We need to add multiply records for each movie count.https://piazza.com/class/jqilta01qxy7p7?cid=240
    And display all of them in the checkout page. A very big number is not reasonable here. May be telling the user to contact the sales company if the they want to buy more in one purchase. For a 0 or negative number, just tell them it is invalid, you can handle this with js code

- **return object**
    - status
    - message: add/update a movie in shopping cart, movieId: " + movieId + ", count: " + count

    - if the input is less than 1 or more than 20, the item won't be added

7. DeleteShoppingCart(/api/deleteShoppingCart)

- **request parameter**
    - movieId: the movie's movieId

- **return object**
    - if the movieId you send me is in the shopping cart, I will be able to delete it.
        - status: SUCCESS
        - message: successfully deleted a movie in shopping cart, movieId: " + movieId
    - if the movieId is not in shopping cart due to unexpected reason, this should not happen, I think we should handle this
        - status: ERROR
        - message: Deleting a non-existing movie! movieId: " + movieId

8. ShoppingCartServlet(api/shoppingCart)
- **request parameter**
    - no parameter here, call this api to display the current list of shoppingCart items

- **return object**
    - status
    - message: current list of shopping cart items
    - data (a list of ShoppingMovieVo)

9. CheckOutServlet(api/checkOut)
- **request parameter**
    - cardId
    - firstName
    - lastName
    - expiration    should be a String in the following format, **yyyy-MM-dd** you need to manipulate the user input here to handle it correctly

- **return object**
    - status
    - message: current list of shopping cart items
    - data (a list of SalesRecord)

#### Message Case analysis:
- if the user fail to login: need to login message
    - loged in, not currentCustomer: session not matched to a customer, need to login
        - miss some fields in the card info: The card information is not complete, can't proceed to checkout.
            - the card info doesn't match anything in the database: The card information is invalid
            - the card match a record in the database
                - if the database insertion fails at some step:Fail to insert data into database
                - if the records are successfully inserted into the database:Checkout Successfully, only in this case the response would have a data field with a list of SalesRecord


# Project 3
## backend
### Servlet
1. AdminLoginServlet(api/adminLogin)
- **request parameter**
    - email
    - password

- **return object**
    - status
    - message
    - data(email, fullname, password(not declared))

- Remember!!!!!!!!!!: for the css and js for adminLogin page, it should be adminLogin.js and adminlogin.js

3. AddNewStarServlet(api/addStar)
- **request parameter**
    - starName(string)
    - birthYear(int)

- **return object**
    - status
    - message
    - data(id, name, birthYear)

4. AddNewMovieServlet(api/addMovie)
- **request parameter**
    - title
    - year
    - director
    - genreId (send this parameter when it is a existing genre)
    - genreName (send this parameter when user want to create a new genre and **do not** send genreId)
    - starId (send this parameter when it is a existing star and **do not** send starName)
    - starName (send this parameter when user want to create a new star and **do not** send starId)
    - birthYear

- **return object**
    - status
    - message
        - if

5. UpdateMovieServlet(api/updateMovie)
    - **request parameter**
    - title
    - year
    - director
    - genreId (send this parameter when it is a existing genre **do not** send when adding new genre)
    - genreName (send this parameter all the time)
    - starId (send this parameter when it is a existing star and **do not** send starName)
    - starName (send this parameter when user want to create a new star and **do not** send starId)
    - birthYear

- **return object**
    - status
    - message
        - adding a existing genre will give you a message containing "genre xx has been already added"
        - adding a existing star will give you a message containing "star xx has been already added"

### PlaceHolder status


5. ShowMetaDataServlet(api/showMetadata)
- **request parameter**
    - no parameter here

- **return object**
    - status
    - message
    - data (a list of DBTableVo)
    [
        {
            tableName: xxx,
            metadataVoList: [
             {
                 attribute: ddd,
                 type: varchar
             }
            ]
        }
    ]

6. SearchGenreServlet(api/searchGenre)
- **request parameter**
    - partialGenre (a substring for searching)

- **return object**
    - status
    - message
    - data: (a list of Genre objects, https://github.com/UCI-Chenli-teaching/cs122b-winter19-team-10/blob/master/cs122b-project/src/com/cs122b/fablix/entity/pojo/Genre.java, this is to be displayed to help to do the search)
    [
        {
            id: a1,
            name: action
        }
    ]

7. SearchStarServlet（api/searchStar)
- **request parameter**
    - partialStarName (a substring for searching)

- **return object**
    - status
    - message
    - data: (a list of Star objects, this is to be displayed to help to do the search), you can display all three fields to allow users to choose
    [
        {
            id: a1,
            name: action,
            birthYear: 2000
        }
    ]

    - **Remember!!!!!! Change all the previous code concerning star birth year, if the birthYear of star is 0, just display "not available", but don't display 0, do some processing**


8. VerifyMovieServlet(api/verifyMovie)
- **request parameter**
    - title
    - year
    - director

- **return object**
    - status
        - SUCCESS: the movie exists in the database
        - ERROR: the movie doesn't exist in the database (if ERROR, you can proceed to next block, or you can't proceed to next block)
    - message


## Project4
1. LoginServlet(api/login)

- **request parameter:**
    - email
    - password
    - mobile(send "mobile")

- **return object**
    - status
    - message
    - data (will disappear if the status is ERROR)


2. FullTextSearchServlet(/api/fullTextSearch)
### Use local storeage to save things

- **request parameter:**
    - query
    - sequenceBase (title/year/rating, default is title)
    - ascOrDes (asc/desc, default is asc)
    - limit (default 20 if this parameter is not sent, auto complete will display the first 20)
    - offset

- **return object**
    - status
    - message
    - data: a AutoCompleteListVo
    {
        numOfRecords:
        numOfPages:
        List<AutoCompleteVo>: (previous list)
    }


    (a list of AutoCompleteVo objects, every object have a value(movie title) and data field(a movieVo corresponding to that movie title))

- **Explanation**
    - In the search, full text search will be triggered first, if it is empty, fuzzy search will be triggered to do the search. The message shows whether it system use fuzzy search of full text search to retrieve the result.

# Project 5
## Google cloud platform ip: http://35.236.56.154/cs122b-project/page/login.html
## AWS platform ip: http://52.53.158.173/cs122b-project/page/login.html
## instance2: http://13.57.248.101:8080/cs122b-project/page/login.html
## instance3: http://54.153.23.28:8080/cs122b-project/page/login.html


http://35.236.56.154/cs122b-project/page/login.html  http://52.53.158.173/cs122b-project/page/login.html  http://13.57.248.101:8080/cs122b-project/page/login.html  http://54.153.23.28:8080/cs122b-project/page/login.html
## Write a servlet to show JMeter results
