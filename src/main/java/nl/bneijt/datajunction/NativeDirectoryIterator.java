package nl.bneijt.datajunction;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

public class NativeDirectoryIterator implements Iterator<Source> {

	private Stack<File> d_stack;

	public NativeDirectoryIterator(File baseDir) {
		d_stack = new Stack<File>();
		d_stack.push(baseDir);
	}
	@Override
	public boolean hasNext() {
		return !d_stack.empty();
	}

	@Override
	public Source next() {
		File current = d_stack.pop();
		
		//Explode stack till we have the deepest file
		while(current.isDirectory())
		{
			for(File child : current.listFiles())
				d_stack.push(child);
			current = d_stack.pop();
		}
		try {
			return new NativeFile(current.getPath());
		} catch(IOException e) {
			return null;
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram does not know what _remove_ does for iterators.");

	}

}
