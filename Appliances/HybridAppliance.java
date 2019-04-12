/** 
 * A holder class that tracks details of appliances using both water and electricity.
 * */

package cap_stone.Appliances;

public class HybridAppliance extends WaterAppliance {
	int wattage;


	public HybridAppliance(String name) {
		super(name);
		this.wattage = 0;
	}

	public HybridAppliance(String name, int waterUsage, double hotWaterUsage, int wattage, int avg) {
		super(name, waterUsage, hotWaterUsage, avg);
		this.wattage = wattage;
	}

	public void setWattage(int wattage) {
		this.wattage = wattage;
	}

	public int getWattage() {
		return this.wattage;
	}


	public String toString() {
		String str = super.toString();
		str = str + "Average Runtime: " + this.getAverageRunTime() + " minutes per usage\n" + "Wattage Consumption: "
				+ this.getWattage() + " watts/h\n";
		return str;
	}
}