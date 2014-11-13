import java.util.InputMismatchException;
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
    private int aLength;
    private int bcLength;

    // Diverse attribut för statistiksamling
    private int carsPassed = 0;
    private int timeWaited = 0;

    private void collectStats(Car c){
	if(c != null){
	    carsPassed++;
	    timeWaited = timeWaited + (time - c.getBornTime());
	}
    }
    
    private int time = 0;

    public TrafficSystem(int ll, int ll2, int lTR, int gTR, int lTL, int gTL, int a, int d){
	r0 = new Lane(ll);
	r1 = new Lane(ll2);
	r2 = new Lane(ll2);
	s1 = new Light(lTR, gTR);
	s2 = new Light(lTL, gTL);
	ankomstIntensitet = a;
	destinationer = d;	
    }

    public TrafficSystem(){
	readParameters();
    }

    public void readParameters(){
	Scanner input = new Scanner(System.in);
	int tempP = 1;
	while(true){
	    try{
		System.out.print("Length of r0: ");
		r0 = new Lane(input.nextInt());
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
	
	while(true){
	    try{
		System.out.print("\nLength of r1 and r2: ");
		int tempn = input.nextInt();
		r1 = new Lane(tempn);
		r2 = new Lane(tempn);
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
	while(true){
	    try{
		System.out.print("\nRight traffic light period: ");
		tempP = input.nextInt();
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}

	while(true){
	    try{
		System.out.print("\nRight traffic green time: ");
		s1 = new Light(tempP, input.nextInt());
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
	while(true){
	    try{
		System.out.print("\nLeft traffic light period: ");
		tempP = input.nextInt();
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
	while(true){
	    try{
		System.out.print("\nLeft traffic light green time: ");
		s2 = new Light(tempP, input.nextInt());
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
	while(true){
	    try{
		System.out.print("\nArrival intensity: ");
		ankomstIntensitet = input.nextInt();
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
	while(true){
	    try{
		System.out.print("\nDestination ratio: ");
		destinationer = input.nextInt();
		break;
	    }
	    catch(InputMismatchException x){
		System.out.println("Invalid input try again with int");
	    }
	}
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
	if((int)(Math.random()*ankomstIntensitet) == 0){
	    if((int)(Math.random()*destinationer) == 0){
		r0.putLast(new Car(time, 1));
	    }
	    else{
		r0.putLast(new Car(time, 2));
	    }
	}
    }

    public void printStatistics() {
	// Skriv statistiken samlad så här långt
	System.out.println("Cars passed: " + carsPassed + "\nAverage time passing through :" + ((double)timeWaited/(double)carsPassed) + " ticks");
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
