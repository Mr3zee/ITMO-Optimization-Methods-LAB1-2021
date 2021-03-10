package app;

import algo.Algorithm;
import algo.Optimization;
import algo.OptimizationResult;
import algo.Variant;
import expression.exceptions.ExpressionException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        setupScene();
        setupSplitPane();
        loadFonts();
        setupListViews();
        setupFields();
        setupCanvas();
        setLineChart();
        setupTextFields();
    }

    @FXML
    private AnchorPane scene;

    private void setupScene() {
        scene.setOnMouseClicked((e) -> {
            scene.requestFocus();
        });
    }

    @FXML
    private SplitPane splitPane;

    private void setupSplitPane() {
        splitPane.setDividerPositions(0.4, 0.6);
    }

    private static Font shrekFont20;
    private static Font shrekFont24;
    private static Font texFont19;

    private void loadFonts() {
        shrekFont20 = Font.loadFont(getClass().getResourceAsStream("/Shrek-Font.ttf"), 20);
        shrekFont24 = Font.loadFont(getClass().getResourceAsStream("/Shrek-Font.ttf"), 26);
        texFont19 = Font.loadFont(getClass().getResourceAsStream("/Tex-Font.ttf"), 19);

        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/base/jlm_cmmi10.ttf"), 1);
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/maths/jlm_cmsy10.ttf"), 1);
        Font.loadFont(getClass().getResourceAsStream("/org/scilab/forge/jlatexmath/fonts/latin/jlm_cmr10.ttf"), 1);
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
                toggleButton.setFont(shrekFont20);
                toggleButton.setGraphic(iv);
                toggleButton.setUserData(setSelected.apply(toggleButton));
                setGraphic(toggleButton);
            }
        }
    }

    @FXML
    private LineChart<Double, Double> lineChart;

    private static void disable(Node node) {
        node.setStyle("visibility: hidden;");
    }

    private static void enable(Node node) {
        node.setStyle("visibility: visible;");
    }

    private void setLineChart() {
        ToggleButton algoButton = (ToggleButton) algoGroup.getSelectedToggle();
        Variant variant = getVariant();
        if (variant != null) {
            setField(leftField, variant.getLeft());
            setField(rightField, variant.getRight());
            setField(formulaField, variant.toString());
            setNewTexFormula(variant.getTex());
        }
        Double epsilon = getEpsilon();
        if (algoButton != null && variant != null && epsilon != null) {
            String algoName = algoButton.textProperty().getValue();
            Algorithm algorithm = Optimization.ALGORITHMS.get(algoName);
            enable(lineChart);
        } else {
            disable(lineChart);
        }
    }

    private void setField(TextField field, Double value) {
        setField(field, String.valueOf(value));
    }

    private void setField(TextField field, String value) {
        field.textProperty().set(value == null ? "" : value);
    }

    private void test(Algorithm algorithm, String algoName, Variant variant, String variantName, Double epsilon) {
        OptimizationResult result = Optimization.run(algorithm, variant, epsilon);
        System.out.format(Locale.US,"Algorithm %14s, %s: %.18f\n", algoName, variantName, result.getResult());
    }

    public static final Parser<Double> PARSER = new ExpressionParser<>(DoubleEType::parseDouble);

    @FXML
    private TextField formulaField;

    @FXML
    private TextField epsilonField;

    @FXML
    private TextField leftField;

    @FXML
    private TextField rightField;

    private void setupFields() {
        formulaField.setFont(texFont19);
        epsilonField.setFont(texFont19);
        leftField.setFont(texFont19);
        rightField.setFont(texFont19);

        var focusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> v, Boolean o, Boolean n) {
                if (!n) {
                    variantsGroup.selectToggle(null);
                    setLineChart();
                };
            }
        };
        formulaField.focusedProperty().addListener(focusListener);
        epsilonField.focusedProperty().addListener(focusListener);
        leftField.focusedProperty().addListener(focusListener);
        rightField.focusedProperty().addListener(focusListener);
    }

    private Double getEpsilon() {
        return getNumberField(epsilonField);
    }

    private Double getLeft() {
        return getNumberField(leftField);
    }

    private Double getRight() {
        return getNumberField(rightField);
    }

    private Double getNumberField(TextField field) {
        try {
            return Double.parseDouble(field.textProperty().getValue());
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private Variant getVariant() {
        ToggleButton variantButton = (ToggleButton) variantsGroup.getSelectedToggle();
        if (variantButton != null) {
            return Variant.VARIANTS.get(variantButton.textProperty().getValue());
        }
        try {
            return Variant.createVariant(
                    formulaField.textProperty().getValue(),
                    getLeft(),
                    getRight()
            );
        } catch (ExpressionException ignored) {
            return null;
        }
    }

    @FXML
    private Text fText;

    @FXML
    private Text epsilonText;

    @FXML
    private Text leftText;

    @FXML
    private Text rightText;

    private void setupTextFields() {
        setText(fText, "F(x) =");
        setText(epsilonText, "Epsilon =");
        setText(leftText, "Left =");
        setText(rightText, "Right =");
    }

    private void setText(Text text, String textStr) {
        text.setText(textStr);
        text.setFont(shrekFont24);
        text.setStyle("-fx-text-fill: #C3BC95;");
    }

    @FXML
    private StackPane formulaCanvasPane;

    private TexCanvas texFormula;

    private void setupCanvas() {
        texFormula = createTex("Your formula will be displayed here", formulaCanvasPane, 0.5f, 2.7f);
    }

    private TexCanvas createTex(String tex, StackPane pane, float dx, float dy) {
        TexCanvas texCanvas = new TexCanvas(tex.replace(" ", "\\;"), dx, dy);
        pane.getChildren().add(texCanvas);
        texCanvas.widthProperty().bind(pane.widthProperty());
        texCanvas.heightProperty().bind(pane.heightProperty());
        return texCanvas;
    }

    private void setNewTexFormula(String tex) {
        texFormula.changeCanvas(String.format("f(x)=%s", tex));
        // TODO: 10.03.2021 scroll canvas
    }
}