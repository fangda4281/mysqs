package mysqs;

public class SHashCell {
	public int status;//0empty 1active 2deleted (3received)
	public String msgId;//hash key
	public String content;
	public int qidx;//queue index
	public int midx;//message index
	
	public SHashCell(){
		this.status = 0;//empty
		this.msgId = "null";
		this.content = "null";
		this.qidx = -1;
		this.midx = -1;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public String toString(){
		String s = "";
		s = this.msgId+" "+this.content+" "+this.status
				+" "+this.qidx+" "+this.midx;
		return s;
	}
	//assign by value
	public void assign(SHashCell cell){
		this.status = cell.status;
		this.msgId = cell.msgId;
		this.content = cell.content;
		this.qidx = cell.qidx;
		this.midx = cell.midx;
	}
}
