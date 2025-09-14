
package br.com.mariojp.figureeditor;

import javax.swing.*;

import br.com.mariojp.figureeditor.shapes.models.AbstractShape;
import br.com.mariojp.figureeditor.shapes.models.CircleShape;
import br.com.mariojp.figureeditor.shapes.models.RectangleShape;

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

    DrawingPanel() {
        
        setBackground(Color.WHITE);
        setOpaque(true);
        setDoubleBuffered(true);

        var mouse = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1 && startDrag == null) {
                    int size = Math.max(Math.min(DEFAULT_SIZE, DEFAULT_SIZE), 10);
                    AbstractShape s =  new CircleShape(e.getPoint().x, e.getPoint().y, size, size, Color.BLUE);
                    shapes.add(s);
                    repaint();
                }
            }
        };
        addMouseListener(mouse);        
        addMouseMotionListener(mouse);

    }

    void clear() {
        shapes.clear();
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (AbstractShape s : shapes) {
            Shape shape = s.createShape();
            g2.setColor(s.getColor());
            g2.fill(shape);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(shape);
        }

        g2.dispose();
    }

}
