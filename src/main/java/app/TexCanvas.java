package app;

import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.Box;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.*;

class TexCanvas extends Canvas {
    private final FXGraphics2D graphics;

    private final Box box;

    private final float dx;
    private final float dy;

    public TexCanvas(String tex, float dx, float dy) {
        this.dx = dx;
        this.dy = dy;

        this.graphics = new FXGraphics2D(getGraphicsContext2D());
        this.graphics.scale(20, 20);

        // create a formula
        TeXFormula formula = new TeXFormula(tex);
        formula.setColor(new Color(0, 0, 0));
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 25);

        // the 'Box' seems to be the thing we can draw directly to Graphics2D
        this.box = icon.getBox();

        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();
        getGraphicsContext2D().clearRect(0, 0, width, height);
        this.box.draw(graphics, dx, dy);
    }

    @Override
    public boolean isResizable() {
        return false;
    }

    @Override
    public double prefWidth(double height) { return getWidth(); }

    @Override
    public double prefHeight(double width) { return getHeight(); }
}
