package com.sayanrajguha.nimbuscreations.memlock.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sayanrajguha.nimbuscreations.memlock.R;
import com.sayanrajguha.nimbuscreations.memlock.fragment.NavigationDrawerFragment;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 */
public class MainActivity extends AppCompatActivity {

    private static final String KEY_LOG = "- Main Activity -";
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(mToolBar);

        if(null != getSupportActionBar()) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        NavigationDrawerFragment navigationDrawerFragment =
                (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragNavDrawer);
        navigationDrawerFragment.setup(R.id.fragNavDrawer,(DrawerLayout)findViewById(R.id.dlMain),mToolBar);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
