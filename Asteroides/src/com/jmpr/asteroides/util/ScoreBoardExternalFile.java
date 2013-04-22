package com.jmpr.asteroides.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ScoreBoardExternalFile implements ScoreBoard {
	private static String FILE = Environment.getExternalStorageDirectory() + "/scores.txt";
	private Context context;

	public ScoreBoardExternalFile(Context context) {
		this.context = context;
	}

	public void storeNewScore(int score, String name, long date) {
		// Checks external storage writable 
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(context, "External storage not available", Toast.LENGTH_LONG).show();
			return;
		}
		
		try {
			FileOutputStream f = new FileOutputStream(FILE, true);
			String text = score + " " + name + "\n";
			f.write(text.getBytes());
			f.close();
		} catch (Exception e) {
			Log.e("Asteroids", e.getMessage(), e);
		}
	}

	public Vector<String> getScores(int maxResults) {
		Vector<String> result = new Vector<String>();
		
		// Checks external storage readable 
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			Toast.makeText(context, "External storage not available", Toast.LENGTH_LONG).show();
			return result;
		}

		try {
			FileInputStream f = new FileInputStream(FILE);
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
