==================================================================================================================================//
ASSIGNMENT-1

Design a simple P2P system that has two components:
1. A central indexing server:-  This server indexes the contents of all of the peers that
register with it. It also provides search facility to peers. In our simple version, you don't
need to implement sophisticated searching algorithms; an exact match will be fine.
Minimally, the server should provide the following interface to the peer clients:
o registry(peer id, file name, ...) -- invoked by a peer to register all its files with the
indexing server. The server then builds the index for the peer. Other sophisticated
algorithms such as automatic indexing are not required, but feel free to do
whatever is reasonable. You may provide optional information to the server to
make it more 'real', such as the clients’ bandwidth, etc.
o lookup(file name) -- this procedure should search the index and return all the
matching peers to the requestor.

2. A peer :-  A peer is both a client and a server. As a client, the user specifies a file name
with the indexing server using "lookup". The indexing server returns a list of all other
peers that hold the file. The user can pick one such peer and the client then connects to
this peer and downloads the file. As a server, the peer waits for requests from other peers
and sends the requested file when receiving a request.
Minimally, the peer server should provide the following interface to the peer client:
o retrieve(file name) -- invoked by a peer to download a file from another peer.
========================================================================================================================================//


List of files used to complete the task:
________________________________________

Server:-
1. Server.java  
2. ServerThread.java  
3. cRequest.java  
4. cResponse.java
5. RequestType.java
6. makefile    

Client:-
1. Client.java
2. peerClientThread.java  
3. peerServerThread.java  
4. cRequest.java  
5. cResponse.java      
6. RequestType.java  
7. updateThread.java
8. makefile


=========================================================================================================================================//


Installation instructions:
_________________________


1)For compile Server

Compile & Run : make         // port No.(45678) is inbuilt in makefile

	  	or

Compile : javac *.java
Run 	: java Server 45678      //45678			


2)For compile Client

Compile & Run : make         // java Client 11111 client1 35.164.243.127 45678    all are inbuilt in makefile
	
		or

Compile : javac *.java
Run 	: java Client 11111 client1 35.164.243.127 45678      //Input Format : java Client <Client Port> <Client Name> <Server Ip> <Server port>			


==========================================================================================================================================//

File descriptions: 
Server Files:- 

1) Server.java :   This class contains the main() function, it generate a server thread for processing different kinds requests from the clients. 
				It calls the run() function which generate server thread and creates ServerThread.java object for processing client request.

2) ServerThread.java : This class uses ObjectInputStream ad other class objects to process the RequestType of the Client. 
•	It register the files of a requesting client on the server side when RequestType is request. It also checks redundancy by rejecting the same 
	request from the same client if no changes has been made.
•	If the RequestType is RequestPeerList then it uses the filename passed to the server with the request object to find out which all peer has 
	the same file and return the peer list to the client.
•	If the RequestType is unregister then this class unregister the files of the related peer which made the unregister request.
•	If the RequestType is RequestUpdate then it first unregister the peer making request and the register fresh update on the server side.
•	If the RequestType is RequestAllFiles then it return all the files related to the peer’s request.
•	boolean unregisterClient(int clientPort) : This function is used to unregister Client using the client port number.
•	void sendObject(Response response, boolean fromServer) : It bundle the response from the server into an object and then send it to the requesting client.

3) Request.java : Implements Serializable java package, have following functions :-
•	public final InetAddress getIpAddr() : Returns IP-Address InetAddress object which is set by void setIpAddr(InetAddress ipAddr)  function.
•	void setIpAddr(InetAddress ipAddr) : Set InetAddress object IP-Address using the passed parameter ipAddr.
•	int getPort() : Returns the port number.
•	void setPort(int port) : Sets the port number to the port value passed to the function.
•	String getHostName() : Return HostName as string value.
•	void setHostName(String hostName) : Set HostName value with the parameter string value hostName.
•	String getFileName() : Return the name of the filename set by void setFileName(String fileName) function.
•	RequestType getRequestType() : Return RequestType enum value identifying the type of request made by peers.
•	void setRequestType(RequestType requestType) : Set the RequestType enum value identifying the type of request made by peers.
•	boolean isDownload() : Return boolean value related to whether Peer wants to download the requested file.
•	void setDownload(boolean download) : Set boolean value for download variable.
•	ArrayList<String> getFilenames() : Return ArrayList of all the filenames for processing
•	void setfilenames(ArrayList<String> filenames) : Set filenames into a ArrayList.

4) RequestType.java : enum RegisterType having some values 

