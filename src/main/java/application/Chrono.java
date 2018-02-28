package application;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Chrono {
	
	Timer t;
	int iTimeUp;
	JLabel lCountTime;
	
	
	public Chrono(int time, JLabel count) {
		iTimeUp = time;
		lCountTime = count;
		t = new Timer(1000, new ActionListener() { 
			   public void actionPerformed(ActionEvent e) {
				  lCountTime.setText(Integer.toString(Chrono.this.iTimeUp)); 
			      iTimeUp--;
			      if (iTimeUp==0) t.stop();
			   } 
			}); 

	}
	
		
	
	
}
