package br.com.mariojp.figureeditor.shapes.ShapeFactory;

import java.awt.Color;

import br.com.mariojp.figureeditor.shapes.models.AbstractShape;

public interface ShapeCreator {
    AbstractShape create(int x, int y, int w, int h, Color color);
}
