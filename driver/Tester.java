// Project1
// Author: Ivan Martinez 
// Course: CSCE314-599
// UIN: 529006731

package driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import hash341.City;
import hash341.CityTable;

import java.io.*;

// import hash341.*;
public class Tester {

	public static void main(String[] args) {
		
		CityTable myCityTable = new CityTable(new File("driver/US_Cities_LL.txt").getAbsolutePath(), 16000);
		
		
		ArrayList<City> cities = myCityTable.cities;
		Boolean flag = false;
		for(int i = 0; i < cities.size(); i++){
			// System.out.println(i + " Driver");
			City found = myCityTable.find(cities.get(i).name);
			if(!found.name.equals(cities.get(i).name)){
				flag = true;
			}
		}

		if(flag){
			System.out.println("Not all cities found");
		}else{
			System.out.println("All cities found !!!!!!!!!!!!!!!");
		}
		


		
		
		
		
	}

}
