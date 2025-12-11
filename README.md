# Film Collection DB

Aplikasi manajemen koleksi film sederhana berbasis JavaFX dan SQLite.

## Struktur Project

Project ini terdiri dari beberapa class utama yang menangani logika aplikasi, koneksi database, dan antarmuka pengguna.

### 1. `DatabaseConnection.java`
Class ini menangani koneksi ke database SQLite `my_films.db`.

- **`connect()`**
  - Membuat dan mengembalikan objek `Connection` ke database SQLite.
  - Menangani error `SQLException` jika koneksi gagal.

### 2. `Movie.java`
Class model yang merepresentasikan data film.

- **`Movie(int id, String title, String genre, int year)`**
  - Constructor untuk membuat objek Movie baru.
- **`getId()`**
  - Mengembalikan ID film (untuk keperluan database).
- **`getTitle()`**
  - Mengembalikan judul film.
- **`getGenre()`**
  - Mengembalikan genre film.
- **`getYear()`**
  - Mengembalikan tahun rilis film.

### 3. `MovieRepository.java`
Class Data Access Object (DAO) yang menangani operasi CRUD (Create, Read, Update, Delete) ke database.

- **`createTables()`**
  - Membuat tabel `movies` dan `genres` jika belum ada di database.
- **`addMovie(String title, String genre, int year)`**
  - Menambahkan data film baru ke tabel `movies`.
- **`deleteMovie(int id)`**
  - Menghapus film berdasarkan ID dari tabel `movies`.
- **`getAllMovies()`**
  - Mengambil semua data film dari database dan mengembalikannya sebagai `List<Movie>`.
- **`addGenre(String genreName)`**
  - Menambahkan genre baru ke tabel `genres` (mengabaikan jika duplikat).
- **`getAllGenres()`**
  - Mengambil semua nama genre dari database untuk ditampilkan di dropdown.
- **`updateMovie(int id, String title, String genre, int year)`**
  - Memperbarui data film yang sudah ada berdasarkan ID.

### 4. `HelloApplication.java`
Class utama aplikasi yang mengatur antarmuka pengguna (GUI) menggunakan JavaFX.

- **`start(Stage stage)`**
  - Method utama JavaFX untuk inisialisasi dan menampilkan window aplikasi.
  - Mengatur layout tabel, form input, dan tombol aksi.
- **`setupEditMode(Movie movie, Button cancelBtn)`**
  - Mengaktifkan mode edit ketika user memilih film dan menekan tombol "Edit Terpilih".
  - Mengisi form dengan data film yang dipilih dan mengubah tombol "Tambah" menjadi "Simpan Perubahan".
- **`actionSaveMovie()`**
  - Menangani logika tombol utama (Tambah/Simpan).
  - Jika dalam mode edit, memanggil `repo.updateMovie()`.
  - Jika mode tambah, memanggil `repo.addMovie()`.
  - Memvalidasi input sebelum menyimpan.
- **`resetForm()`**
  - Membersihkan input form dan mengembalikan aplikasi ke mode "Tambah Film" dari mode edit.
- **`actionAddGenre()`**
  - Mengambil input genre baru dan menyimpannya menggunakan `repo.addGenre()`.
- **`actionDeleteMovie()`**
  - Menghapus film yang sedang dipilih di tabel menggunakan `repo.deleteMovie()`.
- **`refreshTable()`**
  - Mengambil data terbaru dari database dan memperbarui tampilan tabel.
- **`refreshGenreList()`**
  - Memperbarui isi dropdown genre dengan data terbaru dari database.
- **`showAlert(Alert.AlertType type, String msg)`**
  - Menampilkan dialog pop-up untuk informasi, peringatan, atau error.
- **`main(String[] args)`**
  - Entry point aplikasi java untuk menjalankan `launch()`.

### 5. `HelloController.java`
Class controller default dari JavaFX (saat ini tidak digunakan secara aktif dalam logika utama aplikasi, karena logika GUI ditangani langsung di `HelloApplication`).

- **`onHelloButtonClick()`**
  - Contoh method event handler sederhana.
