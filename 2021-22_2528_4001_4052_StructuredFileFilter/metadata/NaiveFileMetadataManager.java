package metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NaiveFileMetadataManager implements MetadataManagerInterface{

	private String pAlias;
	private File pFile;
	private String pSeparator;
	private String[] columnNames; 
	private ArrayList<String []> document;
	
	public String[] separateLine(String line, String separator){
		 return line.split(separator);
	}
	
	public NaiveFileMetadataManager(String pAlias, File pFile, String pSeparator) {
		this.pAlias 		= pAlias;
		this.pFile 			= pFile;
		this.pSeparator 	= pSeparator;
	}
	
	public void registerFile() throws IOException {
		ArrayList<String [] > document = new ArrayList<String []>();
		File file;
		Scanner scanner;
	    try {
	        scanner = new Scanner(this.pFile);
	        String columnNames = scanner.nextLine();
	        setColumnNames(columnNames);
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String [] lineArray = separateLine(line,pSeparator);
	            document.add(lineArray);
	          }
		} catch (IOException e) {
		  throw new IOException();
		}
	    setDocument(document);
		scanner.close();
	}
	public void setDocument(ArrayList<String [] > document) {
		this.document = document;
	}
	public void setColumnNames(String columnNames) {
		this.columnNames = separateLine(columnNames,pSeparator);
	}
	
	/**
	 * Returns a mapping of the fields to their position in the structure of the
	 * record files
	 * 
	 * @return a Map with the String name of the column as key and an Integer with
	 *         the position of the column as value
	 */
	public Map<String, Integer> getFieldPositions(){
		Map <String, Integer>  fieldPositions = new HashMap<String, Integer>();
		for (int i = 0; i < this.columnNames.length; i++) {
			fieldPositions.put(this.columnNames[i],i);
		}
        System.out.print("third statement");  

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
		return null;
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
	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<string[]> getDocument(){
		return this.document;
	}
	
}
