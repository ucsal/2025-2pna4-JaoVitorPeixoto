package br.com.mariojp.figureeditor;

import javax.swing.*;

import br.com.mariojp.figureeditor.shapes.enums.ShapeType;

import java.awt.*;

public class App {
    public static void main(String[] args) {  
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Figure Editor — Clique para inserir figuras");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            DrawingPanel pnlDrawing = new DrawingPanel();

            // Barra de ferramentas
            JToolBar tlbMenuBar = new JToolBar();
            tlbMenuBar.setFloatable(false);
            ButtonGroup btgGroupActionButtons = new ButtonGroup();
            JToggleButton tgbSelect = new JToggleButton("Selecionar");
            JToggleButton tgbDraw = new JToggleButton("Desenhar");
            btgGroupActionButtons.add(tgbSelect);
            btgGroupActionButtons.add(tgbDraw);
            tlbMenuBar.add(tgbSelect);
            tlbMenuBar.add(tgbDraw);
            tlbMenuBar.addSeparator();
            JComboBox<ShapeType> cmbShapes = new JComboBox<>(ShapeType.values());
            cmbShapes.setMaximumSize(new Dimension(150, cmbShapes.getPreferredSize().height));
            tlbMenuBar.add(cmbShapes);
            JButton btnColor = new JButton("Cor");
            tlbMenuBar.add(btnColor);
            tlbMenuBar.addSeparator();
            JButton btnClear = new JButton("Limpar");
            tlbMenuBar.add(btnClear);
            tlbMenuBar.addSeparator();
            JButton btnRedo = new JButton("Refazer");
            tlbMenuBar.add(btnRedo);
            JButton btnUndo = new JButton("Desfazer");
            tlbMenuBar.add(btnUndo);

            // Adicione a barra no topo
            frame.add(tlbMenuBar, BorderLayout.NORTH);
            frame.add(pnlDrawing, BorderLayout.CENTER);

            tgbDraw.setSelected(true);
            pnlDrawing.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

            btnColor.addActionListener(e -> {
                Color novaCor = JColorChooser.showDialog(frame, "Escolha uma cor", Color.BLUE);
                if (novaCor != null) {
                    pnlDrawing.setSelectedColor(novaCor);
                }
            });

            cmbShapes.addActionListener(e -> {
                ShapeType selectedShape = (ShapeType) cmbShapes.getSelectedItem();
                if (selectedShape != null) {
                    pnlDrawing.setCurrentShapeType(selectedShape);
                }
            });

            btnClear.addActionListener(e -> pnlDrawing.clear());

            btnRedo.addActionListener(e -> {
                pnlDrawing.getCommandManager().redo();
                pnlDrawing.repaint();
            });

            btnUndo.addActionListener(e -> {
                pnlDrawing.getCommandManager().undo();
                pnlDrawing.repaint();
            });

            tgbSelect.addActionListener(e -> {
                if (tgbSelect.isSelected()) {
                    pnlDrawing.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    pnlDrawing.setSelectionMode(true);
                } else {
                    pnlDrawing.setCursor(Cursor.getDefaultCursor());
                    pnlDrawing.setSelectionMode(false);
                }
            });

            tgbDraw.addActionListener(e -> {
                if (tgbDraw.isSelected()) {
                    pnlDrawing.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    pnlDrawing.setSelectionMode(false);
                } else {
                    pnlDrawing.setCursor(Cursor.getDefaultCursor());
                    pnlDrawing.setSelectionMode(true);
                }
            });

            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
