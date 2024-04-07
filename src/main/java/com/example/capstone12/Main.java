package com.example.capstone12;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for the image presenter program used in capstone 12.
 *
 * @author Macintosh_Fan
 */
public final class Main extends Application {
    /**
     * The title to display on the top of the window.
     */
    public static final String TITLE = "Image presenter";

    /**
     * The window width.
     */
    public static final int WIDTH = 800;

    /**
     * The window height.
     */
    public static final int HEIGHT = 500;

    /**
     * WARNING: advanced config. The program screen configuration file.
     */
    public static final String FXML_FILENAME = "main-view.fxml";

    /**
     * The scene of the program.
     */
    public static Scene scene;

    /**
     * JavaFX initialization.
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if an error occurs during loading
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FXML_FILENAME));
        scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();

        // *** CUSTOM TO THIS PROJECT ***
        scene.setOnKeyPressed(fxmlLoader.getController());
    }

    /**
     * The main method.
     *
     * @param args unused in this program
     */
    public static void main(String[] args) {
        launch();
    }
}
