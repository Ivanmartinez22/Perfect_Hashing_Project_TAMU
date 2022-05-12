// Project1
// Author: Ivan Martinez 
// Course: CSCE314-599
// UIN: 529006731
package driver;
import java.util.* ;
import java.io.* ;
import hash341.City ;
import hash341.CityTable ;
public class Second {
    //Ubuntu is slow for some reason
    public static void main (String [] args) {   
        
        CityTable US_Cities ;
        String cName ;
  
        US_Cities = CityTable.readFromFile("driver/US_Cities_LL.ser") ;
        // System.out.println("The fuck");
        while(true){
            Scanner input = new Scanner(System.in);
            System.out.print("Enter City, State (or 'quit'): ");
            String read = input.nextLine();
            if(read.equals("quit")){
                break;
            }
            City Pontential = US_Cities.find(read);
            if(Pontential == null){
                System.out.println("Could not find \'" + read + "\'");
            }
            else{
                System.out.print("Found " + Pontential.print_s() + "\n");
                System.out.println("http://www.google.com/maps?z=10&q=" + Pontential.latitude + "," + Pontential.longitude);
            }
        }

    }
}
