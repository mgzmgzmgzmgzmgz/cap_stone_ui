package cap_stone;

import java.util.Map.Entry;
import java.util.Random;
import java.util.function.BiConsumer;
import cap_stone.Appliances.*;



public class Calculator {
	
	private DataGenerator data = new DataGenerator();
	private double totalKilowattHoursUsed = 0;  // Sum of all kilowatt hours used.
	private double totalHeatedWater = 0; // Gallons of heated water.
	private int totalGallonsUsed = 0; // Sum of all gallons used.
	private int days = 0; // Represents the weekend or the weekday. Is initialized in calcWeekend/day().
	
	private double totalWeeklyEstimatedCost;
	private double totalMonthlyEstimatedCost;
	private double totalYearlyEstimatedCost;
	private double estKilowattHoursCost;
	private double estGallonCost;
	
	/**Once a Calculator object is initialized, it already calculates the gallon and kwh values.
	 *  All that remains are the getter values for interface use. */
	Calculator(){
		calcWeekday();
		calcWeekend();
		extraCalculations();
	
	}
	
	void recalculate(){
		calcWeekday();
		calcWeekend();
		extraCalculations();
	}
	
	/**Returns the value holding the total BASE number of kilowatt hours that were used in a  WEEK.
	 * @return totalKilowattHoursUsed*/
	public double getTotalKilowattHoursUsed(){
		return totalKilowattHoursUsed;
	}
	
	/**Returns the total BASE number of gallons that were used in a given WEEK.
	 * @return totalGallonsUsed*/
	public int getTotalGallonsUsed(){
		return totalGallonsUsed;
	}
	
	/**Retsurns the estimated cost of the kilowatt hours for the time span of a WEEK, MONTH, or YEAR.
	 * @return estKilowattHoursCost*/
	public double getEstKilowattHoursCost(){
		return estKilowattHoursCost;
	}
	
	/**Retsurns the estimated cost of the gallons for the time span of a WEEK, MONTH, or YEAR.
	 * @return estGallonsCost*/
	public double getEstGallonsCost(){
		return estGallonCost;
	}
	
	/**Returns the total weekly BASE cost of all utilities.
	 * @return totalWeeklyEstimatedCost*/
	public double getTotalWeeklyEstimatedCost(){
		return totalWeeklyEstimatedCost;
	}

	/**Returns the total monthly BASE cost of all utilities.
	 * @return totalMonthlyEstimatedCost*/
	public double getTotalMonthlyEstimatedCost(){
		return totalMonthlyEstimatedCost;
	}
	
	/**Returns the yearly BASE cost of all utilities.*/
	public double getTotalYearlyEstimatedCost(){
		return totalYearlyEstimatedCost;
	}
	
	/**Method calculates the weekly cost of the utilities. After calling this method, the 'estKilowattHoursCost'
	 * and the 'estGallonCost' values will be saved with respective weekly values, as well as the 'totalWeeklyEstimatedCost'.
	 * The first two values will change (to MONTHLY or YEARLY values) upon calling another method. */
	public void calcWeeklyCosts(){
		estKilowattHoursCost = (totalKilowattHoursUsed * 0.12); // 0.12 cents is the cost of every kwh.
		estGallonCost = ((((totalGallonsUsed *  4.33)/748.0) * 2.98)  + 26.94)/4.33; // Roughly 0.03 cents is the cost of every gal.
		totalWeeklyEstimatedCost = estKilowattHoursCost + estGallonCost; // Total predicted bill value for the week.
		
	}
	
	/**Method calculates the monthly cost of the utilities. After calling this method, the 'estKilowattHoursCost'
	 * and the 'estGallonCost' values will be saved with respective monthly values, as well as the 'totalMonthlyEstimatedCost'.
	 * The first two values will change (to WEEKLY or YEARLY values) upon calling another method. */
	public void calcMonthlyCosts(){
		estKilowattHoursCost = (totalKilowattHoursUsed * 0.12) * 4.33; // 52 weeks/12months is 4.33 (Not a perfect 4 weeks in a month.)
		estGallonCost = (((totalGallonsUsed *  4.33)/748.0) * 2.98)  + 26.94;
		totalMonthlyEstimatedCost = estKilowattHoursCost + estGallonCost; // Total predicted bill value for the month.
	}

