package nl.bneijt.datajunction.ChunkStorage;

import static org.junit.Assert.*;

import java.io.File;

import nl.bneijt.datajunction.NativeFile;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


public class WritableChunkStorageTest {
	@Test
	public void testInitialization() throws Exception {
		File rootPath = File.createTempFile("chunker", "test");
		assertEquals("After createTempFile, there should exist a temporary file", true, rootPath.exists());
		assertEquals("It should be possible to remove the temporary file", true, rootPath.delete());
		assertEquals("The chunk storage root should not exist before initialization", false, rootPath.exists());
		WritableChunkStorage s = new WritableChunkStorage(rootPath.getAbsolutePath());
		assertEquals("The chunk storage root should exist after the constructor", true, rootPath.exists());
		s.put(new NativeFile("/etc/services"));
		
		//Clean up
		FileUtils.deleteDirectory(rootPath);
	}
	
}
