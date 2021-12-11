package file.manager;

public class StructuredFileManagerFactory {

	public StructuredFileManagerInterface createStructuredFileManager() {
		return new StructuredFileManager();
	}

}
