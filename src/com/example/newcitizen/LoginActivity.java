package com.example.newcitizen;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    

	    TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
	    Button loginButton = (Button) findViewById(R.id.btnLogin);
	    
	    loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), FirstActivity.class);
				startActivity(i);
			}
		});
	
	 // Listening to register new account link
	    registerScreen.setOnClickListener(new View.OnClickListener() {
	    	
	    	public void onClick(View v) {
	            // Switching to Register screen
	            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
	            startActivity(i);
	        }
	    	
	    });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
