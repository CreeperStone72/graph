package graph.simple;

import graph.standard.Graph;
import graph.standard.Link;

public class SimpleGraph extends Graph<Integer> {
    public SimpleGraph() { super(); }

    public Matrix getAdjacencyMatrix() {
        Matrix m = new Matrix(order());

        for(Link link : getLinks())
            m.setValue((int) link.getX().getData(), (int) link.getY().getData(), 1);

        return m;
    }
}
