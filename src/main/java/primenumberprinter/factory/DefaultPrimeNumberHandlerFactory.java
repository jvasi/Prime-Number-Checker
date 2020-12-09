package primenumberprinter.factory;

import primenumberprinter.handlers.PrimeHandlerCollection;
import primenumberprinter.api.IPrimeNumberHandler;
import primenumberprinter.api.IPrimeNumberHandlerFactory;
import primenumberprinter.handlers.ConsolePrinterPrimeHandler;
import primenumberprinter.handlers.PersistPrimeHandler;

/**
 * Default factory for prime number handlers. Creates a prime number handler collection
 * with two handlers - one for persisting the prime numbers and one for printing them
 * on the console.
 *
 * This class is really a singleton, but was kept that way as in the future
 * the prime handler collection might not be concurrent and we may need to return different instances.
 *
 */
public class DefaultPrimeNumberHandlerFactory implements IPrimeNumberHandlerFactory {

    private static final PrimeHandlerCollection sInstance = new PrimeHandlerCollection();

    static {
        sInstance.addHandler(new PersistPrimeHandler());
        sInstance.addHandler(new ConsolePrinterPrimeHandler());
    }

    public IPrimeNumberHandler createInstance() {
        return sInstance;
    }

}
