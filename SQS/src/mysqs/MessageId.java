package mysqs;

public class MessageId extends Thread{
	public String msgId;
	public int vtime;//visibility time
	public boolean isLocked;
	
	public MessageId(){
		this.msgId = "null";
		this.vtime = 40;//4 seconds
		this.isLocked = false;
	}
	public MessageId(String msgId){
		this.msgId = msgId;
		this.vtime = 40;
		this.isLocked = false;
	}
	public String toString(){
		String info = "";
		info = "msgId:"+this.msgId
		+" vtime:"+this.vtime
		+" isLocked:"+this.isLocked;
		return info;
	}
	public void assign(MessageId msg){//assign by value
		this.msgId = msg.msgId;
		this.vtime = msg.vtime;
		this.isLocked = msg.isLocked;
	}
	public void lock(){
		this.isLocked = true;
	}
	public void unlock(){
		this.isLocked = false;
	}
	public void run(){//lock 1000 ms, 1 second
		this.lock();
		try {
			Thread.sleep(40000);//4 second
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.unlock();
	}
}
