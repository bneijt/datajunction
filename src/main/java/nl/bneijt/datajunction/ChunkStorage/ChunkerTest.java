package nl.bneijt.datajunction.ChunkStorage;

import static org.junit.Assert.*;

import org.junit.Test;


public class ChunkerTest {
	@Test
	public void testHashToName() throws Exception {
		String actual = Chunker.hashToName("05fd8c5f538622024d19475a731a44e1551035f1");
		assertEquals("05/fd8c5f538622024d19475a731a44e1551035f1", actual);
	}
}
