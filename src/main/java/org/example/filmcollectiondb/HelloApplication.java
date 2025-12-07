package org.example.filmcollectiondb;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private MovieRepository repo = new MovieRepository();

    // GUI Components
    private TableView<Movie> table = new TableView<>();
    private TextField titleInput, yearInput;
    private ComboBox<String> genreComboBox;
    private TextField newGenreInput;

    // Main Button
    private Button mainActionButton;

    // Variables for Edit Mode
    private boolean isEditMode = false;
    private int currentEditingId = 0;

    @Override
    public void start(Stage stage) {
        // SETUP TABLE
        TableColumn<Movie, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(40);

        TableColumn<Movie, String> titleCol = new TableColumn<>("Judul Film");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setPrefWidth(200);

        TableColumn<Movie, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreCol.setPrefWidth(100);

        TableColumn<Movie, Integer> yearCol = new TableColumn<>("Tahun");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        table.getColumns().addAll(idCol, titleCol, genreCol, yearCol);

        // GENRE
        Label genreLabel = new Label("1. Kelola List Genre");
        genreLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        genreLabel.setTextFill(Color.DARKBLUE);

        newGenreInput = new TextField();
        newGenreInput.setPromptText("Ketik Genre Baru");
        Button addGenreBtn = new Button("Simpan Genre");
        addGenreBtn.setOnAction(e -> actionAddGenre());

        HBox genreLayout = new HBox(10, newGenreInput, addGenreBtn);

        // FORM INPUT
        Label filmLabel = new Label("2. Data Film (Tambah / Edit)");
        filmLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        filmLabel.setTextFill(Color.DARKBLUE);

        titleInput = new TextField();
        titleInput.setPromptText("Judul Film");

        genreComboBox = new ComboBox<>();
        genreComboBox.setPromptText("- Pilih Genre -");
        genreComboBox.setPrefWidth(150);

        yearInput = new TextField();
        yearInput.setPromptText("Tahun");
        yearInput.setPrefWidth(80);

        // ADD / UPDATE
        mainActionButton = new Button("Tambah Film");
        mainActionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        mainActionButton.setOnAction(e -> actionSaveMovie());

        Button cancelEditBtn = new Button("Batal Edit");
        cancelEditBtn.setVisible(false); // Hide, will appear when editing
        cancelEditBtn.setOnAction(e -> resetForm());

        HBox inputLayout = new HBox(10, titleInput, genreComboBox, yearInput, mainActionButton, cancelEditBtn);

        // EDIT & REMOVE
        Button editButton = new Button("Edit Terpilih"); // TOMBOL BARU
        editButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            Movie selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                setupEditMode(selected, cancelEditBtn);
            }
        });

        Button deleteButton = new Button("Hapus Terpilih");
        deleteButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> actionDeleteMovie());

        HBox tableActions = new HBox(10, editButton, deleteButton);
        tableActions.setPadding(new Insets(5, 0, 0, 0));

        // MAIN LAYOUT
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        mainLayout.getChildren().addAll(
                genreLabel, genreLayout, new Separator(),
                filmLabel, inputLayout, new Separator(),
                table, tableActions
        );

        refreshTable();
        refreshGenreList();

        Scene scene = new Scene(mainLayout, 700, 600);
        stage.setTitle("Film Manager Pro - Edit Mode");
        stage.setScene(scene);
        stage.show();
    }

    // UPDATE & EDIT

    // Activates Edit Mode when pressing "Edit" button
    private void setupEditMode(Movie movie, Button cancelBtn) {
        isEditMode = true;
        currentEditingId = movie.getId(); // saves Film ID that is currently being edited

        // Fills form with the data that is chosen
        titleInput.setText(movie.getTitle());
        yearInput.setText(String.valueOf(movie.getYear()));
        genreComboBox.setValue(movie.getGenre());

        // Changes button's appearence
        mainActionButton.setText("Simpan Perubahan");
        mainActionButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;"); // Jadi biru
        cancelBtn.setVisible(true);
    }

    // Main Button Functions (To Add, To Update)
    private void actionSaveMovie() {
        try {
            String title = titleInput.getText();
            String genre = genreComboBox.getValue();
            String yearText = yearInput.getText();

            if (title.isEmpty() || genre == null || yearText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Mohon lengkapi semua data!");
                return;
            }

            int year = Integer.parseInt(yearText);

            if (isEditMode) {
                // UPDATE'S LOGIC
                repo.updateMovie(currentEditingId, title, genre, year);
                showAlert(Alert.AlertType.INFORMATION, "Data Berhasil Diupdate!");
            } else {
                // ADD'S LOGIC
                repo.addMovie(title, genre, year);
            }

            // After saving, form will be reset to normal
            resetForm();
            refreshTable();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Tahun harus angka!");
        }
    }

    // Reset Form (Exits out edit mode)
    private void resetForm() {
        titleInput.clear();
        yearInput.clear();
        genreComboBox.getSelectionModel().clearSelection();

        // Returns status to "Add" mode
        isEditMode = false;
        currentEditingId = 0;
        mainActionButton.setText("Tambah Film");
        mainActionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Balik hijau
    }

    private void actionAddGenre() {
        String newGenre = newGenreInput.getText();
        if (!newGenre.isEmpty()) {
            repo.addGenre(newGenre);
            newGenreInput.clear();
            refreshGenreList();
        }
    }

    private void actionDeleteMovie() {
        Movie selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            repo.deleteMovie(selected.getId());
            refreshTable();
        }
    }

    private void refreshTable() {
        ObservableList<Movie> data = FXCollections.observableArrayList(repo.getAllMovies());
        table.setItems(data);
    }

    private void refreshGenreList() {
        ObservableList<String> genres = FXCollections.observableArrayList(repo.getAllGenres());
        genreComboBox.setItems(genres);
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.show();
    }

    public static void main(String[] args) {
        launch();
    }
}