/**
 * A holder class tracking details of appliances that use electricity.
 * */

package cap_stone.Appliances;

public class Electronic extends Appliance {
	private int wattage;
	
	// The electronics also need "average time usage" for the calculator. - Tiana .3/26/19
	int averageRunTime; 
	
	public Electronic(String name) {
		super(name);
		this.setWattage(0);
		this.setAverageRunTime(0);
	}

	public Electronic(String name, int wattage, int avg) {
		super(name);
		this.setWattage(wattage);
		this.averageRunTime = avg;
	}

	public int getWattage() {
		return wattage;
	}

	public void setWattage(int wattage) {
		this.wattage = wattage;
	}
	
	/*NEW!!!!
	 * --> 3/26/19*/
	public void setAverageRunTime(int averageRunTime) {
		this.averageRunTime = averageRunTime;
	}

	public int getAverageRunTime() {
		return this.averageRunTime;
	}

	public String toString() {
		String str = "Name: " + this.getName() + "\n" + "Current Running Time: " + this.getUsageTime() + " minutes\n"
				+ "Wattage Consumption: " + this.getWattage() + " watts/h\n";
		return str;
	}
}
