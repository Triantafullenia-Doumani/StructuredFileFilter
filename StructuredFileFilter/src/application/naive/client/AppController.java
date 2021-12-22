package application.naive.client;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import file.manager.StructuredFileManager;
import metadata.NaiveFileMetadataManager;

public class AppController {
	
	private static Scanner scanner;
	private static AppController appController;
	private static NaiveApplicationController naiveAppController;
	private static StructuredFileManager fileManager; 
	private String pAlias;
	
	private String askForInput() {
		String input;
		while(true) {
			input = scanner.nextLine();
			if(input != null) break;
			System.out.println("Input can not be null. Try again");
		}
		return input;
	}
	
	private String askForAlias() {
		
        System.out.println("Please choose alias");
        String pAlias = askForInput();
        return pAlias;
	}
	
	private String askForPath() {
		System.out.println("Please enter file path");
		String pPath = null;
		while(true) {
			pPath = scanner.nextLine();
			try {
	        	Paths.get(pPath);
				return pPath;
	   		} catch (InvalidPathException | NullPointerException ex) {
	   			System.out.println("The form of this path is not correct. Please try again");
	   			continue;
	        }
		}
	}
	private String askForSeparator() {
		
        System.out.println("Please enter the file separator. ',' '\\t' or '|' for example");
        String pSeparator = askForInput();
        return pSeparator;
	}
	private String askForColumnName() {
		System.out.println("Please enter a column name\n");
		String columnName = askForInput();
		return columnName;
	}
	private String askForNextMove() {
        String nextMove = null;
        while(true) {
            System.out.println("****** Choose activity ******\n1) Register a new file\n2) Filter file\n3) Show Chart\n4) Exit\n--------------------");
            nextMove = scanner.nextLine();
            if(nextMove.equals("1") || nextMove.equals("2") || nextMove.equals("3") || nextMove.equals("4") || nextMove.equals("5")) {
            	break;
            }
           	System.out.println("Invalid input. Think what do you wanna do! :)");
        }      
		return nextMove;
	}
	
	private List<String> askForAcceptableValues(){
		System.out.println("Separate your values with comma: value1, value2, value3...");
		String values = scanner.nextLine();
		return Arrays.asList(values.split(","));
	}
	private String askForOutputFilename() {
		System.out.println("Please enter an output file name");
		String outputFilename = askForInput();
		return outputFilename;
	}
	
	private Map<String, List<String>> askForAtomicFilters(String[] columnNames) {
		Map<String, List<String>> atomicFilters = new HashMap<String, List<String>>();
		while(true) {
			String columnName = appController.askForColumnName();
			if(!Arrays.asList(columnNames).contains(columnName)) {
				System.out.println("Invalid field for: "+pAlias+"\nThese are your options:\n"+Arrays.asList(columnNames));
				continue;
			}
			System.out.println("Please enter acceptable values for: "+columnName);
			List<String> acceptableValues = appController.askForAcceptableValues();
			atomicFilters.put(columnName, acceptableValues);
			System.out.println("Do you want to add more filters? y or n");
			String addMoreFilters = scanner.nextLine();
			System.out.println(addMoreFilters);
			if(addMoreFilters.equals("y")) {
				System.out.println("Next filter:)");
				continue;
			}
			System.out.println("Ok, no more filters");
			return atomicFilters;
		}
	}
	private String askForColumnName(String[] columnNames) {
		String name;
		while(true) {
			name = askForInput();
			System.out.println("input: "+name);
			if(name.equals("exit")) {
				return null;
			}
			if(!Arrays.asList(columnNames).contains(name)) {
				System.out.println("Axis name if not valid.\nThese are your options:\n"+Arrays.asList(columnNames)+"\nPlease try again or exit(type exit)");
				continue;
			}break;
		}
		return name;
	}
	private void registerFile() {
		while(true) {
//			pAlias = "test";
//			String pPath = "test/resources/input/simple.csv";
//			String pSeparator = ",";
			pAlias = appController.askForAlias();
	        String pPath = appController.askForPath();
	        String pSeparator = appController.askForSeparator();
	        try {
		        naiveAppController.registerFile(pAlias, pPath, pSeparator);
		        break;
			}catch(Exception e) {
        		System.out.println("This file does not exist on your filesystem, Please try again");
	        	continue;
        	}
		}
	}
	private void filter() throws Exception{
		String pAlias = appController.askForAlias();
		NaiveFileMetadataManager metadata = fileManager.getMetadataObject(pAlias);
		if(metadata == null) {
			System.out.println("The alias you asked, does not exist on the Database");
			return;
		}
		String[] columnNames = fileManager.getFileColumnNames(pAlias);
		
		Map<String, List<String>> atomicFilters = new HashMap<String, List<String>>();
		atomicFilters = appController.askForAtomicFilters(columnNames);
		if(atomicFilters == null) {
			return;
		}
		File file = metadata.getDataFile();
		String outputFilename = appController.askForOutputFilename();
//		System.out.println("Do you want to save this filtered file? y or n");
//		String save = scanner.nextLine();
//		if(save.equals("n")) {
//			System.out.println("No save");
//			return;
//		}
		List<String[]> filteredMetadata = naiveAppController.executeFilterAndShowJTable(pAlias,atomicFilters,outputFilename);
		NaiveFileMetadataManager newMetadataObj = new NaiveFileMetadataManager(outputFilename, file , metadata.getSeparator());
		newMetadataObj.setSeries((ArrayList<String[]>) filteredMetadata);
		newMetadataObj.setColumnNames(columnNames);
		fileManager.addNewObj(outputFilename,newMetadataObj);
	}
	private void Chart() {
		String pAlias = appController.askForAlias();
		List<String []> metadata = fileManager.getMetadata(pAlias);
		String[] columnNames = fileManager.getFileColumnNames(pAlias);
		System.out.println("Please enter x axis name");
		String x = askForColumnName(columnNames);
		System.out.println("Please enter y axis name");
		String y = askForColumnName(columnNames);
		String outputFilename = askForOutputFilename();
		System.out.println("Do you want bar chart or line chart");
		while(true) {
			String chartType = askForInput();
			if(chartType.equals("bar")) {
				naiveAppController.showSingleSeriesBarChart(pAlias,metadata,x,y,outputFilename);
				break;
			}
			else if(chartType.equals("line")) {
				naiveAppController.showSingleSeriesLineChart(pAlias,metadata,x,y,outputFilename);
				break;
			}
			else {
				System.out.println("No valid chart type. Try again");
			}
		}
	}
	private void Play() throws Exception {
    	while(true) {
    		String nextMove = appController.askForNextMove();
    		if(nextMove.equals("1")) {
        		appController.registerFile();
    		}else if(nextMove.equals("2")) {
    			appController.filter();
    		}else if(nextMove.equals("3")){
    			appController.Chart();
    		}else if(nextMove.equals("4")){
    			System.out.println("Exit");
    			return;
    		}
    	}
	}
	/******** Program Starts Here ********/
	public static void main(String[] args) throws Exception{
		naiveAppController = new NaiveApplicationController();
		fileManager = (StructuredFileManager) naiveAppController.getFileManager();
		appController  = new AppController();
    	scanner = new Scanner(System.in);
    	appController.Play();
        scanner.close();
        return;			
	}
}
