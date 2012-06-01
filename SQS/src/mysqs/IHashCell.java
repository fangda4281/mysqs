package mysqs;

public class IHashCell {
	public int status;//0empty 1active 2deleted
	public String qName;
	public Squeue q;
	
	public IHashCell(){
		this.status = 0;//0empty
		this.qName = "null";
		this.q = new Squeue();//default constructor does 
							//not assign size, so is safe
	}
	public IHashCell(String newName){
		this.status = 0;
		this.qName = newName;
		this.q = new Squeue();					
	}
	public String toString(){
		String s = "";
		s = "qname:"+this.qName+" status:"+this.status;
		return s;
	}
}
