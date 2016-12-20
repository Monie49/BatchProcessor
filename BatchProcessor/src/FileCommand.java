import org.w3c.dom.Element;

public class FileCommand extends Command{

	public FileCommand(Element elem) throws ProcessException {
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}

		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
		}
		
		this.id = id;
		this.path = path;
		}
	
	@Override
	public String describePath(){
		return this.path;
	}
	@Override
	public String describeId(){
		return this.id;
	}
	
	
	public void execute(String workingDir){
		System.out.println("File Command on file: " + this.path);
	}
	
	private String path;
	private String id;

	
}
