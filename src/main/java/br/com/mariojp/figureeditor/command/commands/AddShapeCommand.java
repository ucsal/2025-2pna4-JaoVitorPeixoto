package br.com.mariojp.figureeditor.command.commands;

import java.util.List;

import br.com.mariojp.figureeditor.command.Command;
import br.com.mariojp.figureeditor.shapes.models.AbstractShape;

public class AddShapeCommand implements Command{
    private final List<AbstractShape> shapes;
    private final AbstractShape shape;

    public AddShapeCommand(List<AbstractShape> shapes, AbstractShape newShape){
        this.shapes = shapes;
        this.shape = newShape;
    }

    @Override
    public void execute() {
        shapes.add(shape); 
    }

    @Override
    public void undo() {
        shapes.remove(shape);
    }
}