5) Response.java : Implements Serializable java package, having following functions :-
•	String getMessage() : This function return a message related to response.
•	void setMessage(String message) : This function set the message to some string value passed as a parameter.
•	boolean isSuccess() : this function return true if a communication between Client and server is a successful one else it return false.
•	void setSuccess(boolean success) : This function set the success boolean variable equals to the boolean value passed to the function as the 
	parameter confirming successful communication or not.
•	ArrayList<Request> getPeerList() : This function returns a string array list with all the peer information in it.
•	void setPeerList(ArrayList<Request> peerList) : This function set a string array list with peer information.
•	boolean isfileExists() : This function checks whether a particular file exist or not.
•	void setfileExits(boolean fileExists) : This function set boolean fileExists variable to true if a particular file exists else it set the variable to false.

Client Files:- 
1) Client.java : This class has the Client side main() function, which intern class updateThread Class object to keep checking the status of the files present at 
				 the Client associated and update automatically when there is a change in the file system. It also create peerClientThread and peerServerThread class   
				 object to take and process the client request.

2) UpdateThread.java : This class invoke it’s constructor when it’s object is created, a value one second is passed for checking the status of the file at 
						the client side whether it has undergone any change. If it has undergone any change then it update the file status at the server side as well.

3) PeerClientThread.java : This class take the client requestType and calls related functions :-
•	run() : This function accept the client request and call the respective function.s
•	Register() : This function register the files in the client-side at the server-side.
•	Unregister() : This function unregister the files of the particular client at the server-side.
•	RequestFilelist(): This function access all the file at the server.
•	RequestPeerlist(): This function access all the peer names and information associated with it from the server.
•	SendObject2Server(): This function use object ObjectOutputStream to send related object to the server-side.
•	SendObject2Client() :This function use object ObjectOutputStream to send related object to the client-side.\
•	receiveFile() : This function receive the file and its content from the client which has the reuested file.

4) PeerServerThread.java : This class is used to make client act as a server by communicating with other client member in the connected cluster. It has following functions :-
•	run() : This function accept the filename requested from requesting client and then process on the file depending upon the requestType parameter. 
•	DoesFileExists: This function set the boolean variable fileExist to True or False depending upon whether the file requested exists in the client or not.
•	SendFile() : This function return the file to the requesting client.
•	ReadRequestObject(): This function read the object related to the communication between the requesting client and processing client.

5) Request.java : Implements Serializable java package, have following functions :-
•	public final InetAddress getIpAddr() : Returns IP-Address InetAddress object which is set by void setIpAddr(InetAddress ipAddr)  function.
•	void setIpAddr(InetAddress ipAddr) : Set InetAddress object IP-Address using the passed parameter ipAddr.
•	int getPort() : Returns the port number.
•	void setPort(int port) : Sets the port number to the port value passed to the function.
•	String getHostName() : Return HostName as string value.
•	void setHostName(String hostName) : Set HostName value with the parameter string value hostName.
•	String getFileName() : Return the name of the filename set by void setFileName(String fileName) function.
•	RequestType getRequestType() : Return RequestType enum value identifying the type of request made by peers.
•	void setRequestType(RequestType requestType) : Set the RequestType enum value identifying the type of request made by peers.
•	boolean isDownload() : Return boolean value related to whether Peer wants to download the requested file.
•	void setDownload(boolean download) : Set boolean value for download variable.
•	ArrayList<String> getFilenames() : Return ArrayList of all the filenames for processing
•	void setfilenames(ArrayList<String> filenames) : Set filenames into a ArrayList.

6) RequestType.java : enum RegisterType having some values related to the type of request client initiates.

7) Response.java : Implements Serializable java package, having following functions :-
•	String getMessage() : This function return a message related to response.
•	void setMessage(String message) : This function set the message to some string value passed as a parameter.
•	boolean isSuccess() : this function return true if a communication between Client and server is a successful one else it return false.
•	void setSuccess(boolean success) : This function set the success boolean variable equals to the boolean value passed to the function as the 
	parameter confirming successful communication or not.
•	ArrayList<Request> getPeerList() : This function returns a string array list with all the peer information in it.
•	void setPeerList(ArrayList<Request> peerList) : This function set a string array list with peer information.
•	boolean isfileExists() : This function checks whether a particular file exist or not.
•	void setfileExits(boolean fileExists) : This function set boolean fileExists variable to true if a particular file exists else it set the variable to false.

Files :
•	Input_files : This folder contains all the documented files at the client side and this folder is accessible by the Java classes at the client side

•	Output_files : This folder contains the requested file after receiving it from other peers.

