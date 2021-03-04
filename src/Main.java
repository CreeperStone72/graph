import exception.NodeNotFoundException;
import simple.SimpleGraph;

public class Main {
    public static void main(String[] args) {
        SimpleGraph graph = new SimpleGraph();

        for(int i = 0 ; i < 10 ; i++)
            graph.insert(i);

        try { graph.link(3, 5); }
        catch(NodeNotFoundException e) { e.printStackTrace(); }

        System.out.println(graph.getLinks());
        System.out.println(graph.getNodes());
        System.out.println(graph.size());
        System.out.println(graph.order());
    }
}
