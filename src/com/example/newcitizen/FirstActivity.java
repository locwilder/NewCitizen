package com.example.newcitizen;

import java.util.ArrayList;
import java.util.List;

import com.example.newcitizen.TodoAdjust.I_get_todo_infobyid_adjust;
import com.example.newcitizen.TodoFragment.I_get_todo_infobyid;
import com.example.newcitizen.TodoFragment.I_change_statusbyid;
import com.example.newcitizen.TodoSelectFragment.I_get_TodoObjectList;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FirstActivity extends FragmentActivity implements TodoSelectFragment.OnTodoSelectedListener, I_get_todo_infobyid, I_get_todo_infobyid_adjust, I_get_TodoObjectList, I_change_statusbyid{
	private String[] navTitles;
	private String[] todoTitles;
	private DrawerLayout navDrawerLayout;
	private ListView navDrawerList;
    private CharSequence navTitle;
    private Databasehandler db;
    private ListView todoDrawerList;
    private Cursor cursor;
    private SimpleCursorAdapter cursoradapter;
    ArrayList<String> todolist = new ArrayList<String>();
    ArrayList<Todo> todoObjectList = new ArrayList<Todo>();
    List<Place> myPlaces = new ArrayList<Place>();
	List<Todo> myTodos = new ArrayList<Todo>();
	Fragment firstFragment = new TodoSelectFragment();
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        
        
        
        db= new Databasehandler(getApplicationContext());
        		     
        navTitles = getResources().getStringArray(R.array.navigation_array);
		
        //get the view to the fragment
		navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawerList = (ListView) findViewById(R.id.left_drawer);
        todoDrawerList = (ListView) findViewById(R.id.right_drawer);
       
        //set adapter for the list view for left and right drawer
        navDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.navdrawer_list_item, navTitles));
        displayRightDrawer();
                
        //set list click listener for left drawer and right drawer
        navDrawerList.setOnItemClickListener(new LeftDrawerItemClickListener());
        todoDrawerList.setOnItemClickListener(new RightDrawerItemClickListener());
        
        if (findViewById(R.id.content_frame_container) != null) {
        	
        	
			if (savedInstanceState != null) {
				Log.d("fragment ", "savedInstanceState  good");
				return;
			}
			FragmentManager fragmentManager = getFragmentManager();
			getIntent().putExtra("sortnr", 0);
			
			firstFragment.setArguments(getIntent().getExtras());
			
			
			fragmentManager.beginTransaction().add(R.id.content_frame_container, firstFragment).commit();
			
			
		}
        
        Button sortbyareaButton = (Button) this.findViewById(R.id.sortbyareaButton);
        sortbyareaButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentManager fragmentManager = getFragmentManager();
				
				TodoSelectFragment fragment = (TodoSelectFragment) getFragmentManager().findFragmentById(R.id.content_frame_container);

			    fragment.setSortnr(1);
			    fragment.onStart();
				
				
			
//				fragmentManager.beginTransaction().replace(R.id.content_frame_container, firstFragment).commit();
				
			}
		});
        
        Button sortbydeadlineButton = (Button) this.findViewById(R.id.sortbydeadlineButton);
        sortbydeadlineButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentManager fragmentManager = getFragmentManager();
				
				TodoSelectFragment fragment = (TodoSelectFragment) getFragmentManager().findFragmentById(R.id.content_frame_container);

			    fragment.setSortnr(2);
			    fragment.onStart();
				
				
			
//				fragmentManager.beginTransaction().replace(R.id.content_frame_container, firstFragment).commit();
				
			}
		});
        
        Button sortbyprioButton = (Button) this.findViewById(R.id.sortbyprioButton);
        sortbyprioButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentManager fragmentManager = getFragmentManager();
				
				TodoSelectFragment fragment = (TodoSelectFragment) getFragmentManager().findFragmentById(R.id.content_frame_container);

			    fragment.setSortnr(3);
			    fragment.onStart();
				
				
			
