package graph.knowledge;

import graph.standard.DataLink;
import graph.standard.Node;

/**
 * A link modelling a named relation in a knowledge graph
 * @author CreeperStone72
 */
public class RelationLink<T> extends DataLink<T> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private String relationName;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Main constructor
     * @param x is the starting node
     * @param y is the ending node
     * @param relationName is the relation's name
     * @param data is the data carried by the link
     */
    public RelationLink(Node<?> x, Node<?> y, String relationName, T data) {
        super(x, y, data);
        setRelationName(relationName);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setRelationName(String relationName) { this.relationName = relationName; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private String getRelationName() { return relationName; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

}