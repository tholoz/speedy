package speedy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Checker extends Thread{
	
	AtomicInteger count;
	private final BlockingQueue<String> queue;
	boolean notFinished;
	
	public Checker(AtomicInteger count, BlockingQueue<String> queue, boolean notFinished) {
		this.count = count;
		this.queue = queue;
		this.notFinished = notFinished;
	}
	
	public static boolean check(String word) {
		try {
			
		HttpResponse<JsonNode> response = Unirest.get("https://montanaflynn-spellcheck.p.mashape.com/check/?text="+word)
		.header("X-Mashape-Key", "pZfvRi4Qodmshkq5DvbkEGACNLZvp1rwasCjsnEQRySCqOuQn0")
		.header("Accept", "application/json")
		.asJson();
		
		JSONObject jsobj = response.getBody().getObject();
		JSONObject corr = jsobj.getJSONObject("corrections");
		
		return(corr.toString().length()==2);
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