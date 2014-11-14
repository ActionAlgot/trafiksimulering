public class Car {

    private final int bornTime;
    private final int dest; // 1 för rakt fram, 2 för vänstersväng

    // konstruktor och get-metoder
    
    public Car(int b, int n) throws Exception(){
	if(b > -1){
	    if(n == 1 || n == 2){
		bornTime = b;
		dest = n;
	    }
	    else{
		throw new Exception("Destination must be either 1 or 2");
	    }
	}
	else{
	    throw new Exception("Borntime cannot be negative");
	}
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