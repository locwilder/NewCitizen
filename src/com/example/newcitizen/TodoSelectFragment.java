package com.example.newcitizen;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

public class TodoSelectFragment extends ListFragment{

	private Cursor cursor;
	private Databasehandler db;
	private String[] todoTitles;
	private TodoAdapter todoAdapter;
	private ArrayAdapter<String> arrayAdapter ;
	private ArrayList<String> todoStringList = new ArrayList<String>();
	ArrayList<Todo> todoObjectList = new ArrayList<Todo>();
	Todo thetodo;
	int posi;
	private int sortnr=0;
	
	
	
	


	OnTodoSelectedListener mCallback;
	I_get_TodoObjectList todoObjectCallback;
	I_change_statusbyid changeStatusbyid;

	public interface OnTodoSelectedListener{
		
		public void onTodoSelected(int position);
		public void onTodoSelected(String name);
	}
		
	public interface I_get_TodoObjectList{
		public ArrayList<Todo> getTodoObjectsList(int i);
	}
	
	public interface I_change_statusbyid{
		public void change_statusbyid(int db_todo_id, int checked);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		getActivity().setTitle(R.string.fragment_todo_list_title);
		
		
		switch (sortnr) {
		case 0:
			todoObjectList = todoObjectCallback.getTodoObjectsList(0);
			break;
		case 1:
			todoObjectList = todoObjectCallback.getTodoObjectsList(1);
			break;
		default:
			break;
		}
		
		todoAdapter = new TodoAdapter(todoObjectList);
 
		setListAdapter(todoAdapter);

		
	}
	
	private class TodoAdapter extends ArrayAdapter<Todo>{
		
		public TodoAdapter(ArrayList<Todo> todos){
			
			super(getActivity(), android.R.layout.simple_list_item_1, todos);
			
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.todo_selectlist, null);
								
			}
			thetodo = getItem(position);
			TextView todonameTextView = (TextView) convertView.findViewById(R.id.TodoSelectTitle);
			todonameTextView.setText(thetodo.getTododesc());
			
			
			
			TextView tododeadlineTextView = (TextView) convertView.findViewById(R.id.todoSelectDeadline);
			tododeadlineTextView.setText(thetodo.getDeadline_at());
			
			
			CheckBox todoCheckBox = (CheckBox) convertView.findViewById(R.id.todoSelectCheckBox);
			if (thetodo.getStatus()==0) {
				todoCheckBox.setChecked(false);
			}else{
				todoCheckBox.setChecked(true);
			}
		
			
//			changeStatusbyid = (I_change_statusbyid)getActivity();
			
			todoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					Log.d("checkboxtest", "id is: "+thetodo.getId()+" - "+posi);
					if (buttonView.isChecked()) { 
							
							Log.d("checkboxtest", "checked");
//							changeStatusbyid.change_statusbyid(thetodo.getId(), 1);
							Log.d("checkboxtest", "status is: "+thetodo.getStatus());
							
						} 
						else 
						{
							Log.d("checkboxtest", "unchecked");
//							changeStatusbyid.change_statusbyid(thetodo.getId(), 0);
							Log.d("checkboxtest", "status is: "+thetodo.getStatus());
							
//							thetodo.setStatus(0);
//							db.updateToDo(thetodo);
						}
					
				}
				
				
			});
			switch (thetodo.getPriority()) {
			case 1:
				convertView.setBackgroundColor(Color.parseColor("#5be7f7"));
				break;
			case 2:
				convertView.setBackgroundColor(Color.parseColor("#c0ff6d"));
				break;
			case 3:
				convertView.setBackgroundColor(Color.parseColor("#ffff6d"));
				break;
			case 4:
				convertView.setBackgroundColor(Color.parseColor("#ffc46d"));
				break;
			case 5:
				convertView.setBackgroundColor(Color.parseColor("#ff816d"));
				break;
			default:
				break;
			}
			
			return convertView;
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		
		// TODO Auto-generated method stub
		super.onAttach(activity);
      
        
        try {
			mCallback = (OnTodoSelectedListener) activity;
			
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "must implement OnTodoSelectedListener");
		}
        
        try {
        	todoObjectCallback = (I_get_TodoObjectList)activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString() + "must implement get_TodoObjectList");
		}

	}
	
	
	
	@Override
	public void onStart() {
		
		super.onStart();
		getActivity().setTitle(R.string.fragment_todo_list_title);
		todoAdapter.clear();
		
		switch (sortnr) {
		case 0:
			
			todoObjectList = todoObjectCallback.getTodoObjectsList(0);
			break;
		case 1:
			todoObjectList = todoObjectCallback.getTodoObjectsList(1);
			break;
		case 2:
			todoObjectList = todoObjectCallback.getTodoObjectsList(2);
			break;
		case 3:
			todoObjectList = todoObjectCallback.getTodoObjectsList(3);
			break;
		default:
			break;
		}
		
		
		todoAdapter = new TodoAdapter(todoObjectList);
 
		
		setListAdapter(todoAdapter);
		
		if (getFragmentManager().findFragmentById(R.id.todoFragment) != null) {
			
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
		}
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Notify the parent activity of selected item
		Log.d("Todo", "LIST ITEM CLICKED");
//		Log.d("selected", l.get);
		TextView text = (TextView) v.findViewById(R.id.TodoSelectTitle);
		//mCallback.onTodoSelected(position);
		mCallback.onTodoSelected(text.getText().toString());
		Log.d("Todo", text.getText().toString());
		// Set the item as checked to be highlighted when in two-pane layout
		getListView().setItemChecked(position, true);
	}


	public TodoAdapter getTodoAdapter() {
		
		return todoAdapter;
	}
	public void setSortnr(int sortnr) {
		this.sortnr = sortnr;
	}
	
}
