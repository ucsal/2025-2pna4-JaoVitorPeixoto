package br.com.mariojp.figureeditor.shapes.models;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import br.com.mariojp.figureeditor.shapes.enums.ShapeType;

public class CircleShape extends AbstractShape {

    public CircleShape(int x, int y, int radius, int size, Color color) {
        super(x, y, radius, radius, color);
    }

    

    @Override
    public Shape createShape() {
        return new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
    }


    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }

}
