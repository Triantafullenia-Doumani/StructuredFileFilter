package metadata;

import java.io.File;
import java.util.Map;

public class MetadataManager implements MetadataManagerInterface{

	/**
	 * Returns a mapping of the fields to their position in the structure of the
	 * record files
	 * 
	 * @return a Map with the String name of the column as key and an Integer with
	 *         the position of the column as value
	 */
	public Map<String, Integer> getFieldPositions(){
		return null;
	}

	/**
	 * Returns a File object representing the data file that the metadata manager
	 * handles
	 * 
	 * @return a File object representing the data file that the metadata manager
	 *         handles
	 */
	public File getDataFile() {
		return null;
	}

	/**
	 * Returns the separator of the file handled by the particular metadata manager
	 * 
	 * @return a String with column separator
	 */
	public String getSeparator() {
		return null;
	}

	/**
	 * Returns the column names of the file handled by the particular metadata
	 * manager
	 * 
	 * @return an array of String with the column names
	 */
	public String[] getColumnNames() {
		return null;
		
	}

}
