package br.com.mariojp.figureeditor.shapes.ShapeFactory;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

import br.com.mariojp.figureeditor.shapes.enums.ShapeType;
import br.com.mariojp.figureeditor.shapes.models.AbstractShape;
import br.com.mariojp.figureeditor.shapes.models.CircleShape;
import br.com.mariojp.figureeditor.shapes.models.RectangleShape;
import br.com.mariojp.figureeditor.shapes.models.TriangleShape;

public class ShapeFactory {
    private static final Map<ShapeType, ShapeCreator> registry = new EnumMap<>(ShapeType.class);

    static {
        registry.put(ShapeType.RECTANGLE, RectangleShape::new);
        registry.put(ShapeType.CIRCLE, CircleShape::new); 
        registry.put(ShapeType.TRIANGLE, TriangleShape::new);
    }

    public static AbstractShape createShape(ShapeType type, int x, int y, int w, int h, Color color) {
        ShapeCreator creator = registry.get(type);
        if (creator != null) {
            return creator.create(x, y, w, h, color);
        }
        throw new IllegalArgumentException("Shape type not supported: " + type);
    }
}
