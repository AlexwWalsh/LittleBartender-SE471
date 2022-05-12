import java.lang.ref.SoftReference;
import java.util.*;
/**
 * Creates an manages a single instance of an Agent object pool
 */
public class ObjectPool implements ObjectPoolIF {

    private List <Object> pool;
    private PopupManager pm;
    private int instanceCount = 0; //The number of pool-managed objects that currently exist.
    private int maxInstances = 0; //ObjectPool capacity
    private static ObjectPool poolInstance; //THE ObjectPool

    /*
     * The ObjectPool constructor
     *
     * @param c the type of object the pool will contain; max capacity of the pool
     * @return a new ObjectPool
     */
    private ObjectPool(int max) { //ObjectPool(RecipePopup p, int max)
        instanceCount = 0;
        maxInstances = max;
        pool = new ArrayList<Object>(maxInstances);
        setCapacity(maxInstances);
        //this.poolInstance = poolClass;  //do we need this and why
    }//end of constructor


    /*
     * Ensures only one instance of an ObjectPool exists
     *
     * @param c the type of object the pool will contain; max the capacity of the pool
     * @return an existing or new ObjectPool
     */
    public synchronized static ObjectPool getPoolInstance(int max) { //public synchronized static ObjectPool getPoolInstance(PopupCreatorIF c, int max) // ObjectPool getPoolInstance(RecipePopup p, int max)
        if(poolInstance == null)
            poolInstance = new ObjectPool(max); //
        return poolInstance;
    }//end of getPoolInstance()

    /*
     * Gets the number of objects in the pool that are awaiting reuse. The actual number may be less than
     * this because what is returned is the number of soft references in the pool. Any or all of
     * the soft references may have been cleared by the garbage collector.
     *
     * @param none
     * @return the number of objects in the pool that are awaiting reuse.
     */
    public int getSize(){
        synchronized(pool) {
            return pool.size();
        }
    }//end of getSize()

    /*
     * Gets the number of pool-managed objects that currently exist.
     *
     * @param none
     * @return the number of pool-managed objects that currently exist.
     */
    public int getInstanceCount(){
        return instanceCount;
    }//end of getInstanceCount()

    /*
     * Gets the maximum number of objects that this pool will allow to exist at one time.
     *
     * @param none
     * @return the maximum number of objects that this pool will allow to exist at one time.
     */
    public int getCapacity(){
        return maxInstances;
    }//end of getCapacity()

    /*
     * Sets the maximum number of objects that this pool will allow to exist at one time.
     * If the pool is asked to produce an instance of poolClass when there are no objects in
     * the pool awaiting reuse and there are already this many
     * pool-managed objects in use, then the pool will not produce an object until an object is
     * returned to the pool for reuse.
     *
     * @param c the new value for the maximum number of pool-managed objects that may exist at one time.
     * Setting this to a value that is less than the value returned by the getInstanceCount method
     * will not cause any objects to be deleted. It will just prevent any new pool-managed objects from being created.
     * @return none
     */
    public void setCapacity(int c){
        maxInstances = c;
    }//end of setCapacity()

    /*
     * Return an object from the pool. If there is no object in the pool, one will be created unless
     * the number of pool-managed objects is greater than or equal to the value returned by the getCapacity
     * method. If the number of pool-managed objects exceeds this amount, then this method returns null.
     *
     * @param none
     * @return Object
     */
    public Object getObject(Recipe r) {
        synchronized (pool) {
            Object thisObject = removeObject();
            if (thisObject != null) {
                return thisObject;
            }
            if (getInstanceCount() < getCapacity()) {
                return createObject(r); // not sure if this is correct
            } else {
                return null;
            }
        } // end of synchronized()
    }// end of getObject()

    /*
     * Return an object from the pool. If there is no object in the pool, one will be created unless
     * the number of pool-managed objects is greater than or equal to the value returned by the getCapacity
     * method. If the number of pool-managed objects exceeds this amount, then this method will wait until an
     * object becomes available for reuse.
     *
     * @param none
     * @return Object
     */
    public Object waitForObject(Recipe r) {
        synchronized (pool) {
            Object thisObject = removeObject();
            if (thisObject != null) {
                return thisObject;
            } // if thisObject
            if (getInstanceCount() < getCapacity()) {
                return createObject(r);
            } else {
                do {
                    try {
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    thisObject = removeObject();
                } while (thisObject == null);
                return thisObject;
            } // if
        } // synchronized (pool)
    }// end of waitForObject()

    /*
     * Create an object to be managed by the pool.
     *
     * @param none
     * @return Object
     */
    private Object createObject(Recipe r) {
        pm = new PopupManager(this);
        Object newObject = pm.getPopUp(r);
        instanceCount++;
        return newObject;
    } // end of createObject()

    /*
     * Remove an object from the pool and return it.
     *
     * @param  none
     * @return Object
     */
    public Object removeObject() {
        while (pool.size() > 0) {
            int lastElem = pool.size() - 1; //index of last item in the pool
            /*
             * SoftReferences provide a form of memory-sensitive caching.
             * Even if they have been tagged for Garbage Collection (GC), a soft reference object (or a softly reachable object)
             * is spared from GC until the JVM needs to recover memory; before "OutOfMemoryError" is thrown by the JVM.
             */
            SoftReference <Object> thisRef = new SoftReference <Object>(pool.remove(lastElem));
            Object thisObject = thisRef.get();
            if (thisObject != null) {
                instanceCount--; //one less instance
                return thisObject; //return the valid object
            } // end of if thisObject exists
        } // end of while loop
        return null;//no valid object, return null
    }// end of removeObject()

    /*
     * Release an object to the Pool for reuse.
     *
     * @param o Object
     * @none
     */
    public void release(Object o) {
        // no nulls
        if (o == null) {
            throw new NullPointerException();
        } // if null
        if (poolInstance == null) {
            String actualClassName = o.getClass().getName();
            throw new ArrayStoreException(actualClassName);
        } // if isInstance
        synchronized (pool) {
            pool.add(o);

            // Notify a waiting thread that we have put an object in the pool.
            pool.notify();
        } // synchronized
        instanceCount--; //one less instance
    }// end of release()

    /*
     * JAVA EQUIVALENT OF C++ System("pause");
     * CAN BE USED IN ANY CLASS
     */
    /*FOR DEBUGGING ONLY*/
    @SuppressWarnings("resource")
    public static void pause() {
        System.out.println("\nPress Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }//end of pause
    
}//end of ObjectPool class