	/**Method calculates the yearly cost of the utilities. After calling this method, the 'estKilowattHoursCost'
	 * and the 'estGallonCost' values will be saved with respective yearly values, as well as the 'totalYearlyEstimatedCost'.
	 * The first two values will change (to WEEKLY or MONTHLY values) upon calling another method. */
	public void calcYearlyCosts(){
		estKilowattHoursCost = (totalKilowattHoursUsed * 0.12) * 52.0; // Total kilowatt hours used in a given week * 52 weeks in a year.
		estGallonCost = ((((totalGallonsUsed *  4.33)/748.0) * 2.98)  + 26.94) * 52.0;
		totalYearlyEstimatedCost = estKilowattHoursCost + estGallonCost; // Total predicted bill value for the year.
	}

	/**This method calculates all of the weekday values by initializing the DataGenerator object 'data'
	 * with the weekday data.*/
	private void calcWeekday(){
		days = 5;
		data.createDataWeekday();
		calcGallons();
		calcKilowatts();
	}
	
	/**This method calculates all of the weekend values by initializing the DataGenerator object 'data'
	 * with the weekend data.*/
	private void calcWeekend(){
		days = 2;
		data.createDataWeekend();
		calcGallons();
		calcKilowatts();
	}
	
	/**This methods focuses on calculating all of the kilowatt values based on the Electronic appliances.*/
	private void calcKilowatts(){
		// Format for iteration came from https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		for (Entry<String, Electronic> entry : (data.getElectronicAppliances().getAllAppliances()).entrySet()) {
		    String key = entry.getKey();
		    Electronic value = entry.getValue();
		    Random rand = new Random();
	    		
		    if(value.getName() == "Clothes Dryer"){
		    	
		    	// Calculations for Clothes dryer.
		    	totalKilowattHoursUsed += (((value.getAverageRunTime() + rand.nextInt(30)) *2.0) / 60.0) * (value.getWattage()/1000.0); 
		    	/*NOTE: the number of run cycles is set to 2 instead of 4 (as provided) since this method will
	    		 *  be called twice. Once for weekday values. Another for weekend values.*/
		    	
		    }else if(value instanceof SwitchedElectronic){
		    	
		    	// Calculations for switched electronics. (IF the switch is used...???)
		    	totalKilowattHoursUsed += (((((SwitchedElectronic)value).getAverageRunTime() + rand.nextInt(30))/60.0) * (((SwitchedElectronic)value).getWattage()/1000.0)) * days; 
		    	// Hours * kilowatt
		    	
		    	totalKilowattHoursUsed += ((((SwitchedElectronic)value).getUsageTime()/60.0) * (((SwitchedElectronic)value).getWattage()/1000.0)) * days; 
		    	// USAGETIME: time from actually flipping the switch.
		    	// Hours * kilowatt
		    }else{
		    	// Calculations for typical electronics.
		    	
		    	totalKilowattHoursUsed += (((value.getAverageRunTime() + rand.nextInt(30))/60.0) * (value.getWattage()/1000.0)) * days; 
		    	// Hours * kilowatt
		    }
		}
		
	}
	
	/**Calculates the remaining kilowatt hours from the use of the water heater and  the refrigerator, in addition to 
	 * the use of the exhaust fan and toilet use in the restrooms. Must be used AFTER the calcGallons() method, hence
	 * the initialization of the Calculator constructor.*/
	private void extraCalculations(){
		
		double heatedWaterTime = 0; // Total minutes of heated gallons.
		heatedWaterTime = totalHeatedWater * 4.0; // 4 minutes to heat each gallon
		totalKilowattHoursUsed += (heatedWaterTime/60.0) * 4.5; // Hour X Kilowatts ( from 4500 watts of Hot Water Heater use.)
		
		totalKilowattHoursUsed += (24.0 * .150) * 7.0; // 24 hours * .150 kilowatts * 7 days| calculations for the refrigerator for a given week.
		
		/*Considering the exhaust fan and therefore the restroom (toilet) use, every person has a minimum of 3 uses in 
		 * the restroom everyday (waking up| coming home| before going to sleep). Thus totaling to 12 a day - or about
		 * 84 flushes in the week. The exhaust fan maybe used for showers or just for convenience to get odors out.
		 * Taking 'conveniences' into account, lets say this is used 1/4 of the time (21 times).Then there's a total of 16 showers
		 * to add onto the total use.  That makes 37 uses in a week. Also, there's the extra MINIMUM of 84 gallons of water being flushed.
		 * 	|- According to https://www.westsidewholesale.com/faq/v/q/ventilation-fans/how-long-should-a-bath-fan-run-after-a-shower-is-u.html,
		 * 		bathroom fans SHOULD run for 20 minutes to "adequately" clear out humidity and keep the fan clean.
		 * 	|- Realistically, the fan may be run half that time.  */
		Random rand = new Random();
		totalKilowattHoursUsed += (((37.0 + rand.nextInt(20)) * 10.0)/60.0) * 0.03; //((37 uses * 10 minutes) / 60 minutes) * .03kw from the fan.
		
		totalGallonsUsed += 84 + rand.nextInt(30); // Additional, toilet flushes.
		
		// HVAC base calculations.
		totalKilowattHoursUsed += ((28.8 + (double) rand.nextInt(15))/60.0) * 4.5; // 28.8 minutes of operation in a week * 4.5 kw from the HVAC.
	}
	
	/**
	 * This method calculates the total gal/kwh used from the water appliances. */
	private void calcGallons(){
		
		// Format for iteration came from https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		for (Entry<String, WaterAppliance> entry : (data.getWaterAppliances().getAllAppliances()).entrySet()) {
		    String key = entry.getKey();
		    WaterAppliance value = entry.getValue();
		    Random rand = new Random();
	    		
		    if(value instanceof HybridAppliance){
		    	// Calculations for Dishwasher and Clothes washer.
		    	
	    		totalGallonsUsed += (value.getWaterUsage() + rand.nextInt(30)) * 2; // Gallons * 2 run cycles
	    		totalHeatedWater += ((value.getWaterUsage() + rand.nextInt(30)) * value.getHotWaterUsage()) * 2; // Gallons * 2 run cycles
	    		
	    		/*This line REQUIRES that the numbers be listed in double format to get the appropriate answer. */
	    		totalKilowattHoursUsed += (((value.getAverageRunTime() + rand.nextInt(30)) * 2.0) / 60.0) * (((HybridAppliance) value).getWattage()/1000.0); // Hours (given 2 run cycles) * kilowatt 
	    		
	    		/*NOTE: the number of run cycles is set to 2 instead of 4 (as provided) since this method will
	    		 *  be called twice. Once for weekday values. Another for weekend values.*/
		    		
		    }else{
		    	// Calculations for bathes and showers.
		    	
		    	totalGallonsUsed += ((value.getWaterUsage() + rand.nextInt(30))* value.getAverageRunTime()) * days; // Gallons * NUMBER OF BATHES/SHOWERS (not minutes)
		    	totalHeatedWater += ((value.getWaterUsage() + rand.nextInt(30)) * value.getHotWaterUsage()) * days;
		    }		    
		}
	}
	
	void addKiloWattUsage(int minutes, int wattage){
		totalKilowattHoursUsed += (minutes/60.0) * (wattage/1000);
	}
	
	void randomizer(){
		
	}
	
	/**Tester method displaying the values produced for a given week.*/
	public static void main(String[] args){
		double st = System.currentTimeMillis();
		
		Calculator calc = new Calculator();
		
		calc.calcWeeklyCosts();
			
		System.out.println("The total amount of gallons used in a week: " + calc.getTotalGallonsUsed());
		System.out.println("The total amount of kilowatts used in a week: " + calc.getTotalKilowattHoursUsed());
		System.out.println("The total cost of gallons used: " + calc.getEstGallonsCost());
		System.out.println("The total cost of kilowatts used: " + calc.getEstKilowattHoursCost());
		System.out.println("The total cost of everything in a week is: " + calc.getTotalWeeklyEstimatedCost());
		
				
		System.out.println("The time in milis: " + (System.currentTimeMillis()- st));
	
	}
}
