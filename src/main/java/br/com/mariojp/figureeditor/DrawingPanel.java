
package br.com.mariojp.figureeditor;

import javax.swing.*;

import br.com.mariojp.figureeditor.command.CommandManager;
import br.com.mariojp.figureeditor.command.commands.AddShapeCommand;
import br.com.mariojp.figureeditor.command.commands.ClearAllShapesCommand;
import br.com.mariojp.figureeditor.command.commands.MoveShapeCommand;
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
    private boolean selectionMode = false;
    private AbstractShape selectedShape = null;
    private Point lastDragPoint = null;
    private Point moveStartPoint = null;

    private CommandManager commandManager = new CommandManager();

    DrawingPanel() {
        
        setBackground(Color.WHITE);
        setOpaque(true);
        setDoubleBuffered(true);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectionMode) {
                    selectedShape = findShapeAt(e.getPoint());
                    if (selectedShape != null) {
                        moveStartPoint = e.getPoint();
                        lastDragPoint = e.getPoint();
                    }
                } else {
                    startDrag = e.getPoint();
                    endDrag = e.getPoint();
                }
            }

            @Override 
            public void mouseDragged(MouseEvent e){
                if (selectionMode && selectedShape != null && lastDragPoint != null) {
                    int dx = e.getX() - lastDragPoint.x;
                    int dy = e.getY() - lastDragPoint.y;
                    selectedShape.move(dx, dy);
                    lastDragPoint = e.getPoint();
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                } else if (!selectionMode) {
                    endDrag = e.getPoint(); 
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectionMode) {
                    if (selectedShape != null && moveStartPoint != null) {
                        int totalDx = e.getX() - moveStartPoint.x;
                        int totalDy = e.getY() - moveStartPoint.y;
                        
                        if (totalDx != 0 || totalDy != 0) {
                            selectedShape.move(-totalDx, -totalDy);
                            
                            commandManager.executeCommand(
                                new MoveShapeCommand(selectedShape, totalDx, totalDy)
                            );
                        }

                        selectedShape = null;
                        lastDragPoint = null;
                        moveStartPoint = null;

                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                } else {
                    if (startDrag != null && endDrag != null) {
                        int x = Math.min(startDrag.x, endDrag.x);
                        int y = Math.min(startDrag.y, endDrag.y);
                        int w = Math.abs(startDrag.x - endDrag.x);
                        int h = Math.abs(startDrag.y - endDrag.y);
                        if (w < 10) w = DEFAULT_SIZE;
                        if (h < 10) h = DEFAULT_SIZE;

                        AbstractShape s = ShapeFactory.createShape(
                            getCurrentShapeType(), x, y, w, h, getSelectedColor()
                        );

                        commandManager.executeCommand(new AddShapeCommand(shapes, s));
                        startDrag = null;
                        endDrag = null;
                        repaint();
                    }
                }
            }

        };
        addMouseListener(mouse);        
        addMouseMotionListener(mouse);

    }

    void clear() {
        commandManager.executeCommand(new ClearAllShapesCommand(shapes));
        repaint();
    }

    public CommandManager getCommandManager(){
        return commandManager;
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

    public void setSelectionMode(boolean enabled) {
        this.selectionMode = enabled;
        selectedShape = null;
        repaint();
    }

    public boolean isSelectionMode() {
        return selectionMode;
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
        if (startDrag != null && endDrag != null ) {
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


    private AbstractShape findShapeAt(Point p) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            AbstractShape s = shapes.get(i);
            if (s.createShape().contains(p)) {
                return s;
            }
        }
        return null;
    }
}
