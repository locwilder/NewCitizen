package com.example.newcitizen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;



public abstract class FragmentActivityBuilder extends FragmentActivity {

	protected abstract Fragment createFragment();
	protected void onCreate(Bundle arg0) {
		 super.onCreate(arg0);
		 setContentView(R.layout.first);
		 	 
		 	        FragmentManager fragManager = getFragmentManager();
		 	 
		 	        // Check if the FragmentManager knows about the Fragment
		 	        // id we refer to
		 	 
		 	        Fragment theFragment = fragManager.findFragmentById(R.id.content_frame_container);
		 	 
		 	        // Check if the Fragment was found
		 	 
		 	        if(theFragment == null){
		 	 
		 	            // If the Fragment wasn't found then we must create it
		 	 
		 	            // NEW We can generate many types of Fragments by having
		 	            // CreateFragment define the type. So
		 	            // theFragment = new ContactFragment();
		 	            // is replaced by
		 	             
		 	            theFragment = createFragment();
		 	             
		 	 
		 	            // Creates and commits the Fragment transaction
		 	            // Fragment transactions add, attach, detach, replace
		 	            // and remove Fragments.
		 	 
		 	            // add() gets the location to place the Fragment into and
		 	            // the Fragment itself.
		 	 
		 	            fragManager.beginTransaction()
		 	            .add(R.id.content_frame_container, theFragment)
		 	            .commit();

		 	        }
	
	}
}