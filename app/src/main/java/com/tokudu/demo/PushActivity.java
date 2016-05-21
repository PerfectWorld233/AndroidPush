package com.tokudu.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PushActivity extends Activity {
	private String mDeviceID;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mDeviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);         
  	  	((TextView) findViewById(R.id.target_text)).setText(mDeviceID);
		Log.v("test","test1");
  	  	final Button startButton = ((Button) findViewById(R.id.start_button));
  	  	final Button stopButton = ((Button) findViewById(R.id.stop_button));
  	  	startButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		    	Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();
		    	editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
		    	editor.commit();
				PushService.actionStart(getApplicationContext());		        
		  		startButton.setEnabled(false);
		  		stopButton.setEnabled(true);				
			}
		});
		Log.v("test","test2");
  	  	stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PushService.actionStop(getApplicationContext());		        								
		  		startButton.setEnabled(true);
		  		stopButton.setEnabled(false);				
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
  	  	Log.v("test","test3");
  	  	SharedPreferences p = getSharedPreferences(PushService.TAG, MODE_PRIVATE);
  	  	boolean started = p.getBoolean(PushService.PREF_STARTED, false);
  	  	
  		((Button) findViewById(R.id.start_button)).setEnabled(!started);
  		((Button) findViewById(R.id.stop_button)).setEnabled(started);
		Log.v("test","test4");
    }
}