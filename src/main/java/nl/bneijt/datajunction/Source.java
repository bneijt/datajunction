package nl.bneijt.datajunction;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * An InputStream combined with metadata.
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public interface Source {
	public InputStream inputStream() throws FileNotFoundException;
	public org.codehaus.jackson.node.ObjectNode meta();
}
