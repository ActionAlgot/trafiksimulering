import java.util.InputMismatchException;
import java.lang.NegativeArraySizeException;
import java.lang.*;
import java.util.Scanner;

public class TrafficSystem {
    // Definierar de vägar och signaler som ingår i det 
    // system som skall studeras.
    // Samlar statistik
    
    // Attribut som beskriver beståndsdelarna i systemet
    private Lane  r0;
    private Lane  r1;
    private Lane  r2;
    private Light s1;
    private Light s2;

    // Diverse attribut för simuleringsparametrar (ankomstintensiteter,
    // destinationer...)
    private int ankomstIntensitet;
    private int destinationer;

    // Diverse attribut för statistiksamling
    private int carsPassed = 0;
    private int timeWaited = 0;
    private int carsThrown = 0;

    private void collectStats(Car c){
	if(c != null){
	    carsPassed++;
	    timeWaited = timeWaited + (time - c.getBornTime());
	}
    }
    
    private int time = 0;

    /* public TrafficSystem(int ll, int ll2, int lTR, int gTR, int lTL, int gTL, int a, int d){
	r0 = new Lane(ll);
	r1 = new Lane(ll2);
	r2 = new Lane(ll2);
	s1 = new Light(lTR, gTR);
	s2 = new Light(lTL, gTL);
	ankomstIntensitet = a;
	destinationer = d;	
	}*/

    public TrafficSystem(){
	readParameters();
    }

    private int readPosInt(String request, Scanner input){
	int temp;
	while(true){
	    try{
		System.out.print(request);
		temp = input.nextInt();
		if(temp > 0){
		    return temp;
		}
		System.out.println("Invalid input try again with an integer greater than zero");
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with an integer greater than zero");
		input.next();
	    }
	}
    }
    private int readPercentageInt(String request, Scanner input){
	int temp;
	while(true){
	    try{
		System.out.print(request);
		temp = input.nextInt();
		if(temp > -1 && temp < 101){
		    return temp;
		}
		System.out.println("Invalid input try again with an integer between 0 and 100");
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with an integer between 0 and 100");
		input.next();
	    }
	}
    }

    private void readParameters(){
	Scanner input = new Scanner(System.in);
	int temp;
	r0 = new Lane(readPosInt("Length of r0: ", input));
	temp = readPosInt("\nLength of r1 and r2: ", input);
	r1 = new Lane(temp);
	r2 = new Lane(temp);
	while(true){
	    try{
		s1 = new Light(readPosInt("\nRight traffic light period", input), readPosInt("\nRight traffic green time", input));
		break;
	    }
	    catch(Exception x){
		System.out.println("Period must be greater than green time, try again");
	    }
	}
	while(true){
	    try{
		s2 = new Light(readPosInt("\nLeft traffic light period", input), readPosInt("\nLeft traffic green time", input));
		break;
	    }
	    catch(Exception x){
		System.out.println("Period must be greater than green time, try again");
	    }
	}
	ankomstIntensitet = readPercentageInt("\nArrival intensity in %: ", input);
	destinationer = readPercentageInt("\n% cars going left: ", input); 
	System.out.print("\n");
	// Läser in parametrar för simuleringen
	// Metoden kan läsa från terminalfönster, dialogrutor
	// eller från en parameterfil. Det sista alternativet
	// är att föredra vid uttestning av programmet eftersom
	// man inte då behöver mata in värdena vid varje körning.
        // Standardklassen Properties är användbar för detta. 
    }

    public void step() {
	// Stega systemet ett tidssteg m h a komponenternas step-metoder
	// Skapa bilar, lägg in och ta ur på de olika Lane-kompenenterna
	time++;
	s1.step();
	s2.step();
	if(s1.isGreen()){
	    collectStats(r1.getFirst()); //TODO hantera returnen
	}
	if(s2.isGreen()){
	    collectStats(r2.getFirst()); //TODO hantera returnen
	}
	r1.step();
	r2.step();
	Car c = r0.firstCar();
	if(c != null){
	    if ((c.getDest()) == 1){
		if(r1.lastFree()){
		    r1.putLast(r0.getFirst());
		}
	    }
	    else{
		if(r2.lastFree()){
		    r2.putLast(r0.getFirst());
		}
	    }
	}
	r0.step();
	if((int)(Math.random()*100) < ankomstIntensitet){
	    if((int)(Math.random()*100) > destinationer){
		try{
		    r0.putLast(new Car(time, 1));
		}
		catch(OverflowException x){
		    carsThrown++;
		}
	    }
	    else{
		try{
		    r0.putLast(new Car(time, 2));
		}
		catch(OverflowException x){
		    carsThrown++;
		}
	    }
	}
    }

    public void printStatistics() {
	// Skriv statistiken samlad så här långt
	System.out.println("Cars passed: " + carsPassed + "\nAverage time passing through: " + ((double)timeWaited/(double)carsPassed) + " ticks\nCars unable to enter lane: " + carsThrown);
    }
    public void print(){
	System.out.println(s1.toString());
	System.out.print(r1.toString());
	System.out.println(r0.toString());
	System.out.println(r2.toString());
	System.out.println(s2.toString());
	// Skriv ut en grafisk representation av kösituationen
	// med hjälp av klassernas toString-metoder
    }
}
