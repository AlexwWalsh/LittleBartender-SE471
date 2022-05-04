/*
 * Recipe.java ... 
 * It is a (the only??) subclass for ListObject 
 * WHY IS THAT, DO WE NEED THIS!!??
 * SHOULD THIS BE MERGED WITH Recipes.java???
 * */

//import java.awt.*;//not used; erase?
//import java.util.*; //not used; erase?

public class Recipe extends ListObject { //Recipes class vs Recipe class? combine?
	private String ingredients; //recipe ingredients
	private String directions; //recipe directions
	private boolean favoritedStatus; //flagged as favorite recipe

    /*constructor*/
    public Recipe() {
        ingredients = "";
		favoritedStatus = false;
    }//end of  constructor
    
    /**
     * Getters
     */  
	/*return the directions for a given recipe*/
	public String getDirections() {
		return directions;
	}//end of getDirections
	
	/*returns an individual ingredient from the ingredients list*/
	public String getItem(String name) {
		String item = null;
		item = name;
		return item;
	}//end of getItem

	/*returns the entire ingredients string*/
	public String getItems() {		
		return ingredients;
	}//end of getItems
	
	/*checks the status of a recipe to see if it is favorited or not*/
	public boolean getFavorite(){
		return favoritedStatus;
	}//end of getFavorite
	
		
    /**
     * Setters
     */
	/*sets directions equal to the passed string from MainMenuScreen*/
	public void setDirections(String directions) {
		this.directions = directions;
	}//end of setDirections

	/*changes boolean flag to true to tag the recipe as a favorite*/
	public void setFavorite(){
		favoritedStatus = true;
	}//end of setFavorite

	/*changes boolean flag to false to untag the recipe as a favorite*/
	public void unFavorite(){
		favoritedStatus = false;
	}//end of unFavorite

	/*passes the item as a string to the ingredients area to add to the recipe?*/
	public void addItem(String item) {
		ingredients = item;	
	}//end of addItem

}//end of Recipe class