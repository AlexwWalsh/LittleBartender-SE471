/*
 * Where new RecipePopup class objects are instantiated
 */
public class PopupCreator {	
	public Object create(ObjectPool p, Recipe r) {
		RecipePopup pop = new RecipePopup(p, r); 
		return pop;		
	}//end of create
}//end of PopupCreator
