import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.w3c.dom.Element;

public class PipeCommand extends Command{
	 public PipeCommand(List<Element> pipeElems , Map<String, String> realFileNames) throws ProcessException {
		    
	        Element pipeIn = pipeElems.get(0);
	        Element pipeOut = pipeElems.get(1);
	        
	        String id1 = pipeIn.getAttribute("id");
	        if (id1 == null || id1.isEmpty()) {
	            throw new ProcessException("Missing ID in CMD Command");
	        }
	        
	        String path1 = pipeIn.getAttribute("path");
	        if (path1 == null || path1.isEmpty()) {
	            throw new ProcessException("Missing PATH in CMD Command");
	        }
	        
	        this.id1 = id1;
	        
	        //here add path1 to cmdArgs1
	        cmdArgs1.add(path1);
	        String arg = pipeIn.getAttribute("args");
	        
	        if (!(arg == null || arg.isEmpty())) {
	            StringTokenizer st = new StringTokenizer(arg);
	            while (st.hasMoreTokens()) {
	                String tok = st.nextToken();
	                cmdArgs1.add(tok);
	            }
	        }
	        
	        String in = pipeIn.getAttribute("in");
	        inID = realFileNames.get(in);
	        
	        String id2 = pipeOut.getAttribute("id");
	        if (id2 == null || id2.isEmpty()) {
	            throw new ProcessException("Missing ID in CMD Command");
	        }
	        
	        String path2 = pipeOut.getAttribute("path");
	        if (path2 == null || path2.isEmpty()) {
	            throw new ProcessException("Missing PATH in CMD Command");
	        }
	        
	        this.id2 = id2;
	        
	        
	        
	        cmdArgs2.add(path2);
	        
	        String arg2 = pipeOut.getAttribute("args");
	        
	        if (!(arg2 == null || arg2.isEmpty())) {
	            StringTokenizer st = new StringTokenizer(arg2);
	            while (st.hasMoreTokens()) {
	                String tok = st.nextToken();
	                cmdArgs2.add(tok);
	            }
	        }
	        
	        String out = pipeOut.getAttribute("out");
	        outID = realFileNames.get(out);
	    }
	    
	    @Override
		public String describe(){
	        System.out.println("Pipe Command");
	        return null;
	    }
	    
	    @Override
		public void execute(String workingDir) throws InterruptedException, IOException{
	            
	        System.out.println("Pipe Command");
	    	
	        ProcessBuilder out_builder = new ProcessBuilder(cmdArgs1);
	        out_builder.directory(new File(workingDir));
	        File wd = out_builder.directory();
	        final Process out_process = out_builder.start();
	        FileInputStream fis = new FileInputStream(new File(wd, inID));
	        OutputStream os = out_process.getOutputStream();
	        
	        int achar;
	        while(( achar = fis.read()) != -1){
	            os.write(achar);
	        }
	        os.close();
	        fis.close();
	        
	        ProcessBuilder in_builder = new ProcessBuilder(cmdArgs2);
	        in_builder.directory(wd);
	        final Process in_process = in_builder.start();
	        InputStream out_is = out_process.getInputStream();
	        OutputStream in_os = in_process.getOutputStream();
	        while ((achar = out_is.read()) != -1){
	            in_os.write(achar);
	        }
	        in_os.close();
	        System.out.println(id1 + " has exited");
	        
	        File outfile = new File(wd,outID);
	        FileOutputStream fos = new FileOutputStream(outfile);
	        InputStream in_is = in_process.getInputStream();
	        while( (achar = in_is.read()) != -1)
	        {
	            fos.write(achar);

	        }
	        fos.close();
	        
	        System.out.println(id2 + " has exited");



	    }
	    

	    private String id1,inID;
	    private String id2,outID;
	    private List<String> cmdArgs1 = new ArrayList<String>();
	    private List<String> cmdArgs2 = new ArrayList<String>();
}
