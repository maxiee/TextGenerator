package com.maxiee.textgenerator.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maxiee.textgenerator.R;
import com.maxiee.textgenerator.adapters.DrawerListAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private DrawerListAdapter drawerListAdapter;
    private Toolbar toolbar;
    private int mCurrentSelectedPosition = 0;

    private DataFragment dataFragment;
    private GeneratorFragment mGeneratorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        setupDrawer();

        FragmentManager fm = getSupportFragmentManager();
        if (mGeneratorFragment == null) {
            mGeneratorFragment = new GeneratorFragment();
        }
        fm.beginTransaction()
                .replace(R.id.container, mGeneratorFragment)
                .commit();
    }

    void setupDrawer() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.drawer);
        ArrayList<DrawerListAdapter.Item> data = new ArrayList<>();
        data.add(new DrawerListAdapter.Item(
                getString(R.string.generator),
                R.drawable.ic_action_description));
        data.add(new DrawerListAdapter.Item(
                getString(R.string.database),
                R.drawable.ic_action_inbox
        ));
        drawerListAdapter = new DrawerListAdapter(
                this,
                data,
                R.color.white,
                R.color.accent
        );
        drawerList.setAdapter(drawerListAdapter);
        drawerList.setItemChecked(0, true);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (mCurrentSelectedPosition == position) {
                    return;
                }

                mCurrentSelectedPosition = position;
                drawerList.setItemChecked(position, true);
                drawerListAdapter.setSelectedPosition(position);

                FragmentManager fm = getSupportFragmentManager();

                switch (position) {
                    case 0:
                        if (mGeneratorFragment == null) {
                            mGeneratorFragment = new GeneratorFragment();
                        }
                        fm.beginTransaction()
                                .replace(R.id.container, mGeneratorFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        if (dataFragment == null) {
                            dataFragment = new DataFragment();
                        }
                        fm.beginTransaction()
                                .replace(R.id.container, dataFragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });
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
