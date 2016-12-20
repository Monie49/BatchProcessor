import org.w3c.dom.Element;

public class WDCommand extends Command{

	public WDCommand(Element elem) throws ProcessException {
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}

		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
		}		
		this.path = path;
	}

	@Override
	public String describe(){
		return this.path;
	}
	
	public void execute(String workingDir){
		System.out.println("The working directory will be set to " + this.path);
	}
	
	private String path;	
}
