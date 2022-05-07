/*
 * MainMenuScreen.java contains all of the javax.swing package  
 * components necessary to create and manipulate the main menu GUI.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.nio.file.Paths;
import javax.swing.*;
import java.awt.Color;

/*
 * MainMenuScreen swing component descriptions:
 * JFrame - a top-level window with a title and a border; An extended version of java.awt.Frame
 * JList - a component that displays a list of objects and allows the user to select one or more items.
 * JScrollPane - provides a scrollable view of a lightweight component.
 * JPanel - a space on which an application can attach any other component.
 * JLabel - a display area for a short text string or an image, or both.
 * ImageIcon - an implementation of the Icon interface that paints icons from images.
 * JTextField -  a lightweight component that allows the editing of a single line of text.
 * JTextArea - a multi-line area that displays plain text.
 * JButton - a configurable "push" button that generates an event.
 * DefaultListModel - a simple implementation of ListModel used to manage items displayed by a JList control.
 */
public class MainMenuScreen implements MenuProxyIF {
	private JFrame menuFrame;
	private JList<String> recipeList;
	private JScrollPane scrollPane;
	private JScrollPane scrollA;
	private JScrollPane scrollB;
	private JPanel borderPanel;
	private JPanel gridPanel;
	private JPanel buttonPanelA;
	private JPanel buttonPanelB;
	private JLabel recipeNameLabel;
	private JLabel ingredientNameLabel;
	private JLabel directionsLabel;
	private ImageIcon myImage;
	private JTextField recipeNameTextField;
	private JTextArea ingredientTextArea;
	private JTextArea directionsTextArea;
	private JButton buttonSearchByName;
	private JButton buttonSearchByrecipe;
	private JButton buttonViewList;
	private JButton buttonAddRecipe;
	private JButton buttonViewFaves;
	private JButton buttonExit;
	private Recipes recipes;
	private ArrayList<String> ingredients;
	DefaultListModel<String> defaultListModel = new DefaultListModel<String>();//need access mod?
	boolean legal = false; //has age been verified?

	Recipe selectedRecipe;

	ObjectPool popup;

	int poolSize = 2; //size of the service object pool; the limit of popup object instances

	/*constructor*/
	public MainMenuScreen (boolean legal){
        this.legal = legal; //the inbound boolean is the BirthdayChecker.getIfLegal() response
		if(legal) {
			createMenuFrame();//instantiate a window object
			recipes = new Recipes();//a recipe object
			ingredients = new ArrayList<String>(); //the list of drink ingredients

			/*
			 * read through text file and add each line that has values delimited by "-"
			 * to their respective fields,then add each of those to recipes to prepopulate
			 * the list with recipes.
			 * */
			try {
				Scanner input = new Scanner(Paths.get("./Recipes.txt"));
				input.useDelimiter("-|\n");
				while (input.hasNext()) {
					Recipe defaultRecipe = new Recipe();
					defaultRecipe.setObjectName(input.next());
					defaultRecipe.addItem(input.next());
					defaultRecipe.setDirections(input.next());
					recipes.addItem(defaultRecipe);
				}//end of while
				input.close();
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Exception: " + e.getMessage());
			}//end of try/catch block
		}
		else{ //if we are here, the user isn't old enough
			checkAge();
			System.exit(0);
		}//end of else
	}//end of constructor

    /*creates the underlying window for the menu and sets window properties*/
	private void createMenuFrame(){
		menuFrame = new JFrame();
		menuFrame.setBounds(706,286,492,500);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setTitle("LittleBartender");
		menuFrame.add(createBorderPanel());

		/*Image to replace the default java icon, given universal path*/
		myImage = new ImageIcon("./LittleBartender_Icon.png");
		menuFrame.setIconImage(myImage.getImage());
	}//end of newFrame
	
	/*creates the swing object panel for the underlying window*/
	private JPanel createBorderPanel(){
		borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.setBackground(new Color(78,143,175));
		borderPanel.add(createScrollPane(),BorderLayout.EAST);
		borderPanel.add(createGridPanel(), BorderLayout.WEST);
		borderPanel.add(createButtonPanelA(),BorderLayout.NORTH);
		borderPanel.add(createButtonPanelB(),BorderLayout.SOUTH);
		return borderPanel;
	}//end of createBorderPanel
	
