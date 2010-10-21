package nl.bneijt.datajunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.attribute.Attributes;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class NativeFile implements nl.bneijt.datajunction.Source {
	final private File file;
	private ObjectNode metadata;
	
	
	
	public NativeFile(String fileName) throws FileNotFoundException {
		file = new java.io.File(fileName);
		if(!file.exists())
			throw new FileNotFoundException("Can not construct a NativeFile for non-existing: " + file.getAbsolutePath());
		//Initialize metadata
		ObjectMapper mapper = new ObjectMapper();
		metadata = mapper.createObjectNode();
		metadata.put("filename", file.getAbsolutePath());

		Attributes.readPosixFileAttributes();
		
	}
	@Override
	public InputStream inputStream() throws FileNotFoundException {
		return new FileInputStream(file);
	}

	@Override
	public ObjectNode meta() {
		return metadata;
	}
		

}
