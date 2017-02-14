/*******************************************************************************
 *  FILE NAME    : updateThread.java
 *
 *  DESCRIPTION  : his class invoke its constructor when its object is created, 
 a value one second is passed for checking the status of the file at the client side whether 
 it has undergone any change. If it has undergone any change then it update the file status at the server side as well. 
 *
 *
 ******************************************************************************/


/*******************************************************************************
 *          HEADER FILES
 ******************************************************************************/

import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;



public class updateThread {
	Timer timer;
	public static ArrayList<String> filenames =new ArrayList<String>();
	public static ArrayList<String> tfilenames =new ArrayList<String>();
	public static Path currentRelativePath = Paths.get("");
	public static String cpath = currentRelativePath.toAbsolutePath().toString();
    
	public updateThread(int seconds) {    //in constructor make list of files in input folder
		File folder = new File(cpath+"/input_files");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
        	if (listOfFiles[i].isFile()) {
        		filenames.add((String)listOfFiles[i].getName());
            }
        }
        Set<String> hs = new HashSet<>();    //sort and remove duplicates using hashset class
        hs.addAll(filenames);
        filenames.clear();
        filenames.addAll(hs);
        timer = new Timer();
        timer.schedule(new RemindTask(),0, seconds*1000);
	}

	// this class runs in every second set by client
    class RemindTask extends TimerTask {
    	public void run() {
    		try{
    			tfilenames.clear();
                cRequest request = new cRequest();                           // create temporary file list 
                File folder = new File(cpath+"/input_files");
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                	if (listOfFiles[i].isFile()) {
                		tfilenames.add((String)listOfFiles[i].getName());
                    }
                }
                Set<String> hs = new HashSet<>();  //remove duplicate and sort 
                hs.addAll(tfilenames);
                tfilenames.clear();
                tfilenames.addAll(hs);
                int flag=0;
                if(filenames.size()!= tfilenames.size())    //match files 
                	flag=1;
                if(flag!=1)
                	for(int i =0;i<filenames.size();i++)
                	{
                		if(!filenames.get(i).equals(tfilenames.get(i)))
                		{ 
                			flag=1;
                			break;
                		}
                	}
                if(flag==1)                 // if any changes done in folder then send update request to server
                {
                	try{
                		request.setIpAddr(InetAddress.getLocalHost());
                	}
                	catch (Exception e)
                	{
                        e.printStackTrace();
                	}
                	request.setPort(Client.ClientPort);
                	request.setHostName(Client.ClientName);
                	request.setRequestType(RequestType.RequestUpdate);
                	request.setfilenames(tfilenames);
                	try
                	{
                        Socket socket = new Socket(Client.ServerIpAddress,Client.ServerPort);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(request);
                	}
                	catch(Exception e)
                	{
                        System.out.println(e.getMessage());
                	}
                	System.out.println("Update");
                	filenames = tfilenames;	
                }
    		}
            catch (Exception e)
            {
            	e.printStackTrace();
            }
        }
    }
}
