package filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metadata.MetadataManagerInterface;
import metadata.NaiveFileMetadataManager;

public class FilteringEngine implements FilteringEngineInterface {

	private Map<String, List<String>> pAtomicFilters;
	private NaiveFileMetadataManager pMetadatamanager = null;
	
	public FilteringEngine(Map<String, List<String>> atomicFilters, NaiveFileMetadataManager metadataManager) {
		
		pAtomicFilters = new HashMap<String, List<String>>();
		if(setupFilteringEngine(atomicFilters,metadataManager) == -1) {
			System.out.println("Something went wrong. Filtering failed");
		}
	}

	/**
	 * Organizes the Filtering Engine with the appropriate context information, such
	 * that it can later execute the filter over the respective file. The method has
	 * two arguments standing for the filter and the file being filtered. The
	 * invocation of the method can also be part of the constructor of a class
	 * implementing FilteringEngineInterface.
	 * 
	 * @param pAtomicFilters   a Map<String, List<String>>, with the name of a field
	 *                         as key, and a list of acceptable values as the value
	 * @param pMetadataManager a MetadataManager practically representing the file
	 *                         being filtered
	 * @return 0 success, -1 fail.
	 */
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) {

		List<String> acceptableValues = new ArrayList<String>();		
		String[] columnNames = pMetadataManager.getColumnNames();
		
		if(columnNames == null || columnNames.length == 0 ) {
			return -1;
		}
		for (Map.Entry<String, List<String>> pAtomicFilter : pAtomicFilters.entrySet()) {
			String key = pAtomicFilter.getKey();
			acceptableValues = pAtomicFilters.get(key);
			if(!Arrays.asList(columnNames).contains(key)) {
				System.out.println("This key is not valid:"+key + " . Please try again to import filters");
				return -1;
			}
			
			this.pAtomicFilters.put(key,acceptableValues);
		}
		this.pMetadatamanager = (NaiveFileMetadataManager) pMetadataManager;
		return 0;
	}

	/**
	 * Returns the result of applying a filter to a file; requires the
	 * setupFilteringEngine to have fired before such that the FilteringEngine has
	 * been equipped with the appropriate context values.
	 * 
	 * @return a List of String Arrays -- each of the arrays represents a record in
	 *         the file, and each element of the array is a String representation of
	 *         the respective column of the record
	 * @see metadata.MetadataManagerInterface.setupFilteringEngine
	 */
	public List<String[]> workWithFile(){
	List<String []> document = new ArrayList<String [ ]>();
	List<String []> filteredDocument = new ArrayList<String []>();

	List<String> acceptableValues = new ArrayList<String>();
	Map <String, Integer>  fieldPositions = new HashMap<String, Integer>();
	if( this.pMetadatamanager == null) {
		return null;
	}
	fieldPositions = this.pMetadatamanager.getFieldPositions();

	document = this.pMetadatamanager.getSeries();

	for(String[] line : document) {
		filteredDocument.add(line);
	}
	for (Map.Entry<String, List<String>> pAtomicFilter : pAtomicFilters.entrySet()) {
		String key = pAtomicFilter.getKey();
		int position = fieldPositions.get(key);
		acceptableValues = pAtomicFilters.get(key);
		int docSize = filteredDocument.size();
		for(int i=0; i<docSize; i++) {
			String [] line = filteredDocument.get(i);
			String fieldValue = line[position];
			if(acceptableValues.contains(fieldValue)) {
				continue;
			}
			filteredDocument.remove(i);
			// Move the cursor 1 step back, since we removed one line.
			i--;
			// decrease size, since we removed one line
			docSize--;
		}
	}
	return filteredDocument;
 }

}
