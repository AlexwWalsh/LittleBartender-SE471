/*
 * LBDriver.java - The entry point to LittleBartender. 
 * Age check and drink menu creation is done here.
 * 
 * Oracle Docs at: https://docs.oracle.com/en/java/javase/17/docs/api/index.html
 * Change the SDK version number in the URL (...javase/??/docs...) to match your version
 */
public class LBDriver {
	public static void main(String[] args) throws InterruptedException {
		/*All main needs to do is initiate an age check
		* The Menu proxy will either grant or reject
		* access to the Bartender menu
		*/
		MenuProxy proxy = new MenuProxy();
		proxy.checkAge();
	}//end of main
}//end of Main class
