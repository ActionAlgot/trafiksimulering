public class Light {
    private int period;
    private int time;  // Intern klocka: 0, 1, ... period-1, 0, 1 ...
    private int green; // Signalen grön när time<green 

    public Light(int p, int g){
	period = p;
	green = g;
	time = 0;
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
	// Returnerar true om time<green, annars false
    }


    public String  toString()  {
	return (period + " " + green + " " + time);
    }
	
}