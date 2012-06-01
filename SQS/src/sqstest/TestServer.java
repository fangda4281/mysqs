package sqstest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import mysqs.*;
public class TestServer {
	public void test() throws IOException{
		String nodeName = "";
		int port = 0;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("enter node name: ");
		nodeName = stdin.readLine();
		System.out.print("enter port: ");
		String sport = stdin.readLine();
		port = Integer.parseInt(sport);
		Server s = new Server(nodeName, port);
		s.startServer();
	}
}
