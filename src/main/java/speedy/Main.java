package speedy;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world!");
	
	
	// These code snippets use an open-source library. http://unirest.io/java
	try {
	HttpResponse<JsonNode> response = Unirest.get("https://montanaflynn-spellcheck.p.mashape.com/check/?text=This+sentnce+has+some+probblems.")
	.header("X-Mashape-Key", "pZfvRi4Qodmshkq5DvbkEGACNLZvp1rwasCjsnEQRySCqOuQn0")
	.header("Accept", "application/json")
	.asJson();
	
	JSONObject obj = response.getBody().getObject();
	String msg = obj.getString("original");
	JSONObject obj2 = obj.getJSONObject("corrections");
	JSONArray arr = obj2.getJSONArray("sentnce");
	
	System.out.println(obj2);
	}catch (Exception e) {
		e.printStackTrace();
	}
	}
}