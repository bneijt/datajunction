package nl.bneijt.datajunction;

import java.io.IOException;


public class Application 
{
    public static void main( String[] args )
    {
        System.out.println( "Take a source file and output it into a target directory in chunks. Store metadata in json." );

        if(args.length < 1)
        {
        	System.out.println("You need to supply a file as an argument");
        	return;
        }
        	//Start a chunk storage in /tmp
        WritableStorage storage = new nl.bneijt.datajunction.ChunkStorage.WritableChunkStorage("/tmp/testStorage");
        Source f = new NativeFile(args[0]);
        try {
			storage.put(f);
			System.out.println(f.meta().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
