import java.util.ArrayList;
import java.util.List;

public class Batch {

	public Batch(){
		
	}
	
	public String getWorkingDir(){
		return workingDir;
	}
	
	public void setWorkingDir(String workingDir){
		this.workingDir = workingDir;
	}
	
	
	public void addCommand(Command command){
		commands.add(command);
	}
	

	public List<Command> getCommands(){
		return commands;
	}
	
    private String workingDir;
    private List<Command> commands = new ArrayList<Command>();
}
