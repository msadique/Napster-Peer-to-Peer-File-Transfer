/*******************************************************************************
 *  FILE NAME    : Server.Java
 *
 *  DESCRIPTION  : This class has the Client side main() function, which intern class 
				   updateThread Class object to keep checking the status of the files 
				   present at the Client associated and update automatically when 
				   there is a change in the file system. It also create peerClientThread 
				   and peerServerThread class   object to take and process the client request.
				   This class contains the main() function, it generate a server thread 
				  
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Client {
	protected static int ServerPort;
	// Can be changes to the actual Server IP Address.
	protected static String ServerIpAddress;
	protected static int ClientPort;
	protected static String ClientName;
	public final static ArrayList<String> filenames = new ArrayList<String>( );  //holds all information 	
	static ServerSocket ClientServerSock;
	static Socket requestClientSoc;
	Socket socket;
	cResponse serverResponse;
	cResponse peerResponse;
	// made request and response public 
	cRequest request;
	cRequest otherPeer;
	
	//boolean peerConversation=false;
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("Input Format : java Client <Client Port> <Client Name> <Server Ip> <Server port>");
		Client.ClientPort = Integer.parseInt( args[ 0 ] );
		Client.ClientName = args[ 1 ];
		Client.ServerIpAddress = args[ 2 ];
		Client.ServerPort = Integer.parseInt( args[ 3 ] );
		try 
		{
			peerClientThread requestThread = new peerClientThread();
			new updateThread(1);
        	requestThread.start();
			
			ClientServerSock = new ServerSocket(ClientPort);
			System.out.println("\n Client listening on "+ClientPort);
			while( true )
			{ //wait for connection
				requestClientSoc=ClientServerSock.accept();
				peerServerThread thread=new peerServerThread(requestClientSoc, ClientName);
				thread.start();
			}
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
		
	public void run() throws IOException 
	{
	}
}
