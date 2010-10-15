package nl.bneijt.datajunction;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class Application 
{
    public static void main( String[] args )
    {
        System.out.println( "Take a source file and output it into a target directory in chunks. Store metadata in json." );
        //Start a chunk storage in /tmp
        Storage storage = new nl.bneijt.datajunction.ChunkStorage.Output("/tmp/testing");
        File f = new NativeFile("/etc/ld.so.cache");
        try {
			storage.put(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
