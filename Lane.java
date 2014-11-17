

public class Lane {
    
    private Car[] theLane;
    	/**@param n is how many slots a car can fit into lane
    	 */
    public Lane(int n) throws Exception{

	if(n>0){
	    theLane = new Car[n];	    
	}
	else{
	    throw new Exception("Lane length must be greater than zero");
	}
	// Konstruerar ett Lane-objekt med plats f�r n fordon
    }

    public void step() {
	for(int i = 0; i < theLane.length; i++){
	    if(theLane[i] != null){
		if(i != 0){
		    if(theLane[i-1] == null){
			theLane[i-1] = theLane[i];
			theLane[i] = null;
		    }
		}
	    }
	}
	// Stega fram alla fordon (utom det p� plats 0) ett steg 
        // (om det g�r). (Fordonet p� plats 0 tas bort utifr�n 
	// mm h a metoden nedan.)
    }
	/** 
	 * @return returns and removes the first car in a lane
	 *		
	 */
    public Car getFirst() {
	Car temp = theLane[0];
	theLane[0] = null;
	return temp;

	// Returnera och tag bort bilen som st�r f�rst
    }
	/**
	 * @return returns the first car in a lane
	 */
    public Car firstCar() {
	return theLane[0];

	// Returnera bilen som st�r f�rst utan att ta bort den
    }
    /**
     * @return returns true if last place in Lane is free
     * otherwise returns false
     */

    public boolean lastFree() {
	if(theLane[theLane.length-1] == null){
	    return true;
	}
	return false;
	// Returnera true om sista platsen ledig, annars false

    
    }
   /**
     * @param car which is to be put last in Lane
     */
    public void putLast(Car c) throws OverflowException {
 
    if(lastFree()){
	    theLane[theLane.length-1] = c;
	}
	else{
	    throw new OverflowException();

	}
	// St�ll en bil p� sista platsen p� v�gen
	// (om det g�r).
    }
    /**
     * @return returns the representation of either
     * a car or an empty spot for the representation
     * system
     */
    public String toString() {
	String s = "";
	for(Car c: theLane){
	    if(c != null){
		s = s + (c.toString() + " ");
	    }
	    else{
		s = s + ("X ");
	    }
	}
	return s;
    }

}