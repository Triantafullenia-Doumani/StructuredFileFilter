package file.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import metadata.MetadataManager;
import filtering.FilteringEngine;

public class StructuredFileManager implements StructuredFileManagerInterface{

	public String[] separateLine(String line, String separator){
		 return line.split(separator);
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
	
	 //how to use params
	public File registerFile(String pAlias, String pPath, String pSeparator) throws IOException, NullPointerException{
		
		ArrayList<String [] > fileData = new ArrayList<String []>();
		File file;
		if(pAlias == null || pPath == null || pSeparator == null) {
			throw new NullPointerException();
		}
	    try {
			file = new File(pPath);   
	        Scanner scanner = new Scanner(file);
	        String headers = scanner.nextLine();
	        // TODO  pare prwth grammh kai vale thn kapou or not 
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String [] lineSplited = separateLine(line,pSeparator);
	            fileData.add(lineSplited);
	          }
	      } catch (IOException e) {
	    	  throw new IOException();
	      }
        return file;
	}
	
	/**
	 * Returns an array of String with the column names of the file registered with
	 * the alias parameter
	 * 
	 * @param pAlias a string with a short code-name for the registered file
	 * @return an array of String with the column names of the file; null if the
	 *         alias has not been registered
	 */
	public String[] getFileColumnNames(String pAlias) {
		return null;
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
		return null;
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
		return 0;
	}
	
}