package app;

import algo.*;
import expression.exceptions.ExpressionException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // TODO: 05.03.2021 графики следами шрека
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
        setupLineChart();
        setupControls();
    }

    private static final Color limerick = new Color(176, 196, 0);
    private static final Color pear = new Color(213, 222, 46);
    private static final Color coyoteBrown = new Color(121, 90, 45);
    private static final Color pineapple = new Color(82, 50, 19);
    private static final Color sage = new Color(195, 188, 149);
    private static final Color darkVanila = new Color(211, 204, 165);

    @FXML
    private AnchorPane scene;

    private void setupScene() {
        scene.setOnMouseClicked((e) -> scene.requestFocus());
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
        shrekFont20 = Font.loadFont(getClass().getResourceAsStream("/fonts/Shrek-Font.ttf"), 20);
        shrekFont24 = Font.loadFont(getClass().getResourceAsStream("/fonts/Shrek-Font.ttf"), 26);
        texFont19 = Font.loadFont(getClass().getResourceAsStream("/fonts/Tex-Font.ttf"), 19);

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
        setList(algoGroup, listAlgo, listAlgoNames, Optimization.ALGORITHMS.keySet());
        setList(variantsGroup, listVariants, listVariantsNames, Variant.VARIANTS.keySet());
    }

    private void setList(
            ToggleGroup group,
            ListView<String> list,
            ObservableList<String> stringList,
            Set<String> namesSet
    ) {
        list.setCellFactory(a -> new ToggleListCell(group));
        stringList.addAll(namesSet);
        list.setItems(stringList);
        group.selectedToggleProperty().addListener(changeToggleImage);
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
        private static final Image selected = new Image("/images/selected-button.png", 35, 35, true, true);
        private static final Image notSelected = new Image("/images/not-selected-button.png", 35, 35, true, true);
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
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private class SmartLineChart {
        private MainGraph graph;
        private int iteration;
        private int maxIteration;
        private double left;
        private double right;
        private double top;
        private double bottom;
        private double xDelta;
        private double yDelta;

        public void setDimensions(
                MainGraph graph,
                int iteration,
                int nIterations,
                double left,
                double right,
                double top,
                double bottom
        ) {
            this.graph = graph;
            this.iteration = iteration;
            this.maxIteration = nIterations - 1;

            double xDelta = (right - left) / 20;
            double yDelta = (top - bottom) / 20;

            this.xDelta = xDelta;
            this.yDelta = yDelta;

            this.left = left - xDelta;
            this.right = right + xDelta;
            this.top = top + yDelta;
            this.bottom = bottom - yDelta;

            setBounds();
        }

        private void setBounds() {
            xAxis.setLowerBound(left);
            xAxis.setUpperBound(right);
            xAxis.setTickUnit(xDelta);

            yAxis.setLowerBound(bottom);
            yAxis.setUpperBound(top);
            yAxis.setTickUnit(yDelta);
        }

        public MainGraph getGraph() {
            return graph;
        }

        public int getIteration() {
            return iteration;
        }

        public int getMaxIteration() {
            return maxIteration;
        }

        public void incIteration() {
            iteration += iteration < maxIteration ? 1 : 0;
        }

        public void decIteration() {
            iteration -= iteration > 0 ? 1 : 0;
        }

        public double getLeft() {
            return left;
        }

        public double getRight() {
            return right;
        }

        public double getTop() {
            return top;
        }

        public double getBottom() {
            return bottom;
        }
    }

    @FXML
    private LineChart<Double, Double> lineChart;

    private final SmartLineChart lineChartSpecs = new SmartLineChart();

    private void setupLineChart() {
        xAxis.setLabel("X axis");
        yAxis.setLabel("F(X) axis");
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);
        // TODO: 13.03.2021 add legend
    }

    private static void disable(Node node) {
        node.setStyle("visibility: hidden;");
    }

    private static void enable(Node node) {
        node.setStyle("visibility: visible;");
    }

    @FXML
    private AnchorPane chartPane;

    private void setLineChart() {
        stopTimeline();
        ToggleButton algoButton = (ToggleButton) algoGroup.getSelectedToggle();
        Variant variant = getVariant();
        Double epsilon = getEpsilon();
        if (variant == null) {
            setTexText(DEFAULT_TEX_TEXT, Color.BLACK);
            return;
        }
        if (epsilon == null || variant == Variant.ERROR) {
            setTexError();
            return;
        }
        setField(leftField, variant.getLeft());
        setField(rightField, variant.getRight());
        setField(formulaField, variant.toString());
        setNewTexFormula(variant.getTex());
        if (algoButton != null) {
            String algoName = algoButton.textProperty().getValue();
            Algorithm algorithm = Optimization.ALGORITHMS.get(algoName);
            OptimizationResult result = Optimization.run(algorithm, variant, epsilon);

            resultField.textProperty().set(String.format("%.12f", result.getResult()));

            MainGraph graph = result.getGraph();

            double left = result.getLeftBound();
            double right = result.getRightBound();

            double top = graph.getMax(left, right);
            double bottom = graph.getMin(left, right);

            lineChartSpecs.setDimensions(graph, 0, graph.getNIterations(), left, right, top, bottom);

            drawIteration();

            enable(chartPane);
        } else {
            resultField.textProperty().set("");
            disable(chartPane);
        }
    }

    private void drawIteration() {
        lineChart.getData().clear();
        MainGraph graph = lineChartSpecs.getGraph();
        drawGraph(graph, lineChartSpecs.getLeft(), lineChartSpecs.getRight(), pineapple);

        Iteration iteration = graph.getIteration(lineChartSpecs.getIteration());
        for (SingleGraph singleGraph : iteration.getSingleGraphs()) {
            drawGraph(singleGraph, lineChartSpecs.getLeft(), lineChartSpecs.getRight(), sage);

        }

        for (VLineGraph vLineGraph : iteration.getVLineGraphs()) {
            drawGraph(vLineGraph, lineChartSpecs.getBottom(), lineChartSpecs.getTop(), limerick);
        }

        moveShrekProgress();
    }

    public double calcProgress() {
        return (double) lineChartSpecs.getIteration() / lineChartSpecs.getMaxIteration();
    }

    public void moveShrekProgress() {
        double progress = calcProgress();
        progressBar.setProgress(progress);
        if (progress == 1) {
            setBackground(shrekProgressFinishPane, shrekProgress100Image);
            disable(shrekProgressPane);
        } else {
            enable(shrekProgressPane);
            setBackground(shrekProgressFinishPane, shrekProgressNot100Image);
            shrekProgressPane.setTranslateX(progress * progressBar.getWidth());
        }
    }

    private void drawGraph(final Graph graph, double lowerBound, double upperBound, Color color) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.setName(graph.getName());
        series.getData().addAll(convertPoints(graph.getPoints(lowerBound, upperBound)));
        lineChart.getData().add(series);
        Node line = lineChart.lookup(String.format(".series%d", lineChart.getData().size() - 1));
        line.setStyle(String.format("-fx-stroke: %s;", toHex(color)));
    }

    private String toHex(Color color) {
        return String.format("#%s", Integer.toHexString(color.getRGB()).substring(2));
    }

    private List<XYChart.Data<Double, Double>> convertPoints(List<DataPoint> pointList) {
        return pointList.stream()
                .filter(p -> !Double.isNaN(p.getY()))
                .map(p -> new XYChart.Data<>(p.getX(), p.getY()))
                .collect(Collectors.toList());
    }

    @FXML
    private Button leftButton;

    @FXML
    private Button playButton;

    @FXML
    private Button rightButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Pane shrekProgressPane;

    @FXML
    private Pane shrekProgressFinishPane;

    private static final int shrekButtonSize = 44;
    private static final int shrekProgressSize = 54;

    private static final Image shrekLeftImage = new Image("/images/shrek-left.png", shrekButtonSize, shrekButtonSize, true, true);
    private static final Image shrekRightImage = new Image("/images/shrek-right.png", shrekButtonSize, shrekButtonSize, true, true);
    private static final Image shrekPlayImage = new Image("/images/shrek-play.png", shrekButtonSize, shrekButtonSize, true, true);
    private static final Image shrekStopImage = new Image("/images/shrek-stop.png", shrekButtonSize, shrekButtonSize, true, true);
    private static final Image shrekProgressImage = new Image("/images/shrek-progress.png", shrekButtonSize, shrekButtonSize, true, true);
    private static final Image shrekProgress100Image = new Image("/images/shrek-progress-100.png", shrekProgressSize, shrekProgressSize, true, true);
    private static final Image shrekProgressNot100Image = new Image("/images/shrek-progress-not-100.png", shrekProgressSize, shrekProgressSize, true, true);

    private Timeline progressTimeline;

    private void setupControls() {
        disable(chartPane);

        setBackground(leftButton, shrekLeftImage);
        setBackground(rightButton, shrekRightImage);
        setBackground(playButton, shrekPlayImage);

        Function<Procedure, EventHandler<MouseEvent>> onClickedChangeButton = f ->  mouseEvent -> {
            if (lineChartSpecs.getGraph() != null) {
                stopTimeline();
                f.run();
                drawIteration();
            }
        };
        leftButton.setOnMouseClicked(onClickedChangeButton.apply(lineChartSpecs::decIteration));
        rightButton.setOnMouseClicked(onClickedChangeButton.apply(lineChartSpecs::incIteration));

        setBackground(shrekProgressPane, shrekProgressImage);
        setBackground(shrekProgressFinishPane, shrekProgressNot100Image);
        progressBar.widthProperty().addListener((e, o, n) -> moveShrekProgress());

        progressTimeline = new Timeline(new KeyFrame(Duration.millis(400), e -> {
            lineChartSpecs.incIteration();
            drawIteration();
        }));

        playButton.setUserData(false);
        playButton.setOnMouseClicked(e -> {
            if (!(boolean) playButton.getUserData()) {
                progressTimeline.setCycleCount(lineChartSpecs.getMaxIteration() - lineChartSpecs.getIteration());
                progressTimeline.play();
                setBackground(playButton, shrekStopImage);
                playButton.setUserData(true);
            } else {
                stopTimeline();
            }
        });
    }

    private void stopTimeline() {
        if (progressTimeline == null) return;
        progressTimeline.stop();
        setBackground(playButton, shrekPlayImage);
        playButton.setUserData(false);
    }

    private void setBackground(Region button, Image image) {
        button.setBackground(new Background(
                new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT
                )
        ));
    }

    private void setField(TextField field, Double value) {
        setField(field, String.valueOf(value));
    }

    private void setField(TextField field, String value) {
        field.textProperty().set(value == null ? "" : value);
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

    @FXML
    private TextField resultField;

    private void setupFields() {
        formulaField.setFont(texFont19);
        epsilonField.setFont(texFont19);
        leftField.setFont(texFont19);
        rightField.setFont(texFont19);
        resultField.setFont(texFont19);

        resultField.setEditable(false);

        var focusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> v, Boolean o, Boolean n) {
                if (!n) {
                    variantsGroup.selectToggle(null);
                    setLineChart();
                }
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
            String expression = formulaField.textProperty().getValue();
            if (expression.isBlank()) {
                return null;
            }
            Double left = getLeft();
            Double right = getRight();
            if (left == null || right == null) {
                return Variant.ERROR;
            }
            return Variant.createVariant(expression, left, right);
        } catch (ExpressionException ignored) {
            return Variant.ERROR;
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

    @FXML
    private Text resultText;

    private void setupTextFields() {
        setText(fText, "F(x) =");
        setText(epsilonText, "Epsilon =");
        setText(leftText, "Left =");
        setText(rightText, "Right =");
        setText(resultText, "Result:");
    }

    private void setText(Text text, String textStr) {
        text.setText(textStr);
        text.setFont(shrekFont24);
        text.setStyle("-fx-text-fill: #C3BC95;");
    }

    @FXML
    private ScrollPane formulaCanvasPane;

    private TexCanvas texFormula;

    private static final String DEFAULT_TEX_TEXT = "Your formula will be displayed here";

    private void setupCanvas() {
        texFormula = createTex(DEFAULT_TEX_TEXT, formulaCanvasPane, 0.5f, 2.7f);
    }

    private TexCanvas createTex(String tex, ScrollPane pane, float dx, float dy) {
        TexCanvas texCanvas = new TexCanvas(tex, dx, dy);
        texCanvas.setPane(pane);
        texCanvas.setRealHeight();
        texCanvas.setRealWidth();
        return texCanvas;
    }

    private void setNewTexFormula(String tex) {
        changeTex(String.format("f(x)=%s", tex), Color.BLACK);
    }

    private void setTexError() {
        setTexText("Input fields contain errors", Color.RED);
    }

    private void setTexText(String text, Color color) {
        changeTex(text.replace(" ", "\\;"), color);
    }

    private void changeTex(String tex, Color color) {
        texFormula.changeCanvas(tex, color);
        texFormula.setRealWidth();
    }
}