package nl.bneijt.datajunction.ChunkStorage;

import nl.bneijt.datajunction.WritableStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Store files in a directory of chunked files (sha1 hashed chunks)
 * and store the metadata separately in a json object
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public class WritableChunkStorage implements WritableStorage {
	private File basePath;

	public WritableChunkStorage(String rootPath) {
		basePath = new File(rootPath);
		if(!basePath.exists())
			basePath.mkdirs();
		if(!basePath.isDirectory())
			throw new RuntimeException("Given path \"" + rootPath + "\" is not a directory.");
	}


	@Override
	public void put(nl.bneijt.datajunction.Source file) throws IOException {
		//Load file input stream
		//Store each chunk on disk
		InputStream in = file.inputStream();
		Chunker c = new Chunker(in, new File(basePath.getAbsolutePath() + File.separator + "data"));
		try {
			c.run();
			//Add storage specific metadata to file object
			ObjectNode meta = file.meta().putObject("ChunkStorage");
			meta.put("fileHash", c.fileHash());
			
			//Add chunkHashes as a list
			ArrayNode list = meta.putArray("chunkHashes");
			List<String> chunkHashes = c.chunkHashes();
			for(String hash : chunkHashes)
				list.add(hash);
			//TODO Store metadata
			
		}
		catch(NoSuchAlgorithmException e) {
			throw new IOException(e.toString());
		}

	}

}
