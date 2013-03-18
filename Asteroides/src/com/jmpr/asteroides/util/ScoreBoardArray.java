package com.jmpr.asteroides.util;

import java.util.ArrayList;
import java.util.List;


public class ScoreBoardArray implements ScoreBoard {
	private List<String> scores;

	public ScoreBoardArray() {
		scores = new ArrayList<String>();
		scores.add("123000 John");
		scores.add("103000 Mary");
	}

	@Override
	public void storeNewScore(int score, String name, long date) {
		scores.add(0, score + " " + name);

	}

	@Override
	public List<String> getScores(int maxResults) {
		if (maxResults > 0 && maxResults > scores.size()) {
			return scores;
		} else {
			return scores.subList(0,  maxResults - 1);
		}
	}

}
