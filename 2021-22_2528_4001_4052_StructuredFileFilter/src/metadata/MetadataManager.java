package metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class MetadataManager implements MetadataManagerInterface{

	private String pAlias;
	private String pPath;
	private String pSeparator;
	private String[] columnNames;
	private File file;
	private ArrayList<String []> dataFile;
	
	
	public MetadataManager(String pAlias, String pPath, String pSeparator, String [] columnNames, File file, ArrayList<String [] > dataFile) {
		this.pAlias = pAlias;
		this.pPath 	= pPath;
		this.pSeparator = pSeparator;
		this.file = file;
		this.columnNames = columnNames;
		this.dataFile = dataFile;
	}
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
		return this.file;
	}

	/**
	 * Returns the separator of the file handled by the particular metadata manager
	 * 
	 * @return a String with column separator
	 */
	public String getSeparator() {
		return this.pSeparator;
	}

	/**
	 * Returns the column names of the file handled by the particular metadata
	 * manager
	 * 
	 * @return an array of String with the column names
	 */
	public String[] getColumnNames() {
		return this.columnNames;
		
	}

}
