package nl.bneijt.datajunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class NativeFile implements nl.bneijt.datajunction.Source {
	final private File file;
	private ObjectNode metadata;
	public NativeFile(String fileName) {
		file = new java.io.File(fileName);
		
		//Initialize metadata
		ObjectMapper mapper = new ObjectMapper();
		metadata = mapper.createObjectNode();
		metadata.put("filename", file.getAbsolutePath());
		metadata.put("executable", file.canExecute());
		metadata.put("readable", file.canRead());
		metadata.put("writable", file.canWrite());
		
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
