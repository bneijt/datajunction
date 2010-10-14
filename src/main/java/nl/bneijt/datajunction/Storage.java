package nl.bneijt.datajunction;

import org.codehaus.jackson.JsonNode;

import nl.bneijt.datajunction.File;


/**
 * Represents an abstract storage. Should be able to store files and look them up.
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public interface Storage {

	/**
	 * Put a file in the storage. The file must be a DataJunction file
	 */
	public void put(File f);
	public File get(JsonNode f);
	 
}
