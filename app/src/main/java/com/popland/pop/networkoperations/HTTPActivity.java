package com.popland.pop.networkoperations;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPActivity extends AppCompatActivity {
EditText edt;
TextView tv;
String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        edt = (EditText)findViewById(R.id.edt);
        tv = (TextView)findViewById(R.id.tv);
    }

    public void sendRequest(View v){
        search = edt.getText().toString();
        new GetOnlineRes().execute();
    }

    class GetOnlineRes extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://glosbe.com/gapi_v0_1/translate?from=eng&dest=vi&format=json&phrase="+search+"&pretty=true");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setAllowUserInteraction(false);
//                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();
                final StringBuilder content = new StringBuilder();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line;
                    while((line=bufferedReader.readLine())!=null)
                        content.append(line+"\n");
                    bufferedReader.close();
                    HTTPActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(content.toString());//UI control should be wrapped in runOnUiThread
                        }
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
            return null;
        }
    }
}
