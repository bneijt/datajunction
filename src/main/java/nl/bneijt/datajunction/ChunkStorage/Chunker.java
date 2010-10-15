package nl.bneijt.datajunction.ChunkStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

/**
 * Take an input stream and a base directory and place the input stream onto
 * a hashed file.
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public class Chunker {
	private MessageDigest chunkDigest;
	private MessageDigest fileDigest;

	private final int BUFFER_SIZE;
	private final int CHUNK_SIZE;

	private File basePath;
	private List<String> hashes;
	private InputStream inputFile;

	Chunker(InputStream in, File baseDirectory) {
		BUFFER_SIZE = 10240;
		CHUNK_SIZE = 1024 * 1024;

		this.basePath = baseDirectory;
		this.inputFile = in;
		this.hashes = new ArrayList<String>();		
	}

	public void run() throws IOException, NoSuchAlgorithmException {
		chunkDigest = MessageDigest.getInstance("SHA1");
		fileDigest = MessageDigest.getInstance("SHA1");

		
		File tempFile = createTempFile();
		FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
		int written = 0;
		int toWrite = 0;
		while(true)
		{
			byte buffer[] = new byte[BUFFER_SIZE];

			//Read data into temporary file and hash it on the way
			int read = inputFile.read(buffer);

			if(read <= 0)
				break;

			if(written < CHUNK_SIZE)
			{
				toWrite = Math.min(CHUNK_SIZE - written, read);
				//Add to file and hash
				tempFileOutputStream.write(buffer, 0, toWrite);
				chunkDigest.update(buffer, 0, toWrite);
				fileDigest.update(buffer, 0, toWrite);
				read -= toWrite;
			}
			if(read == 0 && written != CHUNK_SIZE)
				continue;

			//Start a new chunk for this file and place the remaining bytes in there
			//Close and move this file
			tempFileOutputStream.close();
			finalize(tempFile);

			//Open new file
			//TODO Make sure the file is only readable for this process for security
			tempFile = createTempFile();
			tempFileOutputStream = new FileOutputStream(tempFile); //TODO optimize with outputstream.open??
			
			//Write remainder if needed
			if(read > 0)			{
				tempFileOutputStream.write(buffer, toWrite, read);
				chunkDigest.update(buffer, toWrite, read);
				fileDigest.update(buffer, toWrite, read);
			}
			written = read;
		}
	}

	public List<String> chunkHashes() {
		return this.hashes;
	}
	public String fileHash() {
		BigInteger number = new BigInteger(1, chunkDigest.digest());
		return number.toString(16);
	}
	
	/**
	 * Store temporary file at the correct position and clean up
	 * @param tempFile
	 */
	private void finalize(File tempFile) {
		BigInteger number = new BigInteger(1, chunkDigest.digest());
		String hash = number.toString(16);
		hashes.add(hash);
		File dest = new File(basePath.getAbsolutePath() + File.separator + hashToName(hash));
		tempFile.renameTo(dest);

		//Clean up
		chunkDigest.reset();
	}


	private File createTempFile() throws IOException {
		return File.createTempFile("datajuncion_chunkstorage", ".tmp");
	}

	static public String hashToName(String hash) {
		return hash.substring(0, 2) + File.separator + hash.substring(2);
	}

}
