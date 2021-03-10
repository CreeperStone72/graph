package graph.standard;

import java.util.ArrayList;
import java.util.List;

public class ArrayMethods {
    /**
     * Merges two arrays and creates a new one without any duplicates
     * @param l1 is the first list to merge
     * @param l2 is the second list to merge
     * @param <T> is the type contained by the lists
     * @return a list containing elements of both lists
     */
    public static <T> List<T> merge(List<T> l1, List<T> l2) {
        List<T> l = new ArrayList<>(l1);

        for(T elem : l2)
            if(!l.contains(elem))
                l.add(elem);

        return l;
    }
}
