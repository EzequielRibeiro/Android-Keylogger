package com.exa.android.softkeyboard;

public class Comment {
	  private long id;
	  private String comment;
	  private String data;
	  private String screen;
	  private long send;
	  	  
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getComment() {
	    return comment;
	  }

	  public void setComment(String comment) {
	    this.comment = comment;
	  }
	  
	  public void setData(String data){
		  
		  this.data = data;
	  }
	  
	  public String getData(){
		  
		  return this.data;
		  
	  }
	  
	  public void setScreen(String screen){
		  
		  this.screen = screen;
	  }
	  
	  public String getScreen(){
		  return this.screen;
	  }
	  
	  public void setSend(long send){
		  
		  this.send = send;
		  
	  }
	  public long getSend(){
		  return this.send;
	  }
	  
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return  "Windows: " + screen +"\n\n" +  comment +"\n\n"+"Date: "+ data;
	 
	  
	  }
	} 


