public class Simulation {

    public static void main(String [] args) {
	TrafficSystem tf = new TrafficSystem();
	for(int i = 0; i<50; i++){
	    tf.step();
	    tf.print();
	}
	tf.printStatistics();
	// Skapar ett TrafficSystem
	// Utför stegningen, anropar utskriftsmetoder


    }
}