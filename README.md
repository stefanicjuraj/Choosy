<!-- CHOOSY -->
<br />
<div align="center">
    <img src="./images/choosy.jpeg" alt="choosy image">
</div>

<h1 align="center">Choosy</h3>

<div align="justify">
    Choosy is a music player application with the purpose of creating & displaying songs within a music library — with an easy-to-use interface, created as a part of ISTE-330 Database Connectivity and Access.
    <br />
    <br />
    This project is created by IT students at RIT Croatia. We are creating this project with the focus on designing and creating a music streaming library platform named “Choosy”, made for music producers, disc-jockeys, aux-cord DJ-s, Bluetooth DJ-s, and all music listeners and lovers.
    </div>

  <p align="center">
    <br />
    <a href="https://github.com/stefanicjuraj/Choosy/"><strong>Explore documentation »</strong></a>
    <br />
    <a href="https://github.com/stefanicjuraj/Choosy/issues">Send Feedback</a>
    ·
    <a href="https://github.com/stefanicjuraj/Choosy/issues">Request Features</a>
  </p>
</div>

<!-- ABOUT CHOOSY -->
## About Choosy

![choosy-image][choosy-image]

<div align="justify">
    
The purpose of this project is to create and display an application with an easy-to-use interface on the web platform that queries a vast library of songs from various artists. The application will contain several major functionalities, such as playing a song, generating a playlist, deleting a playlist, choosing & adding songs to your favorites, and also have an overview of your account settings.
    
The intended clients and users of this project are music producers, disc-jockeys, aux-cord and Bluetooth haulers, all music listeners and lovers. We tend to achieve a user-friendly interface that connects both the functionality and the visual aspect of using a Java application. We also strive towards providing the best user-experience with an emphasis on a vibrant color scheme and modern design, making our project clear and easy to use.
    
The application covers the user’s needs for a music library and keywords assigned to the songs inside which allows for the functionality of generating playlists made up of songs related based on those keywords. This does not allow the user to insert new songs into the library. The main platform on which Choosy will be desktop as it will mainly be used as a plugin which generates playlists either into its own interface, or other DJ software (this most likely won’t be implemented, but in the real world it would be its main use).
    
</div>

<!-- TECHNOLOGY -->
## Technology

* [![java][java]][java-URL]
* [![mysql][mysql]][MySQL-URL]
* [![springboot][springboot]][SpringBoot-URL]

<!-- FUNCTIONAL REQUIREMENTS -->
## Functional requirements

- [x] Favorites
- [x] Log in / out
- [x] Connect
- [x] Register
- [x] Add song
- [x] Remove song
- [x] Add to playlist
- [x] Remove from playlist
- [x] Remove from favorites
- [x] Users
    - [x] Promote user
    - [x] Ban user
- [x] Delete playlist
- [x] Password hashing
- [x] Browse
- [x] Song details
- [x] Create playlist
- [ ] Generator 

