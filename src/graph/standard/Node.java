package graph.standard;

import java.util.Objects;

/**
 * A node within the graph
 * @param <T> represents the data stored within a node
 * @author CreeperStone72
 */
public class Node<T> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The data carried by the node
     */
    private T data;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Main constructor
     * @param data is the data carried by the node
     */
    public Node(T data) { setData(data); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setData(T data) { this.data = data; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public T getData() { return data; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Informal override
     * @param obj is the node being compared
     * @return true if the two nodes carry the same data
     */
    public boolean equals(Node<T> obj) { return Objects.equals(this.getData(), obj.getData()); }

    /**
     * How is the node represented visually ?
     */
    public void model() { System.out.println(this); }

    @Override
    public String toString() { return "(" + getData().toString() + ")"; }
}
