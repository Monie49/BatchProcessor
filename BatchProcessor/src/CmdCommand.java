import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;
public class CmdCommand extends Command{

	public CmdCommand(Element elem,Map<String,String> realFileNames) throws ProcessException {
		
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
        String in = elem.getAttribute("in");
        String out = elem.getAttribute("out");
            
    	this.inID = realFileNames.get(in);
    	this.outID = realFileNames.get(out);

 
        if(out == null || out.isEmpty() || outID == null || outID.isEmpty()){
               throw new ProcessException("Unable to locate OUT FileCommand with id " + out);
        }


		String arg = elem.getAttribute("args");

		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
				}
		}

	}

	@Override
	public String describe(){
        String describe = "Command : " + this.path;
        if (cmdArgs!= null && !cmdArgs.isEmpty()){
            describe += " Arg " ;
            for(String argi: cmdArgs) {
                describe += argi;
            }
        }
        System.out.println(describe);
		return this.id;
	}
	
	
	@Override
	public void execute(String workingDir) throws InterruptedException, IOException{
		describe();
		
		
		List<String> exec = new ArrayList<String>();
		exec.add(path);
		if(cmdArgs != null && !cmdArgs.isEmpty()){
			for (String argi : cmdArgs){
				exec.add(argi);
			}
		}

		ProcessBuilder builder = new ProcessBuilder();
		builder.command(exec);
		builder.directory(new File(workingDir));
		File wd = builder.directory();

		
		if(inID != null){
			File inFile = new File(wd,inID);
			builder.redirectInput(inFile);
		}
		if(outID != null){
			File outFile = new File(wd,outID);
			builder.redirectOutput(outFile);
		} 
		Process process = builder.start();
		process.waitFor();
		
		
		System.out.println(this.id + " has exited" );		
	}
    
	private String id,path,inID,outID;
	private List<String> cmdArgs = new ArrayList<String>();
}
