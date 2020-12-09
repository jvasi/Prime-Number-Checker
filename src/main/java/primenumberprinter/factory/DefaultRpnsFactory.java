package primenumberprinter.factory;

import primenumberprinter.api.IRpns;
import primenumberprinter.api.IRpnsFactory;
import primenumberprinter.rpns.DefaultRpns;

/**
 * Default implementation of the IRpnsFactory. Returns DefaultRpns class instances.
 */
public class DefaultRpnsFactory implements IRpnsFactory {

    public IRpns getInstance(String host, int port) {
        return new DefaultRpns(host, port);
    }

}
