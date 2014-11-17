public class Car {
	/**
	 * @Car 	Creates a car with borntime and destination
	 */

    private final int bornTime;
    private final int dest; // 1 för rakt fram, 2 för vänstersväng

    // konstruktor och get-metoder
    
    public Car(int b, int n) throws Exception(){
	/**
	 * @param b is int = time the car is born from the first step in the simulation
	 * @param n is an int which shows the destination, either 1 for lane 1 or 2 for lane 2
	 */
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
	/**
	 * @return time car entered system
	 */
    }

    public String toString(){
	String s = (bornTime + " " + dest);
	return s;
	/**
	 * Returns representation of a car, including it's borntime and destination
	 */

    }

    public int getDest(){
	return dest;
	/**
	 * returns the destination of a car
	 */
    }	
}