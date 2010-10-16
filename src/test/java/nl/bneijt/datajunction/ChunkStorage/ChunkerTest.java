package nl.bneijt.datajunction.ChunkStorage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;


public class ChunkerTest {
	@Test
	public void testHashToName() throws Exception {
		String actual = Chunker.hashToName("05fd8c5f538622024d19475a731a44e1551035f1");
		assertEquals("05/05fd8c5f538622024d19475a731a44e1551035f1", actual);
	}
	@Test
	public void testEmptyFileStorage() throws Exception {
		File baseDir = new File("/tmp/test");
		Chunker c = new Chunker(new FileInputStream("/etc/services"), baseDir);
		c.run();
		assertEquals( "After chunking a file, the base directory should exist", true, baseDir.exists());

		//assertEquals(true, )

	}
}
