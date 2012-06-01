package mysqs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	public String nodeName;
	public int port;
	public Dest[] destList;
	public Vector qnameList;//queue name list
	public IHashTable ihTable;
	public SHashTable shTable;
	
	public Server(){
		this.nodeName = "terry-Vostro-220s-Series";
		//this.nodeName = "boffi-TW8-SW8-DW8";
		this.port = 10000;
		this.destList = new Dest[5];
		this.qnameList = new Vector();
		//boffi-TW8-SW8-DW8;
		
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
		this.qnameList = new Vector();
		this.ihTable = new IHashTable();//at most 4 queue 
		this.shTable = new SHashTable();//at most 10 msg
	}
	public Server(String nodeName, int port){
		this.nodeName = nodeName;
		//this.nodeName = "boffi-TW8-SW8-DW8";
		this.port = port;
		this.destList = new Dest[5];
		this.qnameList = new Vector();
		//boffi-TW8-SW8-DW8;
		
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
		this.qnameList = new Vector();
		this.ihTable = new IHashTable();//at most 4 queue 
		this.shTable = new SHashTable();//at most 10 msg
	}
	public String toString(){
		String str = "";
		str += this.qnameList.toString()+"\n";
		str += this.ihTable.toString()+"\n";
		str += this.shTable.toString()+"\n";
		return str;
	}
	public IndexPair getTargetServer(String msgid){
		IndexPair pair = new IndexPair();
		//5 server nodes
		pair.server1 = Math.abs(Integer.parseInt(msgid))%5;
		pair.server2 = Math.abs(Integer.parseInt(msgid)+2)%5;
		return pair;
	}
	public String servMgr(String str){
		String result = "";
		String head = "";//"s" or "c"
		head = str.substring(0, str.indexOf("#"));
		str = str.substring(str.indexOf("#")+1);
		String inCmd = str;
		String v1;
		v1 = str.substring(0, str.indexOf("#"));
		str = str.substring(str.indexOf("#")+1);
		if(v1.equals("ListQueue")){//done
			result = this.doCommand(inCmd);
		}else if(v1.equals("CreateQueue") ){//done
			result = this.doCommand(inCmd);
		}else if(v1.equals("DeleteQueue")){//done
			result = this.doCommand(inCmd);
		}else if(v1.equals("SendMessage")){
			String qName = "";
			String msgContent = "";
			qName = str.substring(0, str.indexOf("#"));//v2 queue name
			str = str.substring(str.indexOf("#")+1);
			

			if(head.equals("s") ){
				msgContent = str.substring(0, str.indexOf("#"));
				str = str.substring(str.indexOf("#")+1);
				String msgid = str;
				boolean localFlag = this.SendMessage(qName, msgContent, msgid);
				if(true == localFlag){
					result = "true";
				}else{
					result = "false";
				}
				//result = this.doCommand(inCmd);
			}else{//head.equals("c")
				msgContent = str;//v3 msg content
				String msgidStr = qName+msgContent+System.nanoTime();
				String msgid = ""+Math.abs(msgidStr.hashCode());//v4 msgid
				//get target server
				IndexPair pair = new IndexPair();
				pair = this.getTargetServer(msgid);
				Dest d1 = new Dest();
				Dest d2 = new Dest();
				d1 = this.destList[pair.server1];
				d2 = this.destList[pair.server2];
				System.out.println("server idx: "+pair.server1+" "+pair.server2);
				if(d1.nodeName.equals(this.nodeName) && (d1.port == this.port) ){
					boolean localFlag = this.SendMessage(qName, msgContent, msgid);
					if(localFlag == true){
						result = "true";
					}else{
						result = "false";
					}
				}else{//send to target server
					String outCmd = "s#"+inCmd+"#"+msgid;
					SCommunicate sc = new SCommunicate(d1.nodeName, d1.port, outCmd);
					result = sc.communicate();
				}
				
				if(d2.nodeName.equals(this.nodeName) && (d2.port == this.port) ){
					boolean localFlag = this.SendMessage(qName, msgContent, msgid);
					if(localFlag == true){
						result = "true";
					}else{
						result = "false";
					}
				}else{//send to target server
					String outCmd = "s#"+inCmd+"#"+msgid;
					SCommunicate sc = new SCommunicate(d2.nodeName, d2.port, outCmd);
					result = sc.communicate();
				}
				
				
				//result = "true";//:~
			}
			
			//result = this.doCommand(inCmd);
			
		}else if(v1.equals("ReceiveMessage")){
			result = this.doCommand(inCmd);
			//to do:may lock other server's same messages
			//anther way fake the function is "delete them"
		}else if(v1.equals("DeleteMessage")){//done,5 nodes will both receive "delete"
			result = this.doCommand(inCmd);
		}
		
		
		
		return result;
	}
	//*****************
	//local functions, only change local data structure
	//*****************
	public boolean CreateQueue(String QueueName){
		boolean flag = false;
		int pos = 0;
		pos = this.ihTable.createQueue(QueueName);
		if(pos != -1){
			this.qnameList.add(QueueName);
			flag = true;
		}
		//System.out.println("pos: "+pos);
		return flag;
	}
	public Vector ListQueue(){
		return this.qnameList;
	}
	public boolean DeleteQueue(String QueueName){
		boolean flag = false;
		flag = this.ihTable.deleteQueue(QueueName);
		if(flag == true){
			this.qnameList.remove(QueueName);
		}
		return flag;
	}
	public boolean SendMessage(String QueueName, String Message, String msgid){
		boolean flag = false;
		SHashCell scell = new SHashCell();
		
//		String msgidStr = QueueName+Message+System.nanoTime();
//		String msgid = ""+Math.abs(msgidStr.hashCode());//construct msgid
		MessageId mId = new MessageId(msgid);
		
		//assign SHashCell
		scell.content = Message;
		scell.msgId = msgid;
		
		flag = this.ihTable.put(QueueName, mId, scell);
		
		if(flag == true){//IHashTable is stored,now store in SHashTable
			flag = this.shTable.put(msgid, scell);
			
		}
		return flag;
	}
	public String ReceiveMessage(String QueueName){//return str contain content and msgid
		boolean flag = false;
		SHashCell cell = new SHashCell();
		String str = "-1";
		MessageId mId = new MessageId();
		flag = this.ihTable.get(QueueName, mId);
		if(flag == true){
			//todo: other server add suo
			flag = this.shTable.get(mId.msgId, cell);
			if(flag == true){
				str = cell.content+"#"+cell.msgId;
			}
		}
		return str;
	}
	public boolean DeleteMessage(String MessageID){
		boolean flag = false;
		SHashCell cell = new SHashCell();
		flag = this.shTable.delete(MessageID, cell);

		if(flag == true){
			//todo: use msgId to delete msg on other server
			int target = -1;
			target = this.ihTable.delete(cell);
			if(target != -1){//not on the top
				int qidx = cell.qidx;
				int midx = cell.midx;
				String tMessageID = this.ihTable.htable[qidx].q.queue[midx].msgId;
				flag = this.shTable.changeMidx(tMessageID , cell.midx);

			}else{//on the top
				flag = true;

			}
		}
		
		return flag;
	}
	public String doCommand(String str){
		String cmd = str.substring(0, str.indexOf("#"));
		String v1 = "";
		String v2 = "";
		String result = "";//return value
		if(cmd.equals("CreateQueue") ){
			str = str.substring(str.indexOf("#")+1);
			v1 = str;
			if(true == this.CreateQueue(v1) ){
				result = "true";
			}else{
				result = "false";
			}
			//System.out.println(result);
		}else if(cmd.equals("ListQueue")){
			for(int i=0; i < this.qnameList.size();i++){
				result += this.qnameList.get(i).toString()+"#";
			}
			if(!result.equals("") ){
				result = result.substring(0, result.length()-1);
			}else{
				result = "noqueue#";
			}
			
		}else if(cmd.equals("DeleteQueue")){
			str = str.substring(str.indexOf("#")+1);
			v1 = str;
			if(true == this.DeleteQueue(v1) ){
				result = "true";
			}else{
				result = "false";
			}
			//System.out.println(result);
		}else if(cmd.equals("SendMessage")){
//			str = str.substring(str.indexOf("#")+1);
//			v1 = str.substring(0, str.indexOf("#") );
//			str = str.substring(str.indexOf("#")+1);
//			v2 = str;
//			if(true == this.SendMessage(v1, v2) ){
//				result = "true";
//			}else{
//				result = "false";
//			}
			//System.out.println(result);	
		}else if(cmd.equals("ReceiveMessage")){
			str = str.substring(str.indexOf("#")+1);
			v1 = str;
			result = this.ReceiveMessage(v1);
			//System.out.println(result);
		}else if(cmd.equals("DeleteMessage")){
			str = str.substring(str.indexOf("#")+1);
			v1 = str;
			if(true == this.DeleteMessage(v1) ){
				result = "true";
			}else{
				result = "false";
			}
			//System.out.println(result);
		}	
		return result;
	}
	//*****************
	//main function
	//*****************
	public void startServer(){
		ServerSocket server = null;
		DataInputStream bis;
		DataOutputStream dos;
		boolean flag = true;
		try {
			System.out.println("server start");
			server = new ServerSocket(this.port);
			while(flag){
				Socket socket = null;
				//System.out.println("mark");
				socket = server.accept();//阻塞
				//System.out.println("accpted");
				bis = new DataInputStream(socket.getInputStream());
				String  str = bis.readUTF();
				//System.out.println(str);
				
				String resultStr;//=str;
				//doCommand() function only change local datastructure
				//resultStr = this.doCommand(str);
				resultStr = this.servMgr(str);
				
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(resultStr);
				dos.flush();
				
				//System.out.println("call close");
				socket.close();
			}
			server.close();
		} catch (IOException e) {
			System.out.println("IOException e");
		}
	}

}
