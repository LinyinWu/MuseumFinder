package com.example.linyin.museumfinder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;



public class MainActivity extends Activity implements KeyEvent.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /* after clicking the button, going to search the query */
    public void onClick (View v) {
        EditText city = (EditText) findViewById(R.id.editText);
        String cityStr = city.getText().toString();
        cityStr = cityStr.trim();
        cityStr = cityStr.replaceAll("\\s+","%20");

        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=museums+in+" + cityStr + "&key=AIzaSyDwKlci3En2HYmp6oy3cZjZbdz96NYNQpw";

        Intent i = new Intent(this,SearchActivity.class);
        i.putExtra("url",url);
        startActivity(i);

    }


}