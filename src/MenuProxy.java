/*
 * MenuProxy.java shares the MenuProxy interface
 * with the MainMenuScreen.java. MenuProxy handles user age
 * verification as well as MainMenuScreen instantiation.
 */

public class MenuProxy implements MenuProxyIF {
    boolean answered = false; // boolean flag
    BirthdayChecker checker; //age checker GUI object
    MainMenuScreen menu; //drink menu GUI object

    /*
     * This version of checkAge initiates the age check and
     * provides the response to the main menu constructor
     */
    @Override
    public void checkAge() throws InterruptedException {
        checker = new BirthdayChecker();
        /*Run the age checker GUI until it is "answered"
         *If under-aged, main exits
         *otherwise, the drink menu GUI is displayed
         * */
        while (!answered) {
            Thread.sleep(4000); //check for user response every 4 seconds
            answered = checker.didAnswer;
        }//end of while
        if (answered) {
            /*now that we have a response, check it*/
            menu = new MainMenuScreen(checker.getIfLegal());
            menu.showMenu();
        }//end of if answered
    }//end of checkAge
}//end of MenuProxy class
