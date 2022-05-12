import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Observer implements ActionListener {
    MainMenuScreen menu = new MainMenuScreen(true);
    public void actionPerformed(ActionEvent e) {
        String actionCommand = ((JButton) e.getSource()).getActionCommand();
        switch(actionCommand){
            case "ButtonViewList":
                ArrayList<Recipe> items = menu.recipes.getItems();
                DefaultListModel<String> dList = new DefaultListModel<String>();
                for(Recipe i : items){
                    dList.addElement(i.getObjectName());
                }//end of for
                menu.recipeList.setModel(dList);
                break;
            case "ButtonViewFaves":
                ArrayList<Recipe> items1 = menu.recipes.getItems();
                DefaultListModel<String> dlist = new DefaultListModel<String>();
                for(Recipe i : items1){
                    if(i.getFavorite() == true){
                        dlist.addElement(i.getObjectName());
                    }//end of if
                }//end of for
                menu.recipeList.setModel(dlist);
                break;
            case "ButtonSearchByName":
                ArrayList<Recipe>items2 = menu.recipes.getItems();
                DefaultListModel<String> dList1 = new DefaultListModel<String>();
                for(Recipe i : items2){
                    if(i.getObjectName().contains(menu.recipeNameTextField.getText())){
                        dList1.addElement(i.getObjectName());
                    }//end of if
                }//end of for
                menu.recipeList.setModel(dList1);
                break;
            case "ButtonSearchByRecipe":
                ArrayList<Recipe>items3 = menu.recipes.getItems();
                DefaultListModel<String> aList = new DefaultListModel<String>();
                for(Recipe i : items3){
                    if(i.getItems().contains(menu.ingredientTextArea.getText())){
                        aList.addElement(i.getObjectName());
                    }//end of if
                }//end of for
                menu.recipeList.setModel(aList);
                break;
            case "ButtonAddRecipe":
                Recipe r = new Recipe();
                r.setObjectName(menu.recipeNameTextField.getText());
                r.setDirections(menu.directionsTextArea.getText());
                r.addItem(menu.ingredientTextArea.getText());
                menu.recipes.addItem(r);
                menu.recipeNameTextField.setText("");
                menu.directionsTextArea.setText("");
                menu.ingredients.clear();
                break;
        }
    }
}