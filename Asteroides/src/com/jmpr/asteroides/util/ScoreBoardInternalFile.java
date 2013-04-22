package com.jmpr.asteroides.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

public class ScoreBoardInternalFile implements ScoreBoard {
	private static String FILE = "scores.txt";
	private Context context;

	public ScoreBoardInternalFile(Context context) {
		this.context = context;
	}

	public void storeNewScore(int score, String name, long date) {
		try {
			FileOutputStream f = context.openFileOutput(FILE,
					Context.MODE_APPEND);
			String text = score + " " + name + "\n";
			f.write(text.getBytes());
			f.close();
		} catch (Exception e) {
			Log.e("Asteroids", e.getMessage(), e);
		}
	}

	public Vector<String> getScores(int maxResults) {
		Vector<String> result = new Vector<String>();
		try {
			FileInputStream f = context.openFileInput(FILE);
			BufferedReader input = new BufferedReader(new InputStreamReader(f));
			int n = 0;
			String line;
			do {
				line = input.readLine();
				if (line != null) {
					result.add(line);
					n++;
				}
			} while (n < maxResults && line != null);
			f.close();
		} catch (Exception e) {
			Log.e("Asteroids", e.getMessage(), e);
		}
		return result;
	}

}
