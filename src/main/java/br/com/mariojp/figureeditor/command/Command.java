package br.com.mariojp.figureeditor.command;

public interface Command {
    void execute();
    void undo();
}
