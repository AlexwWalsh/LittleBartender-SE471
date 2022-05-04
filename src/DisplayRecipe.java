/*
 * DisplayRecipe.java contains all of the javax.swing package components necessary to 
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
public class DisplayRecipe {
    private JFrame popupFrame; //individual recipe popup
    private JPanel displayRecipe; //removed JPanel recipePanel; not used
    private JButton closeButton; //closes the drink recipe popup
    
    /*constructor*/
    public DisplayRecipe(Recipe r){
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
        JLabel rName = new JLabel(r.getObjectName());
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
        displayRecipe.add(ingredientsPanel(r));
        displayRecipe.add(directionsPanel(r)); 

        /*determine which image in the project root to add to a specific recipe; each image is 330x165 in size*/
        String name = r.getObjectName();
        if (name.equals("Bloody Mary")){ //EXCHANGE FOR A SWITCH??
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_BM.jpg")));
        } else if (name.equals("French 75")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_F75.jpg")));
        } else if (name.equals("Fresh Lime Margarita")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_FLM.jpg")));
        } else if (name.equals("Gin and Tonic")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_GAT.jpg")));
        } else if (name.equals("Irish Coffee")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_IC.jpg")));
        } else if (name.equals("Martini")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_M.jpg")));
        } else if (name.equals("Mint Julep")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_MJ.jpg")));
        } else if (name.equals("Mimosa")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_MSA.jpg")));
        } else if (name.equals("Mojito")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_MJT.jpg")));
        } else if (name.equals("Pina Colada")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_PC.jpg")));
        } else if (name.equals("Pineapple Rum Punch")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_PRP.jpg")));
        } else if (name.equals("Red Sangria")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_RS.jpg")));
        } else if (name.equals("Screwdriver")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_S.jpg")));
        } else if (name.equals("Whiskey Sour")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_WS.jpg")));
        } else if (name.equals("White Russian")){
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_WR.jpg")));
        } else {
        	 /*R - Placeholder image for new recipes since we can't add new ones after the fact*///WHY NOT, WHAT DOES THIS MEAN??
            displayRecipe.add(new JLabel(new ImageIcon("./drinkImage_Placeholder.jpg"))); //POTENTIAL SWITCH default?  
        }//end of super if
        displayRecipe.add(bottomPanel(r));
        return displayRecipe;
    }//end of createRecipePanel
    

    
    /*panel to hold recipe ingredients in a text area, with the relevant styling to make it approachable.*/
    private JPanel ingredientsPanel(Recipe r) {
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setBackground(Color.WHITE);
        JTextArea textAreaI = new JTextArea(3, 1);
        textAreaI.setBackground(new Color(240,239,245));
        Font font = new Font("Bookman Old Style",Font.PLAIN,12);
        textAreaI.setFont(font);
        textAreaI.setText("Ingredients:\n" + compileIngredients(r));
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
        
        textAreaD.setText("Directions:\n" + r.getDirections());
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
        closeButton.addActionListener(new ExitListener());
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
        public void actionPerformed(ActionEvent e){
            popupFrame.dispose();
        }//end of actionPerformed()
    }//end of ExitListener class
    
}//end of DisplayRecipe class
