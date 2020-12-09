package primenumberprinter.handlers;

import java.math.BigInteger;
import primenumberprinter.api.IPrimeNumberHandler;

/**
 * Console prime number printer handler. Used for printing prime numbers on the console.
 */
public class ConsolePrinterPrimeHandler implements IPrimeNumberHandler {

    @Override
    public void handle(BigInteger prime) {
        System.out.print(prime.toString());
        System.out.print(" ");
    }

}