	/*this is where the list model on the right side of the menu window is constructed*/ 
	private JScrollPane createScrollPane(){
		recipeList = new JList<>();
		/*Add a mouse listener that will pull a recipe upon 2 mouse clicks*/
		recipeList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				selectedRecipe = new Recipe(); //Recipe selectedRec = new Recipe();
				JList<?> list = (JList<?>)evt.getSource(); //replacing explicit cast with ? eliminates the warning
				if(evt.getClickCount() == 2){
					ArrayList<Recipe>items = recipes.getItems();
					int index = list.locationToIndex(evt.getPoint());

					/*
					 * gives functionality after searching for a recipe by matching the updated list model to relevant
					 * recipes so that when they are selected, it transitions to the view of the correct recipe 
					 */
					for(int i = 0; i < items.size(); i++){
							if(items.get(i).getObjectName().contains(list.getModel().getElementAt(index).toString())){
								selectedRecipe = items.get(i);
						}//end of if items.get(i)...
					}//end of for(int i...
					
					/*create the object pool*/
					popup = ObjectPool.getPoolInstance(poolSize);

                    /* If the number of recipe popup instances is less than the limit we can create more; if not a warning is displayed
                     * SOME REFINEMENTS ARE NEED HERE TO MAKE IT MORE ROBUST AND 100% PREDICTABLE
                     */
					if(popup.getInstanceCount() < poolSize ){
						RecipePopup pop = (RecipePopup) popup.getObject(selectedRecipe); //the returned object must be typed casted to RecipePopup
						pop.showPopup();
					}//end of if 
					else{
						JOptionPane.showMessageDialog(null, "You are only allowed " + poolSize + " recipe popups."); //a warning dialog when the poolSize is exceeded
					}//end of else

				}//end of if(evt.getClickCount
			}//end of mouseClicked
		}); //end of recipeList.addMouseListener
		
	    /*creates a scrollable pane for the recipeList */
		scrollPane = new JScrollPane(recipeList);
		scrollPane.getViewport().getView().setBackground(new Color(240,239,245)); 
		return scrollPane;
	}//end of createScrollPane
	
	/*
	 * creates swing object panel for arranging swing 
	 * components on the LittleBartender window. 
	 * and sets them to a standard size and formatting.
	*/
	private JPanel createGridPanel(){
		/*creates the main grid panel*/
		gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(8,1));
		gridPanel.setBackground(new Color(78,143,175));

		/*Recipe name text field*/
		recipeNameLabel = new JLabel("Enter Recipe Name");
		recipeNameTextField = new JTextField();
		recipeNameTextField.setBackground(new Color(240,239,245));
        recipeNameTextField.setColumns(6);

		/*Ingredients text field*/
		ingredientNameLabel = new JLabel("Ingredients");
		ingredientTextArea = new JTextArea();
		scrollA = new JScrollPane (ingredientTextArea,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		ingredientTextArea.setBackground(new Color(240,239,245));
		ingredientTextArea.setLineWrap(true);

		/*Directions text field*/ 
        directionsLabel = new JLabel("Directions");
        directionsTextArea = new JTextArea();
        scrollB = new JScrollPane (directionsTextArea,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		directionsTextArea.setBackground(new Color(240,239,245));
		directionsTextArea.setLineWrap(true);
		directionsTextArea.setSize(200, 300);

		/*adding all elements to the gridPanel.*/
		gridPanel.add(recipeNameLabel, BorderLayout.NORTH);
		gridPanel.add(recipeNameTextField, BorderLayout.NORTH);
		gridPanel.add(ingredientNameLabel, BorderLayout.NORTH);
		gridPanel.add(scrollA, BorderLayout.NORTH);
        gridPanel.add(directionsLabel, BorderLayout.NORTH);
        gridPanel.add(scrollB, BorderLayout.NORTH);
		
		return gridPanel;
	}//end of createGridPanel


	/*creates a panel at the top to hold the view, add and exit buttons*/
	private JPanel createButtonPanelA(){
		buttonPanelA = new JPanel();
		buttonPanelA.setLayout(new FlowLayout(FlowLayout.LEFT));
		//Added a new frame to surround buttons
		buttonPanelA.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder())); 
		buttonPanelA.setBackground(new Color(40,86,110));
		buttonViewList = new JButton("View Recipe List");
		buttonAddRecipe = new JButton("Add Recipe");
		buttonViewFaves = new JButton("View Favorited Recipes");
		buttonExit = new JButton("Exit");

		// Add top buttons
        buttonPanelA.add(buttonViewList);
		buttonPanelA.add(buttonAddRecipe);
		buttonPanelA.add(buttonViewFaves); 
		buttonPanelA.add(buttonExit);

		//Action listeners
		buttonViewList.addActionListener(new ListButtonListener());
		buttonAddRecipe.addActionListener(new AddRecipe());
		buttonViewFaves.addActionListener(new FavoriteButtonListener());
		buttonExit.addActionListener(new ExitListener());	
		return buttonPanelA;
	}
	
	/*creates a panel at the bottom to hold the search buttons*/
	private JPanel createButtonPanelB(){    	
		buttonPanelB = new JPanel();
		buttonPanelB.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanelB.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder())); 
		buttonPanelB.setBackground(new Color(40,86,110));
		buttonSearchByName = new JButton("Search by Name");		
		buttonSearchByrecipe = new JButton("Search by Ingredient");
		buttonPanelB.add(buttonSearchByName);
		buttonPanelB.add(buttonSearchByrecipe);
		buttonSearchByName.addActionListener( new NameSearchListener());
		buttonSearchByrecipe.addActionListener( new IngredientSearchListener());
		return buttonPanelB;
	}//end of createButtonPanelB
	
	
	/**
	 * The 6 nested classes below provide "actions"
	 * to the various buttons used on the menu screen
	 * They all implement the swing ActionListener Interface
	 * and override actionPerformed()
	 * setModel() sets the model that represents the contents or "value" of the list, 
	 * notifies property change listeners, and then clears the list's selection.
	 * NESTED CLASS ARE LEGAL BUT THESE SHOULD BE BROKEN OUT!!
	 * ACTIONLISTENER CLASS???
	 */
	/*Closes program on pressing "exit"*/
	private class ExitListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}	
	}//end of ExitListener class

	/*
	 * Gets all recipes currently in the recipes list, initially just
	 * what is in the text file, then later what is added as well.
	 */
	private class ListButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ArrayList<Recipe>items = recipes.getItems();
			DefaultListModel<String> dList = new DefaultListModel<String>();
			for(Recipe i : items){
				dList.addElement(i.getObjectName());
			}//end of for
			recipeList.setModel(dList);
		} //end of actionPerformed()	
	}//end of ListButtonListener class

	/* Sets recipe objects as favorites*/
	private class FavoriteButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ArrayList<Recipe> items = recipes.getItems();
			DefaultListModel<String> dlist = new DefaultListModel<String>();
			for(Recipe i : items){
				if(i.getFavorite() == true){
					dlist.addElement(i.getObjectName());
				}//end of if
			}//end of for
			recipeList.setModel(dlist);
		}//end of actionPerformed()
	}//end of FavoriteButtonListener class

	/*
	 * Takes the name from the recipe name field and checks the list returned by
	 * getObjectName from Recipes, and if it is there then adds only the ones with that 
	 * name to the list model.
	 */
	private class NameSearchListener implements ActionListener{
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
	}//end of NameSearchListener class

	/*
	 * Looks at the ingredient text area and if any of the items returned
	 * by getItems() contain what is passed, then only those recipes are added to the list model.
	 */
	private class IngredientSearchListener implements ActionListener{
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
	}//end of IngredientSearchListener class

	/*
	 * listens for the add recipe button to be pressed, and when it does passes the text 
	 * in each of the fields to a recipe object, that is passed into the recipes array.
	 */
	private class AddRecipe implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Recipe r = new Recipe();
			r.setObjectName(recipeNameTextField.getText());
			r.setDirections(directionsTextArea.getText());
			r.addItem(ingredientTextArea.getText());	
			recipes.addItem(r);
			recipeNameTextField.setText("");
			directionsTextArea.setText("");
			ingredients.clear();
		}//end of actionPerformed()		
	}//end of AddRecipe class

	/**
	 * UTILITY METHODS
	 */

	/*This version of checkAge provides the under age rejection popup*/
	@Override
	public void checkAge(){
		JOptionPane.showMessageDialog(null, "You must be atleast 21 years old to use LittleBartender!!");
	}//end of checkAge

	/*making sure menuFrame is set to visible so it renders*/
    public void showMenu(){
        menuFrame.setVisible(true);
    }//end of showMenu
}//end of MainMenu class