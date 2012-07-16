package com.connector.connectorapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private ProgressBar mProgress;
	 private int mProgressStatus = 0;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	
        
        new PostTask().execute("http://deeloz.ug/androidServer1.php");
      
          
        	
            //String url = "http://deeloz.ug/androidServer1.php";
            //Testing
             
            
 }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    private class PostTask extends AsyncTask<String,Integer,String>{
    	EditText tvl = (EditText) findViewById(R.id.editText1);
    	String temp;
    	@Override
    	protected void onPreExecute(){
    	mProgress = (ProgressBar)findViewById(R.id.progressBar1);
    	
    	mProgress.setVisibility(View.VISIBLE);
    	
    	
    	 
    	}
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String url = arg0[0];
			
			JSONObject j = new JSONObject();
            try{
            	j.put("action", "getQuote");
            }catch(JSONException e1){
            	e1.printStackTrace();
            	
            }
            
            
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            
            
            try{
           	 
            	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            	nameValuePairs.add(new BasicNameValuePair("APIKey","ultraQuote"));
            	nameValuePairs.add(new BasicNameValuePair("payload", j.toString()));
            	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            	HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                
                 temp = EntityUtils.toString(entity);
                
            }catch(ClientProtocolException e){
            	
            }catch(IOException e){
            	
            }
            
            
            
			return temp;
		}
		@Override
		protected  void onPostExecute(String result ){
	         try {
	          	// create an object holding the JSON data
	          	JSONObject j1 = new JSONObject(result);

	          	// quotes are stored in an array, provisioned for sending more than one.
	          	// we get this array next, called quote.
	 	JSONArray quotes = j1.getJSONArray("quote");

	 	// select the first item in the array.
	 	JSONObject q1 = quotes.getJSONObject(0);

	 	// build output string.
	 	String quoteText = q1.getString("quote");
	 	String quoteAuthor = q1.getString("author");

	 	tvl.setText(quoteText + "\n -" + quoteAuthor);

	 } catch (JSONException e) {
	 	e.printStackTrace();
	 }
      mProgress.setVisibility(View.GONE);
		}
    	
    }
    
}
