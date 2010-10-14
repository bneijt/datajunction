package nl.bneijt.datajunction;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

public interface File {
	public InputStream inputStream() throws FileNotFoundException;
	public JsonNode meta();
}
