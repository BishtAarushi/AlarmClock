package clock;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.PriorityQueue;
import taskScheduler.Scheduled;
import taskScheduler.Scheduler;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AlarmClock {
	private static Comparator<String> sortTime = new SortTimeComparator();
	public static PriorityQueue<String> alarms = new PriorityQueue<>(sortTime);
	Scheduler alarmScheuler;
	Thread schedulerThread;
	public AlarmClock(){
		alarmScheuler = new Scheduler(new Scheduled() {
			@Override
			public void scheduledTaskTriggered(){
				playMusic();
			}
		});
			schedulerThread = new Thread(new Runnable(){ //start the scheduler
			@Override
			public void run() {
				alarmScheuler.start();
					
			}
			
		});
		schedulerThread.start();
		
	}
	public void setAlarm(String time){
		alarmScheuler.scheduleTask(time);
		schedulerThread.interrupt();
	}
	public void disableAlarm(String time){
		alarmScheuler.removeTask(time);
		schedulerThread.interrupt();
	}
	public PriorityQueue<String> getAllAlarms() {
		return alarmScheuler.getAllAlarms();
	}
	private static void playMusic(){
		System.out.println("Alarm Triggered");
		try {
	         URL url = AlarmClock.class.getResource("iphone_alarm.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         Clip clip = AudioSystem.getClip();
	         clip.open(audioIn);
	         clip.start();
	         try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	         clip.stop();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	public static int parseInput(String input) {
		String[] splitted = input.split(":");
		String time = splitted[0]+splitted[1];
		return Integer.parseInt(time);
	}
}
class SortTimeComparator implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		if(AlarmClock.parseInput(o1) > AlarmClock.parseInput(o2)){
			return 1;
		}
		else if(AlarmClock.parseInput(o1) < AlarmClock.parseInput(o2)){
			return -1;
		}
		return 0;
	}
}
