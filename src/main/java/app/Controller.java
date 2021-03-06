package app;

import algo.Algorithm;
import algo.Optimization;
import algo.OptimizationResult;
import algo.Variant;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

public class Controller implements Initializable {
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // TODO: 05.03.2021 графики следами шрека
        // TODO: 06.03.2021 shrek progress bar
        Optimization.init();
        Variant.init();
        setupSplitPane();
        loadFont();
        setupListViews();
        setupCanvas();
        setLineChart();
    }

    @FXML
    private SplitPane splitPane;

    private void setupSplitPane() {
        splitPane.setDividerPositions(0.4, 0.6);
    }

    private static Font toggleFont;

    private void loadFont() {
        toggleFont = Font.loadFont(getClass().getResourceAsStream("/Shrek-Font.ttf"), 20);
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
        listAlgoNames.addAll(Optimization.ALGORITHMS.keySet());
        listAlgo.setItems(listAlgoNames);
        algoGroup.selectedToggleProperty().addListener(changeToggleImage);

        listVariants.setCellFactory(a -> new ToggleListCell(variantsGroup));
        listVariantsNames.addAll(Variant.VARIANTS.keySet());
        listVariants.setItems(listVariantsNames);
        variantsGroup.selectedToggleProperty().addListener(changeToggleImage);
    }

    @SuppressWarnings("unchecked")
    private final ChangeListener<Toggle> changeToggleImage = (o, oldT, newT) -> {
        if (oldT != null) {
            ((Consumer<Boolean>) oldT.getUserData()).accept(false);
        }
        if (newT != null) {
            ((Consumer<Boolean>) newT.getUserData()).accept(true);
        }
        setLineChart();
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
                setGraphic(toggleButton);
            }
        }
    }

    @FXML
    private LineChart<Double, Double> lineChart;

    private static void disable(Node node) {
        node.setStyle("-fx-opacity: 0;");
    }

    private static void enable(Node node) {
        node.setStyle("-fx-opacity: 1;");
    }

    private void setLineChart() {
        ToggleButton algoButton = (ToggleButton) algoGroup.getSelectedToggle();
        ToggleButton variantButton = (ToggleButton) variantsGroup.getSelectedToggle();
        if (algoButton != null && variantButton != null) {
            String algoName = algoButton.textProperty().getValue();
            String variantName = variantButton.textProperty().getValue();
            Algorithm algorithm = Optimization.ALGORITHMS.get(algoName);
            Variant variant = Variant.VARIANTS.get(variantName);
            enable(lineChart);
            test(algorithm, algoName, variant, variantName, 0.00001);
        } else {
            disable(lineChart);
        }
    }

    private static void test(Algorithm algorithm, String algoName, Variant variant, String variantName, double epsilon) {
        OptimizationResult result = Optimization.run(algorithm, variant, epsilon);
        System.out.format(Locale.US,"Algorithm %14s, %s: %.18f\n", algoName, variantName, result.getResult());
    }

    @FXML
    private TextField formulaText;

    @FXML
    private TextField epsilonText;

    @FXML
    private StackPane fxCanvasPane;

    @FXML
    private StackPane epsilonCanvasPane;

    private void setupCanvas() {
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/base/jlm_cmmi10.ttf"), 1);
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/maths/jlm_cmsy10.ttf"), 1);
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/latin/jlm_cmr10.ttf"), 1);

        createTex("f(x)=", fxCanvasPane);
        createTex("\\varepsilon=", epsilonCanvasPane);
    }

    private void createTex(String tex, StackPane pane) {
        TexCanvas texCanvas = new TexCanvas(tex);
        pane.getChildren().add(texCanvas);
        texCanvas.widthProperty().bind(pane.widthProperty());
        texCanvas.heightProperty().bind(pane.heightProperty());
    }
}