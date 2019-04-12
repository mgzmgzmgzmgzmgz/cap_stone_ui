/**
 * This is a driver class. After being initialized, it can speak for all
 * connected electronics, and provide their details without needing to track
 * each individual Electronic objects. It is possible to interact directly with
 * these appliances (i.e., a Electronic object), but my goal is to make it easy
 * to just use this driver class.
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



public class Electronics {
	HashMap<String, Electronic> myElectronics = new HashMap<String, Electronic>();
	Electronic temp; //preallocated for verifying given Electronic exists

	public static void main(String[] args) {
		Electronics driver = new Electronics();
		driver.init("src/Appliances/Data/ElectronicsData.txt");
		String seeking = null; //name of appliance we want
		
		try {
			seeking = "light";
			Electronic node = driver.getAppliance(seeking);
			System.out.println(node.toString());
		} catch (NullPointerException e) {
			System.out.println("No appliance named " + seeking + ".");
		}
	}

	private int init(String fileName) {
		try {
			//initialize variables
			String name = null;
			int wattage;
			boolean isSwitched;
			int avgRunTime = 0;
			
			File file = new File(fileName);
			BufferedReader in = new BufferedReader(new FileReader(file));

			String str;
			String[] split;
			in.readLine(); // read past the notes
			while ((str = in.readLine()) != null) {
				if (str.equals("")) {
					continue;
				}

				try {
					split = str.split(",");
					name = split[0];
					wattage = Integer.parseInt(split[1]);
					isSwitched = Boolean.parseBoolean(split[2]);

					if (!isSwitched) {
						this.addAppliance(name, wattage, avgRunTime);
					} else {
						this.addSwitchedAppliance(name, wattage, avgRunTime);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println(
							"Error with string: \"" + str + "\". Please check that data is entered according to format.");
				}
			}
			in.close();
			return 0;

		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void turnOnAppliance(String name) {
		Electronic node = this.getAppliance(name);

		if (node != null && SwitchedElectronic.class.isInstance(node)) {
			SwitchedElectronic thisElectronic = (SwitchedElectronic) node;
			thisElectronic.turnOn();
		}
	}

	public void turnOffAppliance(String name) {
		Electronic node = this.getAppliance(name);
		
		
		if (node != null && SwitchedElectronic.class.isInstance(node)) {
			SwitchedElectronic thisElectronic = (SwitchedElectronic) node;
			thisElectronic.turnOff();
		}
	}

	public int getApplianceWattage(String name) {
		if ( (temp = this.getAppliance(name)) != null) {
			return temp.getWattage();
		}
		return -1;
	}

	public int getApplianceUsage(String name) {
		if ( (temp = this.getAppliance(name)) != null) {
			return temp.getUsageTime();
		}
		return -1;
	}

	public void addAppliance(String name, int wattage, int avgRunTime) {
		Electronic node = new Electronic(name, wattage, avgRunTime);
		name = name.toLowerCase().trim();
		this.myElectronics.put(name, node);
	}

	public void addSwitchedAppliance(String name, int wattage, int avgRunTime) {
		SwitchedElectronic node = new SwitchedElectronic(name, wattage, avgRunTime);
		name = name.toLowerCase().trim();
		this.myElectronics.put(name, node);
	}

	public Electronic getAppliance(String name) {
		name = name.toLowerCase().trim();
		if ((temp = this.myElectronics.get(name)) != null) {
			return temp;
		}
		//System.out.println("No appliance named " + name + ".");
		return null;
	}
	
	public HashMap<String, Electronic> getAllAppliances(){
		return myElectronics;
	}
}
