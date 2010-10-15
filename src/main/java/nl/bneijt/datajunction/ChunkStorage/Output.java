package nl.bneijt.datajunction.ChunkStorage;

import nl.bneijt.datajunction.Storage;

import org.codehaus.jackson.JsonNode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.XMLSignatureFactory;
/**
 * Store files in a directory of chunked files (sha1 hashed chunks)
 * and store the metadata separately in a json object
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public class Output implements Storage {
	private File basePath;

	public Output(String rootPath) {
		basePath = new File(rootPath);
		if(!basePath.exists())
			basePath.mkdirs();
		if(!basePath.isDirectory())
			throw new RuntimeException("Given path \"" + rootPath + "\" is not a directory.");
	}

	@Override
	public nl.bneijt.datajunction.File get(JsonNode f) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram has not implemented this method yet.");
	}

	@Override
	public void put(nl.bneijt.datajunction.File file) throws IOException {
		//Load file input stream
		//Store each chunk on disk
		InputStream in = file.inputStream();
		Chunker c = new Chunker(in, basePath);
		try {
			c.run();
			List<String> chunkHashes = c.chunkHashes();
			String fileHash = c.fileHash();
		}
		catch(NoSuchAlgorithmException e) {
			throw new IOException(e.toString());
		}

	}

}
