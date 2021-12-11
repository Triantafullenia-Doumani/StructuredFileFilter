package filtering;

import java.util.List;
import java.util.Map;

import metadata.MetadataManagerInterface;
import metadata.NaiveFileMetadataManager; // WTF

public class FilteringEngine implements FilteringEngineInterface {

	public FilteringEngine(Map<String, List<String>> atomicFilters, NaiveFileMetadataManager metadataManager) {
		// TODO Auto-generated constructor stub
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
		
		// TODO ean Map<String> =/ ta pedia mas, return -1;
		
		ArrayList<String[]> document = new ArrayList<String[]>();
		document = pMetadataManager.getDocument();
		
		for(int i=0; i< )
			for(int j=0; j<document.length; j++) {
			
			}
		
		
		return 0;
	}

	/**
	 * Returns the result of applying a filter to a filter; requires the
	 * setupFilteringEngine to have fired before such that the FilteringEngine has
	 * been equipped with the appropriate context values.
	 * 
	 * @return a List of String Arrays -- each of the arrays represents a record in
	 *         the file, and each element of the array is a String representation of
	 *         the respective column of the record
	 * @see metadata.MetadataManagerInterface.setupFilteringEngine
	 */
	public List<String[]> workWithFile(){
		return null;
	}


}
