package nl.bneijt.datajunction;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import org.jruby.ext.posix.POSIXHandler;
import org.jruby.ext.posix.POSIX.ERRORS;

public class JavaPosixHandler implements POSIXHandler {

	@Override
	public void error(ERRORS arg0, String arg1) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram has not implemented this method yet.");
		
	}

	@Override
	public File getCurrentWorkingDirectory() {
		// TODO Auto-generated method stub
		return new File("/");
	}

	@Override
	public String[] getEnv() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public PrintStream getErrorStream() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public InputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public PrintStream getOutputStream() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public int getPID() {
		throw new RuntimeException("Bram has not implemented this method yet.");
		
	}

	@Override
	public boolean isVerbose() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram has not implemented this method yet.");
		
	}

	@Override
	public void unimplementedError(String arg0) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram has not implemented this method yet.");
	}

	@Override
	public void warn(WARNING_ID arg0, String arg1, Object... arg2) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Bram has not implemented this method yet.");
		
	}

}
