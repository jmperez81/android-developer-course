package com.jmpr.asteroides.util;

import java.util.List;

public interface ScoreBoard {
	public void storeNewScore(int score, String name, long date);
	public List<String> getScores(int maxResults);
}
