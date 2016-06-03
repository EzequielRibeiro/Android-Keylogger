package com.exa.android.softkeyboard;

import com.generator.android.softkeyboard.R;

import android.app.Activity;

import android.os.Bundle;

/*
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
*/

public class MainActivity extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
				
		
	}
	
	
	/*
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		
				
		MenuItem itemMenuSair = menu.add("Exit");
		MenuItem itemMenuAbout = menu.add("About");
		
		
		itemMenuAbout.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
								
				AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
				
			  alerta.setMessage("Ano: 2014\nFaceBook fanpage: MyApp\nVersion: 1.0\nEzequiel A. Ribeiro");
		      alerta.setTitle("Sobre");
		      alerta.setCancelable(true);
		      alerta.setNeutralButton("Fechar",
		         new DialogInterface.OnClickListener() {
		         public void onClick(DialogInterface dialog, int whichButton){}
		         }).show();
							
								
				return false;
			}
		});
		
		
		itemMenuSair.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				finish();
				
				return false;
			}
		});
		
		
		
		return true;
	}
	*/
	public void onStart() {
	    super.onStart();
	    
	   
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	  
	    
	  }	
	
	
	
	
	
	
	
	
	
	
}
