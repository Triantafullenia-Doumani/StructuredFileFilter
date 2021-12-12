package filtering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import metadata.HashMap;
import metadata.Integer;
import metadata.MetadataManagerInterface;
import metadata.NaiveFileMetadataManager;
import metadata.String;

public class FilteringEngine implements FilteringEngineInterface {

	private Map<String, List<String>> pAtomicFilters;
	private NaiveFileMetadataManager pMetadatamanager;
	
	public FilteringEngine(Map<String, List<String>> atomicFilters, NaiveFileMetadataManager metadataManager) {
		
		if(setupFilteringEngine(AtomicFilters,metadataManager) == -1) {
			System.out.println("SET UP -1");
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
	 * @return 0 succes, -1 fail.
	 */
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) {

		List<String> acceptableValues = new ArrayList<String []>();
		Map <String, Integer>  fieldPositions = new HashMap<String, Integer>();
		
		String[] columnNames = pMetadataManager.getColumnNames();
		for (Map.Entry<String, List<String>> pAtomicFilter : pAtomicFilters.entrySet()) {
			String key = pAtomicFilter.getKey();
			acceptableValues = pAtomicFilters.getValue();
			if(!columnNames.contains(key)) {
				System.out.println("Field's name is not acceptable: "+key);
				return -1;
			}
			this.pAtomicFilters.put(key,acceptableValues);
		}
		this.pMetadatamanager = pMetadataManager;
		workWithFile();
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
	List<String []>  filteredDocument = new ArrayList<Stirng []>();
	Map <String, Integer>  fieldPositions = new HashMap<String, Integer>();
	fieldPositions = this.pMetadatamanager.getFieldPositions();
	document = this.pMetadatamanager.getDocument();
	for(String [] line: document) {
		String fieldValue = line[position];
		if(!filteredDocument.contains(fieldValue)) {
			System.out.println("Field's value is not acceptable: "+fieldValue);
			return -1;
		}
		filteredDocument.add(line);
	}
	return filteredDocument();
}

}
