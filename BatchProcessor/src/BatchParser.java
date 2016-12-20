import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class BatchParser {
	public static Batch buildBatch(File batchFile)
	{
        try {
        	
        	FileInputStream fis = new FileInputStream(batchFile);
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.parse(fis);
        	
            Element pnode = doc.getDocumentElement();
            NodeList nodes = pnode.getChildNodes();
            for(int idx = 0; idx < nodes.getLength(); idx++){
                Node node = nodes.item(idx);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element)node;
                    parseCommand(elem);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return batch;
	}
	
	
	// create messages
    private static void parseCommand(Element elem) throws ProcessException
    {
        String cmdName = elem.getNodeName();
        
        if (cmdName == null) {
            throw new ProcessException("unable to parse command from " + elem.getTextContent());
        }
        else if ("wd".equalsIgnoreCase(cmdName)) {
            System.out.println("Parsing wd");  
            Command cmd = buildCommand(elem);
            batch.addCommand(cmd);
        }
        else if ("file".equalsIgnoreCase(cmdName)) {
            System.out.println("Parsing file");
            Command cmd = buildCommand(elem);
            batch.addCommand(cmd);
        }
        else if ("cmd".equalsIgnoreCase(cmdName)) {
            System.out.println("Parsing cmd");
            Command cmd = buildCommand(elem);
            batch.addCommand(cmd);

        }
        else if ("pipe".equalsIgnoreCase(cmdName)) {
            System.out.println("Parsing pipe");
            

            NodeList pipeCmdNodes = elem.getChildNodes();
            for(int idx = 0; idx < pipeCmdNodes.getLength(); idx++){
                Node pipeChildNode = pipeCmdNodes.item(idx);
                if(pipeChildNode.getNodeType() == Node.ELEMENT_NODE){
                    pipeElems.add((Element)pipeChildNode);
                    System.out.println("Parsing "+pipeChildNode.getNodeName());
                }
            }                     
            Command pipeCmd = new PipeCommand(pipeElems,realFileNames);
            batch.addCommand(pipeCmd);            
        }
        else {
            throw new ProcessException("Unknown command " + cmdName + " from: " + elem.getBaseURI());
        }
    }
    

	
	/**
	 * Build Command from element
	 * @param elem 
	 * @return Command 
	 * @throws ProcessException
	 */
    private static Command buildCommand(Element elem) throws ProcessException
    {
    	String cmdName = elem.getNodeName();
    	
		Command cmdObj = new Command();
		
		if ("wd".equalsIgnoreCase(cmdName)) {
        	cmdObj = new WDCommand(elem);
        	batch.setWorkingDir(cmdObj.describe());
        }
        else if ("file".equalsIgnoreCase(cmdName)) {
        	cmdObj = new FileCommand(elem);
        	realFileNames.put(cmdObj.describeId(), cmdObj.describePath());
        }
		
        else if ("cmd".equalsIgnoreCase(cmdName)) {
            cmdObj = new CmdCommand(elem,realFileNames);
            
        }
        else if ("pipe".equalsIgnoreCase(cmdName)) {
        }
    	
        return cmdObj;
    }
	
    public static void putFiles(String filex, String realName){
    	realFileNames.put(filex, realName);
    }
	

    /* private instance variable */
    static Batch batch = new Batch() ;
    private static Map<String,String> realFileNames = new HashMap<String,String>();
    private static List<Element> pipeElems = new ArrayList<Element>();

}
