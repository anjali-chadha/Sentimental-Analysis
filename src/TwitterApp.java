/**
 * This Class is used to get the list of status.
 */

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterApp {

	private static String consumerKey = "AStf2JrtDyMe6X9rgk1I8JyZ4";
	private static String consumerSecret = "2EsQjlXj5Zp149sy44HbBmT7rix8snjomegJsiA6y5ocEcVH20";
	private static String accessToken = "118423332-yqaCiwRmA7zNDFJkwRYhpTroPNdlJqqBri4BaVqH";
	private static String accessTokenSecret = "VtvLhLzmLW73iEkq5JhTiFYykVoVMMSOA2w41qAcbAoYE";
	private static String input = "#Greece";
	private static JSONObject object = new JSONObject();

	public static JSONObject getSearchResults() {
		return object;
	}
	
	public static void twitterProcess() {

		TwitterApp tapp = new TwitterApp();
		JSONArray array = new JSONArray();
		tapp.object.put("result", array);
		Twitter twitter = new TwitterFactory().getInstance();

		//  My Applications Consumer and Auth Access Token
		twitter.setOAuthConsumer(consumerKey, consumerSecret );
		twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));

		try {
			Query query = new Query(input);
			query.setLocale("en");
			query.setLang("en");

			QueryResult result;
			int i = 0;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					JSONObject o = new JSONObject();
					o.put("text", tweet.getText());
					o.put("date", "" + tweet.getCreatedAt().getTime());
					array.add(o);
					System.out.println("@ " + i++ + " " + tweet.getUser().getScreenName() + " - " + tweet.getText());
				}
			} while (i < 50);
		}catch(TwitterException e ){
			e.printStackTrace();
			System.out.println("Failed to search tweets: " + e.getMessage());
			System.exit(-1);
		}
	}
}
