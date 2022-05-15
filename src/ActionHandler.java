import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
/*  Action Handler
 *
 *  buttonAction method called when a notification is received by ActionAdaptor
 *  responsible for processing/handling swing object events.
 * FINALLY! the thing that it does
 */

public class ActionHandler implements ActionListener {
    /*
     * THE COLLECTION OF RESUSEABLE SWING OBJECT VARIABLES
     */

    /*buttons*/
    private JButton jb;
    private JCheckBox jcb;

    /*data area widgets*/
    private JTextArea jtaD;
    private JTextArea jtaI;
    private JTextField jtf;

    /*panel widgets*/
    private JFrame jf;
    private RecipePopup rpu;

    /*text supporting widgets*/
    private Recipes r;
    private Recipe rec;
    private JList<String> jl;
    private ArrayList<String> alS;
    ArrayList<Object> groups = new ArrayList<Object>();//copy of the inbound array list for global use; so no pass needed

    /*handles button related events
     * For some unknown reason only the favorite and exit commands can be handled here
     * The rest have to be handled by an external class
     * Enhanced switch introduced in Java 12 has some nice features
     * java 17 docs: https://docs.oracle.com/en/java/javase/17/language/switch-expressions.html
     */
    public void buttonAction(String command, ArrayList group) {
        groups.addAll(group);
        switch (command) {
            case "favorite" -> favorite();
            case "buttonExit"-> buttonExit();
            case "closeButton"-> closeButton();
            case "buttonViewList"-> buttonViewList();
            case "buttonViewFaves"-> buttonViewFaves();
            case "buttonAddRecipe"-> buttonAddRecipe();
            case "buttonSearchByName"-> buttonSearchByName();
            case "buttonSearchByRecipe"-> buttonSearchByRecipe();
        } //end of enhanced switch
    }//end of ButtonAlert

    public void favorite(){
        jcb = (JCheckBox)groups.remove(0);
        rec = (Recipe)groups.remove(0);
        jcb.addActionListener(this);
    }//end of favorite

    public void closeButton(){
        jb = (JButton)groups.remove(0);//removes an element and decrements index of remaining...
        jf = (JFrame)groups.remove(0);//it sort of acts like pop
        rpu = (RecipePopup)groups.remove(0);
        jb.addActionListener(this);
    }//end of closeButton

    public void buttonExit(){
        jb = (JButton)groups.remove(0);
        jb.addActionListener(this);
    }//end of closeButton

    public void buttonViewList(){
        jb = (JButton)groups.remove(0);
        r = (Recipes)groups.remove(0);
        jl = (JList)groups.remove(0);
        jb.addActionListener(new ListButtonListener(jl,r));
    }//end of buttonViewList

    public void buttonViewFaves(){
        jb = (JButton)groups.remove(0);
        r = (Recipes)groups.remove(0);
        jl = (JList)groups.remove(0);
        jb.addActionListener(new FavoriteButtonListener(jl,r));
    }//end of buttonViewFaves

    public void buttonAddRecipe(){
        jb = (JButton)groups.remove(0);
        jtf = (JTextField)groups.remove(0);
        jtaD = (JTextArea)groups.remove(0);
        jtaI = (JTextArea)groups.remove(0);
        r = (Recipes)groups.remove(0);
        alS = (ArrayList<String>)groups.remove(0);
        jb.addActionListener(new AddRecipeListener(jtf, jtaD, jtaI, r, alS));
    }//end of buttonViewFaves

   public void buttonSearchByName(){
       jb = (JButton)groups.remove(0);
       r = (Recipes)groups.remove(0);
       jl = (JList)groups.remove(0);
       jtf = (JTextField)groups.remove(0);
       jb.addActionListener(new NameSearchListener(jl,r,jtf));
   }//end of buttonSearchByName

    public void buttonSearchByRecipe(){
        jb = (JButton)groups.remove(0);
        r = (Recipes)groups.remove(0);
        jl = (JList)groups.remove(0);
        jtaI = (JTextArea)groups.remove(0);
        jb.addActionListener(new IngredientSearchListener(jl,r,jtaI));
    }//end of buttonSearchByRecipe


    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()) {
            case "favorite":
                if(jcb.isSelected()){
                    rec.setFavorite();
                    //r.addItem(rec);
                }else{
                    rec.unFavorite();
                    //r.addItem(rec);
                }
                break;
            case "closeButton":
                System.out.println("We are returning a " + rpu.getClass());
                jf.setVisible(false);
                rpu.returnIt(rpu);
                break;
            case "buttonExit":
                System.exit(0);
                break;
        }//end of switch
    }//end of actionPerformed()

        /*
         * JAVA EQUIVALENT OF C++ System("pause");
         * CAN BE USED IN ANY CLASS
         */
        /*FOR DEBUGGING ONLY*/
        public static void pause() {
            System.out.println("\nPress Any Key To Continue...");
            new java.util.Scanner(System.in).nextLine();
        }//end of pause
}//end of ActionHandler class




