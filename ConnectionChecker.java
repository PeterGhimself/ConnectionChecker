package pingCheck;

import java.io.IOException;
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

	    String google = "google.com";
	    String myIPaddress = "192.168.2.13";
	    
	    long lastTime = System.currentTimeMillis(); 
	    
	    boolean internetConnected = false, lastStatus = true;
	    
	    INFINITE_LOOP:
	    while(true){
	    	long newTime = System.currentTimeMillis();
	    	
	    	if(newTime - lastTime > 5000){
	    		
	    		if(lastStatus != internetConnected){
	    			try{
	    				System.out.println("lastStatus = "+lastStatus+"\ninternetConnected = "+internetConnected);
	    				Date date = new Date();	
	    				System.out.println(new Timestamp(date.getTime()));
	    				System.out.println("Sending Ping Request to " + myIPaddress);
	    				System.out.println(pingLocalHost() ? "Host is reachable" : "Host is NOT reachable");
	    				System.out.println("Sending Ping Request to " + google);   
	    				System.out.println(pingHost(google) ? "Host is reachable" : "Host is NOT reachable");
	    				System.out.println("-----------------------------------------");
	    				
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
	    				System.out.println("UnknownHostException");
	    				System.out.println("-----------------------------------------");
	    			}catch(IOException e){
	    				e.printStackTrace();
	    			}
	    		}try{
	    			if((pingLocalHost() || pingHost(google)) && internetConnected == false){
	    				internetConnected = true;
	    				lastStatus = false;
	    				
	    			}else if((pingLocalHost() || pingHost(google)) == false && internetConnected){
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
	    	}
	    }
	}
}
