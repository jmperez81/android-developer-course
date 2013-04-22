package com.jmpr.asteroides.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class ScoreBoardXmlSAX implements ScoreBoard {
	private static String FILE = "xmlscores.xml";	// Need to copy this file from /res/raw to /data/data/<yourpackage>/files
	private Context context;
	private ScoreBoardXml list;
	private boolean isLoadedList;

	public ScoreBoardXmlSAX(Context context) {
		this.context = context;
		list = new ScoreBoardXml();
		isLoadedList = false;
	}

	@Override
	public void storeNewScore(int score, String name, long date) {
		try {
			if (!isLoadedList) {
				list.readXML(context.openFileInput(FILE));
			}
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			Log.e("Asteroids", e.getMessage(), e);
		}
		list.newElement(score, name, date);
		try {
			list.writeXML(context.openFileOutput(FILE,
					Context.MODE_PRIVATE));
		} catch (Exception e) {
			Log.e("Asteroids", e.getMessage(), e);
		}
	}

	@Override
	public Vector<String> getScores(int maxResults) {
		try {
			if (!isLoadedList) {
				list.readXML(context.openFileInput(FILE));
			}
		} catch (Exception e) {
			Log.e("Asteroids", e.getMessage(), e);
		}
		return list.aVectorString();
	}

	private class ScoreBoardXml {

		private class Score {
			int value;
			String name;
			long date;
		}

		private List<Score> scoreList;

		public ScoreBoardXml() {
			scoreList = new ArrayList<Score>();
		}

		public void newElement(int score, String name, long date) {
			Score scoreObj = new Score();
			scoreObj.value = score;
			scoreObj.name = name;
			scoreObj.date = date;
			scoreList.add(scoreObj);
		}

		public Vector<String> aVectorString() {
			Vector<String> result = new Vector<String>();
			for (Score scoreIter : scoreList) {
				result.add(scoreIter.name + " " + scoreIter.value);
			}
			return result;
		}

		public void readXML(InputStream input) throws Exception {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			XmlHandler xmlHandler = new XmlHandler();
			reader.setContentHandler(xmlHandler);
			reader.parse(new InputSource(input));
			isLoadedList = true;
		}

		class XmlHandler extends DefaultHandler {
			private StringBuilder string;
			private Score score;

			@Override
			public void startDocument() throws SAXException {
				scoreList = new ArrayList<Score>();
				string = new StringBuilder();
			}

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes atr) throws SAXException {
				string.setLength(0);
				if (localName.equals("score")) {
					score = new Score();
					score.date = Long.parseLong(atr.getValue("date"));
				}
			}

			@Override
			public void characters(char ch[], int start, int len) {
				string.append(ch, start, len);
			}

			@Override
			public void endElement(String uri, String localName,
					String qName) throws SAXException {
				if (localName.equals("value")) {
					score.value = Integer.parseInt(string.toString());
				} else if (localName.equals("name")) {
					score.name = string.toString();
				} else if (localName.equals("scoreboard")) {
					scoreList.add(score);
				}
			}

			@Override
			public void endDocument() throws SAXException {
			}
		}

		public void writeXML(OutputStream output) {
			XmlSerializer serializer = Xml.newSerializer();
			try {
				serializer.setOutput(output, "UTF-8");
				serializer.startDocument("UTF-8", true);
				serializer.startTag("", "scoreboard");
				for (Score score : scoreList) {
					serializer.startTag("", "score");
					serializer.attribute("", "date",
							String.valueOf(score.date));
					serializer.startTag("", "name");
					serializer.text(score.name);
					serializer.endTag("", "name");
					serializer.startTag("", "value");
					serializer.text(String.valueOf(score.value));
					serializer.endTag("", "value");
					serializer.endTag("", "score");
				}
				serializer.endTag("", "scoreboard");
				serializer.endDocument();
			} catch (Exception e) {
				Log.e("Asteroids", e.getMessage(), e);
			}
		}
	}
}
