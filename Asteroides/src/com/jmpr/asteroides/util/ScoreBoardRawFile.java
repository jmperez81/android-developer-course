package com.jmpr.asteroides.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jmpr.asteroides.R;

import android.content.Context;
import android.util.Log;


public class ScoreBoardRawFile implements ScoreBoard {
	private Context context;

	public ScoreBoardRawFile(Context context) {
		this.context = context;
	}

	@Override
	public void storeNewScore(int score, String name, long date) {		
		// Nothing to do (read only file!)
	}

	@Override
	public List<String> getScores(int maxResults) {				
		Vector<String> result = new Vector<String>();
		try {
			InputStream f = context.getResources().openRawResource(R.raw.scores);
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
