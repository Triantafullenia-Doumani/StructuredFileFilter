package metadata;

public class MetadataManager implements MetadataManagerInterface{

	/**
	 * Returns a mapping of the fields to their position in the structure of the
	 * record files
	 * 
	 * @return a Map with the String name of the column as key and an Integer with
	 *         the position of the column as value
	 */
	public abstract Map<String, Integer> getFieldPositions(){
		return;
	}

	/**
	 * Returns a File object representing the data file that the metadata manager
	 * handles
	 * 
	 * @return a File object representing the data file that the metadata manager
	 *         handles
	 */
	public abstract File getDataFile() {
		return;
	}

	/**
	 * Returns the separator of the file handled by the particular metadata manager
	 * 
	 * @return a String with column separator
	 */
	public abstract String getSeparator() {
		return;
	}

	/**
	 * Returns the column names of the file handled by the particular metadata
	 * manager
	 * 
	 * @return an array of String with the column names
	 */
	public abstract String[] getColumnNames() {
		
	}

}
