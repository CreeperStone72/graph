package graph.standard;

import graph.exception.LinkNotFoundException;
import graph.exception.NodeNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The main class to represent a graph
 * @author CreeperStone72
 */
public class Graph<T> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The nodes within the graph
     */
    private final List<Node<T>> nodes;

    /**
     * The links within the graph
     */
    private final List<Link> links;

    /**
     * If true, links between nodes are asymmetrical
     */
    private boolean isDirected;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Empty constructor. Creates an oriented graph
     */
    public Graph() { this(true); }

    /**
     * Main constructor
     * @param isDirected denotes whether the graph is oriented or not
     */
    public Graph(boolean isDirected) {
        nodes = new ArrayList<>();
        links = new ArrayList<>();
        setDirected(isDirected);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setDirected(boolean directed) { isDirected = directed; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public List<Node<T>> getNodes() { return nodes; }

    public List<Link> getLinks() { return links; }

    public boolean isDirected() { return isDirected; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //// Predecessor methods /////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Finds all predecessors of a given node
     * @param data is the data carried by the node
     * @return a list of all predecessors
     */
    public List<Node<T>> getPredecessors(T data) {
        List<Node<T>> predecessors = new ArrayList<>();
        Node<T> node = new Node<>(data);

        for(Link link : getLinks()) {

            if (link.matchY(node))
                predecessors.add((Node<T>) link.getX());

            if(!isDirected() && link.matchX(node))
                predecessors.add((Node<T>) link.getY());
        }

        return predecessors;
    }

    /**
     * Finds all links to predecessors of a given node
     * @param data is the data carried by the node
     * @return a list of all links to predecessors
     */
    public List<Link> getPredecessorLinks(T data) {
        List<Link> predecessorLinks = new ArrayList<>();
        Node<T> node = new Node<>(data);

        for(Link link : getLinks()) {
            if (link.matchY(node))
                predecessorLinks.add(link);

            if(!isDirected() && link.matchX(node))
                predecessorLinks.add(link);
        }

        return predecessorLinks;
    }

    //////////////////////////////////////////////////////////////////////
    //// Successor methods ///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Finds all successors of a given node
     * @param data is the data carried by the node
     * @return a list of all successors
     */
    public List<Node<T>> getSuccessors(T data) {
        List<Node<T>> successors = new ArrayList<>();
        Node<T> node = new Node<>(data);

        for(Link link : getLinks()) {

            if (link.matchX(node))
                successors.add((Node<T>) link.getY());

            if(!isDirected() && link.matchY(node))
                successors.add((Node<T>) link.getX());
        }

        return successors;
    }

    /**
     * Finds all links to successors of a given node
     * @param data is the data carried by the node
     * @return a list of all links to successors
     */
    public List<Link> getSuccessorLinks(T data) {
        List<Link> successorLinks = new ArrayList<>();
        Node<T> node = new Node<>(data);

        for(Link link : getLinks()) {
            if (link.matchX(node))
                successorLinks.add(link);

            if(!isDirected() && link.matchY(node))
                successorLinks.add(link);
        }

        return successorLinks;
    }

    //////////////////////////////////////////////////////////////////////
    //// Neighbor methods ////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Finds the open neighborhood of a given node
     * @param data is the data carried by the node
     * @return a list of all neighbors
     */
    public List<Node<T>> getOpenNeighborhood(T data) { return ArrayMethods.merge(getPredecessors(data), getSuccessors(data)); }

    /**
     * Finds the closed neighborhood of a given node
     * @param data is the data carried by the node
     * @return a list of all neighbors plus the node
     */
    public List<Node<T>> getClosedNeighborhood(T data) {
        var res = getOpenNeighborhood(data);

        try { res.add(findNode(data)); }
        catch(NodeNotFoundException e) { e.printStackTrace(); }

        return res;
    }

    /**
     * Finds all links between a given node and its neighbors
     * @param data is the data carried by the node
     * @return a list of all links to neighbors
     */
    public List<Link> getNeighborLinks(T data) { return ArrayMethods.merge(getPredecessorLinks(data), getSuccessorLinks(data)); }

    //////////////////////////////////////////////////////////////////////
    //// Node CRUD methods ///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /* NOTE : findAll corresponds to getNodes() */

    /**
     * Inserts a node into the graph
     * @param data is the data carried by the new node
     */
    public boolean insert(T data) { return getNodes().add(new Node<>(data)); }

    /**
     * Finds a node by its data
     * @param data corresponds to the data carried by the node
     * @return the node if it's found
     * @throws NodeNotFoundException if the node is not found
     */
    private Node<T> findNode(T data) throws NodeNotFoundException {
        for(Node<T> node : getNodes())
            if (Objects.equals(node.getData(), data))
                return node;

        throw new NodeNotFoundException();
    }

    /**
     * Counts the number of nodes within the graph
     * @return the number of nodes in the graph
     */
    public int order() { return getNodes().size(); }

    /**
     * Deletes a node from the graph and all of its attached links
     * @param data is the data carried by the node we want to delete
     * @return true if the node was deleted
     * @throws NodeNotFoundException if the node isn't in the graph
     */
    public boolean remove(T data) throws NodeNotFoundException { return deleteLinks(getNeighborLinks(data)) && getNodes().remove(findNode(data)); }

    //////////////////////////////////////////////////////////////////////
    //// Link CRUD methods ///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /* NOTE : findAll corresponds to getLinks() */

    /**
     * Inserts a link into the graph
     * @param dataX is the data carried by the starting node
     * @param dataY is the data carried by the ending node
     * @return true if the insertion was successful
     * @throws NodeNotFoundException if either node isn't found
     */
    public boolean link(T dataX, T dataY) throws NodeNotFoundException {
        Link newLink = new Link(findNode(dataX), findNode(dataY));

        if (!isDirected() && getLinks().contains(newLink.getSymmetrical())) return false;
        getLinks().add(newLink);
        return true;
    }

    /**
     * Finds a link by its extremities
     * @param dataX is the data carried by the starting node
     * @param dataY is the data carried by the ending node
     * @return the link if it's found
     * @throws NodeNotFoundException if either node isn't found
     * @throws LinkNotFoundException if the link is not found
     */
    private Link findLink(T dataX, T dataY) throws NodeNotFoundException, LinkNotFoundException {
        Node<T> x = findNode(dataX);
        Node<T> y = findNode(dataY);

        return (isDirected()) ? findDirectedLink(x, y) : findNonDirectedLink(x, y);
    }

    /**
     * Finds a link in a directed context
     * @param x is the starting node
     * @param y is the ending node
     * @return the link if it's found
     * @throws LinkNotFoundException if the link is not found
     */
    private Link findDirectedLink(Node<T> x, Node<T> y) throws LinkNotFoundException {
        for(Link link : getLinks())
            if(link.matches(x, y))
                return link;

        throw new LinkNotFoundException();
    }

    /**
     * Finds a link in a non-directed context
     * @param x is the starting node
     * @param y is the ending node
     * @return the link if it's found
     * @throws LinkNotFoundException if the link is not found
     */
    private Link findNonDirectedLink(Node<T> x, Node<T> y) throws LinkNotFoundException {
        for(Link link : getLinks())
            if(link.matches(x, y) || link.getSymmetrical().matches(x, y))
                return link;

        throw new LinkNotFoundException();
    }

    /**
     * Counts the number of links within the graph
     * @return the number of links in the graph
     */
    public int size() { return getLinks().size(); }

    /**
     * Deletes a link from the graph
     * @param dataX is the data from the starting node
     * @param dataY is the data from the ending node
     * @return true if it was successfully removed
     * @throws NodeNotFoundException if the node isn't in the graph
     * @throws LinkNotFoundException if the link isn't in the graph
     */
    public boolean unlink(T dataX, T dataY) throws NodeNotFoundException, LinkNotFoundException { return getLinks().remove(findLink(dataX, dataY)); }

    /**
     * Deletes a link from the graph
     * @param link is the link to be removed
     * @return true if it was successfully removed
     */
    private boolean unlink(Link link) { return getLinks().remove(link); }

    /**
     * Deletes a bunch of links from the graph
     * @param links is the list of links to be removed
     * @return true if it was successfully removed
     */
    private boolean deleteLinks(List<Link> links) {
        boolean success = true;

        for(Link link : links)
            success &= unlink(link);

        return success;
    }

    //////////////////////////////////////////////////////////////////////
    //// Graph methods ///////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Checks whether all nodes are linked to every other node
     * @return true if the graph is complete, otherwise false
     */
    public boolean isComplete() {
        for(Node<T> node : getNodes()) {
            List<Node<T>> sublist = new ArrayList<>(getNodes());
            sublist.remove(node);

            for(Node<T> otherNode : sublist) {
                try { findLink(node.getData(), otherNode.getData()); }
                catch(NodeNotFoundException | LinkNotFoundException e) { return false; }

                if(!isDirected()) {
                    try { findLink(otherNode.getData(), node.getData()); }
                    catch(NodeNotFoundException | LinkNotFoundException e) { return false; }
                }
            }
        }

        return true;
    }
}
