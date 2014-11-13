public class Car {

    private int bornTime;
    private int dest; // 1 för rakt fram, 2 för vänstersväng

    // konstruktor och get-metoder
    
    public Car(int b, int n){
	bornTime = b;
	dest = n;
    }

    public int getBornTime(){
	return bornTime;
    }

    public String toString(){
	String s = (bornTime + " " + dest);
	return s;
    }

    public int getDest(){
	return dest;
    }	
}