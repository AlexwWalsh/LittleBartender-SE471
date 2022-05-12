/**
 *  Gets popups from the RecipePopup object pool 
 */
public class PopupManager implements PopupManagerIF {
    private ObjectPool pool;
    private RecipePopup rp;

    /*constructor*/
    public PopupManager(ObjectPool p){
       pool = p;
    }//end of constructor
    
    @Override
    public Object getPopUp(Recipe r){  //return object or popup??
        rp = new RecipePopup(pool, r);
      return rp;
    }//end of getPopUp
    
}//end of popupRequester class
