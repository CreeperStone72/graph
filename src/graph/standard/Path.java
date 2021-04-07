package graph.standard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A path from node to node
 * @param <T> is the type of data carried by the node
 * @author CreeperStone72
 */
public class Path<T> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The nodes within the path
     */
    private final List<Node<T>> nodes;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Empty constructor. Initialises the list
     */
    public Path() { nodes = new ArrayList<>(); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void insert(Node<T> node) { nodes.add(node); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public Node<T> get(int i) { return nodes.get(i); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int size() { return nodes.size(); }

    private boolean match(int index1, int index2) { return get(index1).getData() == get(index2).getData(); }

    /**
     * Checks whether the path is elementary
     * @return true if all nodes within the path are distinct, otherwise false
     */
    public boolean isElementary() {
        Set<Node<T>> set = new HashSet<>();

        for(Node<T> node : nodes) { if (!set.add(node)) return false; }

        return true;
    }

    /**
     * If a path isn't elementary, then a loop needs to be removed
     * @param path is the non-elementary path
     * @param jumpStart is the index where the loop starts
     * @param jumpEnd is the index where the biggest loop ends
     * @return the elementary path
     */
    private static <T> Path<T> removeLoop(Path<T> path, int jumpStart, int jumpEnd) {
        Path<T> newPath = new Path<>();

        for(int i = 0 ; i < jumpStart ; i++) { newPath.insert(path.get(i)); }

        for(int j = jumpEnd ; j < path.size() ; j++) { newPath.insert(path.get(j)); }

        return newPath;
    }

    /**
     * Finds the elementary path from a given one
     * @param path is the point of reference
     * @return an elementary path
     */
    public static <T> Path<T> getElementary(Path<T> path) {
        if(!path.isElementary())
            for(int i = 0 ; i < path.size() ; i++) { for(int j = path.size() - 1 ; j > i ; j--) { if(path.match(i, j)) { return removeLoop(path, i, j); } } }

        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < size() ; i++) {
            sb.append(get(i));

            if(i < size() - 1) sb.append("---");
        }

        return sb.toString();
    }
}
