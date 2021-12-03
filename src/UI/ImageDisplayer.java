package UI;

import Logic.ImageLoader;
import Logic.ImageViewPane;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class ImageDisplayer {

    private Scene view;
    private BorderPane basePane;

    private List<Image> images;
    private List<Image> notViewedImages;

    private final String photosFolderPath;
    private final String labelMessage;

    private ImageView imageView;

    public ImageDisplayer(String message, String folderPath) {
        this.photosFolderPath = folderPath;
        this.labelMessage = message;

        init();
        generateScene();
    }

    public Scene getScene() {
        return this.view;
    }

    private void generateScene() {
        imageView.setImage(generateImage());

        ImageViewPane imagePane = new ImageViewPane();
        imagePane.setImageView(imageView);

        Button switchImage = switchImageButton();

        ToolBar bottomToolbar = new ToolBar();
        bottomToolbar.setOrientation(Orientation.HORIZONTAL);
        bottomToolbar.getItems().add(switchImage);

        Label message = new Label(this.labelMessage);
        message.setFont(new Font("Vernada", 20));

        this.basePane.setTop(message);
        this.basePane.setCenter(imagePane);
        this.basePane.setBottom(bottomToolbar);

        this.view = new Scene(basePane);
    }

    private void init() {
        this.basePane = new BorderPane();
        this.basePane.setPadding(new Insets(10, 10, 10, 10));

        this.imageView = new ImageView();
        imageView.setPreserveRatio(true);

        ImageLoader imageLoader = new ImageLoader(this.photosFolderPath);
        this.images = imageLoader.getImages();
        this.notViewedImages = new ArrayList<>();
        fullNotViewedImages();
    }

    private Button switchImageButton() {
        Button button = new Button("Next");

        button.setOnAction(event -> {
            Image randomImage = generateImage();
            imageView.setImage(randomImage);
        });

        return button;
    }

    private Image generateImage() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(this.notViewedImages.size());
        Image image = this.notViewedImages.get(randomIndex);

        updateNotViewedImages(randomIndex);

        return image;
    }

    private void updateNotViewedImages(int index) {
        this.notViewedImages.remove(index);
        if (this.notViewedImages.isEmpty()) {
            fullNotViewedImages();
        }
    }

    private void fullNotViewedImages() {
        for (Image im : images) {
            notViewedImages.add(im);
        }
    }

}
