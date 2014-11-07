public class Lane {

    public static class OverflowException extends RuntimeException {
        // Undantag som kastas när det inte gick att lägga 
        // in en ny bil på vägen
    }

    private Car[] theLane;

    public Lane(int n) {
	theLane = new Car[n];
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
	// Returnera och tag bort bilen som står först
    }

    public Car firstCar() {
	return theLane[0];
	// Returnera bilen som står först utan att ta bort den
    }


    public boolean lastFree() {
	if(theLane[theLane.length-1] == null){
	    return true;
	}
	return false;
	// Returnera true om sista platsen ledig, annars false
    }

    public void putLast(Car c) throws OverflowException {
	if(lastFree()){
	    theLane[theLane.length-1] = c;
	}
	else{
	    //TODO OverflowException
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
}