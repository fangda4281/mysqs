package myclinet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
//5 node
//communicate with other servers
public class SClient {
	public Dest[] destList;
	
	public SClient(){
		this.destList = new Dest[5];//5 nodes
		this.destList[0] = new Dest("terry-Vostro-220s-Series", 10000);
		this.destList[1] = new Dest("terry-Vostro-220s-Series", 10001);
		this.destList[2] = new Dest("terry-Vostro-220s-Series", 10002);
		this.destList[3] = new Dest("terry-Vostro-220s-Series", 10003);
		this.destList[4] = new Dest("terry-Vostro-220s-Series", 10004);
		/*
		this.destList[0] = new Dest("boffi-TW8-SW8-DW8", 10000);
		this.destList[1] = new Dest("boffi-TW8-SW8-DW8", 10001);
		this.destList[2] = new Dest("boffi-TW8-SW8-DW8", 10002);
		this.destList[3] = new Dest("boffi-TW8-SW8-DW8", 10003);
		this.destList[4] = new Dest("boffi-TW8-SW8-DW8", 10004);
		*/
	}
	public SClient(String nodeName){
		this.destList = new Dest[5];//5 nodes
//		for(int i=0; i < 5; i++){
//			destList[i] = new Dest(nodeName, 10000+i);
//		}
		this.destList[0] = new Dest(nodeName, 10000);
		this.destList[1] = new Dest(nodeName, 10001);
		this.destList[2] = new Dest(nodeName, 10002);
		this.destList[3] = new Dest(nodeName, 10003);
		this.destList[4] = new Dest(nodeName, 10004);
	}
	public int findServer(int numOfServ){
		int pos = 0;
		//pos = Math.abs(System.nanoTime())%numOfServ;
		pos = (int)((Math.random()*numOfServ+1)%numOfServ);
		return pos;
	}
	public String communicate(String cmd, String nodeName, int port){
		
		String head = "c#";
		cmd = head+cmd;//"c#"+cmd
		
		String result="";
		DataInputStream bis;
		DataOutputStream dos;
		try {
			InetAddress addr;
			addr = InetAddress.getByName(nodeName);
			//System.out.println(addr);
			Socket socket = new Socket(addr,port);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(cmd);
			dos.flush();
			
			bis = new DataInputStream(socket.getInputStream());
			result = bis.readUTF();

			socket.close();
		} catch (UnknownHostException e) {
			//e.printStackTrace();
			System.out.println("unknowhost exception");
		} catch (IOException e) {//deal with fail to connect
			//System.out.println("server is not exist");
			//do nothing, if kill a server, client may be access here
		}
		
		return result;
	}
	public boolean CreateQueue(String QueueName){
		boolean flag = false;
		String result = "";
		String command = "CreateQueue";
		command += "#"+QueueName;
		for(int i =0;i<5;i++){
			String tnodeName = this.destList[i].nodeName;
			int tport = this.destList[i].port;
			result = this.communicate(command, tnodeName, tport);
		}
		
		//System.out.println(result);
		if(result.equals("true") ){
			flag = true;
		}
		return flag;
	}
	public Vector ListQueue(){
		String command = "ListQueue";
		command += "#";
		String result = "";
		String tnodeName = "";
		int tport = 0;
		int pos = this.findServer(5);
		for(int i=0; i<5;i++){
			tnodeName = this.destList[pos].nodeName;//!!!
			tport = this.destList[pos].port;//!!!
			result = this.communicate(command, tnodeName, tport);
			pos = (pos+1)%5;
			if(!result.equals("")){
				break;
			}
		}
		
		Vector vqnamelist = new Vector();
		String[] qnamelist;
		//:~
		//System.out.println("ListQueue result: "+result);
		qnamelist = result.split("#");
		for(int i=0; i < qnamelist.length; i++){
			vqnamelist.add(qnamelist[i]);
		}
		return vqnamelist;
	}
	public boolean DeleteQueue(String QueueName){
		boolean flag = false;
		String result = "";
		String command = "DeleteQueue";
		command += "#"+QueueName;
		for(int i =0;i<5;i++){
			String tnodeName = this.destList[i].nodeName;
			int tport = this.destList[i].port;
			result = this.communicate(command, tnodeName, tport);
		}
		//System.out.println(result);
		if(result.equals("true") ){
			flag = true;
		}
		return flag;
	}
	public boolean SendMessage(String QueueName, String Message){
		boolean flag = false;
		String command = "SendMessage";
		String result = "";
		String tnodeName = "";
		int tport = 0;
		command += "#"+QueueName+"#"+Message;
		int pos = this.findServer(5);
//		pos = pos%5;
		for(int i=0;i<5;i++){
			tnodeName = this.destList[pos].nodeName;//!!!
			tport = this.destList[pos].port;//!!!
			result = this.communicate(command, tnodeName, tport);
			if(!result.equals("")){
				break;
			}
			pos = (pos+1)%5;
		}
		
		//System.out.println(result);
		if(result.equals("true") ){
			flag = true;
		}
		return flag;
	}
	public Vector ReceiveMessage(String QueueName){
		String result = "";
		String command = "ReceiveMessage";
		command += "#"+QueueName;
		int pos = this.findServer(5);
		//result = this.communicate(command);
		for(int i =0;i<5;i++){
			String tnodeName = this.destList[pos].nodeName;
			int tport = this.destList[pos].port;
			result = this.communicate(command, tnodeName, tport);
			pos = (pos+1)%5;
			if(!(result.equals("-1") || result.equals(""))){
				break;
			}
		}
		//System.out.println(result);
		Vector v = new Vector();
		Vector vException = new Vector();
		String[] slist;
		slist = result.split("#");
		for(int i=0; i < slist.length; i++){
			v.add(slist[i]);
		}
		
		//deal with exception
		vException.add("-1");
		vException.add("-1");
		if(v == null || v.size() == 0){
			v = vException;
		}else if(v.get(0).equals("")){
			v = vException;
		}
		return v;
	}
	public boolean DeleteMessage(String MessageID){
		String tresult = "";
		String result = "";
		boolean flag = false;
		String command = "DeleteMessage";
		command += "#"+MessageID;
		//result = this.communicate(command);
//		int pos = this.findServer(5);
//		pos = pos%5;
		for(int i =0;i<5;i++){
			String tnodeName = this.destList[i].nodeName;
			int tport = this.destList[i].port;
			tresult = this.communicate(command, tnodeName, tport);
			if(tresult.equals("true") ){
				result = "true";
			}
			//pos = (pos+1)%5;
		}
		//System.out.println(result);
		if(result.equals("true") ){
			flag = true;
		}
		return flag;
	}
	

}
