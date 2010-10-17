package nl.bneijt.datajunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class NativeFile implements nl.bneijt.datajunction.Source {
	final private File file;
	public NativeFile(String fileName) {
		file = new java.io.File(fileName);
	}
	@Override
	public InputStream inputStream() throws FileNotFoundException {
		return new FileInputStream(file);
	}

	@Override
	public ObjectNode meta() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.createObjectNode();
	}

}
