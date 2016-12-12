package unlam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import unlam.cal.Cluster;
import unlam.cal.Event;
import java.util.Date;
import android.util.Log;
import java.lang.System;
import unlam.cal.Calendario;

public class CalendarDB extends SQLiteOpenHelper {

	/* Database */
	private static final int DATABASE_VERSION 	= 13;
	private static final String DATABASE_NAME 	= "unlam_calendar";

	/* Tables */
	public static final String CLUSTERS		= "cluster";
	public static final String COMMENTS		= "comments";
	public static final String EVENTS		= "events";

	public CalendarDB(Context context, String name, CursorFactory factory, int version) { 
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		/* Main tables */
		db.execSQL(	" CREATE TABLE "+CLUSTERS+" (								" +
				" 	id INTEGER PRIMARY KEY AUTOINCREMENT,						" +
				" 	title CHAR(100) NOT NULL,							" +
				" 	type INTEGER NOT NULL DEFAULT 0							" +
				" );											" 
		);
		db.execSQL(	" CREATE TABLE "+COMMENTS+" (								" +
				"	id INTEGER PRIMARY KEY AUTOINCREMENT,						" +
				"	text CHAR(255) NOT NULL,							" +
				"	cluster_id INTEGER REFERENCES "+CLUSTERS+"(id) ON DELETE CASCADE 		" +
				" );											" 
		);
		db.execSQL(	" CREATE TABLE "+EVENTS+" (								" +
				"	id INTEGER PRIMARY KEY AUTOINCREMENT,						" +
				"	title CHAR(100) NOT NULL,							" +
				"	subtitle CHAR(100) NOT NULL,							" +
				"	type INTEGER NOT NULL DEFAULT 0,						" +
				"	date INTEGER NOT NULL DEFAULT 0,						" +
				"	cluster_id INTEGER REFERENCES "+CalendarDB.CLUSTERS+"(id) ON DELETE CASCADE 	" +
				" );											" 
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS "+CLUSTERS+";");
		db.execSQL("DROP TABLE IF EXISTS "+COMMENTS+";");
		db.execSQL("DROP TABLE IF EXISTS "+EVENTS+";");
		onCreate(db);
	}

	public void createCluster(Cluster cluster) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values;

		values = new ContentValues();
		values.put("title", 		cluster.getTitle());
		values.put("type",  		cluster.getType());
		long cluster_id = db.insert(CalendarDB.CLUSTERS, null, values);

		for (String s: cluster.getComments()) {
			values = new ContentValues();
			values.put("text", s);
			values.put("cluster_id", cluster_id);
			db.insert(CalendarDB.COMMENTS, null, values);
		}

		db.close();

		for (Event e: cluster.getEvents()) {
			createEvent(e,cluster_id);
		}
	}

	public void createEvent(Event event,long cluster_id) {
		SQLiteDatabase db = this.getWritableDatabase();	
		ContentValues values = new ContentValues();
		values.put("title", 	 event.getTitle());
		values.put("type",  	 event.getType());
		values.put("subtitle", 	 event.getSubtitle());
		values.put("date", 	 event.getLong());
		values.put("cluster_id", cluster_id);
		db.insert(CalendarDB.EVENTS, null, values);
		db.close();
	}

	public Calendario getCalendar() {
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM "+CalendarDB.CLUSTERS;
		Cursor cursor = db.rawQuery(query, null);	
		if (cursor.moveToFirst()) {
        		do {
				int id = cursor.getInt(0);
				clusters.add(getCluster(id));
        		} while (cursor.moveToNext());
    		}	
		db.close();
		Calendario cal = new Calendario();
		cal.setCalendar(clusters);
		return cal;
	}

	public Cluster getCluster(int cluster_id) {
		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor cursor = db.query(CLUSTERS, new String[] {"title","type"}, "id=?", new String[] { String.valueOf(cluster_id) }, null, null, null, null);
    		if (cursor != null)
        		cursor.moveToFirst();
		Cluster cluster = new Cluster();
		cluster.setTitle(cursor.getString(0));
		cluster.setEvents(getEvents(cluster_id));
		cluster.setID(cluster_id);
		cluster.setComments(getComments(cluster_id));
		db.close();
		return cluster;
	}

	public ArrayList<Event> getEvents(int cluster_id) {
		ArrayList<Event> events = new ArrayList<Event>();
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT id FROM "+CalendarDB.EVENTS+" WHERE cluster_id="+cluster_id;
		Cursor cursor = db.rawQuery(query, null);	
		if (cursor.moveToFirst()) {
        		do {
				int id = cursor.getInt(0);
				events.add(getEvent(id));
        		} while (cursor.moveToNext());
    		}	
		db.close();
		return events;
	}

	public ArrayList<String> getComments(int cluster_id) {
		ArrayList<String> comments = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT text FROM "+COMMENTS+" WHERE cluster_id="+cluster_id;
		Cursor cursor = db.rawQuery(query, null);	
		if (cursor.moveToFirst()) {
        		do {
				comments.add(cursor.getString(0));
        		} while (cursor.moveToNext());
    		}	
		db.close();
		return comments;
	}

	public Event getEvent(int event_id) {
		SQLiteDatabase db = this.getReadableDatabase();
    		Cursor cursor = db.query(EVENTS, new String[] {"title","subtitle","date","type"}, 
						"id=?", new String[] { String.valueOf(event_id) }, null, null, null, null);
    		if (cursor != null)
        		cursor.moveToFirst();
		Event event = new Event();
		event.setTitle(cursor.getString(0));
		event.setSubtitle(cursor.getString(1));
		event.setLong(cursor.getLong(2));
		event.setType(cursor.getInt(3));
		db.close();
		return event;
	}

	public void clean() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM "+CLUSTERS+";");
		db.execSQL("DELETE FROM "+COMMENTS+";");
		db.execSQL("DELETE FROM "+EVENTS+";");
		db.close();
	}

	public int count() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id FROM "+CalendarDB.CLUSTERS,null);
		c .moveToFirst();
		int count = c.getCount();
		db.close();
		return count;
	}
	
} 
