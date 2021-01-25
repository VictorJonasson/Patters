package command;

import java.util.Stack;

public class CommandManager {

    //Stacks för command-historia

    private final Stack<Command> undoCollector = new Stack<>();
    private final Stack<Command> redoCollector = new Stack<>();

    public Stack<Command> getUndoCollector() {
        return undoCollector;
    }
    public Stack<Command> getRedoCollector() {
        return redoCollector;
    }

    public void addToUndoCollector(Command command){
        undoCollector.push(command);
    }
}
