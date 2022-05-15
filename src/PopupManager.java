import javax.swing.JOptionPane;
/**
 *  Creates the ObjectPool. Sets the pool capacity. Gets popups both new and reusable
 */
public class PopupManager implements PopupManagerIF {
    private static ObjectPool pool;
    private Recipe rp;

	/*
	 * This is the pool size as well as the count of newly created popup objects
	 * Only this many new popups will be instantiated. Their release builds
	 * the pool.
	 * See the header of ObjectPool.getObject() for clarifying info.
	 */
    int poolSize = 5;

    /*constructor*/
    public PopupManager(Recipe r){
    	rp = r;
    	pool = ObjectPool.getPoolInstance(poolSize);
    	getPopup(rp);
    }//end of constructor
    
    /*This is where the popup creation begins*/
    public void getPopup(Recipe r) {
        /* If the number of recipe popup instances is less than the limit we can create more; if not a warning is displayed
         * SOME REFINEMENTS ARE NEED HERE TO MAKE IT MORE ROBUST AND 100% PREDICTABLE
         */
    	RecipePopup pop = (RecipePopup) pool.getObject(r); //the returned object must be typed casted to RecipePopup
    	    if(pop == null ){ 
				JOptionPane.showMessageDialog(null, "You are only allowed " + poolSize + " recipe popups.\n Close a popup to see more recipes."); 
				pop = (RecipePopup) pool.waitForObject(r); //can't create more objects so wait for a pool object to be available
				if(pop != null ) {pop.showPopup();} //only get valid popups from waitForObject()
    	    }	
    	    else {pop.showPopup();}
    }//end of getPopup
   
}//end of popupRequester class
