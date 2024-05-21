package com.example.capstone12.gui;

import com.example.capstone12.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The controller for the main GUI.
 *
 * @author Macintosh_Fan
 */
public class MainController implements EventHandler<KeyEvent> {
    /**
     * The container for holding the two sub-containers.
     */
    @FXML
    public VBox superPane;

    /**
     * The container for holding the contents used in non-presentation mode (welcome screen).
     */
    @FXML
    public VBox mainPane;

    /**
     * The container for holding the contents used in presentation mode.
     */
    @FXML
    public VBox presentationPane;

    /**
     * The image list displayed to the user.
     */
    @FXML
    public ListView<Image> imageListView;

    /**
     * The error label displayed to the user.
     */
    @FXML
    public Label errorLabel;

    /**
     * The image view for presenting images.
     */
    @FXML
    public ImageView imageView;

    /**
     * Is the user presenting?
     */
    public boolean isPresenting = false;

    /**
     * The loaded images.
     */
    public ArrayList<Image> images = null;

    /**
     * Image array index used when presenting.
     */
    public int imageCounter = 0;

    /**
     * Total number of loaded images.
     */
    public int imageCount = 0;

    /**
     * Called when a key is pressed.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(KeyEvent event) {
        if (!isPresenting) {
            return;
        }
        KeyCode key = event.getCode();
        if (key == KeyCode.ESCAPE) {
            presentationPane.setVisible(false);
            presentationPane.setManaged(false);
            mainPane.setVisible(true);
            mainPane.setManaged(true);

            imageCounter = 0;
            isPresenting = false;
            superPane.setStyle("-fx-background-color: WHITE");
        } else {
            Image currentImage;
            if (key == KeyCode.LEFT || key == KeyCode.PAGE_UP && imageCounter != 0) {
                currentImage = images.get(--imageCounter);
            } else if (key == KeyCode.RIGHT || key == KeyCode.PAGE_DOWN && imageCounter + 1 != imageCount) {
                currentImage = images.get(++imageCounter);
            } else {
                return;
            }
            imageView.setFitWidth(Main.scene.getWidth());
            imageView.setFitHeight(Main.scene.getHeight());
            imageView.setImage(currentImage);
        }
    }

    /**
     * Called when the "Import Images" button is pressed, used for moving the selected image up.
     */
    public void onImportImagesButtonClick() {
        FileChooser fileChooser = new FileChooser();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles == null) {
            errorPrompt("No images were selected.");
            return;
        }
        if (images == null) {
            images = new ArrayList<>(selectedFiles.size());
        } else {
            images.ensureCapacity(images.size() + selectedFiles.size());
        }
        for (File imageFile : selectedFiles) {
            images.add(new Image(imageFile.toURI().toString()) {
                @Override
                public String toString() {
                    return imageFile.getName();
                }
            });
            if (images.get(imageCount).isError()) {
                errorPrompt(imageFile.getName() + " is not a valid image file or is not supported.");
                images.remove(imageCount);
            } else {
                imageCount++;
            }
        }

        imageListView.getItems().clear();
        imageListView.getItems().addAll(images);
    }

    /**
     * Called when the "↑" button is pressed, used for moving the selected image up.
     */
    public void onUpButtonClick() {
        if (imageListView.getSelectionModel() != null) {
            int selectedIndex = imageListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == 0) {
                return;
            }
            Collections.swap(images, selectedIndex, selectedIndex - 1);
            Collections.swap(imageListView.getItems(), selectedIndex, selectedIndex - 1);
            imageListView.getSelectionModel().select(selectedIndex - 1);
        }
        imageListView.requestFocus();
    }

    /**
     * Called when the "↓" button is pressed, used for moving the selected image down.
     */
    public void onDownButtonClick() {
        if (imageListView.getSelectionModel() != null) {
            int selectedIndex = imageListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex == images.size() - 1) {
                return;
            }
            Collections.swap(images, selectedIndex, selectedIndex + 1);
            Collections.swap(imageListView.getItems(), selectedIndex, selectedIndex + 1);
            imageListView.getSelectionModel().select(selectedIndex + 1);
        }
        imageListView.requestFocus();
    }

    /**
     * Called when the "✕" button is pressed, used for removing an image to present.
     */
    public void onRemoveButtonClick() {
        if (imageListView.getSelectionModel() != null) {
            int selectedIndex = imageListView.getSelectionModel().getSelectedIndex();
            images.remove(selectedIndex);
            imageListView.getItems().remove(selectedIndex);
            imageCount--;
        }
        imageListView.requestFocus();
    }

    /**
     * Called when the "Start presentation" button is pressed, used for starting a presentation.
     */
    public void onStartButtonClick() {
        if (imageCount == 0) {
            errorPrompt("There are no images to present.");
            return;
        }

        mainPane.setVisible(false);
        mainPane.setManaged(false);
        presentationPane.setVisible(true);
        presentationPane.setManaged(true);

        imageView.setImage(images.get(imageCounter));
        imageView.setFitWidth(Main.scene.getWidth());
        imageView.setFitHeight(Main.scene.getHeight());
        isPresenting = true;
        superPane.setStyle("-fx-background-color: BLACK");
    }

    /**
     * Prompts the specified error message to the user.
     *
     * @param text the error text to display
     */
    public void errorPrompt(String text) {
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        errorLabel.setStyle("-fx-text-fill: red");
        errorLabel.setText(text);
    }
}