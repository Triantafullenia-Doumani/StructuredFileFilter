package metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NaiveFileMetadataManager implements MetadataManagerInterface{

	private String pAlias;
	private File pFile;
	private String pSeparator;
	private String[] columnNames; 
	private ArrayList<String[]> series;
	
	public NaiveFileMetadataManager(String pAlias, File pFile, String pSeparator) throws NullPointerException, IOException {
		if(pAlias == null || pSeparator == null) {
	           throw new NullPointerException("Null arguments are not valid in NaiveFileMetadataManager");
		}
		this.pAlias 		= pAlias;
		this.pFile 			= pFile;
		this.pSeparator 	= pSeparator;
		registerFile();
	}
	public String[] separateLine(String line, String separator){
		 return line.split(separator);
	}
	
	public void registerFile() throws IOException {
		ArrayList<String [] > series = new ArrayList<String []>();
		Scanner scanner;
	    try {
	        scanner = new Scanner(this.pFile);
	        String columnNames = scanner.nextLine();
			String[] columnNamesSplited = separateLine(columnNames,this.pSeparator);
	        setColumnNames(columnNamesSplited);
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String [] lineArray = separateLine(line,pSeparator);
	            series.add(lineArray);
	          }
		} catch (IOException e) {
		  throw new IOException();
		}
	    setSeries(series);
		scanner.close();
	}
	public void setSeries(ArrayList<String [] > series) {
		this.series = new ArrayList<String [] >();
		this.series = series;
	}
	public void setColumnNames(String[] columnNamesIn) {
		this.columnNames = new String[columnNamesIn.length];
		for(int i=0; i<columnNamesIn.length; i++ ) {
			this.columnNames[i] = columnNamesIn[i];
		}
	}
	
	/**
	 * Returns a mapping of the fields to their position in the structure of the
	 * record files
	 * 
	 * @return a Map with the String name of the column as key and an Integer with
	 *         the position of the column as value
	 */
	public Map<String, Integer> getFieldPositions(){
		Map<String, Integer> fieldPositions = new HashMap<String, Integer>();
		if(this.columnNames == null) {
			System.out.println("NULL column names");
			return fieldPositions;
		}
		for(int i=0; i<this.columnNames.length; i++) {
			fieldPositions.put(this.columnNames[i],i);
		}
		return fieldPositions;		
	}

	/**
	 * Returns a File object representing the data file that the metadata manager
	 * handles
	 * 
	 * @return a File object representing the data file that the metadata manager
	 *         handles
	 */
	public File getDataFile() {
		return this.pFile;
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
		String [] columnNames = {};
		if(this.columnNames==null) {
			return columnNames;
		}
		return this.columnNames;
		
	}
	public String getAlias() {
		return this.pAlias;
	}
	public ArrayList<String[]> getSeries(){
		return this.series;
	}
}