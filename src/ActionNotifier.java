import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
/*  ActionNotifier
 *
 *   Receives notifications from the calling class.
 *   Responsible for delivering notifications of security events to ActionAdapter(which implements the ActionObserver interface)
 *   This is done by calling ActionAdaptor.notify() below
 *   ....so from here to ActionAdaptor and from ActionAdaptor to ActionHandler
 */
public class ActionNotifier  {
    /*stores list of registered observer classes - for this example it is ActionAdapter*/
    private List<ActionObserver> observers = new ArrayList();

    /*
    * registers ActionAdapters to receive notifications
    * */
    public void addObserver(ActionObserver observer){
        observers.add(observer);
    }//end of

    /*
     * unregisters ActionAdapters to receive notifications
     * */
    public void removeObserver(ActionObserver observer){
        observers.remove(observer);
    }//end of

    public void notify(String command, ArrayList group){
        Iterator it = observers.iterator();
        while(it.hasNext()){
            ((ActionObserver)it.next()).notify(command, group); //pass it on...
        } //end of while
    }//end of notify
}//end of ActionNotifier class