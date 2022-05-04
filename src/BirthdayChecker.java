/*
 * BirthdayChecker.java creates a GUI just for user DOB entry 
 * We must be in compliance with NMDAA-84.
 * It is the gatekeeper for LittleBartender
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //for action listener
import java.util.*; //for Calendar
import java.awt.Color;

//@SuppressWarnings("serial")//suppresses the serialization runtime warning? or provide a unique id?
public class BirthdayChecker extends JPanel {
    private int month;
    private int day;
    private int year;
    private boolean isLegalAdult = false;//legal flag
    public boolean didAnswer = false; //response flag
    public static Calendar currentDate = Calendar.getInstance();
    public static int currentYear = currentDate.get(Calendar.YEAR);
    public final int LEGAL_YEAR = currentYear - 21;
    
    private static final long serialVersionUID = 6326472295628776147L;  // unique id? or suppress?
    
    /*constructor*/
    public BirthdayChecker(){
        JFrame frame = new JFrame("LittleBartender");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(693,332,500,300);  //frame.setBounds(693,332,500,300);
        ImageIcon myImage = new ImageIcon("./LittleBartender_Icon.png");
		frame.setIconImage(myImage.getImage());

        ImageIcon image = new ImageIcon("./LittleBartender_bkImage.png");
        JLabel imageLabel = new JLabel(image);
        frame.add(imageLabel);     
        
        imageLabel.setLayout(new FlowLayout(1, 0, 80));//vgap changed from 105 to 80 to fix overlap
       
        JPanel panel = new JPanel();
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        panel.setBackground(new Color(54,32,32));        

        JLabel DOB = new JLabel("Enter your date of birth:");
        DOB.setForeground(Color.white);
        panel.add(DOB);
    
        /*sets selectable year range: current year (cy) to cy - 80*/
        String[] yearSelection = new String[80];
        for(int i = 0; i < 80; i++){
            int calculatedYear = currentYear - i;
            yearSelection[i] = String.valueOf(calculatedYear);
        }//end of for
        
        /*sets selectable month range: 1 to 12*/
        String[] monthSelection = new String[12];
        fillDateArrayGeneric(monthSelection, 12);
        
        /*sets selectable day range: 1 to 31*/
        String[] daySelection = new String[31];
        fillDateArrayGeneric(daySelection, 31);

        /*BirthdayChecker GUI month pulldown*/
        final JComboBox<String> cm = new JComboBox<String>(monthSelection);
        cm.setVisible(true);
        panel.add(cm);
        
        /*BirthdayChecker GUI day pulldown*/
        final JComboBox<String> cd = new JComboBox<String>(daySelection);
        cd.setVisible(true);
        panel.add(cd);
        
        /*BirthdayChecker GUI year pulldown*/
        final JComboBox<String> cy = new JComboBox<String>(yearSelection);
        cy.setVisible(true);
        panel.add(cy);
        
        /*BirthdayChecker GUI OK button + ActionListener*/
        JButton btn = new JButton("OK");
        panel.add(btn);
        imageLabel.add(panel);
        frame.setVisible(true);
     
        btn.addActionListener(new ActionListener() { //TO ACTIONLISTENER CLASS???
            @Override
            public void actionPerformed(ActionEvent e){
                month = cm.getSelectedIndex() + 1;
                day = cd.getSelectedIndex() + 1;
                year = currentYear - cy.getSelectedIndex();
                revalidate();
                isLegalAdult = checkIfLegal(); //IF LEGAL GO, IF NOT?...             
                didAnswer = true; //THIS FLAG SET SHOULD BE IN A CONDITIONAL
                frame.dispose(); //close whether or not isLegalAdult()          
            }
        });
    }//end of constructor
    
    /**
     * UTILITY METHODS
     */
    
    /*checks if the user is of legal drinking age*/
    private boolean checkIfLegal(){
        if(year < LEGAL_YEAR){
            return true;
        }//end of if year <
        if(year == LEGAL_YEAR){
            if (month > currentDate.get(Calendar.MONTH)){
                return true;
            }//end of if month
            else if(month == currentDate.get(Calendar.MONTH)){
                if(day >= currentDate.get(Calendar.DATE)){
                    return true;
                }//end if if day
            }//end of else if
        }//end of if year ==
        return false;        
    }//end of checkIfLegal
    
    /*is does how it is named*/
    public int convertFromStringToInt(String input){
        return Integer.parseInt(input);
    }//end of convertFromStringToInt
    
    /*fills the fields of the JComboBoxes in the constructor with user-selectable values for year month and day*/
    public static void fillDateArrayGeneric(String[] array, int n){
        for(int i = 0; i < n; i++){
            array[i] = String.valueOf(i+1);
        }//end of for
    }//end of fillDateArrayGeneric

    /*calling class (LBDriver) gets to know whether or not the user has entered a DOB*/
    public boolean getIfLegal(){
        return isLegalAdult;
    }//end of getIfLegal

}//end of BirthdayChecker class