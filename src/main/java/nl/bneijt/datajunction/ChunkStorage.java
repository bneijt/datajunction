package nl.bneijt.datajunction;

import org.codehaus.jackson.JsonNode;

/**
 * Store files in a directory of chunked files (sha1 hashed chunks)
 * and store the metadata separately in a json object
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public class ChunkStorage implements Storage {

	public ChunkStorage(String rootPath, boolean createRoot) {
		throw new RuntimeException("Bram has not implemented this method yet.");
	}

	@Override
	public File get(JsonNode f) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram has not implemented this method yet.");
	}

	@Override
	public void put(File f) {
		//Load file input stream
		//Store each chunk on disk
		//Store metadata with chunk information in separate json file.
		throw new RuntimeException("Bram has not implemented this method yet.");

	}

}
