package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button randomColorButton;

    @FXML
    private TextField colorTextField;

    @FXML
    private Label colorLabel;

    private final Random randomNumberGenerator = new Random();

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        setInitialLabelColor();
    }

    private void setInitialLabelColor() {
        setLabelColor(Color.CADETBLUE.toString());
    }

    private void setLabelColor(final String colorString) {
        try {
            final Color color = Color.valueOf(colorString);
            colorLabel.setStyle(
                    "-fx-background-color: #" + color.toString().substring(2));
        } catch (NullPointerException | IllegalArgumentException ignore) { }
    }

    @FXML
    private void handleRandomColorButtonAction(final ActionEvent event) {
        setLabelColor(generateRandomColorString());
    }

    private String generateRandomColorString() {
        final double red = randomNumberGenerator.nextDouble();
        final double green = randomNumberGenerator.nextDouble();
        final double blue = randomNumberGenerator.nextDouble();
        return Color.color(red, green, blue).toString();
    }

    @FXML
    private void handleColorTextFieldAction(final ActionEvent event) {
        final String colorString = colorTextField.getText();
        if (!colorString.isEmpty()) {
            setLabelColor(colorTextField.getText());
        }
    }

}