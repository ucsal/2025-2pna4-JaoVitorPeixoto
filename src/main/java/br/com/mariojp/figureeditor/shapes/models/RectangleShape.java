package br.com.mariojp.figureeditor.shapes.models;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends AbstractShape {

    public RectangleShape(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }
    
    @Override
    public Shape createShape() {
        return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
    }

}
