package nl.bneijt.xdg;

import org.apache.commons.io.FileUtils;

/**
 * http://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html
 * Probably will move away from static interface soon??
 * @author A. Bram Neijt <bneijt@gmail.com>
 *
 */
public class Configuration {
	/**
	 * BaseDirectoryDataHome
	 */
	static public String BDDataHome() {
		//Check env to see if it is defined there,
		//Otherwise return default
		return FileUtils.getUserDirectoryPath() + "/.local/share";
	}
	/**
	 * There is a single base directory relative to which user-specific configuration files should be written. 
	 * @return
	 */
	static public String BDConfigHome() {
		//default equal to $HOME/.config
		return FileUtils.getUserDirectoryPath() + "/.config";
	}
}
