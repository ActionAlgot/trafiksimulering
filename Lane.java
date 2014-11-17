

public class Lane {
    
    private Car[] theLane;

    public Lane(int n) throws Exception{
    	/**@param n is how many slots a car can fit into
    	 * in lane
    	 */
	if(n>0){
	    theLane = new Car[n];	    
	}
	else{
	    throw new Exception("Lane length must be greater than zero");
	}
	// Konstruerar ett Lane-objekt med plats för n fordon
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
	// Stega fram alla fordon (utom det på plats 0) ett steg 
        // (om det går). (Fordonet på plats 0 tas bort utifrån 
	// mm h a metoden nedan.)
    }

    public Car getFirst() {
	Car temp = theLane[0];
	theLane[0] = null;
	return temp;
	/** 
	 * @return returns and removes the first car in a lane
	 *		
	 */
	// Returnera och tag bort bilen som står först
    }

    public Car firstCar() {
	return theLane[0];
	/**
	 * @return returns the first car in a lane
	 */
	// Returnera bilen som står först utan att ta bort den
    }


    public boolean lastFree() {
	if(theLane[theLane.length-1] == null){
	    return true;
	}
	return false;
	// Returnera true om sista platsen ledig, annars false
    /**
     * @return returns true if last place in Lane is free
     * otherwise returns false
     */
    
    }

    public void putLast(Car c) throws OverflowException {
    /**
     * @param car which is to be put last in Lane
     */
    if(lastFree()){
	    theLane[theLane.length-1] = c;
	}
	else{
	    throw new OverflowException();

	}
	// Ställ en bil på sista platsen på vägen
	// (om det går).
    }

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
    /**
     * @return returns the representation of either
     * a car or an empty spot for the representation
     * system
     */
}