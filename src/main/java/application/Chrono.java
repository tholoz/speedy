package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.swing.JLabel;
import javax.swing.Timer;



public class Chrono extends Thread{
	
	Timer t;
	int iTimeUp;
	JLabel lCountTime;
	Condition start;
	volatile boolean started;
	Lock lock;
	
	public void run() {
		t = new Timer(1000, new ActionListener() { 
			   public void actionPerformed(ActionEvent e) {
				  lCountTime.setText(Integer.toString(Chrono.this.iTimeUp)); 
			      iTimeUp--;
			      if (iTimeUp==0) {
			    	  t.stop();
			      }
			   } 
			});
		
		synchronized(lock) {
		try {
				System.out.println("waiting");

				lock.wait();
			} catch (InterruptedException e1) {
				System.out.println("interrompu");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println("Chrono started");
		t.start();
		

		try {
			Thread.sleep((long) 1000*iTimeUp);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	public Chrono(int time, JLabel count, boolean started, Lock lock) {
		iTimeUp = time;
		lCountTime = count;
		this.start= start;
		this.started = started;
		this.lock= lock;
		
	}
	
		
	
	
}
