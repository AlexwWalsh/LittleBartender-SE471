/*
 * Recipes.java ...  
 *
 * SHOULD THIS BE MERGED WITH Recipe.java???
 * */
import java.util.*;
//import java.util.Collections; //not used; erase?

public class Recipes {
	private static ArrayList<Recipe> recipes; //recipes container; should be static
	
	/*constructor*/
	public Recipes(){
		recipes = new ArrayList<Recipe>();
	}//end of constructor
	
    /**
     * Getters
     */ 
	/*returns the entire Arraylist of recipes*/
	public ArrayList<Recipe> getItems() {	
		return recipes;
	}//end of getItem(s)
	
	/*returns a recipe object from the recipes Arraylist*/
	public Recipe getItem(String name) {
		Recipe item = null;
		for(Recipe i: recipes){
			if(i.getObjectName().equals(name)){
				item=i;
			}//end of if
		}//end of for
		return item;
	}//end of getItem
	
	
    /**
     * Setter
     */
	/*adds a recipe object to the recipes Arraylist*/
	public void addItem(Recipe item) {
		recipes.add(item);
	}//end of addItem
	
}//end of Recipes class