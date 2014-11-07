public class Car {

    private int bornTime;
    private int dest; // 1 f�r rakt fram, 2 f�r v�nstersv�ng

    // konstruktor och get-metoder
    
    public Car(int b, int n){
	bornTime = b;
	dest = n;
    }

    public String toString(){
	String s = (bornTime + " " + dest);
	return s;
    }

    public int getDest(){
	return dest;
    }	
}