/** 
 * A generic template to be passed down to the Electronics and WaterAppliances classes for their
 * internal objects.
 * 
 *  @author Gunner Miller
 *  @version 1.0
 **/

package cap_stone.Appliances;

public class Appliance {
	private String name;
	private int usageTime; //in seconds
	
	public Appliance(String name) {
		this.name = name;
		this.usageTime = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getUsageTime() {
		return this.usageTime;
	}
	
	public void setUsageTime(int time) {
		this.usageTime = time;
	}
	
	public String toString() {
		String str = "Name: " + this.getName() + "\nCurrent Running Time: " + this.getUsageTime() + " minutes\n";
		return str;
	}
}
