import java.awt.event.*; //needed for actionListener
import javax.swing.*;
import java.util.ArrayList;
/*
 * Performs the actions for a specific button
 */
public class NameSearchListener implements ActionListener {
    private JTextField recipeNameTextField;
    private Recipes recipes;
    private JList<String> recipeList;

    /*constructor*/
    public NameSearchListener(JList<String> jl, Recipes r, JTextField jtf){
        recipeList = jl;
        recipes = r;
        recipes = new Recipes();
        recipeNameTextField = jtf;
    }//end of constructor

    /*actions on button press*/
    public void actionPerformed(ActionEvent e) {
        ArrayList<Recipe>items = recipes.getItems();
        DefaultListModel<String> dList = new DefaultListModel<String>();
        for(Recipe i : items){
            if(i.getObjectName().contains(recipeNameTextField.getText())){
                dList.addElement(i.getObjectName());
            }//end of if
        }//end of for
        recipeList.setModel(dList);
    }//end of actionPerformed()
}//end of Listener class
