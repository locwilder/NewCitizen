package com.example.newcitizen;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Databasehandler extends SQLiteOpenHelper {

	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "citizendbtest";
 
    // Table Names
    private static final String TABLE_TODO = "todos";
    private static final String TABLE_PLACE = "place";
    private static final String TABLE_TODO_PLACE = "todo_place";
    private static final String TABLE_USER ="user";
 
    // Common column names
    private static final String KEY_ID = "_id";
    private static final String KEY_CREATED_AT = "created_at";
 
    // NOTES Table - column names
    private static final String KEY_TODODESC = "tododesc";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DEADLINE_AT = "deadline_at";
    private static final String KEY_CATEGORY ="category";
    private static final String KEY_REMARKS	= "remarks";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_STREET = "street";
    private static final String KEY_AREACODE ="areacode";
    private static final String KEY_CITY = "city";
    private static final String KEY_DOCUMENTS = "documents";
    
 
    // Place Table - column names
    private static final String KEY_PLACE_NAME = "place_name";
    private static final String KEY_PLACE_ADRESS = "place_adress";
    private static final String KEY_PLACE_STREET = "place_street";
    private static final String KEY_PLACE_AREACODE ="place_areacode";
    private static final String KEY_PLACE_CITY = "place_city";
 
    // NOTE_WhereS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_PLACE_ID = "place_id";
    
    //logindata
    
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_USER_EMAIL = "user_email";
    
    
    
    // Table Create Statements
    // Todo table create statement
    
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODODESC
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME," + KEY_DEADLINE_AT + " DATETIME," + KEY_CATEGORY + " TEXT,"
            + KEY_REMARKS + " TEXT," + KEY_PRIORITY +" INTEGER," + KEY_STREET + " TEXT," + KEY_AREACODE + " TEXT,"
            + KEY_CITY + " TEXT," + KEY_DOCUMENTS + " TEXT" +")";
    
    // Place table create statement
    private static final String CREATE_TABLE_PLACE = "CREATE TABLE " 
    		+ TABLE_PLACE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLACE_NAME 
    		+ " TEXT," + KEY_CREATED_AT + " DATETIME," + KEY_PLACE_ADRESS + " TEXT,"
            + KEY_PLACE_STREET + " TEXT," + KEY_PLACE_AREACODE + " TEXT,"
            + KEY_PLACE_CITY + " TEXT" + ")";
    
    // Todo-Place table create statement
    private static final String CREATE_TABLE_TODO_PLACE = "CREATE TABLE "
            + TABLE_TODO_PLACE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_PLACE_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";
    
    // User table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " 
    + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_USER_NAME + " TEXT,"+ KEY_USER_PASSWORD +" TEXT,"+ KEY_USER_EMAIL +" TEXT" +")";
 
    
    
	public Databasehandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_PLACE);
        db.execSQL(CREATE_TABLE_TODO_PLACE);
        db.execSQL(CREATE_TABLE_USER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_PLACE);
		
     // create new tables
        onCreate(db);
	}
	
	/*
	 * Creating a todo
	 */
	public long createToDo(Todo todo, long[] where_ids) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    	    
	    ContentValues values = new ContentValues();
	    values.put(KEY_TODODESC, todo.getTododesc());
	    values.put(KEY_STATUS, todo.getStatus());
	    values.put(KEY_CREATED_AT, getDateTime());
	    values.put(KEY_DEADLINE_AT , todo.getDeadline_at());
	    values.put(KEY_CATEGORY , todo.getCategory());
	    values.put(KEY_REMARKS , todo.getRemarks());
	    values.put(KEY_PRIORITY , todo.getPriority());
	    values.put(KEY_STREET, todo.getStreets());
	    values.put(KEY_AREACODE, todo.getAreacode());
	    values.put(KEY_CITY, todo.getCity());
	    values.put(KEY_DOCUMENTS, todo.getDocuments());
	    
	    
	 
	    // insert row
	    long todo_id = db.insert(TABLE_TODO, null, values);
	 
	    // assigning where to todo
