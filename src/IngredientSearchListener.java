import java.awt.event.*; //needed for actionListener
import javax.swing.*;
import java.util.ArrayList;
/*
 * Performs the actions for a specific button
 */
public class IngredientSearchListener implements ActionListener {
    private JTextArea ingredientTextArea;
    private Recipes recipes;
    private JList<String> recipeList;

    /*constructor*/
    public IngredientSearchListener(JList<String> jl, Recipes r, JTextArea jtaI){
        recipeList = jl;
        recipes = r;
        recipes = new Recipes();
        ingredientTextArea = jtaI;
    }//end of constructor

    /*actions on button press*/
    public void actionPerformed(ActionEvent e) {
        ArrayList<Recipe>items = recipes.getItems();
        DefaultListModel<String> aList = new DefaultListModel<String>();
        for(Recipe i : items){
            if(i.getItems().contains(ingredientTextArea.getText())){
                aList.addElement(i.getObjectName());
            }//end of if
        }//end of for
        recipeList.setModel(aList);
    }//end of actionPerformed()
}//end of Listener class
