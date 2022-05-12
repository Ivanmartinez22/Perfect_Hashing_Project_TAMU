// Project1
// Author: Ivan Martinez 
// Course: CSCE314-599
// UIN: 529006731

package hash341;

import java.io.Serializable;
public class Pair implements Comparable<Pair>, Serializable{
    public int index = 0; 
    public Hash24 hash; 
    public int tries = 0;

    public Pair(){}

    public Pair( int index_, Hash24 hash_){
        this.index = index_;
        this.hash = hash_;
    }
    
    public String toString(){
        String i = String.valueOf(index);
        return  "Index: " + i + " Hash: " + hash + " Tries: " + tries;
    }

    public int compareTo(Pair p)
    {
        return this.index - p.index;
    }
}
