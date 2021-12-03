package UI;

import javafx.scene.paint.Color;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class WelcomeWindow extends Application {

    private HBox directoryField;
    private TextField theLabelTextField;
    private Stage stage;

    private Label warningLabel;

    @Override
    public void init() {
        this.directoryField = makeDirectoryFieldLine();
        this.theLabelTextField = new TextField("Type the label of the message you want to show in the final program");

        this.warningLabel = new Label();
        this.warningLabel.setFont(new Font("Vernada", 10));
        this.warningLabel.setTextFill(Color.RED);

    }

    @Override
    public void start(Stage stage) throws Exception {
        initStageField(stage);

        BorderPane basePane = new BorderPane();
        Insets spacing = new Insets(10, 10, 10, 10);
        basePane.setPadding(spacing);

        Label message = new Label("Choose the parameters to run the random image application");

        VBox fields = new VBox();
        fields.setSpacing(10);

        fields.getChildren().addAll(directoryField, theLabelTextField);

        Button applyAndNext = settingsApplier();
        HBox buttonAndLabel = new HBox(5);
        buttonAndLabel.getChildren().addAll(applyAndNext, warningLabel);

        basePane.setTop(message);
        BorderPane.setMargin(message, new Insets(0, 0, 10, 0));
        basePane.setCenter(fields);
        basePane.setBottom(buttonAndLabel);
        BorderPane.setMargin(buttonAndLabel, new Insets(10, 0, 0, 0));

        Scene scene = new Scene(basePane);
        stage.setScene(scene);
        stage.setTitle("Random image application");
        stage.show();
    }

    private HBox makeDirectoryFieldLine() {
        HBox result = new HBox();
        result.setSpacing(10);

        TextField directoryPathField = new TextField("Select direcotory with the photos you want to show");
        Button chooseDirectory = directoryChooser();

        result.getChildren().addAll(directoryPathField, chooseDirectory);

        return result;
    }

    private Button directoryChooser() {
        Button chooseDirectory = new Button("Choose the directory");
        DirectoryChooser directoryChooser = new DirectoryChooser();

        String homeFolder = System.getProperty("user.home");
        directoryChooser.setInitialDirectory(new File(homeFolder));

        chooseDirectory.setOnAction(event -> {
            File selectedDirectory = directoryChooser.showDialog(this.stage);

            try {
                setTextFieldPath(selectedDirectory);
            } catch (NullPointerException e) {
                selectedDirectory = new File(homeFolder);
                setTextFieldPath(selectedDirectory);
            }
        });

        return chooseDirectory;
    }

    private void setTextFieldPath(File file) throws NullPointerException {
        TextField field = getTextFieldOfPath();

        String path = file.getAbsolutePath();
        field.setText(path);
    }

    private TextField getTextFieldOfPath() {
        return (TextField) this.directoryField.getChildren().get(0);
    }

    private Button settingsApplier() {
        Button next = new Button("Apply settings and open the program");

        next.setOnAction(event -> {
            applySettings();
        });

        return next;
    }

    private boolean applySettings() {
        String finalPath = getTextFieldOfPath().getText();

        if (directoryExists(finalPath)) {
            switchScene(finalPath);

            return true;
        } else {
            setWarningText("The path is not correct");
            return false;
        }
    }

    private void setWarningText(String text) {
        this.warningLabel.setText(text);
    }

    private void switchScene(String finalPath) {
        ImageDisplayer displayer = new ImageDisplayer(theLabelTextField.getText(), finalPath);
        this.stage.setScene(displayer.getScene());
        this.stage.setMaximized(true);
    }

    private void initStageField(Stage stage) {
        this.stage = stage;
    }

    private boolean directoryExists(String dirPath) {
        return Files.exists(Paths.get(dirPath));
    }

}
