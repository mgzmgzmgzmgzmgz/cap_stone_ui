package cap_stone;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherRouter {

	private static int currWeather = 70; // Degrees in Fahrenheit.
	private static int maxWeather = 79; // Degrees in Fahrenheit.
	private static int minWeather = 55; // Degrees in Fahrenheit.
	
	
	
	WeatherRouter(){
		System.out.println("The WeatherRouther has started.... The current weather is now " + this.getCurrWeather() + "degrees.");
		this.watchWeather();
		
	}
	
	void watchWeather(){
		 //Code structure provided by "Boris the Spider" via https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	   	executorService.scheduleAtFixedRate(WeatherRouter::randomWeather, 0, 60, TimeUnit.SECONDS); // Every minute, the weather is checked.
	   	//Should Hvac do this?
	   	
	}
	
	@SuppressWarnings("deprecation")
	static void randomWeather(){
		
		System.out.println("Random weather has started!");
		
		/* Pseudo Code: Weather [DO NOT DELETE]
		 * - Every minute the weather is checked.
		 * - There is a chance that the weather either increases or decreases.
		 * ---| Advanced: decrease can be due to nighttime or rainy weather. increase due to sunrise until noon. ************GOOD!
		 * - The temp cannot inc/dec forever, thus there must be a cut off for the max/min temperatures.
		 * - Should the temperature be at max, then it starts decreasing instead OR stays the same temp.
		 * ---|Same for reaching minimum temp.*/
		
		Date date = new Date();		
		
		if(date.getHours() >= 7 && date.getHours() < 17 ){ //if = to 7am, and between this and 5pm, then daytime. otherwise night time.
			System.out.println("It's daytime! Will the temperature increase?");
			increaseTemp();
			
		}else{
			
			decreaseTemp();
			
		}
	}
	
	static void increaseTemp(){
		
		if(currWeather == maxWeather ){	// If the weather is at max, do nothing.
			System.out.println("Nope... The temperature is at max.");
			// Do nothing. Temperature is at peak.
			
		}else{ 							// Otherwise, there is a 20% chance in the temperature increasing.
			Random rand = new Random();
			System.out.println("Randomizing.....");
			if(rand.nextInt(7) == 1){
				currWeather++;
				System.out.println("Yes! The temperature increased to " + getCurrWeather() + "degrees.");
			}else{
				System.out.println("No.. the temperature remains at " + getCurrWeather()+ " degrees.");
			}
		}
	}
	
	static void decreaseTemp(){
		
		if(currWeather == minWeather ){	// If the weather is at min, do nothing.
			System.out.println("Nope... The temperature is at min.");
			// Do nothing. Temperature is at lowest point.
			
		}else{ 							// Otherwise, there is a 20% chance in the temperature deccreasing.
			Random rand = new Random();
			
			System.out.println("Randomizing.....");
			if(rand.nextInt(7) == 1){
				currWeather--;
				System.out.println("Yes! The temperature decreased to " + getCurrWeather() + "degrees.");
				
			}else{
				System.out.println("No.. the temperature remains at " + getCurrWeather()+ " degrees.");
			}
		}
	}
	
	/**Retrieves the current weather for the day. (For now
	 * this value is hard coded at 80 degrees Fahrenheit.)*/
	public static int getCurrWeather(){
		return currWeather;
	}
	
	public void setCurrWeather(int newTemperatureOutside){
		currWeather = newTemperatureOutside;
	}
	
	
	public static void main(String[] args){
		
		WeatherRouter w = new WeatherRouter();
		
	}
}
