package com.popland.pop.networkoperations;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends AppCompatActivity {
    EditText edt;
    TextView tv;
    String search;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        edt = (EditText)findViewById(R.id.edt);
        tv = (TextView)findViewById(R.id.tv);
    }

    public void sendRequest(View v){
        checkInternet();
        search = edt.getText().toString();
        requestQueue = Volley.newRequestQueue(VolleyActivity.this);
        String url = "https://glosbe.com/gapi_v0_1/translate?from=eng&dest=vi&format=json&phrase="+search+"&pretty=true";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null
//                , new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//                tv.setText(response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tv.setText("Error");
//            }
//        });
//        jsonObjectRequest.setTag("json");

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url
        , new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                tv.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText("Error");
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(requestQueue!=null)
            requestQueue.cancelAll("json");//cancel all request with this tag
    }

    public void checkInternet(){
        ConnectivityManager check = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = check.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
            Toast.makeText(VolleyActivity.this,"Internet Connected",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(VolleyActivity.this,"Internet Disconnected",Toast.LENGTH_SHORT).show();
    }
}
