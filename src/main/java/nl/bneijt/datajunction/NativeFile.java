package nl.bneijt.datajunction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.jruby.ext.posix.JavaFileStat;

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
		JavaFileStat fs = new JavaFileStat(null, null);
		fs.setup(file.getAbsolutePath());
		
		//Add JavaFileStat metadata
		metadata.put("dev", fs.dev()); // dev() 
		metadata.put("ftype", fs.ftype()); // java.lang.String 	ftype() 
		metadata.put("gid", fs.gid()); // int 	gid() 
		metadata.put("ino", fs.ino()); // long 	ino() 
		metadata.put("isBlockDev", fs.isBlockDev()); // boolean 	isBlockDev() 
		metadata.put("isCharDev", fs.isCharDev()); // boolean 	isCharDev() 
		metadata.put("isDirectory", fs.isDirectory()); // boolean 	isDirectory() 
		metadata.put("isEmpty", fs.isEmpty()); // boolean 	isEmpty() 
		metadata.put("isExecutable", fs.isExecutable()); // boolean 	isExecutable() 
		metadata.put("isExecutableReal", fs.isExecutableReal()); // boolean 	isExecutableReal() 
		metadata.put("isFifo", fs.isFifo()); // boolean 	isFifo() 
		metadata.put("isFile", fs.isFile()); // boolean 	isFile() 
		metadata.put("isGroupOwned", fs.isGroupOwned()); // boolean 	isGroupOwned() 
		metadata.put("isNamedPipe", fs.isNamedPipe()); // boolean 	isNamedPipe() 
		metadata.put("isOwned", fs.isOwned()); // boolean 	isOwned() 
		metadata.put("isReadable", fs.isReadable()); // boolean 	isReadable() 
		metadata.put("isReadableReal", fs.isReadableReal()); // boolean 	isReadableReal() 
		metadata.put("isROwned", fs.isROwned()); // boolean 	isROwned() 
		metadata.put("isSetgid", fs.isSetgid()); // boolean 	isSetgid() 
		metadata.put("isSetuid", fs.isSetuid()); // boolean 	isSetuid() 
		metadata.put("isSocket", fs.isSocket()); // boolean 	isSocket() 
		metadata.put("isSticky", fs.isSticky()); // boolean 	isSticky() 
		metadata.put("isSymlink", fs.isSymlink()); // boolean 	isSymlink() 
		metadata.put("isWritable", fs.isWritable()); // boolean 	isWritable() 
		metadata.put("isWritableReal", fs.isWritableReal()); // boolean 	isWritableReal() 
		metadata.put("mode", fs.mode()); // int 	mode() 
		metadata.put("mtime", fs.mtime()); // long 	mtime() 
		metadata.put("nlink", fs.nlink()); // int 	nlink() 
		metadata.put("rdev", fs.rdev()); // long 	rdev() 
		metadata.put("size", fs.st_size()); // long 	st_size() 
		metadata.put("uid", fs.uid()); // int 	uid() 
		
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
