package com.example.newcitizen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TodoAdjust extends Activity{
	private I_get_todo_infobyid_adjust get_todo_infobyid;
	private TextView todoTitle;
	private Databasehandler db;
	private Todo todoinfos;
	private EditText totoTitleEdit;
	private EditText todoDeadline;
	private EditText todostreet;
	private EditText todoareacode;
	private EditText todocity;
	private EditText todoremarks;
	
	public interface I_get_todo_infobyid_adjust {
	    public Todo get_todo_infobyid(int db_todo_id) ;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_adjust);
		db= new Databasehandler(getApplicationContext());
	    
		int id = getIntent().getExtras().getInt("id");
		Log.d("test", "id = "+id);
		todoinfos = db.getTodo(id);
		todoTitle = (TextView) this.findViewById(R.id.TodoTitle);
		todoTitle.setText(todoinfos.getTododesc());
		totoTitleEdit = (EditText) this.findViewById(R.id.todotitleEditText);
		totoTitleEdit.setText(todoinfos.getTododesc());
		Spinner catSpinner = (Spinner) findViewById(R.id.categorySpinner);
		
		if (todoinfos.getCategory().toString().equalsIgnoreCase("Administration")) {
			catSpinner.setSelection(0);
		
		}else if (todoinfos.getCategory().toString().equalsIgnoreCase("Arbeit")) {
			catSpinner.setSelection(1);
		}else if (todoinfos.getCategory().toString().equalsIgnoreCase("Freizeit")) {
			catSpinner.setSelection(2);
		}else{
			catSpinner.setSelection(3);
		}
		
		Spinner prioSpinner = (Spinner) findViewById(R.id.priospinner);
		switch (todoinfos.getPriority()) {
		case 1:
			prioSpinner.setSelection(0);
			break;
		case 2:
			prioSpinner.setSelection(1);
			break;
		case 3:
			prioSpinner.setSelection(2);
			break;
		case 4:
			prioSpinner.setSelection(3);
			break;
		case 5:
			prioSpinner.setSelection(4);
			break;
		default:
			break;
		}
		todostreet = (EditText) this.findViewById(R.id.streetEditText);
		todostreet.setText(todoinfos.getStreets());
		todoareacode= (EditText) this.findViewById(R.id.areacodeEditText);
		todoareacode.setText(todoinfos.getAreacode());
		todocity = (EditText) this.findViewById(R.id.cityEditText);
		todocity.setText(todoinfos.getCity());
		todoremarks = (EditText) this.findViewById(R.id.remarksEditText);
		todoremarks.setText(todoinfos.getRemarks());
		
		Button saveButton = (Button) this.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				todoinfos.setTododesc(totoTitleEdit.getText().toString());
				DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
				 int day = datePicker.getDayOfMonth();
				 int month = datePicker.getMonth() + 1;
				 int year = datePicker.getYear();
				todoinfos.setDeadlineAt(year+"-"+month+"-"+day);
				Spinner prioSpinner = (Spinner) findViewById(R.id.priospinner);
				todoinfos.setPriority(prioSpinner.getSelectedItemPosition()+1);
				Spinner catSpinner = (Spinner) findViewById(R.id.categorySpinner);
				todoinfos.setCategory(catSpinner.getSelectedItem().toString());
				todoinfos.setStreets(todostreet.getText().toString());
				todoinfos.setAreacode(todoareacode.getText().toString());
				todoinfos.setCity(todocity.getText().toString());
				todoinfos.setRemarks(todoremarks.getText().toString());
				
				db.updateToDo(todoinfos);
				
				finish();
				
			}
		});
		
	}
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}
