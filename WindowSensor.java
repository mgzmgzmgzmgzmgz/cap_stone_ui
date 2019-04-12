package cap_stone;

import java.util.concurrent.TimeUnit;

public class WindowSensor extends Sensor {

	WindowSensor(String name, WeatherRouter weather, HVAC h) {
		super(name,weather, h);

	}
	
	synchronized void calcDegrees(int internal, int external){
		System.out.println("It was Window sensor!!");
		int min = Math.min(internal, external);
		int max = Math.max(internal, external);
	
		if(min == (this.getHVAC()).getDefaultTemp()){ // The HVAC hasn't changed value yet.
			
			try {

	            TimeUnit.SECONDS.sleep(60); // Waits for a minute before it updates the temp.
				min += 1;
				(this.getHVAC()).setCurrTemp(min);

			} catch (InterruptedException e) {
	            System.err.format("IOException: %s%n", e);
	        }

		}else{
			
			try {

	            TimeUnit.SECONDS.sleep(60);
				max -= 1;
				(this.getHVAC()).setCurrTemp(max);

			} catch (InterruptedException e) {
	            System.err.format("IOException: %s%n", e);
	        }
		}
		
	}

}
