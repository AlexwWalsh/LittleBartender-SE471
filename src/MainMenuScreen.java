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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class MainMenuScreen implements MenuProxyIF{
	public JFrame menuFrame;
	public JList<String> recipeList;
	public JScrollPane scrollPane;
	public JScrollPane scrollA;
	public JScrollPane scrollB;
	public JPanel borderPanel;
	public JPanel gridPanel;
	public JPanel buttonPanelA;
	public JPanel buttonPanelB;
	public JLabel recipeNameLabel;
	public JLabel ingredientNameLabel;
	public JLabel directionsLabel;
	public ImageIcon myImage;
	public JTextField recipeNameTextField;
	public JTextArea ingredientTextArea;
	public JTextArea directionsTextArea;
	public JButton buttonSearchByName;
	public JButton buttonSearchByrecipe;
	public JButton buttonViewList;
	public JButton buttonAddRecipe;
	public JButton buttonViewFaves;
	public JButton buttonExit;
	public JRadioButton darkButton;
	public JRadioButton lightButton;
	public Recipes recipes;
	public ArrayList<String> ingredients;
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
	public void createMenuFrame(){
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
	public JPanel createBorderPanel(){
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
	public JScrollPane createScrollPane(){
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
	public JPanel createGridPanel(){
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
	public JPanel createButtonPanelA(){
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

		buttonViewList.setActionCommand("ButtonViewList");
		buttonAddRecipe.setActionCommand("ButtonAddRecipe");
		buttonViewFaves.setActionCommand("ButtonViewFaves");
		buttonExit.setActionCommand("ButtonExit");
		//Action listeners
		buttonViewList.addActionListener(new Observer());
		buttonAddRecipe.addActionListener(new Observer());
		buttonViewFaves.addActionListener(new Observer());
		buttonExit.addActionListener(new Observer());
		return buttonPanelA;
	}

	/*creates a panel at the bottom to hold the search buttons*/
	public JPanel createButtonPanelB(){
		buttonPanelB = new JPanel();
		buttonPanelB.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanelB.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
		buttonPanelB.setBackground(new Color(40,86,110));
		buttonSearchByName = new JButton("Search by Name");
		buttonSearchByrecipe = new JButton("Search by Ingredient");
		darkButton = new JRadioButton("Dark Mode");
		lightButton = new JRadioButton("Light Mode");
		buttonPanelB.add(buttonSearchByName);
		buttonPanelB.add(buttonSearchByrecipe);
		buttonPanelB.add(darkButton);
		buttonPanelB.add(lightButton);
		buttonSearchByName.setActionCommand("ButtonSearchByName");
		buttonSearchByrecipe.setActionCommand("ButtonSearchByRecipe");
		buttonSearchByName.addActionListener( new Observer());
		buttonSearchByrecipe.addActionListener( new Observer());
		//darkButton.addActionListener(new darkModeListener());
		//lightButton.addActionListener(new lightModeListener());
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
}//end of MainMenu class