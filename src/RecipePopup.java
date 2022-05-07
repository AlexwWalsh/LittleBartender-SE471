/*
 * RecipePopup.java contains all of the javax.swing package components necessary to
 * create the GUI popups that contain individual recipe images ingredients and directions.
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color; 
import java.awt.Dimension;
import javax.swing.JCheckBox; 

/*
 * MainMenuScreen swing component descriptions:
 * JFrame - a top-level window with a title and a border; An extended version of java.awt.Frame
 * JPanel - a space on which an application can attach any other component.
 * JButton - a configurable "push" button that generates an event.
 */
public class RecipePopup {
    private JFrame popupFrame; //individual recipe popup
    private JPanel displayRecipe; //removed JPanel recipePanel; not used
    private JButton closeButton; //closes the drink recipe popup
    private ObjectPool op;//This class needs to be familiar with the object pool

    private Recipe r;
    /*constructor*/
    public RecipePopup(ObjectPool p, Recipe r){
        op = p;
        this.r = r;
        createFrame();
        popupFrame.add(namePanel(r),BorderLayout.NORTH);             // R - Recipe name
        popupFrame.add(createRecipePanel(r),BorderLayout.CENTER);    // R - Recipe info
    }//end of constructor

    public RecipePopup(){
        createFrame();
        popupFrame.add(namePanel(r),BorderLayout.NORTH);             // R - Recipe name
        popupFrame.add(createRecipePanel(r),BorderLayout.CENTER);    // R - Recipe info
    }//end of constructor

    /*creates the underlying window for the recipe popup and sets window properties*/
    private void createFrame(){
        popupFrame = new JFrame("LittleBartender");
        popupFrame.setBounds(705, 120, 500, 800);
        ImageIcon myImage = new ImageIcon("./LittleBartender_Icon.png");
		popupFrame.setIconImage(myImage.getImage());
        popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        popupFrame.setVisible(false);
    }//end of createFrame
    
    /*a panel to hold the name of the recipe at the very top of the display window, with styling*/
    private JPanel namePanel(Recipe r){
        JPanel namePanel = new JPanel();
        JLabel rName = new JLabel(r.getObjectName()); //GET OBJECT NAME HERE //JLabel rName = new JLabel(r.getObjectName());
        namePanel.setBackground(new Color(40,70,101));
        rName.setFont(new Font("Rockwell", Font.PLAIN, 48));
        rName.setForeground(new Color(240,239,245));
        namePanel.add(rName);
        return namePanel;
    }//end of namePanel

    /*a panel to display the recipe ingredients and directions*/
    private JPanel createRecipePanel(Recipe r){
        displayRecipe = new JPanel();
        GridLayout layout = new GridLayout(4,1);
        layout.setVgap(10);
        displayRecipe.setLayout(layout);
        displayRecipe.setBackground(new Color(40,70,101));      
        displayRecipe.add(ingredientsPanel(r)); //SET INGREDIENT PANEL HERE
        displayRecipe.add(directionsPanel(r)); //SET DIRECTIONS PANEL HERE
        
        /*determine which image in the project root to add to a specific recipe; each image is 330x165 in size*/
        popupImage(displayRecipe,r); //GETS THE CORRECT DRINK IMAGE
        displayRecipe.add(bottomPanel(r)); //SET RECIPE IMAGE PANEL HERE
        return displayRecipe;
    }//end of createRecipePanel
    
    /*switch statements are much better looking than endless ifs*/
    private JPanel popupImage(JPanel j, Recipe r){
        String name = r.getObjectName();
        switch(name){
            case "Bloody Mary": j.add(new JLabel(new ImageIcon("./drinkImage_BM.jpg"))); break;
            case "French 75": j.add(new JLabel(new ImageIcon("./drinkImage_F75.jpg"))); break;
            case "Fresh Lime Margarita": j.add(new JLabel(new ImageIcon("./drinkImage_FLM.jpg"))); break;
            case "Gin and Tonic": j.add(new JLabel(new ImageIcon("./drinkImage_GAT.jpg"))); break;
            case "Irish Coffee": j.add(new JLabel(new ImageIcon("./drinkImage_IC.jpg"))); break;
            case "Martini": j.add(new JLabel(new ImageIcon("./drinkImage_M.jpg"))); break;
            case "Mint Julep": j.add(new JLabel(new ImageIcon("./drinkImage_MJ.jpg"))); break;
            case "Mimosa": j.add(new JLabel(new ImageIcon("./drinkImage_MSA.jpg"))); break;
            case "Mojito": j.add(new JLabel(new ImageIcon("./drinkImage_MJT.jpg"))); break;
            case "Pina Colada": j.add(new JLabel(new ImageIcon("./drinkImage_PC.jpg"))); break;
            case "Pineapple Rum Punch": j.add(new JLabel(new ImageIcon("./drinkImage_PRP.jpg"))); break;
            case "Red Sangria": j.add(new JLabel(new ImageIcon("./drinkImage_RS.jpg"))); break;
            case "Screwdriver": j.add(new JLabel(new ImageIcon("./drinkImage_S.jpg"))); break;
            case "Whiskey Sour": j.add(new JLabel(new ImageIcon("./drinkImage_WS.jpg"))); break;
            case "White Russian": j.add(new JLabel(new ImageIcon("./drinkImage_WR.jpg"))); break;
            default: j.add(new JLabel(new ImageIcon("./drinkImage_Placeholder.jpg"))); break;
        }//end of switch
      return j;
    }//end of popupImage
    
    
    /*panel to hold recipe ingredients in a text area, with the relevant styling to make it approachable.*/
    private JPanel ingredientsPanel(Recipe r) {
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setBackground(Color.WHITE);
        JTextArea textAreaI = new JTextArea(3, 1);
        textAreaI.setBackground(new Color(240,239,245));
        Font font = new Font("Bookman Old Style",Font.PLAIN,12);
        textAreaI.setFont(font);
        textAreaI.setText("Ingredients:\n" + compileIngredients(r)); //SET INGREDIENT TEXT HERE
        textAreaI.setWrapStyleWord(true);
        textAreaI.setLineWrap(true);
        textAreaI.setOpaque(false);
        textAreaI.setEditable(false);
        textAreaI.setPreferredSize(new Dimension(470,300));
        ingredientsPanel.add(textAreaI);
        return ingredientsPanel;
    }//end of ingredientsPanel
    
