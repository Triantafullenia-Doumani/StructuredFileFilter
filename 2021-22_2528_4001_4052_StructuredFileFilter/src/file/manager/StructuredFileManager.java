package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import metadata.NaiveFileMetadataManager;
import filtering.FilteringEngine;

public class StructuredFileManager implements StructuredFileManagerInterface{
	
	private HashMap<String, NaiveFileMetadataManager> allMetadata;	
	private NaiveFileMetadataManager metadata;
	
	public StructuredFileManager() {
		this.allMetadata	= new HashMap<String, NaiveFileMetadataManager>();
	}
	

	/**
	 * A method to allow the back end engine to learn the characteristics of a file
	 * 
	 * @param pAlias     a string with a short code-name for the registered file, to
	 *                   allow retrieval by it in later method calls
	 * @param pPath      a String with the path to the registered file
	 * @param pSeparator a String with the separator between the columns of each
	 *                   record
	 * @return a File object for the newly registered engine
	 * @throws IOException
	 * @throws NullPointerException
	 */
	
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException{
		File pFile = new File(pPath);  
		if(!pFile.exists()) {
			System.out.println("The file that you entered does not exists on filesystem");
			return null;
		}
		NaiveFileMetadataManager metadata = new NaiveFileMetadataManager(pAlias,pFile,pSeparator); 
		metadata.registerFile();
		this.allMetadata.put(pAlias,metadata);
        return pFile;
	}
	
	/**
	 * Returns an array of String with the column names of the file registered with
	 * the alias parameter
	 * 
	 * @param pAlias a string with a short code-name for the registered file
	 * @return an array of String with the column names of the file; 
	 *  or return zero-sized array of strings instead, if the
	 *  alias has not been registered
	 */
	public String[] getFileColumnNames(String pAlias) {
		String[] columnNames = {};
		if(pAlias == null) {
			return columnNames;
		}
		//MetadataManager metadata = new MetadataManager(); 
		NaiveFileMetadataManager metadata = this.allMetadata.get(pAlias);
		if(metadata == null) {
			return columnNames;
		}
		columnNames = metadata.getColumnNames();
		return columnNames;
	}

	/**
	 * Applies a composite conjunctive filter, composed of atomic filters, to the
	 * file and returns the rows that satisfy its conditions.
	 * 
	 * The result includes exactly those rows of the file that satisfy every atomic
	 * filter in the filter submitted.
	 * 
	 * @param pAlias         a String with the alias of a registered file
	 * @param pAtomicFilters a Map with the name of the filtered field as key and
	 *                       the list of permissible values as the value of the map.
	 *                       All values are treated as strings in the files that the
	 *                       system handles.
	 * @return a List of String[], which is the result set of the filter. Each
	 *         record in the result is represented as an array of strings.
	 */
	public List<String[]> filterStructuredFile(String pAlias, Map<String, List<String>> pAtomicFilters){
		metadata = this.allMetadata.get(pAlias);
		List<String[]> filteredFile = new ArrayList<String[]> ();
		String[] columnNames = getFileColumnNames(pAlias);
		filteredFile.add(columnNames);
		FilteringEngine filteringEngine = new FilteringEngine(pAtomicFilters,metadata);
		return filteringEngine.workWithFile();
	}

	/**
	 * Directs a filter result in a PrintStream
	 * 
	 * @param recordList a List of String[] which is typically the result set of a
	 *                   filter
	 * @param pOut       a PrintStream (that must have been externally prepared)
	 *                   where the results are presented
	 * 
	 * @return -1 if the printing is impossible due to file or null problems; the
	 *         number of records directed to the PrintStream otherwise.
	 */
	public int printResultsToPrintStream(List<String[]> recordList, PrintStream pOut) {
		if(pOut == null || recordList == null) {
			return -1;
		}
		int counter = 0;		
		for(String [] record:recordList) {
			pOut.println(Arrays.asList(record));
			counter ++;
		}
		return counter;
	}
	
	public ArrayList<String[]> getMetadata(String pAlias) {
		NaiveFileMetadataManager metadata = allMetadata.get(pAlias);
		if(metadata == null) {
			return null;
		}
		ArrayList<String[]> series = metadata.getSeries();
		return series;
	}
	public NaiveFileMetadataManager getMetadataObject(String pAlias) {
		NaiveFileMetadataManager metadataObj = allMetadata.get(pAlias);
		if(metadataObj == null) {
			return null;
		}
		return metadataObj;
	}


	public void addNewObj(String pAlias,NaiveFileMetadataManager newMetadataObj) {
		this.allMetadata.put(pAlias,newMetadataObj);
		
	}
}