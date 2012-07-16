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
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private ProgressBar mProgress;
	 private int mProgressStatus = 0;
	 private Handler mHandler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	
        
        
       	new Thread(new Runnable(){
        public void run(){  
        	JSONObject j = new JSONObject();
            try{
            	j.put("action", "getQuote");
            }catch(JSONException e1){
            	e1.printStackTrace();
            	
            
            	
            }
            String url = "http://deeloz.ug/androidServer1.php";
            //Testing
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            EditText tvl = (EditText) findViewById(R.id.editText1); 
            
        	
        	
        try{
        	 
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        	nameValuePairs.add(new BasicNameValuePair("APIKey","ultraQuote"));
        	nameValuePairs.add(new BasicNameValuePair("payload", j.toString()));
        	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        	HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            
            String temp = EntityUtils.toString(entity);
            tvl.setText(temp);
        }catch(ClientProtocolException e){
        	
        }catch(IOException e){
        	
        }
        }
    }).start(); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
