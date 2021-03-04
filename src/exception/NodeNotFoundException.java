package exception;

/**
 * Exception thrown when a node is not found
 *
 * @author CreeperStone72
 */
public class NodeNotFoundException extends Exception {
    public NodeNotFoundException() { super("standard.Node with given data package doesn't exist in graph."); }
}
