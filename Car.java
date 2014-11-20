

public class Car {

    private final int bornTime;
    private final int dest; // 1 för rakt fram, 2 för vänstersväng

    // konstruktor och get-metoder
    	/**
	 * @param b is int = time the car is born from the first step in the simulation
	 * @param n is an int which shows the destination, either 1 for lane 1 or 2 for lane 2
	 */
    public Car(int b, int n) throws IllegalArgumentException{

	   	if(b > -1){
	    if(n == 1 || n == 2){
		bornTime = b;
		dest = n;
	    }
	    else{
		throw new IllegalArgumentException("Destination must be either 1 or 2");
	    }
	}
	else{
	    throw new IllegalArgumentException("Borntime cannot be negative");
	}
    }
	/**
	 * @return time car entered system
	 */
    public int getBornTime(){
	return bornTime;

    }
	/**
	 * Returns representation of a car, including it's borntime and destination
	 */
    public String toString(){
	String s = (bornTime + " " + dest);
	return s;


    }
	/**
	 * returns the destination of a car
	 */
    public int getDest(){
	return dest;

    }	
}