[Open issues »](https://github.com/stefanicjuraj/Choosy/issues) view a full list of functional features and requests.

<!-- DATABASE CONNECTIVITY LAYER -->
## Database Connectivity Layer

<div align="justify">

* songs

table containing columns called song_id, song_title, song_bpm, song_key, song_duration,		release_year, genre_id, publisher_id, album_id. The songs table is connected to many different tables. It is connected to the playlist table in a many-to-many relationship. It’s connected to the		albums, artist, genre, and publisher tables in a one-to-many relationship. All of the IDs and the song_bpm are of data type INT. The song_title is a VARCHAR, the song_key is a CHAR, the song_duration is a DOUBLE, and the release_year is a YEAR data type.

* genres

table containing columns called genre_id and genre_name. it is connected to the songs		table with a one-to-many relationship. The genre_id is of data type INT, while the genre_name is a VARCHAR. 

* publisher

table containing columns called publisher_id, publisher_name, and publisher_state. It is	connected to the songs table in a one-to-many relationship. The publisher_id is of data type INT, while the publisher_name and publisher_state is a VARCHAR.

* albums

table containing columns called album_id, album_name, release_year, album_length, num_of_songs, artist_id. Connected to the songs table in a one-to-many relationship. The album_id, artist_id, and num_of_songs are of INT data type. The album_name is a VARCHAR,		the release_yaer is a YEAR, the album_length is a DOUBLE.

* artist

table containing columns called artist_id and  artist_name. Connected with the albums table	with a one-to-many relationship, and to the songs table with a many-to-many relationship. The artist_id is an INT, while the artist_name is of VARCHAR data type. 

* artist-song

table used to connect tables songs and artist in a many-to-many relationship. It contains columns called song_id and artist_id. Both attributes in this table are of INT data type.

* favorites

table containing columns called favorites_id, favorites_name, num_of_songs, username. Connected to the songs table in a many-to-many relationship. The favorites_id and num_of_songs is of INT data type. The favorites_name and username attributes are of VARCHAR data type.

* favorites-song

table used to connect tables favorites and songs in a many-to-many relationship. It	has columns called song_id and favorites_id. Both attributes are of INT data type.

* playlist

table containing columns called playlist_id, playlist_name, playlist_description, num_of_songs, length_of_playlist, and username. Connected to the songs table in a many-to-many	relationship, and with the users table in a one-to-many relationship. The playlist_id and 			num_of_songs is of INT data type. The playlist_name, playlist_description and username are a VARCHAR, while the length_of_playlist is a DOUBLE data type.
    
* playlist_song

tabe used to connect tables playlist and songs in a many-to-many relationship. It has	columns called song_id and playlist_id. Both attributes are of INT data type.

* users

tables used to store the users of the application with columns called username, fname,		name, and password. Connected to the favorites, playlist tables in a one-to-many relationship. Also	connected to the roles table in a many-to-many relationship. All of the attributes are of VARCHAR 	data type. 

* roles

table containing columns called role_id and role_name. Connected to the users and	 	permissions tables in a many-to-many relationship. The role_id is of INT data type, while the role_name is of VARCHAR.

* user_role

table used to connect tables users and roles in a many-to-many relationship. It  contains 	columns called username, and role_id. The username attribute is of VARCHAR, while the role_id is of INT data type. 

* permissions

table containing columns called permission_id and permission_name. It is connected	to the roles table in a many-to-many relationship. The permission_id is of INT data type, while the permission_name is a VARCHAR.
    
* role-permission

table used to connect tables permissions and roles in a many-to-many relationship. It contains columns called role_id and permission_id. Both attributes are of INT data type.

Within the database layer we have a database that is called “Choosy”. In that database we have fifteen tables. Firstly, we created a user table, used to store all the registered users that can connect to the database. The songs, albums, artist and playlist tables are all used to store important information regarding their corresponding tables. In order to properly connect certain tables (due to the nature of their relationships) we needed to create additional tables which are mainly used as the connection between those tables.

We’ve inserted 50 rows of content into the songs table. Regarding Java programming, we have established a connection to the database (as well as a way to disconnect) and have implemented basic fetch, post, put and remove methods for one of our tables (artist table). We’ve also created a very simple starting GUI. 
    
</div>

Our plan is to make a Java class for each table in the MySQL database. Each of the classes should have requirements, such as: 
  ```java
  fetchP()
  postP()
  putP()
  removeP()
  ```
<!-- BUSINESS LAYER -->
## Business Layer

<div align="justify">
    
The Business layer of our project will focus on combining several objects into one business object, checking authorization of the user, formatting output and input. Regarding formatting inputs and outputs, we will have a simple GUI that displays results.
    <br />
    <br />
Establishing a connection to the database will be the component which connects the Business Layer with the Database Connectivity Layer. Once there is a connection a method will check authorization of the information. The username and password of an already existing user must match. If the user does not already have an account, they have the option to register. To register the client must provide their full name, a suggested username and a password.

The Presentation layer is also close to the Business layer. This layer also consists of objects, as well as The Business layer. Multiple presentation objects will make one Business object. The presentation layer will be a web service (or JavaFX if Spring Boot shows to be too much).
</div>

<!-- PRESENTATION LAYER -->
## Presentation Layer

<div align="justify">
Graphical user interface will be designed to suit and welcome easy-to-use user interface with great user experience. We focus on providing a solution that is simple, and needs no further explanation or documentation before using it. Hence, we strive towards accomplishing a GUI design that is understandable, and which the end-user is fully comfortable with.
<br />
<br />
To achieve that, we will follow UX/UI guidelines along with testing together our functionalities with our design. We will also focus on design principles such as; consistency and standards, user control and freedom, and visibility of system status (navigation).

As mentioned above, we will have several options and screens regarding our JavaFX application. One of the challenging menus will be designed in the form of an account settings, where user’s information when logging in will be visible and stored. This information will only be visible to the end-user who is using the application.

Our program will first take form in JavaFX as it is the easiest for us to use until all of the functionalities are developed. After the requirements are finished, we will move to a more modern solution in the shape of a web application meaning that our presentation layer will be accessible via browser.

The graphical user interface will have corresponding buttons which will generate playlists, create a favorites playlist, and mark a song as favorite. There will also be a side menu in which the user will  have the availability to view their playlists, their favorites and to browse for songs. 

</div>
    
<!-- CONTACT -->
## Contact

[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/jurajstefanic/
[choosy-image]: ./images/choosy-about.jpeg
[java]: https://img.shields.io/badge/java-DD0031?style=for-the-badge&logo=coffeescript&logoColor=white
[java-URL]: https://www.java.com/
[mysql]: https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white
[mysql-URL]: https://www.mysql.com/
[springboot]: https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[springboot-URL]: https://spring.io/projects/spring-boot