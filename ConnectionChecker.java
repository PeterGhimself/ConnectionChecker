import java.io.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

public class ConnectionChecker {

	public static boolean pingHost(String ipAddress) throws UnknownHostException, IOException{
	    InetAddress inet = InetAddress.getByName(ipAddress);

	    return inet.isReachable(5000);
	}

	public static boolean pingLocalHost() throws UnknownHostException, IOException{
		InetAddress inet = InetAddress.getLocalHost();

		return inet.isReachable(5000);
	}

	public static void main(String[] args){

	    String google = "google.com"; //Could be any IP Address on your network / Web
	    File logFile = new File("logFile.txt");

	    FileWriter fileWriter = null;
	    BufferedWriter bufferedWriter = null;
	    PrintWriter out = null;

	    try{
	    	fileWriter = new FileWriter(logFile, true);
	        bufferedWriter = new BufferedWriter(fileWriter);
	        //closing this is problematic so we'll have to do without that for now...
	        out = new PrintWriter(bufferedWriter);
	    }
	    catch (FileNotFoundException e1) {
	    	System.out.println("Error, could not create file.");
			out.println("Error, could not create file.");
			e1.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}

	    long lastTime = System.currentTimeMillis();

	    boolean internetConnected = false, lastStatus = true;

	    INFINITE_LOOP:
	    while(true){
	    	long newTime = System.currentTimeMillis();

//	    	pingHost(localHost);

	    	if(newTime - lastTime > 10000){

	    		if(lastStatus != internetConnected){
	    			try{
	    				System.out.println("lastStatus = "+lastStatus+"\ninternetConnected = "+internetConnected);
	    				out.println("lastStatus = "+lastStatus+"\ninternetConnected = "+internetConnected);

	    				Date date = new Date();

	    				System.out.println(new Timestamp(date.getTime()));
	    				out.println(new Timestamp(date.getTime()));

	    				System.out.println("Sending Ping Request to this computer's IP address");
	    				out.println("Sending Ping Request to this computer's IP address");

	    				System.out.println(pingLocalHost() ? "Host is reachable" : "Host is NOT reachable");
	    				out.println(pingLocalHost() ? "Host is reachable" : "Host is NOT reachable");

	    				System.out.println("Sending Ping Request to " + google);
	    				out.println("Sending Ping Request to " + google);

	    				System.out.println(pingHost(google) ? "Host is reachable" : "Host is NOT reachable");
	    				out.println(pingHost(google) ? "Host is reachable" : "Host is NOT reachable");

	    				System.out.println("-----------------------------------------");
	    				out.println("-----------------------------------------");

	    				if(pingHost(google)){
	    					internetConnected = true;
	    				}else{
	    					internetConnected = false;
	    				}
	    			}catch(UnknownHostException e){
	    				internetConnected = false;
	    				lastStatus = internetConnected;

	    				Date date = new Date();

	    				System.out.println(new Timestamp(date.getTime()));
	    				out.println(new Timestamp(date.getTime()));

	    				System.out.println("UnknownHostException");
	    				out.println("UnknownHostException");

	    				System.out.println("-----------------------------------------");
	    				out.println("-----------------------------------------");
	    			}catch(IOException e){
	    				e.printStackTrace();
	    			}
	    		}try{
	    			if(pingHost(google) && internetConnected == false){
	    				internetConnected = true;
	    				lastStatus = false;

	    			}else if(pingHost(google) == false && internetConnected){
	    				internetConnected = false;
	    				lastStatus = true;
	    			}else{
	    				lastStatus = internetConnected;
	    			}
	    		}catch(UnknownHostException e){
	    			internetConnected = false;
	    			lastStatus = internetConnected;
	    		}
	    		catch(IOException e){
	    			e.printStackTrace();
	    		}
	    		lastTime = newTime;
	    		out.flush();
	    	}
	    }
	}
}