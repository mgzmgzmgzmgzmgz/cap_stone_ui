package cap_stone;
import cap_stone.Appliances.*;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import cap_stone.Appliances.Electronics;
import cap_stone.Appliances.WaterAppliances;

public class DataGenerator {

	/*All INTEGERS are read in terms of MINUTES.*/
	
	
	private static WaterAppliances waterAppliances = new WaterAppliances();
	private static Electronics electronics = new Electronics();
	
	DataGenerator(){
		
	}
	
	/**
	 * Initializes the private WaterAppliances and Electronics objects to week day values.*/
	public void createDataWeekday(){
		// Electronics.
		electronics.addAppliance("Microwave", 1100, 20);
		electronics.addAppliance("Stove", 3500, 15);
		electronics.addAppliance("Oven", 4000, 45);
		electronics.addAppliance("Clothes Dryer", 3000, 30);	// Runs 4 times a week. Extra Calculation or no?
		
		electronics.addSwitchedAppliance("Living Room TV", 636, 240);
		electronics.addSwitchedAppliance("Bed Room TV", 100, 120);		
		/*BTRAINSTORM:
		 * ~Lights~
		 * There are a total of 16 lights in the house.
		 * - Realisticly, some lights may have more than 1 light bulb.
		 * - The est. total of bulbs after some thinking is: 28.
		 * IDEA 1: This can be a value that assumes all lights are turned on 
		 * 			when the family is up. Therefore, using the adult schedule
		 * 			for waking up till leaving in the morning AND the time the
		 * 			kids get home until the adults sleep, this will act as a 
		 * 			base formula for the lights. 
		 * 					- (Going with this) Tiana 3/26/19
		 * 
		 * - Thus the lights will be on from 5AM to 7:30 AM and 4 PM to 10:30 PM.
		 * - Total time is 9 Hours x 60 = 540 minutes per day. (I haven't considered the weekend yet.)
		 * 
		 *  V V V V V */
		electronics.addSwitchedAppliance("Lights", 60, 540);
		
		//Water Appliances.
		//NOTE: Not 2 MINUTES, but 2 TIMES that a bath/shower is taken.
		waterAppliances.addAppliance("Bath", 30, 0.65, 2);
		waterAppliances.addAppliance("Shower", 25, 0.65, 2);
		
		waterAppliances.addHybridApplianceliance("Dishwasher", 6, 1.0, 1800, 45);	// Runs 4 times a week. Extra Calculation or no?
		waterAppliances.addHybridApplianceliance("Clothes Water", 20, 0.85, 500, 30);	// Runs 4 times a week. Extra Calculation or no?
		
		
	}
	
	/**
	 * Initializes the private WaterAppliances and Electronics objects to weekend values.*/
	public void createDataWeekend(){
		// Electronics.
		electronics.addAppliance("Microwave", 1100, 30);
		electronics.addAppliance("Stove", 3500, 30);
		electronics.addAppliance("Oven", 4000, 60);
		electronics.addAppliance("Clothes Dryer", 3000, 30);
		
		electronics.addSwitchedAppliance("Living Room TV", 636, 480);
		electronics.addSwitchedAppliance("Bed Room TV", 100, 240);
		/*Given a random idea that for the weekend kids and adults follow a schedule like this:
		 * - Adults wake at 8AM. idle in the home until 1PM. 
		 * - Kids wake at 9AM.
		 * - Everyone is idle in the home until 1PM, using the lights.
		 * - The household leaves at 1PM for a family outing and return at 5PM.
		 * - Kids go to sleep at 8:30PM.
		 * - Adults got to bed at 10:30PM. 
		 * 
		 * This gives a total of 10.5 hours for each day of the weekend * 60= 630 minutes.
		 * Perhaps not every single light is on, so only half will be accounted for (about 14 bulbs).
		 * 14 bulbs * .06 kilowatts * 10.5 hours = 8.82 kwh each day.
		 * */
		electronics.addSwitchedAppliance("Lights", 60, 630);
		
		
		//Water Appliances.
		//NOTE: Not 3 MINUTES, but 3 TIMES that a bath/shower is taken.
		waterAppliances.addAppliance("Bath", 30, 0.65, 3);
		waterAppliances.addAppliance("Shower", 25, 0.65, 3);
		
		waterAppliances.addHybridApplianceliance("Dishwasher", 6, 1.0, 1800, 45);
		waterAppliances.addHybridApplianceliance("Clothes Water", 20, 0.85, 500, 30);
	}
	
	/*STILL NEED
	 * 
	 * - Door Opening Events considering the OPen door info.
	 * - Interior Temp Change with House closed and Open Window. (Randomized weather for now.)
	 * - Calculate HVAC operations
	 * 
	 * - Need weather api
	 * 
	 * Once calculations are finished being coded...................
	 * 
	 * - add in graph functionality (Needs Calculator)	|Good??
	 * - possibly connect toggles. (Maybe Micah)		|Good??
	 * 
	 * */
	
	/**
	 * Returns the value of the class' private WaterAppliances object/attribute 'waterAppliances'.
	 * 'waterAppliances' holds the information of the various water-related appliances in the given household. 
	 * @return waterAppliances */
	public WaterAppliances getWaterAppliances(){
		return waterAppliances;
	}
	
	/**
	 * Returns the value of the class' private Electronics object/attribute 'electronics'.  
	 * @return electronics*/
	public Electronics getElectronicAppliances(){
		return electronics;
	}
	/*	Test	*/
	
	public static void main(String[] args){
		DataGenerator tester =new DataGenerator();

		tester.createDataWeekday(); // Initialize the data.
		
		WaterAppliance temp = tester.waterAppliances.getAppliance("Bath");
		
		System.out.println(temp.toString());
		System.out.println("");
		System.out.println("");
		
		waterAppliances.getAllAppliances();
	
	
		// Format for iteration came from https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		for (Entry<String, WaterAppliance> entry : (waterAppliances.getAllAppliances()).entrySet()) {
		    String key = entry.getKey();
		    WaterAppliance value = entry.getValue();
		    System.out.println(value.toString());
		    System.out.println("");	    
		    System.out.println("The water usage is: " + value.getWaterUsage() + " gallons, The hot water usage is: " + value.getHotWaterUsage() +
		    		" of the gallons used.");
		    if(value instanceof HybridAppliance){
		    	System.out.println("************ This is a hybrid appliance!");
		    }else{
		    	System.out.println("************ This... this is NOT a hybrid!!! >:O");
		    }
		    System.out.println("");
		    System.out.println("");
		}
	}
}
