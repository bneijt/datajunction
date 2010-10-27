package nl.bneijt.datajunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import nl.bneijt.xdg.Configuration;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;


public class Application 
{
    public static void main( String[] args ) throws FileNotFoundException
    {
    	InputStream input = new FileInputStream(new File(Configuration.BDConfigHome() + "/datajunction/default.yaml"));
        Yaml yaml = new Yaml();
        Object data = yaml.load(input);
    	    	
        System.out.println( "Take a source file and output it into a target directory in chunks. Store metadata in json." );

        if(args.length < 1)
        {
        	System.out.println("You need to supply a file as an argument");
        	return;
        }
        	//Start a chunk storage in /tmp
        WritableStorage storage = new nl.bneijt.datajunction.ChunkStorage.WritableChunkStorage("/tmp/testStorage");
        File f = new File(args[0]);
        if(!f.exists())
        {
        	System.out.println("File not found: " + args[0]);
        	return;
        }
        try {
            Source s = new NativeFile(args[0]);
			storage.put(s);
			System.out.println(s.meta().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
