package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.*;

class TexCanvas extends Canvas {
    private final FXGraphics2D graphics;
    private ScrollPane parent;

    private Box box;

    private final float dx;
    private final float dy;

    public TexCanvas(String tex, float dx, float dy) {
        this.dx = dx;
        this.dy = dy;

        this.graphics = new FXGraphics2D(getGraphicsContext2D());
        this.graphics.scale(20, 20);

        setCanvas(tex);

        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        getGraphicsContext2D().clearRect(0, 0, width, height);
        this.box.draw(graphics, dx, dy);
    }

    private void setCanvas(String tex) {
        TeXFormula formula = new TeXFormula(tex);
        formula.setColor(new Color(0, 0, 0));
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 25);

        this.box = icon.getBox();
    }

    void changeCanvas(String tex) {
        setCanvas(tex);
        draw();
    }

    @Override
    public boolean isResizable() {
        return false;
    }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }

    public void setRealWidth() {
        widthProperty().bind(parent.widthProperty().subtract(10).multiply(Math.max(1, this.box.getWidth() / 25)));
    }

    public void setRealHeight() {
        heightProperty().bind(parent.heightProperty().subtract(20));
    }

    public void setPane(ScrollPane pane) {
        pane.contentProperty().set(this);
        this.parent = pane;
    }
}
