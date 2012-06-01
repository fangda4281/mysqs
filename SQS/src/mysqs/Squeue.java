/*
 * msgId queue
 * receive() always receive the first available msgid obj
 * */
package mysqs;
//the max num of nodes in queue 
//is actually SIZE-1
//which is hint in isFull()
public class Squeue {
	//"final": means constant variable
	private static final int SIZE = 50000;//should be 50k-1?
	public int maxsize;
	public int front;
	public int rear;
	public int cur;//index of first unlocked node
	public int count;//current queue size
	public MessageId queue[];
	
	public Squeue(){
		//max num of nodes is SIZE-1?
		this.maxsize = SIZE;
		this.front = 0;
		this.rear = 0;
		this.cur = 0;
		this.count = 0;
		this.queue = new MessageId[SIZE];
	}
	public Squeue(int newSize){
		this.maxsize = newSize;
		this.front = 0;
		this.rear = 0;
		this.cur = 0;
		this.count = 0;
		this.queue = new MessageId[newSize];
	}
	public String toString(){
		String info = "";
		info = " front:"+this.front+" cur:"
		+this.cur+" rear:"+this.rear
		+" count:"+this.count+" maxsize:"
		+this.maxsize;
		return info;
	}
	public boolean isEmpty(){
		boolean flag = false;
		if(this.count == 0){
			flag = true;
		}
		return flag;
	}
	public boolean isFull(){
		boolean flag = false;
		if(this.count == (this.maxsize-1)){
			flag = true;
		}
		return flag;
	}
	public int getLength(){
		return this.count;
	}
	public boolean push(MessageId msg){
		boolean flag = false;
		if(!this.isFull()){
			int pos = (this.rear)%this.maxsize;
			this.queue[pos] = msg;
			this.count++;
			this.rear = (this.rear+1)%this.maxsize;
			flag = true;
		}
		return flag;
	}
	public boolean pop(){//only cur behind front will call pop(),so is safe
		boolean flag = false;
		if(!this.isEmpty()){
			this.front = (this.front+1)%this.maxsize;
			this.count--;
			flag = true;
		}
		return flag;
	}
	//the only useful infomation is msgId
	public boolean receive(MessageId msg){//msg:empty Message obj,tested a little, may be correct
		boolean flag = false;
		if(this.cur != this.rear){//has available msg
			msg.msgId = this.queue[this.cur].msgId;
			//lock message, start lock() and unlock()
			this.queue[this.cur].start();
			//cur index change
			this.cur = (this.cur+1)%this.maxsize;
			flag = true;
		}
		return flag;
	}
	//erase() may not be used at all
	//due to the cost is not O(1)
	//it will be replaced by 
	//eraseByPos()
	public boolean erase(String msgId){//tested a little, may be correct
		boolean flag = false;
		int pos = this.front;
		while(this.cur != pos){
			if(this.queue[pos].msgId.equals(msgId) ){//find it
				if(pos == this.front){
					this.pop();
				}else{
					//move front to target position
					//if has problem use Message.assign(msg) to assign by value
					//assign by reference may cause problems
					this.queue[pos] = this.queue[this.front];
					this.pop();
				}
				break;
			}
			pos = (pos+1)%this.maxsize;
		}
		return flag;
	}
	//in target position, out new position(front)
	public int eraseByPos(int target){
		int pos = target;
		if(pos == this.front){
			this.pop();
			pos = -1;//no position should be changed
		}else{
			this.queue[target].assign(this.queue[this.front]);
			//pos = this.front;
			this.pop();
		}
		
		return pos;//target or -1, needed
	}
	
}
