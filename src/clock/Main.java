package clock;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){
		
		AlarmClock alarmClock = new AlarmClock();
		while(true){
			System.out.println("Choose from options below:\n1.Set Alarm\n2.Disable Alarm\n3.Disable all alarms and exit");
			Scanner sc = new Scanner(System.in);
			int selection = sc.nextInt();
			switch(selection){
				case 1:	
						System.out.println("Enter the time for the alarm in the format HH:mm:ss Eg 20:30:00\n");
						String input = sc.next();
						if(checkValidInput(input)){
							alarmClock.setAlarm(input);
							System.out.println("Alarm set for "+input+"\n");
						}
						else{
							System.out.println("Not a valid input");
						}
						break;
		
				case 2:
						PriorityQueue<String> alarms = alarmClock.getAllAlarms();
						if(!alarms.isEmpty()){
							System.out.println("No alarm set yet");
						}
						else {
							System.out.println("Select the alarm to disable");
							Iterator<String> it = alarms.iterator();
							while(it.hasNext()){
								System.out.println(it.next());
							}
							alarmClock.disableAlarm(sc.next());
							break;
			}
				case 3:
						System.exit(0);
						break;
				default:
						System.out.println("Not a valid option");
			}
		}
	}
	private static boolean checkValidInput(String time) {
		if(!time.contains(":")) {
			return false;
		}
		String[] splitted = time.split(":");
		if(Integer.parseInt(splitted[0]) >= 24) {
			return false;
		}
		if(Integer.parseInt(splitted[1]) >=60){
			return false;
		}
		if(Integer.parseInt(splitted[2]) >=60){
			return false;
		}
		return true;
	}
	
}
