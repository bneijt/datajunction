package nl.bneijt.datajunction;

import java.awt.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.LinkOption;
import java.nio.file.attribute.Attributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class NativeFile implements nl.bneijt.datajunction.Source {
	final private File file;
	private ObjectNode metadata;
	private boolean d_symLink;
	
	public NativeFile(String fileName) throws IOException {
		file = new java.io.File(fileName);
		if(!file.exists())
			throw new FileNotFoundException("Can not construct a NativeFile for non-existing: " + file.getAbsolutePath());
		//Initialize metadata
		ObjectMapper mapper = new ObjectMapper();
		metadata = mapper.createObjectNode();
		metadata.put("filename", file.getAbsolutePath());

		PosixFileAttributes attrs = Attributes.readPosixFileAttributes(file.toPath(), LinkOption.NOFOLLOW_LINKS);
		metadata.put("group", attrs.group().getName());
		metadata.put("owner", attrs.owner().getName());
		
		ArrayNode permissions = metadata.putArray("permissions");
		for(PosixFilePermission perm : attrs.permissions())
			permissions.add(perm.toString());
		
		FileTime t = attrs.creationTime();
		if(t != null)
			metadata.put("creationTime", t.toString());
		//Probably useless: metadata.put("fileKey", attrs.fileKey());
		metadata.put("isDirectory", attrs.isDirectory());
		metadata.put("isOther", attrs.isOther());
		metadata.put("isRegularFile", attrs.isRegularFile());
		if(attrs.isSymbolicLink()) {
			metadata.put("isSymbolicLink", attrs.isSymbolicLink());
			this.d_symLink = true;
			
		}
		metadata.put("lastAccessTime", attrs.lastAccessTime().toString());
		metadata.put("lastModifiedTime", attrs.lastModifiedTime().toString());
		metadata.put("size", attrs.size());
	}
	public boolean isSymbolicLink() {
		return d_symLink;
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
