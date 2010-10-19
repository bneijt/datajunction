package nl.bneijt.datajunction.ChunkStorage;

import nl.bneijt.datajunction.WritableStorage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
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
			ObjectNode fileMeta = file.meta();
			ObjectNode meta = fileMeta.putObject("ChunkStorage");
			meta.put("fileHash", c.fileHash());

			//Add chunkHashes as a list
			ArrayNode list = meta.putArray("chunkHashes");
			List<String> chunkHashes = c.chunkHashes();
			for(String hash : chunkHashes)
				list.add(hash);

			//Metadata generation
			String metadata = fileMeta.toString();
			//Digest metadata to find out which name it should have
			String sha1sum = sha1sum(metadata);
			File metaDataFile = metaFileFromHash(sha1sum);
			File parent = metaDataFile.getParentFile();
			if(!parent.exists())
				parent.mkdirs();

			FileOutputStream out = new FileOutputStream(metaDataFile);
			out.write(metadata.getBytes());
			out.close();
		}
		catch(NoSuchAlgorithmException e) {
			throw new IOException(e.toString());
		}

	}


	private String sha1sum(String data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA1");
		digest.update(data.getBytes());
		BigInteger number = new BigInteger(1, digest.digest());
		return number.toString(16);
	}


	private File metaFileFromHash(String sha1sum) {
		return new File(basePath + File.separator + "meta" + File.separator + sha1sum.substring(0, 2) + File.separator + sha1sum + ".json");

	}

}