//				fragmentManager.beginTransaction().replace(R.id.content_frame_container, firstFragment).commit();
				
			}
		});
        
 
	    
    }
	
	public void displayRightDrawer(){
		cursor = db.getReadableDatabase().rawQuery("SELECT * FROM todos", null);
		 
		 todoTitles = new String[]{
				 cursor.getColumnName(0),
				 cursor.getColumnName(1)
	        };
		 int [] to = new int[]{
	        		R.id.id,
	        		R.id.tododesc
	        		
	        };
		 
		 //checks if Cursor is empty or not
		 if (cursor.getCount()<1) {
			 
			Toast.makeText(getApplicationContext(), "cursor is empty", 3000).show();
			
		}else {
			cursor.moveToFirst();
			Log.d("test", "-----"+cursor.getColumnName(0)+"---"+cursor.getColumnName(1));
			cursoradapter = new SimpleCursorAdapter(this, 
					R.layout.right_todo_drawer_list_item, 
					cursor, 
					todoTitles, 
					to, 
					0);
			todoDrawerList.setAdapter(cursoradapter);
			Toast.makeText(getApplicationContext(), "cursor rdy", 3000).show();
		}
	        
		
	}
		
	private class RightDrawerItemClickListener implements ListView.OnItemClickListener{

		
		@Override
        public void onItemClick(AdapterView<?> todoDrawerList, View view, int position, long id) {
			Cursor cursor = (Cursor) todoDrawerList.getItemAtPosition(position);
			String todo_title = cursor.getString(cursor.getColumnIndexOrThrow("tododesc"));
			Toast.makeText(getApplicationContext(), todo_title, 3000).show();
			
			onTodoSelected(todo_title);
        }

		
		
		private void selectItem(String todo_title) {
			// Create a new fragment and specify the todo to show based on position
		    Fragment fragment = new TodoFragment();
		    Bundle args = new Bundle();
		    
		    //Log.d("cursor text", todo_title);
		    
		    args.putString("todoname", todo_title);
		    fragment.setArguments(args);
		    
		    

		    // Insert the fragment by replacing any existing fragment
		    FragmentManager fragmentManager = getFragmentManager();
		    fragmentManager.beginTransaction()
		                   .replace(R.id.content_frame_container, fragment)
		                   .commit();

		    // Highlight the selected item, update the title, and close the drawer
		    todoDrawerList.setItemChecked(cursor.getPosition(), true);
		    setTitle(todoTitles[cursor.getPosition()]);
		    navDrawerLayout.closeDrawer(todoDrawerList);

		}
	}
	
	private class LeftDrawerItemClickListener implements ListView.OnItemClickListener{
	
			
			@Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            selectItem(position);
	        }
	
			private void selectItem(int position) {
				
				switch (position) {
				case 0:
					Place where1 = new Place("Kreisbuero", "Langstrasse 2, 8004, Zuerich", "Langstrasse 2", "8004", "Zuerich");
			        Place where2 = new Place("Verkehrsamt", "Badenerstrasse 137, 8005, Alstetten", "Badenerstrasse 137", "8005", "Alstetten");;
			        Place where3 = new Place("Berufsinformationszentrum", "Schimmelstrasse 2, 8003, Wiedikon", "Schimmelstrasse 2", "8003", "Wiedikon");;
			        Place where4 = new Place("Fitness Center", "Bahnhofstrasse 55, 8001, Zuerich", "Bahnhofstrasse 55", "8001", "Zuerich");;
			        
			        myPlaces.add(where1);
			        myPlaces.add(where2);
			        myPlaces.add(where3);
			        myPlaces.add(where4);
			        
			        Todo todo1 = new Todo("Kreisbuero anmelden", 0, "2014-01-22", "administrativ", "Dokumente vorhanden", 4 ,"Langstrasse 2","8004" ,"Zuerich", "Heimatschein");
			        Todo todo2 = new Todo("Auto anmelden", 1, "2013-12-22", "administrativ", "Fahrzeugsausweiss", 3 ,"Badenerstrasse 137", "8005", "Alstetten", "Fahrzeugausweis");
			        Todo todo3 = new Todo("BIZ informationen sammeln", 0, "2014-02-22", "Arbeit", "Zeugnisse", 2, "Schimmelstrasse 2", "8003", "Wiedikon", "nix" );
			        Todo todo4 = new Todo("Fitness abo lösen ", 1, "2013-12-21", "freizeit", "nichts", 1 , "Bahnhofstrasse 55", "8001", "Zuerich","Kleidung");
			        
			        Todo todo5 = new Todo("Kreisbuero suchen", 2, "2014-02-31", "Administration", "Karte", 5, "Bildgass 2", "9494", "Schaan", "nix");
			        Todo todo6 = new Todo("Fitnessgeräte testen", 0,"2014-02-31", "Freizeit", "nichts", 5, "Bildgass 2", "9494", "blah", "nix");
			        
			        myTodos.add(todo1);
			        myTodos.add(todo2);
			        myTodos.add(todo3);
			        myTodos.add(todo4);
			        myTodos.add(todo5);
			        myTodos.add(todo6);
			        
			        
			        //Inserting wheres in db
			        for (Place place : myPlaces) {
			            place.setId((int) db.createWhere(place));
			            Log.d("Place Name", place.getId()+" - "+place.getWhere_name()+" - "+place.getWhere_adress());
			        }
			        todo1.setId((int) db.createToDo(todo1, new long[] { where1.getId() }));
			        todo2.setId((int) db.createToDo(todo2, new long[] { where2.getId()}));
			        todo3.setId((int) db.createToDo(todo3, new long[] { where3.getId() }));
			        todo4.setId((int) db.createToDo(todo4, new long[] { where4.getId() }));
			        //alibitodos
			        todo5.setId((int) db.createToDo(todo5, new long[] { where1.getId() }));
			        todo6.setId((int) db.createToDo(todo6, new long[] { where4.getId() }));
			        for (Todo todos : myTodos){
			        	Log.d("Todos: ", todos.getId()+" : "+todos.getTododesc()+"---"+todos.getCategory()+"---"+todos.getDeadline_at());
			        }
			        
					Toast.makeText(getApplicationContext(), "SQL TEST Data Inserted", 3000).show();
					onCreate();
					break;
				case 1:
					Log.d("Todos: ", "here are the sql entries");
					List<Place> allPlaces = db.getAllWheres();
			        for (Place place : allPlaces) {
			            Log.d("Place Name", place.getId()+" - "+place.getWhere_name());
			        }
			        List<Todo> allToDos = db.getAllToDos();
			        for (Todo todos : allToDos) {
			        	Log.d("Todos: ", todos.getId()+" : "+todos.getTododesc()+"---"+todos.getCategory()+"---"+todos.getDeadline_at());
			        }
					
					Toast.makeText(getApplicationContext(), "SQL Entries in LOG", 3000).show();
					break;
				case 2:
					replaceMainFragement(firstFragment);
					
	
				default:
					break;
				}
	
			}
		}
	
	public ArrayList<String> get_todolist (){
		
		
		cursor = db.getReadableDatabase().rawQuery("SELECT * FROM todos", null);
		
		if (cursor.getCount()<1) {
			 
			Toast.makeText(getApplicationContext(), "cursor is empty", 3000).show();
			
		}else {
			if (todolist.isEmpty()) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					
				    
//				    Log.d("cursor test", cursor.getInt(0)+" - "+cursor.getString(1));
				    todolist.add(cursor.getString(1));
				    
				    
				}
				
			}else{
				todolist.removeAll(todolist);
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					
				    
//				    Log.d("cursor test", cursor.getInt(0)+" - "+cursor.getString(1));
				    todolist.add(cursor.getString(1));
				    
				    
				}
			}
			
			
		}
		
		Log.d("arraylist methode -", "list returned");
        
		
		return todolist;
		
	}
	
	public Todo get_todo_infobystring(String db_tododesc){
		Todo todoinfos = db.getTodobyDesc(db_tododesc);
		
		return todoinfos;
		
	}
	
	public Todo get_todo_infobyid(int db_todo_id){
		Todo todoinfos = db.getTodo(db_todo_id);
		
		return todoinfos;
		
	}
	
	public ArrayList<Todo> getTodoObjectsList(int i){
		switch (i) {
		case 0:
			cursor = db.getReadableDatabase().rawQuery("SELECT * FROM todos", null);
			break;

		case 1:
			cursor = db.getReadableDatabase().rawQuery("SELECT * FROM todos order by areacode desc", null);
			break;
		case 2:
			cursor = db.getReadableDatabase().rawQuery("SELECT * FROM todos order by deadline_at desc", null);
			break;
		case 3:
			cursor = db.getReadableDatabase().rawQuery("SELECT * FROM todos order by priority desc ", null);
			break;
		default:
			break;
		}
		
		
		if (cursor.getCount()<1) {
			 Toast.makeText(getApplicationContext(), "cursor is empty", 3000).show();
		}else {
			if (todoObjectList.isEmpty()) {
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					Todo temp = new Todo();
					temp.setId(cursor.getInt(0));
					temp.setTododesc(cursor.getString(1));
					temp.setStatus(cursor.getInt(2));
					temp.setCreatedAt(cursor.getString(3));
					temp.setDeadline_at(cursor.getString(4));
					temp.setCategory(cursor.getString(5));
					temp.setRemarks(cursor.getString(6));
					temp.setPriority(cursor.getInt(7));
					temp.setStreets(cursor.getString(8));
					temp.setAreacode(cursor.getString(9));
					temp.setCity(cursor.getString(10));
					temp.setDocuments(cursor.getString(11));
					
//				    Log.d("cursor test", cursor.getInt(0)+" - "+cursor.getString(1));
					todoObjectList.add(temp);
				    				    
				}
				
			}else{
				todoObjectList.removeAll(todolist);
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					Todo temp = new Todo();
					temp.setId(cursor.getInt(0));
					temp.setTododesc(cursor.getString(1));
					temp.setStatus(cursor.getInt(2));
					temp.setCreatedAt(cursor.getString(3));
					temp.setDeadline_at(cursor.getString(4));
					temp.setCategory(cursor.getString(5));
					temp.setRemarks(cursor.getString(6));
					temp.setPriority(cursor.getInt(7));
					temp.setStreets(cursor.getString(8));
					temp.setAreacode(cursor.getString(9));
					temp.setCity(cursor.getString(10));
					temp.setDocuments(cursor.getString(11));
//				    Log.d("cursor test", cursor.getInt(0)+" - "+cursor.getString(1));
					todoObjectList.add(temp);
				    				    
				}
			}
			
			
		}
		
		return todoObjectList;
		
	}
	
	public void setTitle(CharSequence title) {
        navTitle = title;
        getActionBar().setTitle(navTitle);
    }

	public void onCreate() {
		// TODO Auto-generated method stub
		
	}
	
	

	public void replaceMainFragement(Fragment fragment){
		
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.content_frame_container, fragment);
		
		transaction.commit();
		
		
	}
	                                  
	@Override
	public void onTodoSelected(int position) {
		// TODO Auto-generated method stub
		Log.d("firstactivity", "on overview list click");
		TodoFragment todoFrag = (TodoFragment) getFragmentManager().findFragmentById(R.id.todoFragment);
		
		if (todoFrag != null) {
			todoFrag.updateTodoView(position);
		}else{

			TodoFragment newFragment = new TodoFragment();
			Bundle args = new Bundle();
			args.putInt(TodoFragment.ARG_POSITION, position);
			newFragment.setArguments(args);
			
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			
			transaction.replace(R.id.content_frame_container, newFragment);
			
			transaction.addToBackStack(null);
			
			transaction.commit();
			
		}
	}
	@Override
	public void onTodoSelected(String name) {
		// TODO Auto-generated method stub
				Log.d("firstactivity", "on overview list click");
				TodoFragment todoFrag = (TodoFragment) getFragmentManager().findFragmentById(R.id.todoFragment);
				
				if (todoFrag != null) {
					todoFrag.updateTodoView(name);
				}else{

					TodoFragment newFragment = new TodoFragment();
					Bundle args = new Bundle();
					args.putString(TodoFragment.ARG_NAME, name);
					newFragment.setArguments(args);
					
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.setCustomAnimations(
							R.animator.card_flip_right_in, R.animator.card_flip_right_out,
		                    R.animator.card_flip_left_in, R.animator.card_flip_left_out);

					transaction.replace(R.id.content_frame_container, newFragment);
					
					transaction.addToBackStack(null);
					
					transaction.commit();
					
				}
	}

	@Override
    public void onBackPressed() {

//        logger.d("@@@@@@ back stack entry count : " + getSupportFragmentManager().getBackStackEntryCount());

        if (getFragmentManager().getBackStackEntryCount() >  0) {
        	
        	getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE );
        	
        	

        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {

            // or just go back to main activity
            super.onBackPressed();
        }
    }

	@Override
	public void change_statusbyname(String db_todo_name, int checked) {
		Todo item = get_todo_infobystring(db_todo_name);
		item.setStatus(checked);
		Log.d("from main activity", "checked="+item.getStatus());
		db.updateToDo(item);
		
	}

	
}

