package hash341;
import java.lang.Math;
import java.util.HashSet;
import java.util.ArrayList;
// import java.util.List;
import java.io.*;
import java.util.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

import java.io.Serializable;
public class CityTable implements Serializable {
    private int table_size;
    public Hash24 primary_hash;// primary hash function
    public ArrayList<City> cities = new ArrayList<City>(); // 1d list of cities 
    private ArrayList<ArrayList<City>> cities2d = new ArrayList<ArrayList<City>>(); // 2d array that serves as the actual storage for the hash table 
    public ArrayList<Pair> pairs = new ArrayList<Pair>(); // stores hashes and indexes
    public ArrayList<Integer> collision_indeces = new ArrayList<Integer>(); // List of the collision indeces 
    private ArrayList<City> colliding_cities = new ArrayList<City>(); // cities that collide
    private int pairs_num = 0;

    public CityTable(){}

    public CityTable (String fname, int tsize){
        // this.filename = fname;
        this.table_size = tsize;
        //File input
        Scanner file_read = null; 
		try
		{
		    file_read = new Scanner(new FileReader(fname));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
        for(int i = 0; i < tsize; i++){ //creates empty city storage 
            ArrayList<City> one_city = new ArrayList<City>();
            City append = new City("", 0, 0);
            Pair pappend = new Pair();
            append.holder_city = true;
            one_city.add(append);
            cities2d.add(one_city);
            pairs.add(pappend);
        }
        primary_hash = new Hash24(); // primary hash
        while(file_read.hasNextLine()) //extracts data from file and places into hash storage 
		{
			String city_name = file_read.nextLine(); //parse block
            String coordinates = file_read.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(coordinates);
			float longi = Float.parseFloat(tokenizer.nextToken());
			float lat = Float.parseFloat(tokenizer.nextToken());
            City append = new City(city_name, longi, lat); //
            cities.add(append);
            int index = primary_hash.hash(city_name) % tsize; // get index 
            append.index = index;
            if(cities2d.get(index).get(0).holder_city){ //check if index is empty
                cities2d.get(index).set(0, append); //place into primary table
            }
            else{ // if not record the cities that colliding
                collision_indeces.add(index);
                cities2d.get(index).get(0).num_collisions += 1;
                colliding_cities.add(append);
            }
		}
        for(int i = 0; i < collision_indeces.size(); i++){ //make secondary tables of size collisions^2 in primary indexes where collisions occured
            int collisions = cities2d.get(collision_indeces.get(i)).get(0).num_collisions;
            if(collisions > 0){
                collisions += 1;
                colliding_cities.add(cities2d.get(collision_indeces.get(i)).get(0));
                City append = new City("", 0, 0); // empty cities in order to check for collisions 
                append.holder_city = true;
                cities2d.get(collision_indeces.get(i)).clear();
                for(int j = 0; j < (collisions*collisions); j++){
                    cities2d.get(collision_indeces.get(i)).add(append);
                }
            }
        }
        HashSet<Integer> set = new HashSet<Integer>(collision_indeces); // removes repeats of collision indexes 
        ArrayList<Integer> list = new ArrayList<Integer>(set);
        collision_indeces = list;
        //sort colliding cities and indexes by index
        Collections.sort(colliding_cities); 
        Collections.sort(collision_indeces);
        int offset = 0;
        for(int i = 0; i < collision_indeces.size(); i++){ // add cities to secondary table by collision indexes 
            
            int chunk_size = (int) Math.sqrt( cities2d.get(collision_indeces.get(i)).size()); // the number of cities per secondary table is equal secondary tabel size squared
            int end_index = offset + (chunk_size); //gets last index of the relevant chunk of collided cities array
            ArrayList<City> chunk = new ArrayList<City>(colliding_cities.subList(offset, end_index)); // cities that belong in secondary table
            boolean exit = false;
            City append = new City("", 0, 0);  // empty city in order to check for collisions
            append.holder_city = true;
            int tries = 0; // counts number of hashes tried

            while(true){
                Hash24 secondary_hash = new Hash24(); // secondary hash
                tries += 1;
                Stack<City> stack = new Stack<City>(); // tracks unadded cities
                for(int j = 0; j < chunk.size(); j++){
                    stack.push(chunk.get(j));
                }
                int placed = 0;
                while(true){ // try to place cities into secondary table
                    if(placed == chunk.size()){ //if number placed equal number of cities break
                        exit = true;
                        break;
                    }
                    City popped = stack.pop(); // popping from stack
                    int index = secondary_hash.hash(popped.name) % (chunk.size() * chunk.size() ); // get index 
                    if(cities2d.get(collision_indeces.get(i)).get(index).holder_city){ //check if index is empty
                        popped.index2 = index;
                        popped.num_collisions = 0;
                        cities2d.get(collision_indeces.get(i)).set(index, popped); // place in index 
                        placed += 1;
                    }
                    else{ // if not empty table break loop start again
                        cities2d.get(collision_indeces.get(i)).clear(); //empties secondary table 
                        for(int k = 0; k < (chunk.size() *chunk.size() ); k++){ 
                            cities2d.get(collision_indeces.get(i)).add(append); // adding empty cities
                        }
                        break;
                    }
                }
                if(exit){// if all placed exit loop
                    Pair new_pair = new Pair(collision_indeces.get(i), secondary_hash); 
                    new_pair.tries = tries;
                    pairs.set( collision_indeces.get(i), new_pair); // add to track hashes and indexes 
                    pairs_num += 1;
                    break;
                }    
            }
            offset += chunk_size; //track offset of collisions 
        }
		file_read.close();
        this.print_stats();
    }

    public City find(String cName){ // work on cities that do not exist 
        int primary_index = primary_hash.hash(cName) % table_size; //get primary index
        int secondary_index = 0;
        City return_city = null;
        if(cities2d.get(primary_index).get(secondary_index).name.equals(cName)){ // if in primary table/slot return city
            return_city = cities2d.get(primary_index).get(secondary_index);
            return return_city;
        }
        else{ //check secondary
            Pair key = pairs.get(primary_index);
            Hash24 secondary_hash_loc = key.hash; // get secondary hash
            if(secondary_hash_loc == null){ // check if hash is empty due to non existent city input
                return return_city;
            }
            secondary_index = secondary_hash_loc.hash(cName) % cities2d.get(primary_index).size();
            return_city = cities2d.get(primary_index).get(secondary_index);
            if(return_city.name.equals("")){ //check if city is empty
                return null;
            }
            if(!return_city.name.equals(cName)){//check if wrong city
                return null;
            }
        }
        return return_city;

    }

    

    public void print_testing(){ // Do not delete print code for testing
        int placed = 0;
        System.out.println("City Table Print Function: ");
            int size = cities2d.size();
            for(int i = 0; i < size; i++){
                int size2 = cities2d.get(i).size();
                for(int j = 0; j < size2; j++){
                    if(!cities2d.get(i).get(j).holder_city){
                        placed += 1;
                    }
                    
                }
                
            }
        System.out.println("Number placed: " + placed);
        System.out.println("Collsions indexs size " + collision_indeces.size());
        System.out.println("2d size " + cities2d.size());
        System.out.println("Pairs num: " + pairs_num);
    }

    public void print_stats(){ // Do not delete print code for testing
        System.out.println("Primary hash table hash function:");
        this.primary_hash.dump();
        System.out.println("");
        System.out.println("Primary hash table statistics:");
        System.out.println("   Number of cities: " + cities.size());
        System.out.println("   Table size: " + this.table_size);
        ArrayList<String> Strings = new ArrayList<String>();
        int max_collisions = 0;
        int size = cities2d.size();
        int slot = 0;
        int num_of_second_more_than_one = 0;
        for(int i = 0; i < 25; i++){
            int num_slots = 0;
            for(int j = 0; j < size; j++){
                int size2 = cities2d.get(j).size();
                int placed = 0;
                for(int k = 0; k < size2; k++){
                    if(!cities2d.get(j).get(k).holder_city){
                        placed += 1;
                    }
                }
                if(placed == i){
                    num_slots += 1;
                    max_collisions = i;
                    slot = j;
                    if(placed > 1){
                        num_of_second_more_than_one += 1;
                    }
                }
            }
            Strings.add("    # of primary slots with " + i + " cities = " + num_slots);
        }
        System.out.println("   Max collisions = " + max_collisions);
        for(int i = 0; i < 25; i++){
            System.out.println(Strings.get(i));
        }
        System.out.println("");
        System.out.println("");
        int size2 = cities2d.get(slot).size();
        System.out.println("*** Cities in the slot with most collisions ***");
        for(int i = 0; i < size2; i++){
            if(!cities2d.get(slot).get(i).holder_city){
                System.out.println("   " + cities2d.get(slot).get(i).print_s());
            }
        }
        //Secondary table stats 
        float average = 0;
        System.out.println("");
        System.out.println("Secondary hash table statistics:");
        int num_of_pairs = 0;
        for(int i = 1; i < 21; i++){
            int num_tables = 0;
            for(int j = 0; j < pairs.size(); j++){
                if(pairs.get(j).tries == i){
                    num_tables += 1;
                }
                if(pairs.get(j).tries > 0){
                    num_of_pairs += 1;
                }
                average += pairs.get(j).tries;
            }
            System.out.println("   # of secondary hash tables trying " + i + " hash functions = " + num_tables);
        }
        average /= num_of_pairs;
        System.out.println("");
        System.out.println("Number of secondary hash tables with more than 1 item = " + num_of_second_more_than_one);
        System.out.println("Average # of hash functions tried = " + average);
    }

    public void writeToFile(String fName){
        try {
            FileOutputStream fstream = new FileOutputStream(fName);
            ObjectOutputStream ostream = new ObjectOutputStream(fstream);
            // write something in the file
            ostream.writeObject(this);
            // close the stream
            ostream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static CityTable readFromFile(String fName){
        CityTable return_table = null;
        try {
            ObjectInputStream oistream = new ObjectInputStream(new FileInputStream(fName));
            return_table = (CityTable) oistream.readObject();
            oistream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return return_table;

    }

}
