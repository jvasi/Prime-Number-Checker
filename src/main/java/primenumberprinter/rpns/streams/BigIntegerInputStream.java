package primenumberprinter.rpns.streams;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * Decorator for input streams which supports reading big integers.
 */
public class BigIntegerInputStream extends InputStream {

    /**
     * The wrapped underlying input stream.
     */
    private final DataInputStream mIn;

    /**
     * Creates instance of this class.
     * @param in the input stream that this instance will wrap.
     */
    public BigIntegerInputStream(InputStream in) {
        super();
        mIn = new DataInputStream(in);
    }

    @Override
    public int read() throws IOException {
        return mIn.read();
    }

    /**
     * Reads a big integer.
     * @return the read big integer.
     * @throws IOException if I/O error occurs.
     */
    public BigInteger readBigInteger() throws IOException {
        int len = mIn.readInt();

        byte numberBuf[] = new byte[len];
        
        mIn.read(numberBuf);
        
        return new BigInteger(numberBuf);
    }

    @Override
    public void close() throws IOException {
        mIn.close();
        super.close();
    }

}
