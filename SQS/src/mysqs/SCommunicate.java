package mysqs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SCommunicate {
	public String nodeName;
	public int port;
	public String command;
	
	public SCommunicate(){
		this.nodeName = "null";
		this.port = -1;
		this.command = "null";
	}
	public SCommunicate(String nodeName, int port, String command){
		this.nodeName = nodeName;
		this.port = port;
		this.command = command;
	}
	public String toString(){
		String s="";
		s += "nodeName: "+this.nodeName
		+" port: "+this.port+" command: "
		+this.command;
		return s;
	}
	public String communicate(){
		String result = "";
		InetAddress addr;
		DataInputStream bis;
		DataOutputStream dos;
		try {
			addr = InetAddress.getByName(this.nodeName);
			Socket socket = new Socket(addr, this.port);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(this.command);
			dos.flush();
			
			bis = new DataInputStream(socket.getInputStream());
			result = bis.readUTF();
			socket.close();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("get by name fail");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.out.println("create socket fail");
			//do nothing, server down
		}
		
		
		return result;
	}
}
