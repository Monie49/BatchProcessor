import java.util.List;
import java.io.File;
import java.io.IOException;
public class BatchProcessor {

	public static void main(String [] args) throws InterruptedException, IOException 
	{
		String filename = null;
		if (args.length == 1 ){
			filename = args[0];
		} else {
			System.out.println("Please give me a file to handle");
			filename = "work/batch5.broken.xml";
		}
		
		Batch batch = buildBatchFromFile(filename);
		executeBatch(batch);
		
		System.out.println("Finished Batch");
	}
	
	/**
	 * Build a batch object from filename
	 * @param filename the filename of the file to be handle
	 * @return a Batch Object created by the filename passed in 
	 */
	private static Batch buildBatchFromFile(String filename) 
	{
		File batchFile = new File(filename);
		Batch batch = BatchParser.buildBatch(batchFile);			
		return batch;
	}
	
	/**
	 * Execute Commands of the giving batch object
	 * @param batch  the batch object that needed to handle 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void executeBatch(Batch batch) throws InterruptedException, IOException {
		List<Command> commands = batch.getCommands();
		for (int i = 0; i < commands.size(); i++) {
			commands.get(i).execute(batch.getWorkingDir());
		}
	}
}
