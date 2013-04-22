package com.jmpr.asteroides.util;

import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreBoardPreferences implements ScoreBoard {
	private static String PREFERENCES = "scores";
	private Context context;

	public ScoreBoardPreferences(Context context) {
		this.context = context;
	}

	public void storeNewScore(int score, String name, long date) {
		SharedPreferences preferences = context.getSharedPreferences(
				PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		for (int n = 9; n >= 1; n--) {
			editor.putString("score" + n,
					preferences.getString("score" + (n - 1), ""));
		}
		editor.putString("score0", score + " " + name);

		editor.commit();
	}

	public Vector<String> getScores(int maxResults) {
		Vector<String> result = new Vector<String>();
		SharedPreferences preferences = context.getSharedPreferences(
				PREFERENCES, Context.MODE_PRIVATE);
		for (int n = 0; n <= 9; n++) {
			String s = preferences.getString("score" + n, "");
			if (s != "") {
				result.add(s);
			}
		}

		return result;
	}

}
