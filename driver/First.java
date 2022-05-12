// Project1
// Author: Ivan Martinez 
// Course: CSCE314-599
// UIN: 529006731

package driver;
import hash341.City;
import hash341.CityTable;
import java.util.ArrayList;
import java.io.*;
public class First {
    public static void main(String[] args) {
		CityTable myCityTable = new CityTable(new File("driver/US_Cities_LL.txt").getAbsolutePath(), 16000);
		myCityTable.writeToFile("driver/US_Cities_LL.ser");
	}
}
