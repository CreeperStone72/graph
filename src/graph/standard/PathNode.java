package graph.standard;

import org.jetbrains.annotations.Nullable;

/**
 * A node within a path
 * @author CreeperStone72
 */
public class PathNode {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * A node within the path
     */
    private Node<?> node;

    /**
     * The next node within the path
     */
    @Nullable
    private PathNode next;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Main constructor.
     * @param node is the data carried by the first node in line
     */
    public PathNode(Node<?> node) {
        setNode(node);
        setNext(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setNode(Node<?> node) { this.node = node; }

    private void setNext(@Nullable PathNode next) { this.next = next; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public Node<?> getNode() { return node; }

    @Nullable
    public PathNode getNext() { return next; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void next(Node<?> node) { setNext(new PathNode(node)); }
}