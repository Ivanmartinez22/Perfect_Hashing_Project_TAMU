package driver;
import hash341.City;
import hash341.CityTable;
import java.util.ArrayList;
import java.io.*;
public class First {
    public static void main(String[] args) {
		CityTable myCityTable = new CityTable(new File("src/driver/US_Cities_LL.txt").getAbsolutePath(), 16000);
		myCityTable.writeToFile("src/driver/US_Cities_LL.ser");
	}
}
