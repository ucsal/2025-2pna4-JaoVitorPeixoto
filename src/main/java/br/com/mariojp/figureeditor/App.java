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
            JComboBox<ShapeType> cmbShapes = new JComboBox<>(ShapeType.values());
            cmbShapes.setMaximumSize(new Dimension(150, cmbShapes.getPreferredSize().height));
            tlbMenuBar.add(cmbShapes);
            JButton btnColor = new JButton("Cor");
            tlbMenuBar.add(btnColor);
            tlbMenuBar.addSeparator();
            JButton btnClear = new JButton("Limpar");
            tlbMenuBar.add(btnClear);

            // Adicione a barra no topo
            frame.add(tlbMenuBar, BorderLayout.NORTH);
            frame.add(pnlDrawing, BorderLayout.CENTER);

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

            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
