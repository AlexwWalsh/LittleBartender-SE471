import javax.swing.*;
import java.util.ArrayList;
/*  ActionObserver - Interface for ActionAdapter class
 *
 * instances of a class that need to be able to receive notifications must implement this interface(See Context in book)
 * Only classes that implement this interface can be registered with a ActionNotifier object to receive notifications from it
 * this happens
 */
public interface ActionObserver {

    /**
     *  Registered classes will forward these parameters to the ActionHandler
     */
    public void notify(String command, ArrayList group);
} //end of ActionObserver class
