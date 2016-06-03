package com.exa.android.softkeyboard;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CommentsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
     MySQLiteHelper.COLUMN_SCREEN,MySQLiteHelper.COLUMN_LOGGER,MySQLiteHelper.COLUMN_DATA ,
     MySQLiteHelper.COLUMN_SEND};

  public CommentsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public void update(String[] ids){
	  
	  ContentValues values = new ContentValues();
	  values.put("send", "1");
    
		for(String i: ids)
        	database.execSQL("update logger set send='1' where _id ='"+i+"'; ");	
	  
	// database.update(MySQLiteHelper.TABLE_LOGGER, values, "_id=?", ids);
	  	  
  }
  
  public Comment createComment(String screen,String comment,String data,int send) {
    ContentValues values = new ContentValues();
    
    values.put(MySQLiteHelper.COLUMN_SCREEN, screen);
    values.put(MySQLiteHelper.COLUMN_LOGGER, comment);
    values.put(MySQLiteHelper.COLUMN_DATA, data);
    values.put(MySQLiteHelper.COLUMN_SEND, send);
    
    long insertId = database.insert(MySQLiteHelper.TABLE_LOGGER, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGGER,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Comment newComment = cursorToComment(cursor);
    cursor.close();
    return newComment;
  }

  public void deleteComment(Comment comment) {
    long id = comment.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_LOGGER, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

    
  public List<Comment> getAllComments() {
    List<Comment> comments = new ArrayList<Comment>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGGER,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Comment comment = cursorToComment(cursor);
      comments.add(comment);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return comments;
    
    
  }

  private Comment cursorToComment(Cursor cursor) {
    Comment comment = new Comment();
    comment.setId(cursor.getLong(0));
    comment.setScreen(cursor.getString(1));
    comment.setComment(cursor.getString(2));
    comment.setData(cursor.getString(3));
    comment.setSend(cursor.getLong(4));
       
    return comment;
  }
} 
