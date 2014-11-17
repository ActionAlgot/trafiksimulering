public class Light {
    private int period;
    private int time;  // Intern klocka: 0, 1, ... period-1, 0, 1 ...
    private int green; // Signalen grön när time<green 

    public Light(int p, int g) throws Exception{
    	/**
    	 * @param p is the period which alterates between
    	 * green and red light
    	 * @param g defines when the light is supposed to be green
    	 */
	if(p > g && p > 1 && g > 0){
	    period = p;
	    green = g;
	    time = 0;
	}
	else{
	    throw new Exception("Period must be greater than green time.");
	}
    }    
    public void step() {
	if(time < period-1){
	    time++;
	}
	else{
	    time = 0;
	}
       // Stegar fram klocka ett steg
    }

    public boolean isGreen(){
	return (time<green);
	/**@return returns true if time is smaller than
	 * green which is used to know if a car can go
	 * through in the simulation
	 * if it is not green, false is returned
	 */
	// Returnerar true om time<green, annars false
    }


    public String  toString()  {
	return (period + " " + green + " " + time);
    /**
     * @return current value of period green and time in a string
     * time is the only which will alterate
     */
    }
	
}