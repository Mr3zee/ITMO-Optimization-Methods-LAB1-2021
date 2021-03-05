package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // TODO: 05.03.2021 графики следами шрека
        setupSplitPane();
        loadFont();
        setupListViews();
    }

    @FXML
    private SplitPane splitPane;

    private void setupSplitPane() {
        splitPane.setDividerPositions(0.4, 0.6);
    }

    private static Font radioFont;

    private void loadFont() {
        try {
            radioFont = Font.loadFont(getClass().getResource("/Shrek-Font.ttf").openStream(), 21);
        } catch (IOException e) {
            radioFont = Font.getDefault();
        }
    }


    @FXML
    private ListView<String> listAlgo;

    private final ObservableList<String> listAlgoNames = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listVariants;

    private final ObservableList<String> listVariantsNames = FXCollections.observableArrayList();

    private static final ToggleGroup algoGroup = new ToggleGroup();

    private static final ToggleGroup variantsGroup = new ToggleGroup();

    private void setupListViews() {
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