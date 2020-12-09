package primenumberprinter.rpns;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import primenumberprinter.api.IRpns;
import primenumberprinter.rpns.streams.BigIntegerOutputStream;

/**
 * Default implementation of the Remote Private Number Service.
 */
public class DefaultRpns implements IRpns {

    /**
     * Default logger.
     */
    private static final Logger sLogger = Logger.getLogger(DefaultRpns.class.getName());

    /**
     * Host name of the remote service.
     */
    private String host;

    /**
     * Port of the remote service.
     */
    private int port;

    /**
     * Creates instance of the RPNS for the given host and port.
     * @param host the host name or IP address
     * @param port the port number that the remote service is listening on.
     */
    public DefaultRpns(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sends request to the RPNS to check for the given number if it's prime.
     * @param n the number to check if it's prime.
     * @return <code>true</code> if the number is prime, <code>false</code> otherwise.
     * @throws UnknownHostException if host could not be resolved.
     * @throws IOException if I/O error occurs while sending/receiving the request.
     */
    public boolean isPrime(BigInteger n) throws UnknownHostException, IOException {
        Socket socket = new Socket(host, port);
        // Wait infinetely for read() as checking if a number is prime can take time.
        socket.setSoTimeout(0);
        socket.setTcpNoDelay(true);

        BigIntegerOutputStream out = null;
        DataInputStream in = null;
        try {
            out = new BigIntegerOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            
            out.writeBigInteger(n);
            return in.readBoolean();
        } finally {
            // First, close the streams so the data is flushed from the wrapping streams.
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Catch and print the exception so the return value or the main exception is not hidden.
                    sLogger.log(Level.SEVERE, "Error while closing stream.", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // Catch and print the exception so the return value or the main exception is not hidden.
                    sLogger.log(Level.SEVERE, "Error while closing stream.", e);
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                // Catch and print the exception so the return value or the main exception is not hidden.
                sLogger.log(Level.SEVERE, "Error while closing socket.", e);
            }
        }
    }
}
