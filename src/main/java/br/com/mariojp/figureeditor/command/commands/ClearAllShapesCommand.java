package br.com.mariojp.figureeditor.command.commands;

import java.util.ArrayList;
import java.util.List;

import br.com.mariojp.figureeditor.command.Command;
import br.com.mariojp.figureeditor.shapes.models.AbstractShape;

public class ClearAllShapesCommand implements Command{
    private final List<AbstractShape> shapes;
    private List<AbstractShape> beforeShapes;

    public ClearAllShapesCommand(List<AbstractShape> shapes){
        this.shapes = shapes;
        this.beforeShapes = new ArrayList<>(shapes);
    }

    @Override
    public void execute() {
        shapes.clear();
    }

    @Override
    public void undo() {
        shapes.addAll(beforeShapes);
    }
}
