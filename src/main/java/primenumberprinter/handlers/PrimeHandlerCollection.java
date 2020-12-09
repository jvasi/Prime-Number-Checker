package primenumberprinter.handlers;

import primenumberprinter.api.IPrimeNumberHandler;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Prime number handler collection. Based on the chain of responsibility design pattern,
 * represents a collection of handlers executed sequentially.
 */
public class PrimeHandlerCollection implements IPrimeNumberHandler {

    ArrayList<IPrimeNumberHandler> handlers = new ArrayList<IPrimeNumberHandler>();

    public void addHandler(IPrimeNumberHandler handler) {
        handlers.add(handler);
    }

    public void handle(BigInteger prime) throws IOException {
        for (IPrimeNumberHandler handler : handlers) {
            handler.handle(prime);
        }
    }

    // TODO: add removeHandler()

}
