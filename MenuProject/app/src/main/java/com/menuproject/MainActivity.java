package com.menuproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast t = Toast.makeText(this, "Settings", Toast.LENGTH_SHORT);
            t.show();
            return true;
        } else if (id == R.id.action_help) {
            Intent intent = new Intent( "com.intentHelp" );
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnFireSms(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode("2016961191")));
        intent.putExtra("sms_body", "Tianyu Wang");
        startActivity(intent);
    }

    public void btnFireTele(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:2016961191"));
        startActivity(intent);
    }

    public void btnFireWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(intent);
    }


    public void btnFireMap(View view) {
        String geoLoc = String.format("geo:40.7114,-74.0050");
        Uri geo = Uri.parse(geoLoc);
        Intent intent = new Intent(Intent.ACTION_VIEW, geo);
        startActivity(intent);
    }

    public void btnFireShare(View view) {
        Intent intent = new Intent(Intent.ACTION_CHOOSER);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share the love!"));
    }

    public void btnFiresNewAct(View view) {
        Intent intent = new Intent( "com.intentNewAct" );
        startActivity(intent);
    }
}