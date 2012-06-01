package mysqs;
//server address
public class Dest {
	public String nodeName;
	public int port;
	public Dest(){
		this.nodeName = "null";
		this.port = 10000;
	}
	public Dest(String nodeName, int port){
		this.nodeName = nodeName;
		this.port = port;
	}
	public String toString(){
		String info = "";
		info = "node: "+this.nodeName
		+" port:"+this.port;
		return info;
	}
}
