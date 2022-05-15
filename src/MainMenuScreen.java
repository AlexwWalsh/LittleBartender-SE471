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
import java.util.ArrayList;

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
	private JButton buttonSearchByRecipe;
	private JButton buttonViewList;
	private JButton buttonAddRecipe;
	private JButton buttonViewFaves;
	private JButton buttonExit;
	public Recipes recipes; //recipes as an Object not a container
	private ArrayList<String> ingredients;
	DefaultListModel<String> defaultListModel = new DefaultListModel<String>();//need access mod?
	boolean legal = false; //has age been verified?
	Recipe selectedRecipe;
	PopupManager pm;//

	ArrayList<Object> group = new ArrayList<Object>(); //holds the group of swing objects used for an action event

	ActionHandler ah;  //creates a handler object
	ActionNotifier an; //creates a notifier object
	ActionAdapter aa; //creates an adapter object

	/*constructor*/
	public MainMenuScreen (boolean legal){
        this.legal = legal; //the inbound boolean is the BirthdayChecker.getIfLegal() response
		if(legal) {
			createMenuFrame();//instantiate a window object
			recipes = new Recipes();//a recipes object//
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
					
					/**********************REVISED POPUP MAKER***************************/
					
					pm = new PopupManager(selectedRecipe); //created a new PopupManager to manage popups	
					
					/**********************REVISED POPUP MAKER***************************/
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

		/* This section handles the swing object events for the buttons of the main menu top ribbon
		 * Each button has a set of custom statements specific to its purpose
		 * Each ActionCommands assigned below will be used in ActionHandler.actionPerformed()
		 * switch statement to apply the appropriate actions
		 * Notify passes the pertinent info and object references to perform the action
		 * group is an ArrayList containing the pertinent info and object references
		 */
		an = new ActionNotifier();//see ActionNotifier header to learn why we use this
		ah = new ActionHandler(); //see ActionNHandler header to learn why we use this
		aa = new ActionAdapter(ah);//see ActionAdapter header to learn why we use this

		/*The main menu Exit button*/
		buttonExit.setActionCommand("buttonExit");
		group.add(buttonExit);
		an.addObserver(aa);
		an.notify("buttonExit",group);//notify passes the pertinent info and object references to perform the action
        an.removeObserver(aa);
		group.clear();

		/*The main menu View Recipe List button*/
		buttonViewList.setActionCommand("buttonViewList");
		group.add(buttonViewList);
		group.add(recipes);
		group.add(recipeList);
		an.addObserver(aa);
		an.notify("buttonViewList",group);//notify passes the pertinent info and object references to ActionNotify
		an.removeObserver(aa);
		group.clear();

		/*The main menu View Favorite Recipes button*/
		buttonViewFaves.setActionCommand("buttonViewFaves");
		group.add(buttonViewFaves);
		group.add(recipes);
		group.add(recipeList);
		an.addObserver(aa);
		an.notify("buttonViewFaves",group);//notify passes the pertinent info and object references to ActionNotify
		an.removeObserver(aa);
		group.clear();

		/*The main menu Add Recipe button*/
		buttonAddRecipe.setActionCommand("buttonAddRecipe");
		group.add(buttonAddRecipe);
		group.add(recipeNameTextField);
		group.add(directionsTextArea);
		group.add(ingredientTextArea);
		group.add(recipes);
		group.add(ingredients);
		an.addObserver(aa);
		an.notify("buttonAddRecipe",group);//notify passes the pertinent info and object references to ActionNotify
		an.removeObserver(aa);
		group.clear();

		return buttonPanelA;
	} //end of createButtonPanelA
	
	/*creates a panel at the bottom to hold the search buttons*/
	private JPanel createButtonPanelB(){    	
		buttonPanelB = new JPanel();
		buttonPanelB.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanelB.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder())); 
		buttonPanelB.setBackground(new Color(40,86,110));
		buttonSearchByName = new JButton("Search by Name");		
		buttonSearchByRecipe = new JButton("Search by Ingredient");
		buttonPanelB.add(buttonSearchByName);
		buttonPanelB.add(buttonSearchByRecipe);

		/* This section handles the swing object events for the buttons of the main menu bottom ribbon
		 * Each button has a set of custom statements specific to its purpose
		 * Each ActionCommands assigned below will be used in ActionHandler.actionPerformed()
		 * switch statement to apply the appropriate actions
		 * Notify passes the pertinent info and object references to perform the action
		 * group is an ArrayList containing the pertinent info and object references
		 */
		an = new ActionNotifier();//see ActionNotifier header to learn why we use this
		ah = new ActionHandler(); //see ActionNHandler header to learn why we use this
		aa = new ActionAdapter(ah);//see ActionAdapter header to learn why we use this

		/*The main menu Search By Name button*/
		buttonSearchByName.setActionCommand("buttonSearchByName");
		group.add(buttonSearchByName);
		group.add(recipes);
		group.add(recipeList);
		group.add(recipeNameTextField);
		an.addObserver(aa);
		an.notify("buttonSearchByName",group);//notify passes the pertinent info and object references to ActionNotify
		an.removeObserver(aa);
		group.clear();

		/*The main menu Search By Recipe button*/
		buttonSearchByRecipe.setActionCommand("buttonSearchByRecipe");
		group.add(buttonSearchByRecipe);
		group.add(recipes);
		group.add(recipeList);
		group.add(ingredientTextArea);
		an.addObserver(aa);
		an.notify("buttonSearchByRecipe",group);//notify passes the pertinent info and object references to ActionNotify
		an.removeObserver(aa);
		group.clear();

		return buttonPanelB;
	}//end of createButtonPanelB

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

	/*
	 * JAVA EQUIVALENT OF C++ System("pause");
	 * CAN BE USED IN ANY CLASS
	 */
	/*FOR DEBUGGING ONLY*/
	public static void pause() {
		System.out.println("\nPress Any Key To Continue...");
		new java.util.Scanner(System.in).nextLine();
	}//end of pause
}//end of MainMenu class