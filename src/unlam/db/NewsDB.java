package unlam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Date;
import android.util.Log;
import unlam.news.News;
import java.util.ArrayList;

public class NewsDB extends SQLiteOpenHelper {

	/* Database */
	private static final int DATABASE_VERSION 	= 11;
	private static final String DATABASE_NAME 	= "unlam_news";

	/* Tables */
	public static final String NEWS 		= "news";

	public NewsDB(Context context, String name, CursorFactory factory, int version) { 
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(	" CREATE TABLE "+NEWS+" (			" +
				" 	id INTEGER PRIMARY KEY AUTOINCREMENT,	" +
				" 	mytext CHAR(250) NOT NULL,		" +
				" 	mydate INTEGER NOT NULL			" +
				" );						" 
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS "+NEWS+";");
		onCreate(db);
	}

	public void storeNews(News n) {
		SQLiteDatabase db 	= this.getWritableDatabase();
		String query2 		= "DELETE FROM "+NEWS+" WHERE mytext='"+n.getText()+"';"; 
		db.execSQL(query2);
		ContentValues values 	= new ContentValues();
		values.put("mytext", 	n.getText());
		values.put("mydate", 	n.getLong());
		db.insert(NEWS, null, values);
		db.close();
	}

	public ArrayList<News> getNews() {
                SQLiteDatabase db = this.getWritableDatabase();
                String query = "SELECT mytext,mydate FROM "+NEWS+" ORDER BY mydate DESC";
                Cursor cursor = db.rawQuery(query, null);
		ArrayList<News> news = new ArrayList<News>();	
                if (cursor.moveToFirst()) {
                        do {
                                String t = cursor.getString(0);
                                int d	 = cursor.getInt(1);
				News n 	 = new News();
				n.setText(t);
				n.setLong(d);
				news.add(n);
                        } while (cursor.moveToNext());
                }
		db.close();
                return news;
	}

	public int count() {
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT mydate FROM "+NEWS,null);
                c.moveToFirst();
                int count = c.getCount();
		db.close();
                return count;
        }

	public void deleteMoreThan(int i) {
		if ( this.count()>i ) {
			SQLiteDatabase db = this.getWritableDatabase();
			String query = "select id from "+NEWS+" order by mydate desc limit "+i+";";
                	Cursor cursor = db.rawQuery(query, null);
                	cursor.moveToLast();
                	int d = cursor.getInt(0);
			String query2 = "DELETE FROM "+NEWS+" WHERE id<"+d+";"; 
			db.execSQL(query2);
			db.close();
		}
	}

} 
