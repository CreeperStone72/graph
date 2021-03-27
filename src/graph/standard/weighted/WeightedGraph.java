package graph.standard.weighted;

import graph.exception.LinkNotFoundException;
import graph.exception.NodeNotFoundException;
import graph.standard.ArrayMethods;
import graph.standard.Graph;
import graph.standard.Node;
import graph.standard.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models a graph with weighted links
 * @param <T> is the type of data carried by the nodes
 * @author CreeperStone72
 */
public class WeightedGraph<T> extends Graph<T, WeightedLink> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean link(T dataX, T dataY) throws NodeNotFoundException { return link(dataX, dataY, 1.0); }

    /**
     * Links two nodes together with a given weight
     * @param dataX is the first node linked
     * @param dataY is the second node linked
     * @param weight is the weight of the link
     * @return true if the linking was successful
     * @throws NodeNotFoundException if either node wasn't found
     */
    public boolean link(T dataX, T dataY, double weight) throws NodeNotFoundException {
        WeightedLink newLink = new WeightedLink(findNode(dataX), findNode(dataY), weight);

        if (!isDirected() && getLinks().contains(newLink.getSymmetrical())) return false;
        getLinks().add(newLink);
        return true;
    }

    /**
     * Calculates the cost of a path
     * @param p is the path taken
     * @return the cost of taking that path
     * @throws LinkNotFoundException if two nodes aren't linked (invalid path)
     * @throws NodeNotFoundException if a node doesn't exist
     */
    public double cost(Path<T> p) throws LinkNotFoundException, NodeNotFoundException {
        double cost = 0.0;

        for(int i = 0 ; i < p.size() - 1 ; i++) {
            Node<T> start = p.get(i);
            Node<T> end = p.get(i + 1);

            WeightedLink l = findLink(start.getData(), end.getData());

            cost += l.getWeight();
        }

        return cost;
    }

    /**
     * Checks whether a path is absorbing
     * @param path is the path to be tested
     * @return true if the length of the path is negative, otherwise false
     */
    public boolean isPathAbsorbing(Path<T> path) {
        try { return cost(path) < 0.0; }
        catch(LinkNotFoundException | NodeNotFoundException e) { return true; }
    }

    /**
     * Uses the Dijkstra algorithm to figure out the shortest path to any node in the graph
     * @param start is the starting point
     * @return a map that links every node to its cost and previous node in the chain
     * @throws NodeNotFoundException if the starting point doesn't exist
     */
    public Map<Node<T>, Cost> dijkstra(T start) throws NodeNotFoundException {
        Node<T> origin = findNode(start);
        Map<Node<T>, Cost> costs = new HashMap<>();
        List<Node<T>> alreadyVisited = new ArrayList<>();

        // Step 1 : Initialization
        // Set every cost to infinity, except for the starting summit which is set to 0
        for(Node<T> node : getNodes()) {
            if(origin.equals(node)) { costs.put(node, new Cost(0.0)); }
            else { costs.put(node, new Cost()); }
        }

        while(ArrayMethods.similarity(getNodes(), alreadyVisited) != order()) {
            // Step 2 : Taking the node with the smallest cost
            Node<T> reference = Cost.findMin(costs, alreadyVisited);

            // Step 3 : Updating the table with the costs from that node
            for(WeightedLink link : findLinks(reference.getData())) {
                Node<T> other = (Node<T>) link.getOther(reference);
                Cost oldCost = costs.get(other);
                double old = oldCost.getCost();
                double cost = link.getWeight() + costs.get(reference).getCost();

                costs.put(other, (cost < old) ? new Cost(cost, reference) : oldCost);
            }

            // Step 4 : Repeat with the smallest costing summit that wasn't visited already
            alreadyVisited.add(reference);
        }

        return costs;
    }
}
