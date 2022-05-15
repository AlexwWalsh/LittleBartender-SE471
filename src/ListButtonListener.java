import java.awt.event.*; //needed for actionListener
import javax.swing.*;
import java.util.ArrayList;
/*
 * Performs the actions for a specific button
 */
public class ListButtonListener implements ActionListener {
       private Recipes recipes;
       private JList<String> recipeList;

       /*constructor*/
      public ListButtonListener(JList<String> jl, Recipes r){
          recipeList = jl;
          recipes = r;
          recipes = new Recipes();
      }//end of constructor

    /*actions on button press*/
    public void actionPerformed(ActionEvent e) {
        ArrayList <Recipe> items = recipes.getItems();
        DefaultListModel<String> dList = new DefaultListModel<String>();
        for(Recipe i : items){
            dList.addElement(i.getObjectName());
        }//end of for
        recipeList.setModel(dList);
    } //end of actionPerformed()
}//end of Listener class
