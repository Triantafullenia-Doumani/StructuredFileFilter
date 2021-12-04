package application.naive.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import application.chart.management.VisualizationEngine;
import application.jtable.management.JTableViewer;
import file.manager.StructuredFileManagerFactory;
import file.manager.StructuredFileManagerInterface;
import java.util.Scanner;

public class NaiveApplicationController {

	private final StructuredFileManagerInterface fileManager;
	private final VisualizationEngine visualizationEngine;
	private static Scanner scanner;
	private ArrayList<File> files = new ArrayList<File>(); // Create an ArrayList object

	public NaiveApplicationController() {
		StructuredFileManagerFactory engineFactory = new StructuredFileManagerFactory();
		this.fileManager = engineFactory.createStructuredFileManager();
		this.visualizationEngine = new VisualizationEngine();
	}

	public File registerFile(String pAlias, String pPath, String pSeparator) {
		File resultFile = null;
		try {
			resultFile = this.fileManager.registerFile(pAlias, pPath, pSeparator);
		} catch (NullPointerException e) {
			System.err.println("NaiveApplicationController::registerFile NullPointerException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("NaiveApplicationController::registerFile IOException");
			e.printStackTrace();
		}
		return resultFile;
	}

	public List<String[]> executeFilterAndShowJTable(String alias, Map<String, List<String>> atomicFilters,
			String outputFileName) {
		List<String[]> result;
		String[] columnNames;
		int numRows;

		result = fileManager.filterStructuredFile(alias, atomicFilters);
		columnNames = fileManager.getFileColumnNames(alias);

		// Show in sysout
		System.out.println("\n\n");
		numRows = fileManager.printResultsToPrintStream(result, System.out);
		System.out.println("\n NUM ROWS: " + numRows + "\n");

		// try to save in a file instead of sysout
		saveToResultTextFile(outputFileName, result);

		// show in JTable
		showJTableViewer(result, columnNames);
		return result;
	}

	public int saveToResultTextFile(String outputFileName, List<String[]> result) {
		int numRows = 0;
		FileOutputStream fOutStream = null;
		try {
			fOutStream = new FileOutputStream(outputFileName);
		} catch (FileNotFoundException e) {
			System.err.println("NaiveClient:: failed to open fout stream");
			e.printStackTrace();
		}
		PrintStream pOutStream = new PrintStream(fOutStream);
		numRows = fileManager.printResultsToPrintStream(result, pOutStream);
		System.out.println("\n SAVED NUM ROWS: " + numRows + " in file: " + outputFileName + "\n");

		pOutStream.close();
		try {
			fOutStream.close();
		} catch (IOException e) {
			System.err.println("NaiveClient:: failed to close fout stream");
			e.printStackTrace();
		}
		return numRows;
	}

	private void showJTableViewer(List<String[]> result, String[] columnNames) {
		JTableViewer jTableViewer;

		jTableViewer = new JTableViewer(result, columnNames);
		jTableViewer.createAndShowJTable();

	}

	public void showSingleSeriesBarChart(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
			String outputFileName) {
		String[] columnNames = this.fileManager.getFileColumnNames(pAlias);
		List<String> colList = Arrays.asList(columnNames);
		int xPos = colList.indexOf(pXAxisName);
		int yPos = colList.indexOf(pYAxisName);
		this.visualizationEngine.showSingleSeriesBarChart(pAlias, series, pXAxisName, pYAxisName, outputFileName, xPos,
				yPos);
	}

	public void showSingleSeriesLineChart(String pAlias, List<String[]> series, String pXAxisName, String pYAxisName,
			String outputFileName) {
		String[] columnNames = this.fileManager.getFileColumnNames(pAlias);
		List<String> colList = Arrays.asList(columnNames);
		int xPos = colList.indexOf(pXAxisName);
		int yPos = colList.indexOf(pYAxisName);
		this.visualizationEngine.showSingleSeriesLineChart(pAlias, series, pXAxisName, pYAxisName, outputFileName, xPos,
				yPos);
	}
	
	public String askForAlias() {
		
        System.out.println("Please enter a file Alias");
        String fileAlias = null;
        while(true) {
            	fileAlias = scanner.nextLine();
            	if(fileAlias == null) {
                    System.out.println("DWSE KALO Alias");
            		continue;
            	}
				return fileAlias;
        }
	}
	
	public String askForPath() {
		System.out.println("Please enter file path");
		String filePath = null;
		while(true) {
			filePath = scanner.nextLine();
			try {
	        	Paths.get(filePath);
				return filePath;
	   		} catch (InvalidPathException | NullPointerException ex) {
	   			System.out.println("I AM ASKING FOR A REAL PATH");
	   			continue;
	        }
		}
	}
	public String askForSeparator() {
		
        System.out.println("Please enter the file separator. ',' '\t' or '|' for example");
        String fileSeparator = scanner.nextLine();        
        return fileSeparator;
        // thelw kanenan elegxo gia auto?

	}
	/** Program Starts Here **/
    public static void main(String[] args){

    	NaiveApplicationController naiveAppController  = new NaiveApplicationController();
    	scanner = new Scanner(System.in);
        String fileAlias = naiveAppController.askForAlias();
        String filePath = naiveAppController.askForPath();
        String fileSeparator = naiveAppController.askForSeparator();
        naiveAppController.registerFile(fileAlias, filePath, fileSeparator);
        scanner.close();
        return;	
    }	 

}// end class

//public VisualizationEngine getVisualizationEngine() {
//return visualizationEngine;
//}
//public StructuredFileManagerInterface getFileManager() {
//return fileManager;
//}