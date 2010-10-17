package nl.bneijt.datajunction.ChunkStorage;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


public class ChunkerTest {
	@Test
	public void testHashToName() throws Exception {
		String actual = Chunker.hashToName("05fd8c5f538622024d19475a731a44e1551035f1");
		assertEquals("05/05fd8c5f538622024d19475a731a44e1551035f1", actual);
	}
	@Test
	public void testEmptyFileStorage() throws Exception {
		File baseDir = File.createTempFile("chunker", "test");
		baseDir.delete();
		Chunker c = new Chunker(new FileInputStream("/etc/services"), baseDir);
		c.run();
		assertThat(c.chunkHashes().size(), greaterThan(0));
		assertEquals( "After chunking a file, the base directory should exist", true, baseDir.exists());
		//TODO Test whether there is at least one chunk in the directories
		//TODO Test for a chunk smaller then the maximum chunk size
		//TODO Test that no chunk is larger then the maximum size
		FileUtils.deleteDirectory(baseDir);
	}
}
