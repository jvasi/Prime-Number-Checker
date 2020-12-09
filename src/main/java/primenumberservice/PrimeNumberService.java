package primenumberservice;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import primenumberprinter.rpns.streams.BigIntegerInputStream;

/**
 * Service that listens on a given port for a number and returns whether the number is prime.
 *
 * This is not part of the solution, it's here for testing purposes only.
 */
public class PrimeNumberService {

    /**
     * Big integer with value 0.
     */
    private static final BigInteger ZERO = BigInteger.valueOf(0);

    /**
     * Big integer with value 2.
     */
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /**
     * Main entry point.
     * @param args the arguments.
     * @throws IOException if error occurs while opening the passed in port.
     */
    public static void main(String args[]) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: java primenumberservice.PrimeNumberService <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);

        launchPrimeService(serverSocket);
    }

    /**
     * Launch the prime number service.
     * @param serverSocket the server socket to take requests from.
     */
    private static void launchPrimeService(ServerSocket serverSocket) {

        while (!Thread.currentThread().isInterrupted()) {
            BigIntegerInputStream oin = null;
            DataOutputStream oout = null;
            Socket socket = null;

            try {
                socket = serverSocket.accept();
                oin = new BigIntegerInputStream(socket.getInputStream());
                oout = new DataOutputStream(socket.getOutputStream());
                BigInteger number = oin.readBigInteger();
                oout.writeBoolean(isPrime(number));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // First close the stream individually so they be flushed.
                if (oin != null) {
                    try {
                        oin.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (oout != null) {
                    try {
                        oout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Then close the socket.
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Checks if the given long is prime.
     * @param p the long to check if it's prime.
     * @return <code>true</code> if the number is prime, <code>false</code> otherwise.
     */
    private static boolean isPrime(BigInteger p) {
        BigInteger half = p.divide(TWO);

        for (BigInteger i = TWO; i.compareTo(half) <= 0; i = i.add(BigInteger.ONE)) {
            if (p.mod(i).compareTo(ZERO) == 0) {
                return false;
            }
        }

        return true;
    }
}
