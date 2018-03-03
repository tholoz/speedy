package speedy;

import org.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Checker {
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
}
