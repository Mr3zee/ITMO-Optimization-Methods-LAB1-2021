package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Optimization Swamp");
        stage.setScene(new Scene(root));
        stage.setMinWidth(1280 + 20);
        stage.setMinHeight(720 + 40);
        stage.show();
    }
}