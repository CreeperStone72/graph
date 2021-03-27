package graph.standard;

import java.util.*;

/**
 * Several methods about groups of elements that are used in the library
 * @author CreeperStone72
 */
public class ArrayMethods {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // List methods ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
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

    /**
     * Counts the number of elements in common between two lists
     * @param l1 is the first list
     * @param l2 is the second list
     * @param <T> is the type contained by the lists
     * @return the number of elements that are contained in both lists
     */
    public static <T> int similarity(List<T> l1, List<T> l2) {
        int similarity = 0;

        for(T elem1 : l1) { for(T elem2 : l2) { if(Objects.equals(elem1, elem2)) similarity++; } }

        return similarity;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Array destructor methods ///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Takes the head of a list
     * @param list is the list
     * @return the list's head
     */
    public static <T> T head(List<T> list) { return list.get(0); }

    /**
     * Takes the tail of a list
     * @param list is the list
     * @return the list's tail
     */
    public static <T> List<T> tail(List<T> list) {
        if(list.size() > 1) return list.subList(1, list.size());
        else return new ArrayList<>();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Map operations /////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Displays a map
     * @param map is the map to display
     * @param <K> is the keys' class
     * @param <V> is the values' class
     */
    public static <K, V> void printMap(Map<K, V> map) {
        for(Map.Entry<K, V> entry : map.entrySet()) { System.out.println(entry.getKey().toString() + " : " + entry.getValue().toString()); }
    }
}
