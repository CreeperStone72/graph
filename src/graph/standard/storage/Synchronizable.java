package graph.standard.storage;

/**
 * A data type that can be stored locally
 * @author CreeperStone72
 */
public interface Synchronizable {
    /**
     * Synchronizes the object with its stored counterpart
     * @param sh is the storage handler
     * @param id is the object's id
     */
    void synchronize(StorageManager sh, int id);
}
