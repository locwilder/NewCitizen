package com.example.newcitizen;

import java.util.ArrayList;
import java.util.Locale;

import com.example.newcitizen.TodoSelectFragment.I_get_TodoObjectList;

import android.R.color;
import android.net.Uri;
import android.os.Bundle;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.sax.RootElement;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TodoFragment extends Fragment{
	public static final String ARG_TODO_NUMBER = "todo_number";
	final static String  ARG_POSITION = "position";
	final static String  ARG_NAME = "name";
	int mCurrentPosition = -1;
	int posi;
	private String todoname ;
	private TextView todoTitle;
	private TextView todoDeadline;
	private TextView todoAdress;
	private TextView todoDocuments;
	private TextView todoPriority;
	private TextView todoRemarks;
	private TextView todoCategory;
	private CheckBox doneCheckBox;
	private Todo todo;
	private Todo todoinfos;
	private I_get_todo_infobyid get_todo_infobyid;
	private I_get_todo_infobyid get_todo_infobystring;
	private I_change_statusbyid change_statusbyid;
	

	ArrayList<Todo> todoObjectList = new ArrayList<Todo>();
	ArrayList<String>todoStringList = new ArrayList<String>();
	private Databasehandler db;
	
	
	public interface I_get_todo_infobyid {
	    public Todo get_todo_infobyid(int db_todo_id) ;
	    public Todo get_todo_infobystring(String name);
	}
	
	public interface I_change_statusbyid{
		public void change_statusbyname(String db_todo_name, int checked);
	}
	
	public TodoFragment(){
		
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		if (savedInstanceState != null) {
			
			mCurrentPosition =savedInstanceState.getInt(ARG_POSITION);
		}
		getActivity().setTitle("Detailansicht");
//		new idea to get to todos
	
		
		
//		Bundle args = getArguments();
//		String todo_title = args.getString("todoname");
//		Log.d("cursor text", todo_title);
		//TextView text_title = new TextView());
		//text_title.setText(todo_title);
		
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        doneCheckBox = (CheckBox) rootView.findViewById(R.id.doneCheckbox);
        
		
        
//        int i = getArguments().getInt(ARG_TODO_NUMBER);
        // gets string to get information out of sql list
        

//        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
//                        "drawable", getActivity().getPackageName());
//        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
//        getActivity().setTitle(planet);
        return rootView;
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Bundle args =getArguments();
		change_statusbyid = (I_change_statusbyid)getActivity();
		//posi  = getArguments().getInt(ARG_POSITION)+1;
		todoname = getArguments().getString(ARG_NAME);
		Button backButton = (Button) getActivity().findViewById(R.id.gotoselectButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
				
			}
		});
		Button adjustButton = (Button) getActivity().findViewById(R.id.adjustButton);
		adjustButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), TodoAdjust.class);
				
				i.putExtra("id", get_todo_infobyid.get_todo_infobystring(todoname).getId());
			
				
				startActivity(i);
			}
		});
		
		Button navigationButton = (Button) getActivity().findViewById(R.id.navigateButton);
		navigationButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String street = get_todo_infobyid.get_todo_infobystring(todoname).getStreets();
				String areacode = get_todo_infobyid.get_todo_infobystring(todoname).getAreacode();
				String city = get_todo_infobyid.get_todo_infobystring(todoname).getCity();
				String uri = "geo:0,0?q="+street+", "+areacode+", "+city;
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				startActivity(intent);
			}
		});
		
		get_todo_infobystring= (I_get_todo_infobyid)getActivity();
		if (get_todo_infobystring.get_todo_infobystring(todoname).getStatus()==1) {
			doneCheckBox.setChecked(true);
		}else{
			doneCheckBox.setChecked(false);
		}
		doneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Log.d("checkboxtest", "id is: "+todoinfos.getId()+" - "+todoinfos.getTododesc());
				
				if (buttonView.isChecked()) { 
						
						Log.d("checkboxtest", "checked"+posi);
						
						change_statusbyid.change_statusbyname(todoname, 1);
						
						
						
//						Log.d("checkboxtest", "status is: "+todoinfos.getStatus());
						
					} 
					else 
					{
						Log.d("checkboxtest", "unchecked");
						change_statusbyid.change_statusbyname(todoname, 0);
//						todoinfos.setStatus(0);
//						Log.d("checkboxtest", "status is: "+todoinfos.getStatus());
//						
//						thetodo.setStatus(0);
//						db.updateToDo(thetodo);
					}
				
			}
		} );

		
		if (args != null) {
			updateTodoView(args.getString(ARG_NAME));
			
		}else if (mCurrentPosition != -1) {
			
			updateTodoView(args.getInt(ARG_NAME));			
		}
	}
	public void updateTodoView(int position) {
		get_todo_infobyid= (I_get_todo_infobyid)getActivity();
		
		
		
		Todo todoinfos = get_todo_infobyid.get_todo_infobyid(position);
		
		todoTitle = (TextView) getActivity().findViewById(R.id.TodoTitle);
		todoTitle.setText(todoinfos.getTododesc());
		todoDeadline = (TextView) getActivity().findViewById(R.id.TodoDeadline);
		todoDeadline.setText(todoinfos.getDeadline_at());
		todoPriority = (TextView) getActivity().findViewById(R.id.todoPriorityView);
		todoPriority.setText(todoinfos.getPriority()+"/5");
		todoAdress = (TextView) getActivity().findViewById(R.id.todoAdressView);
		todoAdress.setText(todoinfos.getStreets()+", "+todoinfos.getAreacode()+" "+todoinfos.getCity());
		todoDocuments = (TextView) getActivity().findViewById(R.id.todoDocumentsView);
		todoDocuments.setText(todoinfos.getDocuments());
		todoRemarks = (TextView) getActivity().findViewById(R.id.todoRemarksTextField);
		todoRemarks.setText(todoinfos.getRemarks());
		todoCategory = (TextView) getActivity().findViewById(R.id.todoCategoryView);
		todoCategory.setText(todoinfos.getCategory());
		mCurrentPosition = position;
		
		}
	
	public void updateTodoView(String name) {
		get_todo_infobyid= (I_get_todo_infobyid)getActivity();
		
		
		
		Todo todoinfos = get_todo_infobyid.get_todo_infobystring(name);
		
		todoTitle = (TextView) getActivity().findViewById(R.id.TodoTitle);
		todoTitle.setText(todoinfos.getTododesc());
		todoDeadline = (TextView) getActivity().findViewById(R.id.TodoDeadline);
		todoDeadline.setText(todoinfos.getDeadline_at());
		todoPriority = (TextView) getActivity().findViewById(R.id.todoPriorityView);
		todoPriority.setText(todoinfos.getPriority()+"/5");
		todoAdress = (TextView) getActivity().findViewById(R.id.todoAdressView);
		todoAdress.setText(todoinfos.getStreets()+", "+todoinfos.getAreacode()+" "+todoinfos.getCity());
		todoDocuments = (TextView) getActivity().findViewById(R.id.todoDocumentsView);
		todoDocuments.setText(todoinfos.getDocuments());
		todoRemarks = (TextView) getActivity().findViewById(R.id.todoRemarksTextField);
		todoRemarks.setText(todoinfos.getRemarks());
		todoCategory = (TextView) getActivity().findViewById(R.id.todoCategoryView);
		todoCategory.setText(todoinfos.getCategory());
//		mCurrentPosition = position;
		
		}
	
		
	
}
