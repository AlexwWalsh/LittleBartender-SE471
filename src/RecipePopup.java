/**
 * RecipePopup.java contains all of the javax.swing package components necessary to
 * create the GUI popups that contain individual recipe images ingredients and directions.
 */
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color; 
import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.util.ArrayList;
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
    private Recipe rec;//the recipe
    private JTextArea textAreaI;//the drink ingredients
    private JTextArea textAreaD; //the drink mixology
    private JLabel rName;//the drink name
    private static JLabel popupPic; //the drink picture
    private JCheckBox favorite; //needs a fix for refreshPanel()
    private JCheckBox newFavorite; //needs a fix for refreshPanel()
    private JPanel bottomPanel;
    ArrayList<Object> group = new ArrayList<Object>(); //holds the group of swing objects used in each action event
    ActionHandler ah;  //creates a handler object
    ActionNotifier an; //creates a notifier object
    ActionAdapter aa; //creates an adapter object
      
    /*constructor*/
    public RecipePopup(ObjectPool p, Recipe r){
        op = p;
        this.rec = r;
        createFrame();
        popupFrame.add(namePanel(rec),BorderLayout.NORTH);             // R - Recipe name
        popupFrame.add(createRecipePanel(rec),BorderLayout.CENTER);    // R - Recipe info
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
        rName = new JLabel(r.getObjectName()); //GET OBJECT NAME HERE //JLabel rName = new JLabel(r.getObjectName());
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
    
    /*Used strictly for reused popup objects - refreshes the information in the target popup with the data of the passed in recipe*/
    public void refreshPanel(Recipe r) {
        an = new ActionNotifier();
        ah = new ActionHandler();
        aa = new ActionAdapter(ah);
    	textAreaI.setText("Ingredients:\n" + compileIngredients(r)); //SET INGREDIENT TEXT HERE
    	textAreaD.setText("Directions:\n" + r.getDirections());
    	rName.setText(r.getObjectName());
    	ImageIcon refreshedImage = new ImageIcon(getURL(r));
    	popupPic.setIcon(refreshedImage);
        favorite = refreshFav(r); //assigns a refreshed copy of the checkbox to the checkbox
        favorite.setSelected(r.getFavorite());//this line is absolutely necessary
        favorite.setActionCommand("favorite");
        /*this part handles the object event now*/
        group.add(favorite);
        group.add(r);
        an.addObserver(aa); //registers the ActionAdapter as a receiver of notifications from the ActionNotifier
        an.notify("favorite",group);//notify passes the pertinent info and object references to perform the action
        an.removeObserver(aa);
        group.clear();
    }//end of refreshPanel

    /*
    * resetFav cuts the reuseable favorite challenge to the bone
    * Just make a new checkbox identical to the original
    * assigning it to the original is the way.
    */
    public JCheckBox refreshFav(Recipe r){
        newFavorite = new JCheckBox("Favorite", r.getFavorite());
        bottomPanel.remove(favorite);
        bottomPanel.add(newFavorite,BorderLayout.CENTER);
        newFavorite.setBackground(new Color(40,70,101));
        newFavorite.setForeground(new Color(240,239,245));
        newFavorite.setSelected(false);
        return newFavorite;
    }//end of resetFav
        
    /*sets the PopupPanel Image*/
    private JPanel popupImage(JPanel j, Recipe r){
        j.add(setPic(getURL(r))); 
      return j;
    }//end of popupImage
      
    /*sets the pictures for each drink popup*/
    public JLabel setPic(String URL) {
    	ImageIcon image = new ImageIcon(URL);
    	popupPic = new JLabel(image, JLabel.CENTER);     	
    	return popupPic;  	
    }//end of setPic
    
    /*switch statements are much better looking than endless ifs - getURL does just that*/
    public String getURL(Recipe r) {
        String name = r.getObjectName();
        switch(name){
            case "Bloody Mary": return "./drinkImage_BM.jpg"; 
            case "French 75": return "./drinkImage_F75.jpg"; 
            case "Fresh Lime Margarita": return "./drinkImage_FLM.jpg"; 
            case "Gin and Tonic": return "./drinkImage_GAT.jpg"; 
            case "Irish Coffee": return "./drinkImage_IC.jpg"; 
            case "Martini": return "./drinkImage_M.jpg"; 
            case "Mint Julep": return "./drinkImage_MJ.jpg"; 
            case "Mimosa": return "./drinkImage_MSA.jpg"; 
            case "Mojito": return "./drinkImage_MJT.jpg";
            case "Pina Colada": return "./drinkImage_PC.jpg";
            case "Pineapple Rum Punch": return "./drinkImage_PRP.jpg";
            case "Red Sangria": return "./drinkImage_RS.jpg"; 
            case "Screwdriver": return "./drinkImage_S.jpg";
            case "Whiskey Sour": return "./drinkImage_WS.jpg";
            case "White Russian": return "./drinkImage_WR.jpg";
            default: return "./drinkImage_Placeholder.jpg"; 
        }//end of switch 	
    }//end of getURL
      
    /*panel to hold recipe ingredients in a text area, with the relevant styling to make it approachable.*/
    private JPanel ingredientsPanel(Recipe r) {
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setBackground(Color.WHITE);
        textAreaI = new JTextArea(3, 1);
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
        textAreaD = new JTextArea(3, 10);
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
        an = new ActionNotifier();
        ah = new ActionHandler();
        aa = new ActionAdapter(ah);
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(40,70,101));
        bottomPanel.setLayout(new BorderLayout());
        favorite = new JCheckBox("Favorite", false);
        bottomPanel.add(favorite,BorderLayout.CENTER);
        favorite.setBackground(new Color(40,70,101));
        favorite.setForeground(new Color(240,239,245));
        favorite.setSelected(false);
        /*this part handles the object event now*/
        favorite.setActionCommand("favorite");
        group.add(favorite);
        group.add(r);
        an.addObserver(aa); //registers the ActionAdapter as a receiver of notifications from the ActionNotifier
        an.notify("favorite",group);//notify passes the pertinent info and object references to perform the action
        an.removeObserver(aa);
        group.clear();

        closeButton = new JButton("Close");
        bottomPanel.add(closeButton, BorderLayout.SOUTH);
        /*this part handles the object event now*/
        closeButton.setActionCommand("closeButton");
        group.add(closeButton);
        group.add(popupFrame);
        group.add(this); //RecipePopup foo = this;
        an.addObserver(aa); //registers the ActionAdapter as a receiver of notifications from the ActionNotifier
        an.notify("closeButton",group);//notify passes the pertinent info and object references to perform the action
        an.removeObserver(aa);
        group.clear();
        return bottomPanel;
    }//end of bottomPanel

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

    public RecipePopup returnIt(RecipePopup r) {
    	  op.release(r);
    	return r;
    } //end of returnIt
    
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
