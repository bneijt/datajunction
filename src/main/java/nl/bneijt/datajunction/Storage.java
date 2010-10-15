package nl.bneijt.datajunction;

import java.io.IOException;

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
	 * @throws IOException 
	 */
	public void put(File f) throws IOException;
	public File get(JsonNode f);
	 
}
