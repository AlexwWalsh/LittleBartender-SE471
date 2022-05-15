/*
 * ListObjects.java "was" an abstract? class with all concrete classes  
 * It is the superclass for Recipe 
 * WHY IS THAT, DO WE NEED THIS!!??
 * CAN/SHOULD THE FUNCTIONALITY BE MOVED TO THE CHILD CLASS??
 * */

public class ListObject { //removed abstract modifier
	private String name;
    
	/*returns the name of the object*/
	public String getObjectName() {
		return name;
	}//end of getObjectName
    
	/*sets the name of the object*/
	public void setObjectName(String name) {
		this.name = name;
	}//end of setObjectName
}//end of ListObject class