import javax.swing.*;
import java.util.ArrayList;
/*  ActionAdapter
 *
 *  Objects of this class become registered to receive notifications from the ActionNotifier
 *  This happens when they are passed to ActionNotifier.addObserver() in MainMenuScreen
 *  ActionNotifier.removeObserver() ends registration
 *  This is adapter class that allows instances of ActionHandler to receive notifications
 *  even though it doesn't implement the ActionObserver interface
 *  Uses the switch below to call the appropriate event handling method of ActionHandler
 */
public class ActionAdapter implements ActionObserver {
    private final ActionHandler ah;

    /*constructor*/
    ActionAdapter(ActionHandler ah){
        this.ah = ah;
    }//end of constructor

    /**
     *  called by ActionNotifier.notify() to deliver object references to the event handling class
     *  The switch cases here must match those in the actionHandler.buttonAction() switch
     */
    @Override
    public void notify(String command, ArrayList group){
        ah.buttonAction(command, group); //relays the action info to the handler
    }//end of notify
}//end of ActionAdapter class
