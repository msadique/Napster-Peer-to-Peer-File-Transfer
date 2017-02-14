/*******************************************************************************
 *  FILE NAME    : Server.Java
 *
 *  DESCRIPTION  : This class contains the main() function, it generate a server thread 
				  for processing different kinds requests from the clients. It calls 
				  the run() function which generate server thread and creates 
				  ServerThread.java object for processing client request.
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class Server {
	
	public static int ServerPort = 45678;	   //Port no for server and client connection.
	public final static ArrayList<cRequest> peerList = new ArrayList<cRequest>();  // holds information for all connected clients
	public static ArrayList<String> filenames = new ArrayList<String>();			// holds information for all file names registered by clients
	
	/*store the socketSock for server*/
	ServerSocket ServerSock;
	
	// Main Thread to invoke serverthread
	public static void main( String[] args )
	{
		Server.ServerPort = Integer.parseInt(args[0]);
		Server server = new Server();  
		server.run();
	}
		
	public void run()
	{
		try 
		{
			ServerSock = new ServerSocket( ServerPort );  // creates server socket 
			System.out.println(" Server listening on port "+ServerPort);
			while( true )			// this loop run to accept client connection 
			{
				Socket clientSoc = ServerSock.accept();   // when any client connects to server it invokes server thread
				ServerThread thread = new ServerThread( clientSoc );
				thread.start();
			}	
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}

