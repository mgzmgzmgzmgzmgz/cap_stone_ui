/**
 * This is a driver class. After being initialized, it can speak for all
 * connected water appliances, and provide their details without needing to track
 * each individual WaterApliance objects. It is possible to interact directly with
 * these appliances (i.e., through the actual Water Appliance object), but my goal is 
 * to make it easy to just use this driver class.
 * 
 * If an appliance uses water at all, it goes here. Appliances that use both water and electricity
 * are considered a "hybrid appliance" or HybridAppliance. Otherwise, it is a WaterAppliance, or a WaterAppliance
 * 
 * @author Gunner Miller
 * @version 1.0
 */

package cap_stone.Appliances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class WaterAppliances {
	HashMap<String, WaterAppliance> myAppliances = new HashMap<String, WaterAppliance>();
	WaterAppliance temp; //preallocated for verifying given WaterAppliance exists
	
	
	
	
	
	public static void main(String[] args) {
		
		WaterAppliances driver = new WaterAppliances();
		String filename = "src/Appliances/Data/WaterData.txt";
		driver.init(filename);
		String seeking = null;
		
		try {
			seeking = "dishwasher";
			WaterAppliance node = driver.getAppliance(seeking);
			System.out.println(node.toString());
			seeking = "bath";
			node = driver.getAppliance(seeking);
			System.out.println(node.toString());
		}catch(NullPointerException e) {
			System.out.println("No appliance named " + seeking + ".");
		}
	}
	
	private int init(String filename) {
		try {
			//init variables
			String name = null;
			int wattage = 0;
			int waterUsage = 0;
			double hotWaterUsage = 0;
			int averageRunTime = 0;

			File file = new File(filename);
			BufferedReader in = new BufferedReader(new FileReader(file));
	
			String str;
			String[] split;
			in.readLine(); //read past the notes
			while( (str = in.readLine()) != null) {
				if (str.equals("")) {
					continue;
				}
				
				try {
					split = str.split(",");
					name = split[0];
					wattage = Integer.parseInt(split[1]);
					waterUsage = Integer.parseInt(split[2]);
					hotWaterUsage = Double.parseDouble(split[3]);
					averageRunTime = Integer.parseInt(split[4]);
					
					if (wattage == 0) {
						this.addAppliance(name, waterUsage, hotWaterUsage, averageRunTime);
					} else {
						this.addHybridApplianceliance(name, waterUsage, hotWaterUsage, wattage, averageRunTime);
					}					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println(
							"Error with string: \"" + str + "\". Please check that data is entered according to format.");
				}
			}
			in.close();
			return 0;
		} catch(IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void addAppliance(String name, int waterUsage, double hotWaterUsage, int avg) {
		WaterAppliance node = new WaterAppliance(name, waterUsage, hotWaterUsage, avg);
		name = name.toLowerCase();
		this.myAppliances.put(name,  node);
	}
	
	public void addHybridApplianceliance(String name, int waterUsage, double hotWaterUsage, int wattage, int avg) {
		HybridAppliance node = new HybridAppliance(name, waterUsage, hotWaterUsage, wattage, avg);
		name = name.toLowerCase();
		this.myAppliances.put(name, node);
	}
	
	public WaterAppliance getAppliance(String name) {
		name = name.toLowerCase().trim();
		if ( (temp = this.myAppliances.get(name)) != null ) {
			return temp;
		}
		return null;
	}
	
	/**
	 * This method returns the HashMap 'myAppliances' that holds Strings as the key and WaterAppliances as the value.
	 * @return myAppliances*/
	public HashMap<String, WaterAppliance> getAllAppliances(){
		return myAppliances;
	}
}
