import java.awt.event.*; //needed for actionListener
import javax.swing.*;
import java.util.ArrayList;
/*
 * Performs the actions for a specific button
 */
public class FavoriteButtonListener implements ActionListener {
    private Recipes recipes;
    private JList<String> recipeList;

    /*constructor*/
    public FavoriteButtonListener(JList<String> jl, Recipes r){
        recipeList = jl;
        recipes = r;
        recipes = new Recipes();
    }//end of constructor

    /*actions on button press*/
    public void actionPerformed(ActionEvent e) {
        ArrayList<Recipe> items = recipes.getItems();
        DefaultListModel<String> dlist = new DefaultListModel<String>();
        for(Recipe i : items){
            if(i.getFavorite()){
                dlist.addElement(i.getObjectName());
            }//end of if
        }//end of for
        recipeList.setModel(dlist);
    }//end of actionPerformed()
}//end of Listener class
