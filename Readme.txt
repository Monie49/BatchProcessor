# BatchProcessor
batch processor 
This project built a batch language processor. The scope of this effort is building a tool that parses and executes a batch files containing a number of commands. A batch file, or batch, contains one or more commands that are executed sequentially. 
Each command executed by our batch processor will be executed as a process and communicate (pass information) using files or pipes. 


Batch Language Commands
The following sections describe the four commands to be implemented by this batch processor. 
wd Command
wd
Description	Sets the batch’s working directory i.e. the directory the batch will execute within.
Arguments
id	A name that uniquely identifies the command in the batch file.
path	The path to the working directory

file Command
File
Description	Identifies a file that is contained within the batch’s working directory. 
Arguments
id	A name that uniquely identifies the command in the batch file.
path	The path to the file including its name and extension. The path will always be evaluated relative to the working directory specified by the ‘wd’ command. 
	
cmd Command
cmd
Description	A command that will be executed in a process. 
Arguments
id	A name that uniquely identifies the command in the batch file.
path	The path to the executable. If the path is relative, it will use the system’s executable PATH to locate the executable file. 
args	This is a string that contains the arguments that will be passed to the executable specified by the ‘path’ option. 
in	This is the ID of the file (specified in a file command) that will be directed to the executable process’s stdin stream.  
out	This is the ID of the file (specified in a file command) that will be directed to the executable process’s stdout stream. 

pipe Command
Pipe implements connection between two processes (P1 | P2) where the output from P1 is directed (piped) to the input of P2. So this command requires two subcommands that will be executed concurrently. 
pipe
Description	Pipe is an interconnection between two processes (cmd). The two cmd’s identified 
Arguments
id	A name that uniquely identifies the pipe in the batch file.
Cmd Element	Two CMD elements that define the P1 and P2 described above. Both commands will be executed concurrently with P1 stdout copied to P2 stdin. 

Example
BatchFile:

<batch>
  <wd id='swd1' path="work" />
  <file id="file1" path="numberdata.txt" />
  <file id="file2" path="avgout1.txt" />
  <pipe>
    <cmd id="addLines" path="java.exe" args="-jar addLines.jar" in='file1' />
    <cmd id="avgFile" path="java.exe" args="-jar avgFile.jar" out='file2' />
  </pipe>
</batch>

Outpt:
Parsing wd
Parsing file
Parsing cmd
Parsing file
Parsing cmd
Parsing pipe
The working directory will be set to work
File Command on file: numberdata.txt
Command: addLines
addLines Deferring Execution
File Command on file: avgout1.txt
Command: avgFile
avgFile Deferring Execution
Pipe Command
Waiting for cmd1 to exit
cmd1 has exited
Waiting for cmd2 to exit
cmd2 has exited
Finished Batch
