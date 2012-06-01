package myclinet;
import java.io.*;
import java.net.*;

public class Myclient {
	public void test(){
		DataInputStream bis;
		DataOutputStream dos;
		try {
			InetAddress addr = InetAddress.getByName("terry-Vostro-220s-Series");
			int port = 10000;
			//System.out.println(addr);
			Socket socket = new Socket(addr,port);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("helo");
			dos.flush();
			
			bis = new DataInputStream(socket.getInputStream());
			String str = bis.readUTF();
			System.out.println(str);

			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {//处理socket连接失败(不存在server)
			System.out.println("server is not exist");
			//e.printStackTrace();
		}
		
	}
}
