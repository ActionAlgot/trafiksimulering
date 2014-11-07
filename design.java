import java.util.InputMismatchException;
/*
  Modell för trafiksimulering
  ===========================

  Följande klasser skall användas: 

     Car representerar fordon
         ankomsttid och destination som sätts när objektet skapas

     Light representerar ljussignaler
         Se nedan
  
     Lane representerar ett vägavsnitt
         En väg representeras av en array där varje element
	 antingen är tomt eller innehåller en referens till 
         ett bil-objekt.
         OBS: Klassen Lane påminner om kömekanismen i föregående
         uppgift men den skiljer sig också i flera avseende.
         I denna klass ställs nya bilar i ena änden av ARRAYEN
         och inte närmast efter den som finns där. I kömekanismen        
         var alltid elementen (kunderna) samlade medan bilarna
         i denna klass kan vara utspridda över hela arrayen.
         
 
     TrafficSystem
         Definierar de komponeneter dvs de vägar och signaler
	 som ingår i systemet. Se vidare nedan

     Simulation
         main-metod som driver simuleringen


  Den situation som skall simuleras ser schematiskt ut enligt



         C           B                               A
       s1<----r1-----<---------r0---------------------
       s2<----r2-----< 


  En fil (vägsträcka) r0 delar sig vid B i två filer r1 och r2.
  Signal s1 kontrollerar fil r1 och och signal s2 fil r2.
 
  Bilar uppstår vid A. Sannolikheten att en bil skall komma till A
  vid ett visst tidsteg kallas ankomstintensiteten.

  Vid ett tidssteg rör sig bilarna ett steg framåt (om platsen framför
  är ledig). Vid C tas bilarna ut från filerna om repektive
  signal är grön. Vid B tas bilar ut från r0 och läggs in på r1 eller r2
  beroende på destination (och om platsen är ledig).

  Anm: Man skulle kunna representera systemet med två Lane-objekt
  men då måste man ha något sätt att representera en "avtappning"
  (där svängfilen börjar). Med den här valda representationen
  blir Lane-klassen enklare.  
    
*/

import java.lang.*;
import java.util.Scanner;

// Skiss av klasser. Angivna klasser och metoder skall finnas.
// Det är tillåtet att tillfoga fler attribut och metoder

public class Car {

    private int bornTime;
    private int dest; // 1 för rakt fram, 2 för vänstersväng

    // konstruktor och get-metoder
    
    public Car(int b, int n){
	bornTime = b;
	dest = n;
    }

    public String toString(){
	String s = (bornTime + " " + dest);
	return s;
    }

    public int getDest(){
	return dest;
    }	
}



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
		r1 = new Lane(input.nextInt());
		r2 = r1;
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
	    Car temp1 = r1.getFirst(); //TODO hantera returnen
	}
	if(s2.isGreen()){
	    Car temp2 = r2.getFirst(); //TODO hantera returnen
	}
	r1.step();
	r2.step();
	Car c = r0.firstCar();
	if(c != null){
	    if (c.getDest() == 1){
		if(r1.lastFree()){
		    r1.putLast(c);
		}
	    }
	    else{
		if(r2.lastFree()){
		    r2.putLast(c);
		}
	    }
	}
	r0.step();
	if(Math.random()*ankomstIntensitet == 0){
	    if(Math.random()*destinationer == 0){
		r0.putLast(new Car(time, 1));
	    }
	    else{
		r0.putLast(new Car(time, 2));
	    }
	}
	print();
    }

	//    public void printStatistics() {
	// Skriv statistiken samlad så här långt
	//}
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



public class Simulation {

    public static void main(String [] args) {
	TrafficSystem tf = new TrafficSystem();
	for(int i = 0; i<50; i++){
	    tf.step();
	    tf.print();
	}
	// Skapar ett TrafficSystem
	// Utför stegningen, anropar utskriftsmetoder


    }
}