    /*panel to hold recipe directions in a text area; with the relevant styling to make it approachable too?*/
    private JPanel directionsPanel(Recipe r){
        JPanel directionsPanel = new JPanel();
        directionsPanel.setBackground(Color.WHITE);
        JTextArea textAreaD = new JTextArea(3, 10);
        textAreaD.setBackground(new Color(240,239,245));
        Font font = new Font("Bookman Old Style",Font.PLAIN,12);
        textAreaD.setFont(font);
        textAreaD.setText("Directions:\n" + r.getDirections()); //SET DIRECTIONS TEXT HERE
        textAreaD.setWrapStyleWord(true);
        textAreaD.setLineWrap(true);
        textAreaD.setOpaque(false);
        textAreaD.setEditable(false);
        textAreaD.setPreferredSize(new Dimension(470,300));
        directionsPanel.add(textAreaD);
        return directionsPanel;        
    }//end of directionsPanel

    /*the bottom panel of the view recipe area that holds favoriting functionality*/
    private JPanel bottomPanel(Recipe r){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(40,70,101));
        bottomPanel.setLayout(new BorderLayout());
        JCheckBox favorite = new JCheckBox("Favorite");
        
		if (r.getFavorite() == true) { //Set Favorite checkbox depending on r
			favorite.setSelected(true);
		} else {
			favorite.setSelected(false);
		}
        favorite.setBackground(new Color(40,70,101));
        favorite.setForeground(new Color(240,239,245));      
        favorite.addActionListener(new ActionListener() { //ActionListener would have to be here to access r
            @Override
            public void actionPerformed(ActionEvent e){
                if(favorite.isSelected()){
                    r.setFavorite();
                }
                else{
                    r.unFavorite();
                }
            }
        });
        closeButton = new JButton("Close");
        bottomPanel.add(favorite,BorderLayout.CENTER);
        bottomPanel.add(closeButton, BorderLayout.SOUTH);
        closeButton.addActionListener(new ExitListener(op, this));
        return bottomPanel;
    }//end of bottomPanel
    
    
//    /*NEVER USED BUT MAY BE USEFUL LATER*/
//    private JLabel blankLabel(){
//        JLabel blank = new JLabel();
//        blank.setBackground(new Color(40,70,101));
//        return blank;
//    }
    
    /*grab all ingredients and put them into the relevant box so that the display can have them*/
    private String compileIngredients(Recipe r){
        String ingredientslist = "";
        ingredientslist = r.getItems();
        return ingredientslist;
    }//end of compileIngredients
    
    /*making sure popupFrame is set to visible so it renders*/
    public void showPopup(){
        popupFrame.setVisible(true);
    }//end of showPopup
    
    /**
     * Another nested class 
     */
    /*Closes popup on pressing "Close"*/////TO ACTIONLISTENER CLASS???
    private class ExitListener implements ActionListener{
        private ObjectPool op;
        RecipePopup r;

        public ExitListener(ObjectPool p, RecipePopup r){
            op = p;
            this.r = r;
        }
        public void actionPerformed(ActionEvent e){
            /* on exit press, release the object to the pool and kill the frame*/
            op.release(this.r);
            popupFrame.dispose();//will explore alternate closing functions
        }//end of actionPerformed()
    }//end of ExitListener class
    
    /*
     * JAVA EQUIVALENT OF C++ System("pause");
     * CAN BE USED IN ANY CLASS
     */
    /*FOR DEBUGGING ONLY*/
    @SuppressWarnings("resource")
    public static void pause() {
        System.out.println("\nPress Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }//end of pause

}//end of RecipePopup class
