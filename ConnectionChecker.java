package pingCheck;
import java.io.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

public class ConnectionChecker {
	public static boolean pingHost(String ipAddress) throws UnknownHostException, IOException {
	    InetAddress inet = InetAddress.getByName(ipAddress);

	    return inet.isReachable(5000);
	}

	public static boolean pingLocalHost() throws UnknownHostException, IOException {
		InetAddress inet = InetAddress.getLocalHost();

		return inet.isReachable(5000);
	}
	
	public static boolean isWindows(String os) {

		return (os.toLowerCase().indexOf("win") >= 0);

	}

	public static boolean isMac(String os) {

		return (os.toLowerCase().indexOf("mac") >= 0);

	}

	public static boolean isUnix(String os) {

		return (os.toLowerCase().indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0 );

	}

	public static boolean isSolaris(String os) {

		return (os.toLowerCase().indexOf("sunos") >= 0);

	}
	
	public static void runSystemCommand(String command) {
		try {
			Process p = Runtime.getRuntime().exec(command);
		    BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    
		    String s = "";
		    // reading output stream of the command
	 	    while ((s = inputStream.readLine()) != null) {
	 	    	System.out.println(s);
	 	    }
	 
	 	    } catch(Exception e) {
	 	        e.printStackTrace();
	 	    }
	 }
	
	public static String determineOS() {
		// Determine underlying OS
		String currentOS = System.getProperty("os.name");
		
		// 4 cases
		if (isWindows(currentOS)) {
			return "windows";
		} else if (isMac(currentOS)) {
			return "mac";
		} else if (isUnix(currentOS)) {
			return "unix";
		} else if (isSolaris(currentOS)) {
			return "solaris";
		}
		
		return "";
	}
	
	public static void troubleShoot(String currentOS) {
		if (isWindows(currentOS)) {
			// need a command to elevate privileges before running these commands
			// closest thing to sudo/su on linux? runas command doesn't seem to help but maybe is an option
			// http://stackoverflow.com/questions/606820/is-there-a-java-library-to-access-the-native-windows-api
			// Java Native Access provides Java programs easy access to native shared libraries without using the Java Native Interface. 
			// JNA's design aims to provide native access in a natural way with a minimum of effort. No boilerplate or generated glue code 
			// is required. 
			
			runSystemCommand("netsh interface set interface name =\"Wireless Network Connection 2\" admin=disabled");
			runSystemCommand("netsh interface set interface name =\"Wireless Network Connection 2\" admin=enabled");
		} else if (isMac(currentOS)) {
	
		} else if (isUnix(currentOS)) {
		
		} else if (isSolaris(currentOS)) {
	
		}
	}
	
	public static void main(String[] args) {
		String google = "google.com"; // Could be any IP Address on your network/web
	    File logFile = new File("logFile.txt");
	    
	    FileWriter fileWriter = null;
	    BufferedWriter bufferedWriter = null;
	    PrintWriter out = null;

	    try {
	    	fileWriter = new FileWriter(logFile, true);
	        bufferedWriter = new BufferedWriter(fileWriter);
	        // closing this is problematic so we'll have to do without that for now...
	        out = new PrintWriter(bufferedWriter);
	    }
	    catch (FileNotFoundException e1) {
	    	System.out.println("Error, could not create file.");
			out.println("Error, could not create file.");
			e1.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}

	    long lastTime = System.currentTimeMillis();

	    boolean internetConnected = false, lastStatus = true;

	    // INFINITE LOOP
	    while(true) {
	    	long newTime = System.currentTimeMillis();

	    	if (newTime - lastTime > 10000) {

	    		if (lastStatus != internetConnected) {
	    			try {
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

	    				if (pingHost(google)) {
	    					internetConnected = true;
	    				} else {
	    					internetConnected = false;
	    					System.out.println("Attempting to reconnect");
	    					out.println("Attempting to reconnect");
	    					troubleShoot(determineOS());
	    				}
	    			} catch (UnknownHostException e){
	    				internetConnected = false;
	    				lastStatus = internetConnected;

	    				Date date = new Date();

	    				System.out.println(new Timestamp(date.getTime()));
	    				out.println(new Timestamp(date.getTime()));

	    				System.out.println("UnknownHostException");
	    				out.println("UnknownHostException");
	    				
    					System.out.println("Attempting to reconnect");
    					out.println("Attempting to reconnect");
    					troubleShoot(determineOS());

	    				System.out.println("-----------------------------------------");
	    				out.println("-----------------------------------------");
	    			} catch (IOException e){
	    				e.printStackTrace();
	    			}
	    		} 
	    		try {
	    			if (pingHost(google) && internetConnected == false) {
	    				internetConnected = true;
	    				lastStatus = false;

	    			} else if (pingHost(google) == false && internetConnected) {
	    				internetConnected = false;
	    				lastStatus = true;
    					
	    				System.out.println("Attempting to reconnect");
    					out.println("Attempting to reconnect");
    					troubleShoot(determineOS());
	    			} else {
	    				lastStatus = internetConnected;
	    			}
	    		} catch(UnknownHostException e) {
	    			internetConnected = false;
	    			lastStatus = internetConnected;
					
	    			System.out.println("Attempting to reconnect");
					out.println("Attempting to reconnect");
					troubleShoot(determineOS());
	    		} catch(IOException e) {
	    			e.printStackTrace();
	    		}
	    		lastTime = newTime;
	    		out.flush();
	    	}
	    }
	}
}