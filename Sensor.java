package cap_stone;

import java.util.concurrent.TimeUnit;

public class Sensor {

	private double startTime = 0.0;
	private double endTime = 0.0;
	private boolean isOpen = false;

	double timer = 0.0;	
	String name;
	
	static HVAC hvac = new HVAC();
	static WeatherRouter weather = new WeatherRouter();
	
	Sensor(String name, WeatherRouter w, HVAC h){
		this.name = name;
		this.weather = w;
		this.hvac = h;
	}
	
	
	String getName(){
		return name;
	}
	
	int checkWeather(){
		return weather.getCurrWeather();
	}
	
	
	void checkTimer(){
		if(this.checkOpenDuration() >= 300.0){  // If door is open/ enough time passed.... &&  Get the time that it's been open||| ACTUALLY!!! Still needed if it's closed!
			//Sooooo... I'm getting rid of "this.getState()"
			//  5 minutes should be deducted from the total time.
			System.out.println("The duration of the sensor " + this.getName() + " is: " + this.getTimer());
			
			this.setTimer(this.getTimer() - 300.0);
			System.out.println("The new duration of the sensor " + this.getName() + " is now: " + this.getTimer());
			
			//If it has been 5 min, then calc the change in temp.
			// else ignore.
			this.compareTemperatures();

			// apply this change to the HVAC
			// The HVAC must then compare to the default settings
			// If the settings are different, then the HVAC works to correct the temp.
			// These degrees that are changed are also the same number of minutes that the HVAC is used.
			// The Calculator must then update the kilowatt usage.
		}else{
			System.out.println("The duration of the sensor " + this.getName() + " is: " + this.checkOpenDuration() + ".... Not long enough. Starting over...");
			
		}
	}
	
	// This is a method for the HVAC. Directly comparing with the outside. 
	void compareTemperatures(){
		System.out.println("About to compare temperatures....");
		if(Math.abs((this.checkWeather() - (this.getHVAC()).getCurrTemp())) >= 10.0){ /***************************Curr or Default Temp????*************************************/
			this.calcDegrees((this.getHVAC()).getCurrTemp(), this.checkWeather()); // will calculate respective values for a door or window.
		} else{
			System.out.println("There will be no temperature change.");
		}
		
	}
	

	void calcDegrees(int internal, int external){
		// Parent to be overridden.
	}
	
	double getTimer(){
		return this.timer;
	}
	
	void setTimer(double newTime){
		this.timer = newTime;
	}
	
	public HVAC getHVAC(){ //Does this need to be static????
		return this.hvac;
	}
	
	/****************************************************************************/
	public boolean getState() {
		return isOpen;
	}

	public void setState(boolean state) {
		this.isOpen = state;
	}

	public void hasBeenOpened() {
		
		if (!this.getState()) {
			this.startTime = System.currentTimeMillis();
			this.setState(true);		
		}
		
	}

	public void hasBeenClosed() {
		
		if (this.getState()) {
			this.endTime = System.currentTimeMillis();
			this.checkTimer();
			
			this.setStartTime(0.0);
			this.setEndTime(0.0);
			this.setState(false);
			this.setTimer(0.0);
		}
	}
	
	public double checkOpenDuration() {
			
			this.endTime = System.currentTimeMillis(); // Not the "endTime"!! More like "MID"-time.
			this.calcNewTime();
			System.out.println("About to get timer....");

			return this.getTimer();
		
	}
	
	/**Calculates the elapsed seconds that a door/window has been opened and saves it to the 'timer' variable.*/
	private void calcNewTime() {
		
		double elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds((long) (endTime - startTime)); // end-start is the elapsed time.
		this.startTime = this.endTime;
		System.out.println("Seconds thus far...: "+ elapsedSeconds); // is converted to seconds.
		
		this.setTimer(this.getTimer() + elapsedSeconds); //*****************************************!!! This is where the timer is set for a given sensor!
	}

	public String toString() {
		String str = super.toString();
		if (this.isOpen) {
			str = str + "This device is open\n";
		} else {
			str = str + "This device is closed\n";
		}
		return str;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}
	
	public static void main(String[] args){
		Sensor masterBed = new WindowSensor("Master Bedroom", new WeatherRouter(), new HVAC());
//		masterBed.setSensor(masterBed);
		
		masterBed.hasBeenOpened();
		
			// Code structure provided by "Boris the Spider" via https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		   
//		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//		    executorService.scheduleAtFixedRate(Sensor::myTask, 0, 1, TimeUnit.SECONDS);
		
		//TimeUnit.timedWait();

		
		

	}
}
