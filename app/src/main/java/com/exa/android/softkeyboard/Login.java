package com.exa.android.softkeyboard;

import com.generator.android.softkeyboard.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.storage.OnObbStateChangeListener;
import android.sax.TextElementListener;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Login extends Activity {

	private static final String PREFS_NAME = "Login";
	private  SharedPreferences.Editor editor;
	private SharedPreferences settings;
	private LinearLayout layoutUm;
	private LinearLayout layoutDois;
	private LinearLayout layoutPai;
	private TextView textNewSenha;
	private TextView textRepeat;
	private Context context = this;
	private EditText entradaUm;
	private EditText entradaDois;
	private Button botaoConfirmar;
	private Button botaoCancelar;
	private Button botaoRedefinirSenha;
    private ImageView logoImage;
	
	protected void onCreate(Bundle e){
		super.onCreate(e);
		setContentView(R.layout.login);
		setTitle("SeeKey 1.0");

		/*
		EnviarMail env = new EnviarMail();
		
		
		
		try {
			//env.sendEmail("ezequiel.ribeiro@gmail.com", "ezequiel.ribeiro@gmail.com","eze344363eze", "subjetive", "teste"," ");
		    
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		*/
		
		
		settings = getSharedPreferences(PREFS_NAME, 0);

		editor = settings.edit();

		settings.getString("login", "#8vazio8#");

		textNewSenha = new TextView(context);
		textNewSenha.setText("New Password");

		textRepeat = new TextView(context);
		textRepeat.setText("Repeat");

		entradaUm = new EditText(context);
		entradaUm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		entradaDois = new EditText(context);
		entradaDois.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

		botaoConfirmar = new Button(context);
		botaoConfirmar.setText("Confirm");

		botaoCancelar = new Button(context);
		botaoCancelar.setText("Cancel"); 

		botaoRedefinirSenha = new Button(context);
		botaoRedefinirSenha.setText("Change Password");

		layoutUm = (LinearLayout) findViewById(R.id.layoutUm);
		layoutDois = (LinearLayout) findViewById(R.id.layoutDois);
        layoutPai = (LinearLayout) findViewById(R.id.layoutFilho);

		BotaoAcao acao = new BotaoAcao();

		botaoConfirmar.setOnClickListener(acao);
		botaoCancelar.setOnClickListener(acao);
		botaoRedefinirSenha.setOnClickListener(acao);

		logoImage = new ImageView(context);
		logoImage.setBackgroundResource(R.drawable.seekeylogo);
        logoImage.setMinimumHeight(200);
        logoImage.setMaxWidth(150);
		
		if(settings.getString("login", "#8vazio8#").equals("#8vazio8#")){

			layoutUm.addView(textNewSenha);
			layoutUm.addView(entradaUm);

			layoutUm.addView(textRepeat);
			layoutUm.addView(entradaDois);


		}else{

			textNewSenha.setText("Password:");
			layoutUm.addView(textNewSenha);
			layoutUm.addView(entradaUm);

		}


		layoutDois.addView(botaoConfirmar); 
		layoutDois.addView(botaoCancelar);

		layoutPai.addView(logoImage);
	}


	public void redefinirSenha(){

		editor.putString("login","#8vazio8#" );
		editor.commit();

		finish();


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

	public void finish(){
		super.finish();
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		editor.commit();

	}


	private class BotaoAcao implements OnClickListener{

		@Override
		public void onClick(View v) {


			if(v.equals(botaoConfirmar)){

				if(settings.getString("login", "#8vazio8#").equals("#8vazio8#")){

					if((entradaUm.getText().length() > 0) && (entradaDois.getText().length() > 0)){

						if(entradaUm.getText().toString().equals(entradaDois.getText().toString())){

							editor.putString("login", entradaUm.getText().toString() );
							editor.commit();

							finish();

							Log.e("Senha",settings.getString("login", null));

						}else{

							Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
						}

					}else{

						Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show();

					}


				}else{

					if(entradaUm.getText().toString().equals(settings.getString("login", null))){

						Toast.makeText(context, "Accept", Toast.LENGTH_SHORT).show();

						Intent it = new Intent(context, Settings.class);

						startActivity(it);

					}else{

						Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();

					}
				}
			}else if(v.equals(botaoCancelar)){



				finish();


			}else if(v.equals(botaoRedefinirSenha)){

				redefinirSenha();
				Toast.makeText(context, "Password Clear", Toast.LENGTH_SHORT).show();

			}



			entradaUm.setText("");
			entradaDois.setText("");

		}



	}





}

