package mysqs;

public class IndexPair {
	public int server1;
	public int server2;
	public IndexPair(){
		this.server1 = -1;
		this.server2 = -1;
	}
	public String toString(){
		String s = "";
		s += "server1: "+this.server1
		+" server2: "+this.server2;
		return s;
	}
	
}
