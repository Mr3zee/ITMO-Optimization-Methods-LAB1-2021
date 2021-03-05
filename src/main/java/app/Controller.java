package app;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

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

    private static Font toggleFont;

    private void loadFont() {
        try {
            toggleFont = Font.loadFont(getClass().getResource("/Shrek-Font.ttf").openStream(), 20);
        } catch (IOException e) {
            toggleFont = Font.getDefault();
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
        listAlgo.setCellFactory(a -> new ToggleListCell(algoGroup));
        listVariants.setCellFactory(a -> new ToggleListCell(variantsGroup));

        listAlgoNames.addAll(
                "BRENT", "PARABOLIC", "GOLDEN SECTION", "DICHOTOMY", "FIBONACCI"
        );

        listVariantsNames.addAll(
                "VAR_1", "VAR_2", "VAR_3", "VAR_4", "VAR_5", "VAR_6", "VAR_7", "VAR_8", "VAR_9", "VAR_10"
        );

        listAlgo.setItems(listAlgoNames);

        listVariants.setItems(listVariantsNames);

        algoGroup.selectedToggleProperty().addListener(changeToggleImage);
        variantsGroup.selectedToggleProperty().addListener(changeToggleImage);
    }

    @SuppressWarnings("unchecked")
    private static final ChangeListener<Toggle> changeToggleImage = (o, oldT, newT) -> {
        if (oldT != null) {
            ((Consumer<Boolean>) oldT.getUserData()).accept(false);
        }
        if (newT != null) {
            ((Consumer<Boolean>) newT.getUserData()).accept(true);
        }
    };

    private static class ToggleListCell extends ListCell<String> {
        private final ToggleGroup group;
        private static final Image selected = new Image("/selected-button.png", 35, 35, true, true);
        private static final Image notSelected = new Image("/not-selected-button.png", 35, 35, true, true);
        private final ImageView iv;
        private final Function<ToggleButton, Consumer<Boolean>> setSelected;

        private ToggleListCell(ToggleGroup group) {
            this.group = group;
            this.iv = new ImageView(notSelected);
            setSelected = (button) -> (selected) -> {
                iv.setImage(selected ? ToggleListCell.selected : notSelected);
                button.setStyle(String.format("-fx-text-fill: %s", selected ? "#523213" : "#C3BC95"));
            };
        }

        @Override
        public void updateItem(String obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                ToggleButton toggleButton = new ToggleButton(obj);
                toggleButton.setToggleGroup(group);
                toggleButton.setFont(toggleFont);
                toggleButton.setGraphic(iv);
                toggleButton.setUserData(setSelected.apply(toggleButton));
                // Add Listeners if any
                setGraphic(toggleButton);
            }
        }
    }


}