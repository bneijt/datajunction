package nl.bneijt.datajunction;


public class Application 
{
    public static void main( String[] args )
    {
        System.out.println( "Take a source file and output it into a target directory in chunks. Store metadata in json." );
        //Start a chunk storage in /tmp
        Storage storage = new ChunkStorage("/tmp/testing", true);
        File f = new NativeFile("/etc/ld.so.cache");
        storage.put(f);
    }
}
