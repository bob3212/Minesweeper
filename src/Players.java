
public class Players implements Comparable{
private String name;//String storing the name of user
private int time;//Integer storing the time in seconds it took for user to win
	//Constructor for Players class
	Players(String n, int t){
		name = n;
		time = t;
	}
	//Comparing the initial time to the new time 
	public int compareTo(Object o)
	{
	    Players p = (Players)o;
	    //if the initial time is less than the new recorded time, return 1
	    if (time<p.time)
	    {
	        return 1;
	    }
	    //if the initial time is more than the new recorded time, return -1
	    else if (time>p.time)
	    {
	        return -1;
	    }
	    //if they were the same, return 0
	    else
	    {
	        return 0;
	    }
	}
	//Converts everything into a string which will be displayed
	public String toString(){
		return ""+getName()+" time: "+getTime()+" seconds";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}


	
}
