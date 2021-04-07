package graph.standard;

import graph.exception.LinkNotFoundException;
import graph.exception.NodeNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The main class to represent a graph
 * @param <T> is the type carried by each node
 * @param <L> is the type of Link used
 * @author CreeperStone72
 */
public class Graph<T, L extends Link> {
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
    private final List<L> links;

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

    public List<L> getLinks() { return links; }

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
    public List<L> getPredecessorLinks(T data) {
        List<L> predecessorLinks = new ArrayList<>();
        Node<T> node = new Node<>(data);

        for(L link : getLinks()) {
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
    public List<L> getSuccessorLinks(T data) {
        List<L> successorLinks = new ArrayList<>();
        Node<T> node = new Node<>(data);

        for(L link : getLinks()) {
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
    public List<L> getNeighborLinks(T data) { return ArrayMethods.merge(getPredecessorLinks(data), getSuccessorLinks(data)); }

    //////////////////////////////////////////////////////////////////////
    //// Node CRUD methods ///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /* NOTE : findAll corresponds to getNodes() */

    /**
     * Inserts a node into the graph
     * @param data is the data carried by the new node
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean insert(T data) {
        Node<T> newNode = new Node<>(data);

        if(getNodes().contains(newNode)) return false;
        getNodes().add(newNode);
        return true;
    }

    /**
     * Finds a node by its data
     * @param data corresponds to the data carried by the node
     * @return the node if it's found
     * @throws NodeNotFoundException if the node is not found
     */
    public Node<T> findNode(T data) throws NodeNotFoundException {
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
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean link(T dataX, T dataY) throws NodeNotFoundException {
        L newLink = (L) new Link(findNode(dataX), findNode(dataY));

        if (!isDirected() && getLinks().contains(newLink.getSymmetrical())) return false;
        getLinks().add(newLink);
        return true;
    }

    /**
     * Finds all links that have a given node
     * @param data is the data carried by the searched node
     * @return a list of all links that start with a given node (or ends with said node if the graph isn't directed)
     * @throws NodeNotFoundException if the node isn't found in the graph
     */
    protected List<L> findLinks(T data) throws NodeNotFoundException {
        Node<T> node = findNode(data);
        List<L> linked = new ArrayList<>();
        List<L> links = getLinks();

        for(L link : links) {
            if(link.matchX(node)) { linked.add(link); }
            if(!isDirected() && link.matchY(node)) { linked.add(link); }
        }

        return linked;
    }

    /**
     * Finds a link by its extremities
     * @param dataX is the data carried by the starting node
     * @param dataY is the data carried by the ending node
     * @return the link if it's found
     * @throws NodeNotFoundException if either node isn't found
     * @throws LinkNotFoundException if the link is not found
     */
    protected L findLink(T dataX, T dataY) throws NodeNotFoundException, LinkNotFoundException {
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
    private L findDirectedLink(Node<T> x, Node<T> y) throws LinkNotFoundException {
        for(L link : getLinks())
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
    protected L findNonDirectedLink(Node<T> x, Node<T> y) throws LinkNotFoundException {
        for(L link : getLinks())
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
    private boolean unlink(L link) { return getLinks().remove(link); }

    /**
     * Deletes a bunch of links from the graph
     * @param links is the list of links to be removed
     * @return true if it was successfully removed
     */
    private boolean deleteLinks(List<L> links) {
        boolean success = true;

        for(L link : links)
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

    //////////////////////////////////////////////////////////////////////
    //// Research methods ////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * An iterative approach to breadth-first search
     * @param data is the information carried by the root node
     * @throws NodeNotFoundException if the node doesn't exist
     */
    public void iterativeBFS(T data) throws NodeNotFoundException {
        List<Node<T>> alreadyVisited = new ArrayList<>();
        Node<T> root = findNode(data);

        root.model();
        List<Node<T>> successors = getSuccessors(root.getData());

        while(ArrayMethods.similarity(alreadyVisited, getNodes()) + 1 != order()) {
            for(Node<T> elem : successors) {
                if(!alreadyVisited.contains(elem)) {
                    elem.model();
                    successors = ArrayMethods.merge(successors, getSuccessors(elem.getData()));
                    alreadyVisited.add(elem);
                }
            }
        }

        System.out.println("=== END OF BFS ===");
    }

    /**
     * A recursive approach to breadth-first search
     * @param data is the information carried by the root node
     * @throws NodeNotFoundException if the node doesn't exist
     */
    public void recursiveBFS(T data) throws NodeNotFoundException {
        Node<T> root = findNode(data);
        root.model();

        recursiveBFS(new ArrayList<>(), getSuccessors(data));
    }

    /**
     * A recursive approach to breadth-first search (the actually recursive part)
     */
    private void recursiveBFS(List<Node<T>> alreadyVisited, List<Node<T>> successors) {
        if(ArrayMethods.similarity(alreadyVisited, getNodes()) + 1 != order()) {
            if(successors.size() > 0) {
                Node<T> elem = ArrayMethods.head(successors);
                List<Node<T>> tail = ArrayMethods.tail(successors);

                if (!alreadyVisited.contains(elem)) {
                    elem.model();
                    successors = ArrayMethods.merge(tail, getSuccessors(elem.getData()));
                    alreadyVisited.add(elem);
                }

                recursiveBFS(alreadyVisited, successors);
            }
        } else { System.out.println("=== END OF BFS ==="); }
    }

    /**
     * An iterative approach to depth-first search
     * @param data is the data carried by the root node
     * @throws NodeNotFoundException if the node doesn't exist
     */
    public void iterativeDFS(T data) throws NodeNotFoundException {
        List<Node<T>> alreadyVisited = new ArrayList<>();
        Node<T> root = findNode(data);

        root.model();
        List<Node<T>> successors = getSuccessors(data);

        while(ArrayMethods.similarity(alreadyVisited, getNodes()) + 1 != order()) {
            if(successors.size() > 0) {
                Node<T> elem = ArrayMethods.head(successors);
                List<Node<T>> tail = ArrayMethods.tail(successors);

                if(!alreadyVisited.contains(elem)) {
                    elem.model();
                    successors = ArrayMethods.merge(getSuccessors(elem.getData()), tail);
                    alreadyVisited.add(elem);
                }
            }
        }

        System.out.println("=== END OF DFS ===");
    }

    //////////////////////////////////////////////////////////////////////
    //// Path methods ////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Checks whether a path is valid within the graph
     * @param path is the path to be tested
     * @return true if the path is valid, otherwise false
     */
    public boolean isPathValid(Path<T> path) {
        for(int i = 0 ; i < path.size() - 1 ; i++) {
            Node<T> start = path.get(i);
            Node<T> end = path.get(i + 1);

            try { findLink(start.getData(), end.getData()); }
            catch(LinkNotFoundException | NodeNotFoundException e) { return false; }
        }

        return true;
    }
}
