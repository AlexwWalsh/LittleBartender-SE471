/**
 *  PopupManager.java gets popups from the RecipePopup object pool
 *  It also provides a connection between the Object pool
 *  and the RecipePopup class by passing a reference to the
 *  ObjectPool so ObjectPool class methods can be used there.
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
        rp = new RecipePopup(pool, r);//pass on the recipe and
      return rp;
    }//end of getPopUp
    
}//end of popupRequester class
