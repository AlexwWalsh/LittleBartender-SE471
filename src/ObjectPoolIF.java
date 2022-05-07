/**
 * Interface for Object pool class
 */
public interface ObjectPoolIF {
    public int getSize();
    public int getCapacity();
    public void setCapacity(int c);
    public Object getObject(Recipe r);
    public Object waitForObject(Recipe r);
    public void release(Object o);
}//end of ObjectPoolIF