//	    for (long where_id : where_ids) {
//	        createTodoWhere(todo_id, where_id);
//	    }
	 
	    return todo_id;
	}
	
	
	/*
	 * get single todo - SELECT * FROM todos WHERE id = 1;
	 */
	public Todo getTodo(long todo_id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
	            + KEY_ID + " = " + todo_id;
	 
	    Log.e(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	        c.moveToFirst();
	 
	    Todo td = new Todo();
	    td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	    td.setTododesc((c.getString(c.getColumnIndex(KEY_TODODESC))));
	    td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	    td.setStatus(Integer.parseInt(c.getString(c.getColumnIndex(KEY_STATUS))));
	    td.setDeadlineAt(c.getString(c.getColumnIndex(KEY_DEADLINE_AT)));
	    td.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
	    td.setRemarks(c.getString(c.getColumnIndex(KEY_REMARKS)));
	    td.setPriority(Integer.parseInt(c.getString(c.getColumnIndex(KEY_PRIORITY))));
	    td.setStreets((c.getString(c.getColumnIndex(KEY_STREET))));
	    td.setAreacode(c.getString(c.getColumnIndex(KEY_AREACODE)));
	    td.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
	    td.setDocuments(c.getString(c.getColumnIndex(KEY_DOCUMENTS)));
	 
	    return td;
	}
	public Todo getTodobyDesc(String todo_desc) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
	            + KEY_TODODESC + " = " + "'"+todo_desc+"'";
	 
	    Log.e(LOG, selectQuery);
	 
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    if (c != null)
	        c.moveToFirst();
	 
	    Todo td = new Todo();
	    td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	    td.setTododesc((c.getString(c.getColumnIndex(KEY_TODODESC))));
	    td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	    td.setStatus(Integer.parseInt(c.getString(c.getColumnIndex(KEY_STATUS))));
	    td.setDeadlineAt(c.getString(c.getColumnIndex(KEY_DEADLINE_AT)));
	    td.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
	    td.setRemarks(c.getString(c.getColumnIndex(KEY_REMARKS)));
	    td.setPriority(Integer.parseInt(c.getString(c.getColumnIndex(KEY_PRIORITY))));
	    td.setStreets((c.getString(c.getColumnIndex(KEY_STREET))));
	    td.setAreacode(c.getString(c.getColumnIndex(KEY_AREACODE)));
	    td.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
	    td.setDocuments(c.getString(c.getColumnIndex(KEY_DOCUMENTS)));
	 
	    return td;
	}
	
	/*
	 * getting all todos -  SELECT * FROM todos;
	 * */
	public List<Todo> getAllToDos() {
	    List<Todo> todos = new ArrayList<Todo>();
	    String selectQuery = "SELECT  * FROM " + TABLE_TODO;
	 
	    Log.e(LOG, selectQuery);
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	            Todo td = new Todo();
	            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	    	    td.setTododesc((c.getString(c.getColumnIndex(KEY_TODODESC))));
	    	    td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	    	    td.setStatus(Integer.parseInt(c.getString(c.getColumnIndex(KEY_STATUS))));
	    	    td.setDeadlineAt(c.getString(c.getColumnIndex(KEY_DEADLINE_AT)));
	    	    td.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
	    	    td.setRemarks(c.getString(c.getColumnIndex(KEY_REMARKS)));
	    	    td.setPriority(Integer.parseInt(c.getString(c.getColumnIndex(KEY_PRIORITY))));
	    	    td.setStreets((c.getString(c.getColumnIndex(KEY_STREET))));
	    	    td.setAreacode(c.getString(c.getColumnIndex(KEY_AREACODE)));
	    	    td.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
	    	    td.setDocuments(c.getString(c.getColumnIndex(KEY_DOCUMENTS)));
	 
	            // adding to todo list
	            todos.add(td);
	        } while (c.moveToNext());
	    }
	 
	    return todos;
	}
	
	/*
	 * getting all todos under single where
	 * SELECT * FROM todos td, tags tg, todo_tags tt WHERE tg.tag_name = "Watchlist" AND tg.id = tt.tag_id AND td.id = tt.todo_id;
	 * */
	public List<Todo> getAllToDosByWhere(String name) {
	    List<Todo> todos = new ArrayList<Todo>();
	 
	    String selectQuery = "SELECT  * FROM " + TABLE_TODO + " td, "
	            + TABLE_PLACE + " tw, " + TABLE_TODO_PLACE + " tt WHERE tw."
	            + KEY_PLACE_NAME + " = '" + name + "'" + " AND tw." + KEY_ID
	            + " = " + "tt." + KEY_PLACE_ID + " AND td." + KEY_ID + " = "
	            + "tt." + KEY_TODO_ID;
	 
	    Log.e(LOG, selectQuery);
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	            Todo td = new Todo();
	            td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	    	    td.setTododesc((c.getString(c.getColumnIndex(KEY_TODODESC))));
	    	    td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	    	    td.setStatus(Integer.parseInt(c.getString(c.getColumnIndex(KEY_STATUS))));
	    	    td.setDeadlineAt(c.getString(c.getColumnIndex(KEY_DEADLINE_AT)));
	    	    td.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
	    	    td.setRemarks(c.getString(c.getColumnIndex(KEY_REMARKS)));
	    	    td.setPriority(Integer.parseInt(c.getString(c.getColumnIndex(KEY_PRIORITY))));
	    	    td.setStreets((c.getString(c.getColumnIndex(KEY_STREET))));
	    	    td.setAreacode(c.getString(c.getColumnIndex(KEY_AREACODE)));
	    	    td.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
	    	    td.setDocuments(c.getString(c.getColumnIndex(KEY_DOCUMENTS)));
	 
	            // adding to todo list
	            todos.add(td);
	        } while (c.moveToNext());
	    }
	 
	    return todos;
	}
	
	/*
	 * Updating a todo
	 */
	public int updateToDo(Todo todo) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_TODODESC, todo.getTododesc());
	    values.put(KEY_STATUS, todo.getStatus());
	    values.put(KEY_DEADLINE_AT, todo.getDeadline_at());
	    values.put(KEY_STATUS, todo.getStatus());
	    values.put(KEY_PRIORITY, todo.getPriority());
	    values.put(KEY_REMARKS, todo.getRemarks());
	    values.put(KEY_CATEGORY, todo.getCategory());
	    values.put(KEY_STREET, todo.getStreets());
	    values.put(KEY_AREACODE, todo.getAreacode());
	    values.put(KEY_CITY, todo.getCity());
	    values.put(KEY_DOCUMENTS, todo.getDocuments());
	    
	 
	    // updating row
	    return db.update(TABLE_TODO, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(todo.getId()) });
	}
	
	/*
	 * Deleting a todo
	 */
	public void deleteToDo(long todo_id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_TODO, KEY_ID + " = ?",
	            new String[] { String.valueOf(todo_id) });
	}
	
	/*
	 * Creating where
	 */
	public long createWhere(Place place) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	   
	    
	    ContentValues values = new ContentValues();
	    values.put(KEY_PLACE_NAME, place.getWhere_name());
	    values.put(KEY_CREATED_AT, getDateTime());
	    values.put(KEY_PLACE_ADRESS, place.getWhere_adress());
	    values.put(KEY_PLACE_AREACODE, place.getWhere_areacode());
	    values.put(KEY_PLACE_CITY, place.getWhere_city());
	    values.put(KEY_PLACE_STREET, place.getWhere_street());
	 
	    
	    // insert row
	    long where_id = db.insertWithOnConflict(TABLE_PLACE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
	 

	    return where_id;
	}
	
	/**
	 * getting all wheres - SELECT * FROM where;
	 * */
	public List<Place> getAllWheres() {
	    List<Place> places = new ArrayList<Place>();
	    String selectQuery = "SELECT  * FROM " + TABLE_PLACE;
	 
	    Log.e(LOG, selectQuery);
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	            Place t = new Place();
	            t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	            t.setWhere_name(c.getString(c.getColumnIndex(KEY_PLACE_NAME)));
	 
	            // adding to where list
	            places.add(t);
	        } while (c.moveToNext());
	    }
	    return places;
	}
	
	/*
	 * Updating a where
	 */
	public int updateWhere(Place place) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_PLACE_NAME, place.getWhere_name());
	    values.put(KEY_PLACE_ADRESS, place.getWhere_adress());
	    values.put(KEY_PLACE_STREET, place.getWhere_street());
	    values.put(KEY_PLACE_AREACODE, place.getWhere_areacode());
	    values.put(KEY_PLACE_CITY, place.getWhere_city());
	    
	    
	    Log.d("test", values.getAsString(KEY_PLACE_NAME)+" - id: "+place.getId());
	    int rows_affected=db.update(TABLE_PLACE, values, KEY_ID + " = "+place.getId(),null);
	    Log.d("test", "affected rows: "+rows_affected);
	    // updating row
	    return db.update(TABLE_PLACE, values, KEY_ID + " = "+place.getId(),null);
	}
	
	
	/*
	 * Deleting a where
	 */
	public void deleteWhere(Place place, boolean should_delete_all_where_todos) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    // before deleting where
	    // check if todos under this where should also be deleted
	    if (should_delete_all_where_todos) {
	        // get all todos under this where
	        List<Todo> allWhereToDos = getAllToDosByWhere(place.getWhere_name());
	 
	        // delete all todos
	        for (Todo todo : allWhereToDos) {
	            // delete todo
	            deleteToDo(todo.getId());
	        }
	    }
	 
	    // now delete the where
	    db.delete(TABLE_PLACE, KEY_ID + " = ?",
	            new String[] { String.valueOf(place.getId()) });
	}
	
	
	/*
     * Creating todo_Where
     * Following method will assign a todo under a where name.
     */
    public long createTodoWhere(long todo_id, long where_id) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        	    
        ContentValues values = new ContentValues();
        values.put(KEY_TODO_ID, todo_id);
        values.put(KEY_PLACE_ID, where_id);
        values.put(KEY_CREATED_AT, getDateTime());
 
        long id = db.insert(TABLE_TODO_PLACE, null, values);
 
        return id;
        
    }
    
    
    /*
    * Updating a todo where - Following method will remove the tag assigned to a todo
    */
   public int updateNoteWhere(long id, long where_id) {
       SQLiteDatabase db = this.getWritableDatabase();
    
       ContentValues values = new ContentValues();
       values.put(KEY_PLACE_ID, where_id);
    
       // updating row
       return db.update(TABLE_TODO, values, KEY_ID + " = ?",
               new String[] { String.valueOf(id) });
   }
   
   
// closing database
   public void closeDB() {
       SQLiteDatabase db = this.getReadableDatabase();
       if (db != null && db.isOpen())
           db.close();
   }

   
   public int getToDoCount() {
       String countQuery = "SELECT  * FROM " + TABLE_TODO;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(countQuery, null);

       int count = cursor.getCount();
       cursor.close();

       // return count
       return count;
   }

   /**
    * get datetime
    * */
   private String getDateTime() {
       SimpleDateFormat dateFormat = new SimpleDateFormat(
               "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
       Date date = new Date();
       return dateFormat.format(date);
   }
   
   public long insertAccount(String userName, String password, String email){
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues values = new ContentValues();
	   values.put(KEY_USER_NAME, userName);
	   values.put(KEY_USER_PASSWORD, password);
	   values.put(KEY_USER_EMAIL, email);
	   
	   return db.insert(TABLE_USER, null, values);
	   
	   
   }
   
    
	

	
}
