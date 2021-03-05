package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<String> listAlgo;

    private final ObservableList<String> listAlgoNames = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listVariants;

    private final ObservableList<String> listVariantsNames = FXCollections.observableArrayList();

    private static final ToggleGroup algoGroup = new ToggleGroup();

    private static final ToggleGroup variantsGroup = new ToggleGroup();

    private static final Font radioFont = Font.loadFont("https://fonts.googleapis.com/css2?family=Chicle&display=swap", 19);

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        listAlgo.setCellFactory(a -> new RadioListCell(algoGroup));
        listVariants.setCellFactory(a -> new RadioListCell(variantsGroup));

        listAlgoNames.addAll(
                "BRENT", "PARABOLIC", "GOLDEN SECTION", "DICHOTOMY", "FIBONACCI"
        );

        listVariantsNames.addAll(
                "VAR_1", "VAR_2", "VAR_3", "VAR_4", "VAR_5", "VAR_6", "VAR_7", "VAR_8", "VAR_9", "VAR_10"
        );

        listAlgo.setItems(listAlgoNames);

        listVariants.setItems(listVariantsNames);
    }

    private static class RadioListCell extends ListCell<String> {
        private final ToggleGroup group;

        private RadioListCell(ToggleGroup group) {
            this.group = group;
        }

        @Override
        public void updateItem(String obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                RadioButton radioButton = new RadioButton(obj);
                radioButton.setToggleGroup(group);
                radioButton.setFont(radioFont);
                // Add Listeners if any
                setGraphic(radioButton);
            }
        }
    }


}