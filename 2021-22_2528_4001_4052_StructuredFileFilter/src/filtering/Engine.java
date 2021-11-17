package filtering;

import metadata.MetadataManagerInterface;

public class Engine implements FilteringEngineInterface {

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
	 * @return
	 */
	public int setupFilteringEngine(Map<String, List<String>> pAtomicFilters,
			MetadataManagerInterface pMetadataManager) {
		return;
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
		return;
	}

}