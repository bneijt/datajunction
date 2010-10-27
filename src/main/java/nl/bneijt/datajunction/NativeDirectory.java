package nl.bneijt.datajunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Stack;

import org.codehaus.jackson.JsonNode;

public class NativeDirectory implements ReadableStorage { 
	
	private File d_baseDir;

	public NativeDirectory(){
		d_baseDir = new File("/tmp");
	}

	@Override
	public Iterator<Source> iterator() {
		return new NativeDirectoryIterator(d_baseDir);
	}

	
}
