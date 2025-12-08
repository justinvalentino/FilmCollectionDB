# 🎬 FilmCollectionDB
### FilmCollectionDB adalah final project OOP berupa database koleksi film yang menerapkan konsep CRUD (Create, Read, Update, Delete) yang menggunakan JBDC dan dikelola menggunakan Maven.

### Development Environment Recommendation
- Gunakan **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows)** sebagai IDE dikarenakan mendukung Maaven, JBDC, dan anything-that-relates-to-Java
- Pastikan **[Maven](https://maven.apache.org/)** sudah terpasang pada System untuk melakukan build, dependency management, dan run aplikasi

## EXPLANATION:
### DBConnections.java
pengelola koneksi ke database SQLite

### MovieRepository.java
berperan atas semua CRUD features yang ada ke database
#### a. createTables()
#### b. addMovie(title, genre, year)
#### c. deleteMovie(id)
#### d. updateMovie(title, genre, year)
#### e. getAllMovies()
#### f. addGenre(genreName)
#### g. getAllGenres()

### Movie.java
class model untuk movie <br>
Attributes:
- id
- title
- genre
- year

### HelloApplication.java
berperan seluruh UI component & interaksi CRUD
#### a. start(Stage)
#### b. actionSaveMovie()
#### c. setupEditMode(movie)
#### d. resetForm()
#### e. actionDeleteMovie()
#### f. actionAddGenre()
#### g. refreshTable()
#### h. refreshGenreList()
