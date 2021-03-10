package graph.standard;

import java.util.HashSet;
import java.util.Set;

public class Path {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The head of the list
     */
    private PathNode head;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Content-based constructor
     * @param node is the node carried by the head of the list
     */
    public Path(Node<?> node) { this(new PathNode(node)); }

    /**
     * Node-based constructor
     * @param head is the head of the list
     */
    public Path(PathNode head) { setHead(head); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setHead(PathNode head) { this.head = head; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private PathNode getHead() { return head; }

    public Node<?> getHeadNode() { return getHead().getNode(); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Attach a path to another
     * @param path is the next path
     */
    public void attachNext(Path path) { setHead(path.getHead()); }

    /**
     * Builds a new path from the tail of the list
     * @return the tail of the list as a new path
     */
    public Path next() { return new Path(getHead().getNext()); }

    /**
     * Checks whether the path is elementary
     * @return true if all nodes within the path are distinct, otherwise false
     */
    public boolean isElementary() {
        Set<Node<?>> set = new HashSet<>();
        Path copy = this;

        while(copy.getHead() != null) {
            if (!set.add(copy.getHeadNode()))
                return false;

            copy = copy.next();
        }

        return true;
    }

    private static void removeLoop(Path path, Path start) {
        Path copy = path;
        while(copy.next() != start) { copy = copy.next(); }

        Path scroll = start.next();
        while(scroll != null && scroll.getHeadNode() != start.getHeadNode()) { scroll = scroll.next(); }

        copy.attachNext(scroll);
    }

    public static Path getElementary(Path path) {
        Path copy = new Path(path.getHead());
        Path next = copy.next();

        while(!copy.isElementary()) {
            removeLoop(copy, next);
            next = next.next();
        }

        return copy;
    }
}
