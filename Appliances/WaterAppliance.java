/** 
 * A holder class that tracks details of appliances that use only water.
 * */

package cap_stone.Appliances;

public class WaterAppliance extends Appliance {
	int waterUsage; // per load
	double hotWaterUsage; // percentage
	
	/*Even Baths/Showers have a duration.
	 * We shall assume showers take 15 minutes, 
	 * and baths take 45 minutes. - Tiana 3/26/19*/
	int averageRunTime; 
	
	
	public WaterAppliance(String name) {
		super(name);
		this.waterUsage = 0;
		this.hotWaterUsage = 0;
	}

	/*Added the "runtime" functionality as well.
	 * --> 3/26/19*/
	public WaterAppliance(String name, int waterUsage, double hotWaterUsage, int avg) {
		super(name);
		this.waterUsage = waterUsage;
		this.hotWaterUsage = hotWaterUsage;
		this.averageRunTime = avg;
	}

	public void setWaterUsage(int waterUsage) {
		this.waterUsage = waterUsage;
	}

	public int getWaterUsage() {
		return this.waterUsage;
	}

	public void setHotWaterUsage(int hotWaterUsage) {
		this.hotWaterUsage = hotWaterUsage;
	}

	public double getHotWaterUsage() {
		return this.hotWaterUsage;
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
		String str = super.toString();
		str = str + "Water Usage: " + this.getWaterUsage() + " gallons per usage\n" + "Hot Water Usage: "
				+ (int) (this.getHotWaterUsage() * 100) + "% per gallon\n";
		return str;
	}
}
