package application;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class Checker extends Thread{

	AtomicInteger count;
	private final BlockingQueue<String> queue;
	volatile boolean notFinished;
	static ConcurrentHashMap<String,Boolean> alreadySeen;
	static BlockingQueue<String> goodWords;

	public Checker(AtomicInteger count, BlockingQueue<String> queue, boolean notFinished, GameModes mode, ConcurrentHashMap<String,Boolean>alreadySeen, BlockingQueue<String> goodWords) {
		this.count = count;
		this.queue = queue;
		this.notFinished = notFinished;
		this.goodWords=goodWords;
		String line;
		this.alreadySeen = alreadySeen;
		if (mode==GameModes.ADVANCED) {
			try {
			File seen = new File("AlreadyUsed.txt");
			if (!seen.exists()) {
			seen.createNewFile();}
			BufferedReader breader = null;
			
				breader = new BufferedReader(new FileReader(seen));

				while ((line = breader.readLine())!=null) {
					alreadySeen.put(line, true);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean check(String word) {
		try {
		HttpResponse<JsonNode> response = Unirest.get("https://montanaflynn-spellcheck.p.mashape.com/check/?text="+word)
		.header("X-Mashape-Key", "pZfvRi4Qodmshkq5DvbkEGACNLZvp1rwasCjsnEQRySCqOuQn0")
		.header("Accept", "application/json")
		.asJson();

		JSONObject jsobj = response.getBody().getObject();
		JSONObject corr = jsobj.getJSONObject("corrections");

		if (corr.toString().length()==2) {
			if (alreadySeen.containsKey(word))return false;
			else {
				alreadySeen.put(word, true);
				goodWords.put(word);
				return true;
			}
		}
		else return false;
		}catch (Exception e) {
			e.printStackTrace();
			return(false);
		}
	}

	public void run(){
		try {
			while(notFinished) {
				String word = queue.take();
				int length = word.length();
				if(check(word)) {
					count.getAndAdd(length);
				}
				else {
					count.getAndAdd(-length);
				}
			}

		}catch(InterruptedException e) {e.printStackTrace();}

	}
}