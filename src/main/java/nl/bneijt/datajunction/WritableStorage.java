package nl.bneijt.datajunction;

import java.io.IOException;

import nl.bneijt.datajunction.Source;


/**
 * Represents an abstract storage. Should be able to store files and look them up.
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public interface WritableStorage {

	/**
	 * Put a file in the storage. The file must be a DataJunction file
	 * @throws IOException 
	 */
	public void put(Source f) throws IOException;	 
}
