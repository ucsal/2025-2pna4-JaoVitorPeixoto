package br.com.mariojp.figureeditor.shapes.models;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;

import br.com.mariojp.figureeditor.shapes.enums.ShapeType;

public class TriangleShape extends AbstractShape {

    public TriangleShape(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public Shape createShape() {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        int[] xs = { x + w / 2, x, x + w };
        int[] ys = { y, y + h, y + h };
        return new Polygon(xs, ys, 3);
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.TRIANGLE;
    }

}
