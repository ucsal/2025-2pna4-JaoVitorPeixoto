package br.com.mariojp.figureeditor.command.commands;

import br.com.mariojp.figureeditor.command.Command;
import br.com.mariojp.figureeditor.shapes.models.AbstractShape;

public class MoveShapeCommand implements Command {
private final AbstractShape shape;
    private final int dx;
    private final int dy;

    public MoveShapeCommand(AbstractShape shape, int dx, int dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        shape.move(dx, dy);
    }

    @Override
    public void undo() {
        shape.move(-dx, -dy);
    }
}
