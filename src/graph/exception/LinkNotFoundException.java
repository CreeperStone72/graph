package graph.exception;

/**
 * Exception thrown when a node is not found
 * @author CreeperStone72
 */
public class LinkNotFoundException extends Exception {
    public LinkNotFoundException() { super("standard.Link with given extremities doesn't exist in graph."); }
}
