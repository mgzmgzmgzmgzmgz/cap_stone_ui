package cap_stone;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HVAC {

	static int currTemp = 69; // The temperature of the house due to the temperature outside.
	static int defaultTemp = 70; // The temperature that the house is set to be by the admin.
	static int additionalKWHTime = 0;
	static Calculator calc = new Calculator();
	static WeatherRouter weather = new WeatherRouter();
	double timer = 0.0;	
	
	// Sensor ArrayLists
	private static ArrayList<Sensor> allSensors =  new ArrayList<Sensor>();
	private static ArrayList<Sensor> openedSensors = new ArrayList<Sensor>();
		
	//List of Sensors
	WindowSensor mB1 = new WindowSensor("MasterBedroom_1" , weather, this);
	WindowSensor mB2 = new WindowSensor("MasterBedroom_2", weather, this);
	WindowSensor office = new WindowSensor("Office", weather, this);
	WindowSensor ktn1 = new WindowSensor("Kitchen_1", weather, this);
	WindowSensor ktn2 = new WindowSensor("Kitchen_2", weather, this);
	WindowSensor grg = new WindowSensor("Garage", weather, this);
	WindowSensor kR1 = new WindowSensor("KidsRoom_1", weather, this);
	WindowSensor kR2 = new WindowSensor("KidsRoom_2", weather, this);
	WindowSensor lR1 = new WindowSensor("LivingRoom_1", weather, this);
	WindowSensor lR2 = new WindowSensor("LivingRoom_2", weather, this);
	
	DoorSensor frontDoor = new DoorSensor("FrontDoor", weather, this);
	DoorSensor backDoor = new DoorSensor("BackDoor", weather, this);
	DoorSensor gDoor1 = new DoorSensor("GarageDoor_1", weather, this);
	DoorSensor gDoor2 = new DoorSensor("GarageDoor_2", weather, this);
	
	// Object Properties for the interface
	static StringProperty currentTemperature = new SimpleStringProperty();
	static StringProperty defaultTemperature = new SimpleStringProperty();
	ObjectProperty<Sensor> Office = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> MasterBedroom_1 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> MasterBedroom_2 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> Kitchen_1 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> Kitchen_2 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> Garage = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor>  KidsRoom_1= new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> KidsRoom_2 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> LivingRoom_1 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> LivingRoom_2 = new SimpleObjectProperty<Sensor>();
	
	ObjectProperty<Sensor> FrontDoor = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> BackDoor = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> GarageDoor_1 = new SimpleObjectProperty<Sensor>();
	ObjectProperty<Sensor> GarageDoor_2 = new SimpleObjectProperty<Sensor>();
	
	/**The HVAC controls the temperature of the house.*/
	public HVAC(){
		
		this.allSensors.add(mB1);
		this.allSensors.add(mB2);
		this.allSensors.add(office);
		this.allSensors.add(ktn1);
		this.allSensors.add(ktn2);
		this.allSensors.add(grg);
		this.allSensors.add(kR1);
		this.allSensors.add(kR2);
		this.allSensors.add(lR1);
		this.allSensors.add(lR2);
		
		this.allSensors.add(frontDoor);
		this.allSensors.add(backDoor);
		this.allSensors.add(gDoor1);
		this.allSensors.add(gDoor2);
	
		/****************************/
		
		currentTemperature.set(currTemp + "");
		defaultTemperature.set(defaultTemp + "");
		
		MasterBedroom_1.set(mB1);
		MasterBedroom_2.set(mB2);
		Office.set(office);
		Kitchen_1.set(ktn1);
		Kitchen_2.set(ktn2);
		Garage.set(grg);
		KidsRoom_1.set(kR1);
		KidsRoom_2.set(kR2);
		LivingRoom_1.set(lR1);
		LivingRoom_2.set(lR2);
		
		FrontDoor.set(frontDoor);
		BackDoor.set(backDoor);
		GarageDoor_1.set(gDoor1);
		GarageDoor_2.set(gDoor2);

	}
	
	
	/**When called, the HVAC will check if it needs to cool/warm the house
	 * based on the temperature outside from the WeatherRouther class.*/
	synchronized void watchOutside(){
		
		//Code structure provided by "Boris the Spider" via https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	   	executorService.scheduleWithFixedDelay(HVAC::compareTemperatures, 0, 5, TimeUnit.MINUTES); 
		
	}
	
	/**This method compares the internal and external temperatures ONLY. 
	 * It is used solely for observing the weather outside.*/
	static void compareTemperatures(){

		if(Math.abs(weather.getCurrWeather() - (getCurrTemp())) >= 10.0){ // Temperature difference between current house temp and outside.
			System.out.println("There will be a temperature change.");
			outsideWeatherChange(getCurrTemp(), weather.getCurrWeather()); // will calculate respective values for a door or window.
		} else{
			System.out.println("There will be no temperature change.");
		}
		
	}
	
	/**This method is specifically for the HVAC as it observes the outside temperature 
	 * and it's affect on the house. Should the requirements be met, the house will
	 * decrease or increase by 2 degrees.*/
	synchronized static void outsideWeatherChange(int internal, int external){
		
		System.out.println("The outside temperature is changing the inside.");
		int min = Math.min(internal, external);
		int max = Math.max(internal, external);

		if(min == getDefaultTemp()){ // The HVAC hasn't changed value yet.
			
			try{

				System.out.println("PLus 1");

	            TimeUnit.SECONDS.sleep(60); // Waits for a minute before it updates the temp.
				min += 1;
				setCurrTemp(min);

				System.out.println("Plus 1");

				TimeUnit.SECONDS.sleep(60); // A total of 2 minutes must pass.
				min += 1;
				setCurrTemp(min);

				
	        } catch (InterruptedException e) {
	            System.err.format("IOException: %s%n", e);
	        }

		}else{
			
			try {

				System.out.println("Minus 1");
	            TimeUnit.SECONDS.sleep(60);
				max -= 1;
				setCurrTemp(max);


				System.out.println("Minus 1");
				TimeUnit.SECONDS.sleep(60);
				max -= 1;
				setCurrTemp(max);

				
	        } catch (InterruptedException e) {
	            System.err.format("IOException: %s%n", e);
	        }
		}
	}
	
	/**
	 * This method is for admin cahnges to the house. Should an admin change the temperature of the house 
	 * - specifically the "defaultTemp"-, the degree difference is used in a loop to change the temperature 
	 * every minute for those number of degrees. */
	synchronized void insideWeatherChange(){
		int difference = Math.abs(currTemp - defaultTemp);
		int min = Math.min(currTemp, defaultTemp);
		int max = Math.max(currTemp, defaultTemp);
		
		for(int i = 0; i < difference ;i++){
			
			if(min == this.getDefaultTemp()){ // The HVAC hasn't changed value yet.
				
				try{
		            TimeUnit.SECONDS.sleep(60); // Waits for a minute before it updates the temp.
					min += 1;
					this.setCurrTemp(min);
					
		        } catch (InterruptedException e) {
		            System.err.format("IOException: %s%n", e);
		        }

			}else{
				
				try {

		            TimeUnit.SECONDS.sleep(60);
					max -= 1;
					this.setCurrTemp(max);
									
		        } catch (InterruptedException e) {
		            System.err.format("IOException: %s%n", e);
		        }
			}
			
		}
	}
	
	
	
	/**Changes the house to the requested default temperature and sends the kilowatts used to the calculator to 
	 * add to the total costs. */
	static void changeHouseTemp(int a, int b){ // Needs a Timer as well!!
		
		if(currTemp != defaultTemp){			
			additionalKWHTime = Math.abs(b-a);
			calc.addKiloWattUsage(additionalKWHTime, 4500);		
		}
	}
	
	/**Returns the current temperature of the house.
	 * @return currTemp;*/
	static int getCurrTemp(){
		return currTemp;
	}
	
	/**The outside temperature changes the internal temperature of the house.
	 * @param newTemp This is the new temperature of the house due to external factors.*/
	static void setCurrTemp(int newTemp){
		currTemp = newTemp;
		currentTemperature.set(currTemp + "");
		additionalKWHTime = 1;
		calc.addKiloWattUsage(additionalKWHTime, 4500);
		
	}
	
	/**Returns the default temperature that the administrator has set.
	 * @return defaultTemp The temperature that the house is suppose to be.*/
	static int getDefaultTemp(){
		return defaultTemp;
	}
	
	/**This is the method run for the administrator to change the house temperature. The value of the 'defaultTemp'
	 * variable is saved into the 'currTemp' object, before it takes the 'newHouseTemp' value. Then changeHouseTemp()
	 * is called to calculate.
	 * @param newHouseTemp This is the new temperature the admin wishes the house to be.*/
	void setDefaultTemp(int newHouseTemp){
		defaultTemp = newHouseTemp; // Second, the 'defaultTemp' NOW becomes the 'newHouseTemp' the admin set.
		defaultTemperature.set(""+defaultTemp);
		this.changeHouseTemp(currTemp, defaultTemp); //Third, the HVAC works to change the temperature, and adds the kwh used to the Calculator.
		//this.insideWeatherChange();| Doesn't work.
	}
	
	/**Returns the Calculator object in the HVAC class.
	 * @return calc*/
	public Calculator getCalculator(){
		return calc;
	}

	
	synchronized void watchHouse(){

		 //Code structure provided by "Boris the Spider" via https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	   	executorService.scheduleWithFixedDelay(HVAC::randomizer, 0, 10, TimeUnit.SECONDS); 
		
	}
	
	static void randomizer(){
		
		Random rand = new Random();
		Sensor temp = new Sensor("", weather, new HVAC());
		temp = allSensors.get(rand.nextInt(allSensors.size())); // returns a random sensor
		
		if(rand.nextInt(2) == 1){ // chance that a sensor is turned on
			
			System.out.println("A sensor was turned on!!!");
			allSensors.remove(temp); // Cannot have copies of the same sensor in the randomizer should it opened.
			openedSensors.add(temp); // Add that same sensor to a list of opened Sensors.
			temp.hasBeenOpened(); // We open this sensor.
		}
		else if(openedSensors.size() > 0){
			System.out.println("Checking if someone has closed a sensor...");


			temp = openedSensors.get(rand.nextInt(openedSensors.size())); // returns a random sensor
			
			if(rand.nextInt(2) == 1){ // There's a higher chance of closing the sensor.
				System.out.println("A sensor was turned off!!! >:) ");
				openedSensors.remove(temp); // Cannot have copies of the same sensor that's now closed.
				allSensors.add(temp); // Add that same sensor back to the list of all Sensors.
				temp.hasBeenClosed(); // We closed this sensor.
				//temp.checkTimer(); // Have to check if the temperature of the house changed.
			}
			else{ // A Sensor CAN be open for a longer period of time, and there for should have 5 minutes deducted from total time.
				System.out.println("Hmmm... A sensor is still on.....");
				temp.checkTimer(); // Otherwise, since it's not closed and still open, the timer is checked to see if it's been 5 minutes.
			}
		}
		else{
			System.out.println("The sensor was not turned on...");
		}		
				
	}
	
	public void setUIProperties(StringProperty in, StringProperty ex, int n_in, int n_ex){
		
	}
	
	
	public static void main(String[] args){
		HVAC hvac = new HVAC();
		hvac.watchHouse();
		hvac.watchOutside();

	}
}
