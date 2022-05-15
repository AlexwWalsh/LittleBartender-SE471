import java.awt.event.*; //needed for actionListener
import javax.swing.*;
import java.util.ArrayList;
/*
 * Performs the actions for a specific button
 */
public class AddRecipeListener implements ActionListener {
    private JTextField recipeNameTextField;
    private JTextArea directionsTextArea;
    private JTextArea ingredientTextArea;
    private Recipes recipes;
    private ArrayList<String> ingredients;

    /*constructor*/
    public AddRecipeListener(JTextField jtf, JTextArea jtaD, JTextArea jtaI, Recipes r, ArrayList<String> alS){
        recipeNameTextField = jtf;
        directionsTextArea = jtaD;
        ingredientTextArea = jtaI;
        recipes = r;
        recipes = new Recipes();
    }//end of constructor

    /*actions on button press*/
    public void actionPerformed(ActionEvent e) {
        Recipe r = new Recipe();
        r.setObjectName(recipeNameTextField.getText());
        r.setDirections(directionsTextArea.getText());
        r.addItem(ingredientTextArea.getText());
        recipes.addItem(r);
        recipeNameTextField.setText("");
        directionsTextArea.setText("");
        ingredientTextArea.setText("");
    }//end of actionPerformed()
}//end of Listener class
