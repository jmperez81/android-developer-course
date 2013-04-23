package com.jmpr.asteroides.util;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreBoardDatabase extends SQLiteOpenHelper implements ScoreBoard {
	public ScoreBoardDatabase(Context context) {
		// A cursor is not needed here, so we pass null
		super(context, "scores", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// This method will be called only if the db needs to be created
		db.execSQL("CREATE TABLE scores ("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "score INTEGER, name TEXT, date LONG)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// In case of a new version we will update tables here
	}

	@Override
	public void storeNewScore(int score, String name, long date) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO scores VALUES ( null, " + score + ", '"
				+ name + "', " + date + ")");

	}

	@Override
	public List<String> getScores(int maxResults) {
		Vector<String> result = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db
				.rawQuery("SELECT score, name FROM "
						+ "scores ORDER BY score DESC LIMIT "
						+ maxResults, null);
		
		// Alternatively we can use query()
		// String[] fields = {"score", "name"};
		// Cursor cursor=db.query("scores", fields, null, null,
		//          null, null, "score DESC", Integer.toString(maxResults));
		
		while (cursor.moveToNext()) {
			result.add(cursor.getInt(0) + " " + cursor.getString(1));
		}
		cursor.close();
		return result;
	}
}
