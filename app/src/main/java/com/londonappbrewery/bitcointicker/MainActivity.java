package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL

    // Member Variables:
    TextView mPriceTextView;
    Double BTCvalue;

    // API PUBLIC KEY to use api data
    private final String PUBLIC_KEY = "NmE5NTE1ZGJiNjQ0NDc3ZGI0NjY5ODFiMzk0MWZmY2M";
    private final String SECRET_KEY = "NjdjYzQxOTlkY2E1NDJmMWFkM2NlZGNmMWNhMzkwN2FjYTg2MTYzMzA1MWU0YTViYWI1NGJjODFkMWIwOTc3OA";
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner

spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int i, long l) {
        Log.d("DEBUGBYJB", String.valueOf(adapter.getItemAtPosition(i)));
        letsDoSomeNetworking(BASE_URL+String.valueOf(adapter.getItemAtPosition(i)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("DEBUGBYJB", "nothing was selected");
    }
});
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    response.getDouble("ask");
                    BTCvalue = response.getDouble("ask");
                    mPriceTextView.setText(Double.toString(BTCvalue));

                    Log.d("debugjjb", "JSON: "+response.getString("ask"));
                    // called when response HTTP status is "200 OK"
                    Log.d("jbdebug", "JSON: " + response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {

                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("jbdebug", "Request fail! Status code: " + statusCode);
                Log.d("jbdebug", "Fail response: " + response);
                Log.e("ERROR", e.toString());
                //   Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
