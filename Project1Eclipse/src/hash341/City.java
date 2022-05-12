package hash341;

import java.io.Serializable;
public class City implements Serializable, Comparable<City> {
    public String name ; 
    public float latitude ; 
    public float longitude ;
    public boolean holder_city = false;
    public int index; 
    public int index2 = 0;
    public int num_collisions = 0;

    public City(){}

    public City(String name_, float latitude_, float longitude_){
        this.name = name_;
        this.latitude = latitude_;
        this.longitude = longitude_;
    }

    public int compareTo(City c)
    {
        return this.index - c.index;
    }

    public void print(){
        System.out.print("[" + name + "]");
    }
    
    public String print_s(){
        return name + " (" + latitude + "," + longitude + ")";
    }

}
