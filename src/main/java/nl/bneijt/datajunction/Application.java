package nl.bneijt.datajunction;

import java.io.IOException;


public class Application 
{
    public static void main( String[] args )
    {
        System.out.println( "Take a source file and output it into a target directory in chunks. Store metadata in json." );
        //Start a chunk storage in /tmp
        WritableStorage storage = new nl.bneijt.datajunction.ChunkStorage.Output("/tmp/testStorage");
        Source f = new NativeFile("/etc/ld.so.cache");
        try {
			storage.put(f);
			System.out.println(f.meta().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
