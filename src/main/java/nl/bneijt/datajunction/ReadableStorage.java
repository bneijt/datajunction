package nl.bneijt.datajunction;

import org.codehaus.jackson.JsonNode;

/**
 * Allows you to read a Source using get
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public interface ReadableStorage {
	public Source get(JsonNode metaData) throws java.io.FileNotFoundException;

}
