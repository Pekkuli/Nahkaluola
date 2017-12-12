package wad;


import java.util.Comparator;
import wad.domain.Uutinen;

public class CustomComparator implements Comparator<Uutinen> {
    
//    CustomComparator uutisten järjestämiseen päivämäärän mukaan
    
    @Override
    public int compare(Uutinen o1, Uutinen o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}