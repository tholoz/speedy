package application;

/*
 * GridBagLayoutDemo.java requires no other files.
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main {
	
	public final String path = "Type In a Path For The Dictionnary of Used Words";
	public static AtomicInteger counter = new AtomicInteger(0);
	public static LinkedBlockingQueue<String> forChecking = new LinkedBlockingQueue<String>();
	private static LinkedList<Checker> listCheckers = new LinkedList<Checker>();
	static boolean notFinished = true;
	static ConcurrentHashMap<String,Boolean> alreadyUsed = new ConcurrentHashMap<String,Boolean>();
	static LinkedBlockingQueue<String> goodWords = new LinkedBlockingQueue<String>();
	
private static void initCheckers(int checkers) {
		
		
		for (int i=0;i<checkers;i++) {
			Checker c = new Checker(counter,forChecking, notFinished, GameModes.ADVANCED,alreadyUsed,goodWords);
			listCheckers.add(c);
			c.start();
			}
		
		
		
	}


private static void joinCheckers() {
	
	
	for (Checker c : listCheckers) {
		try {
			c.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	
	
}
	
	public static void main(String args[]) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					createAndShowGUI();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				
//			}
//		});
		
		initCheckers(8);
		boolean started = false;
		ReentrantLock lock = new ReentrantLock();		
		JLabel tempsRestant = new JLabel();
		tempsRestant.setText("30");
		final Chrono chrono = new Chrono(15, tempsRestant, started,lock);
		
		Interface layout = new Interface(started,lock,forChecking,counter,tempsRestant,chrono);
		layout.start();
		chrono.start();
		try {
			chrono.join();
			notFinished=false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		layout.finish(alreadyUsed, GameModes.ADVANCED);
		System.out.println("c'est fini frer");
		
		

		
		
		
		
	}
}