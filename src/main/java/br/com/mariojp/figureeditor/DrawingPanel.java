
package br.com.mariojp.figureeditor;

import javax.swing.*;

import br.com.mariojp.figureeditor.shapes.ShapeFactory.ShapeFactory;
import br.com.mariojp.figureeditor.shapes.enums.ShapeType;
import br.com.mariojp.figureeditor.shapes.models.AbstractShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class DrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SIZE = 60;
    private final List<AbstractShape> shapes = new ArrayList<>();

    private Point startDrag = null;
    private Point endDrag = null;

    private ShapeType currentShapeType = ShapeType.RECTANGLE;
    private Color selectedColor = Color.BLUE;

    DrawingPanel() {
        
        setBackground(Color.WHITE);
        setOpaque(true);
        setDoubleBuffered(true);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDrag = e.getPoint();
                endDrag = e.getPoint();
            }

            @Override 
            public void mouseDragged(MouseEvent e){
                endDrag = e.getPoint();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (startDrag != null && endDrag != null){
                    int x = Math.min(startDrag.x, endDrag.x);
                    int y = Math.min(startDrag.y, endDrag.y);
                    int w = Math.abs(startDrag.x - endDrag.x);
                    int h = Math.abs(startDrag.y - endDrag.y);
                    if (w < 10) w = DEFAULT_SIZE;
                    if (h < 10) h = DEFAULT_SIZE;
                    AbstractShape s =  ShapeFactory.createShape(getCurrentShapeType(), x, y, w, h, getSelectedColor());
                    shapes.add(s);
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }
            }

        };
        addMouseListener(mouse);        
        addMouseMotionListener(mouse);

    }

    public void setCurrentShapeType(ShapeType shapeType) {
        this.currentShapeType = shapeType;
    }

    public ShapeType getCurrentShapeType() {
        return this.currentShapeType;
    }

    public void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    public Color getSelectedColor() {
        return this.selectedColor;
    }   

    void clear() {
        shapes.clear();
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        DrawShapes(g2);
        
        DrawPreviewShape(g2);

        g2.dispose();
    }

    public void DrawShapes(Graphics2D g2) {
        for (AbstractShape s : shapes) {
            Shape shape = s.createShape();
            g2.setColor(s.getColor());
            g2.fill(shape);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(shape);
        }
    }

    public void DrawPreviewShape(Graphics2D g2) {
        if (startDrag != null && endDrag != null) {
            int x = Math.min(startDrag.x, endDrag.x);
            int y = Math.min(startDrag.y, endDrag.y);
            int w = Math.abs(startDrag.x - endDrag.x);
            int h = Math.abs(startDrag.y - endDrag.y);

            AbstractShape previewShape = ShapeFactory.createShape(
                    getCurrentShapeType(), x, y, w, h, getSelectedColor()
            );

            Shape shape = previewShape.createShape();

            Color previewColor = new Color(
                getSelectedColor().getRed(),
                getSelectedColor().getGreen(),
                getSelectedColor().getBlue(),
                50
            );
            g2.setColor(previewColor);
            g2.fill(shape);

            float[] dash = {5f, 5f};
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                                        BasicStroke.JOIN_MITER, 10f, dash, 0f));
            g2.setColor(Color.BLACK);
            g2.draw(shape);
        }
    }

}
