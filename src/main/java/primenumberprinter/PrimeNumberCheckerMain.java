package primenumberprinter;

import primenumberprinter.factory.DefaultRpnsFactory;
import primenumberprinter.factory.DefaultPrimeNumberHandlerFactory;
import primenumberprinter.api.IPrimeNumberHandlerFactory;
import primenumberprinter.api.IRpnsFactory;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main class.
 */
public class PrimeNumberCheckerMain {

    /**
     * Default logger.
     */
    private static final Logger sLogger = Logger.getLogger(PrimeNumberCheckerMain.class.getName());


    /**
     * Array for the threads that will check for prime numbers.
     */
    private final ArrayList<PrimeNumberCheckerThread> threadsArray = new ArrayList<PrimeNumberCheckerThread>();

    /**
     * Main method.
     * @param args the program arguments. Expected format: <remote_host_1> <remote_port_1> <remote_host_2> <remote_port_2> ...
     */
    public static void main(String args[]) {
        if (args.length == 0) {
            sLogger.log(Level.INFO, "Usage: java -jar <application_jar> <remote_host_1> <remote_port_1> " +
                    "<remote_host_2> <remote_port_2>");
        }
        PrimeNumberCheckerMain primeNumberChecker = new PrimeNumberCheckerMain(args,
                new DefaultRpnsFactory(),
                new DefaultPrimeNumberHandlerFactory());

        primeNumberChecker.start();
    }

    /**
     * Creates instance of this class. Also initializes the threads that will check for prime numbers.
     * @param args the arguments passed in on the console.
     * @param rpnsFactory the remote prime number service factory.
     * @param primeNumberHandlerFactort the prime number handler.
     */
    public PrimeNumberCheckerMain(String[] args, IRpnsFactory rpnsFactory,
            IPrimeNumberHandlerFactory primeNumberHandlerFactort) {



        NumberGenerator numberGenerator = new NumberGenerator();
        numberGenerator.setInitial(PrimeNumberPersistentStorage.getInstance().getLastPersistedPrime());
        
        for (int i = 0; i < args.length ; i += 2) {
            threadsArray.add(
                    new PrimeNumberCheckerThread(
                        rpnsFactory.getInstance(args[i], Integer.parseInt(args[i+1])), 
                        primeNumberHandlerFactort.createInstance(), numberGenerator));
        }
    }

    /**
     * Starts the threads that will check for prime numbers.
     */
    public void start() {
        for (PrimeNumberCheckerThread thread: threadsArray) {
            thread.start();
        }
    }

}
