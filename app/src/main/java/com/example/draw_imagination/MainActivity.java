package com.example.draw_imagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
//import android.support.design.widget.TabLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

//    Fragment_variation variation = new Fragment_variation();
    private static Context context;

    Fragment[] fragments = new Fragment[3];
    Fragment[] newFragments = {
            new Fragment_generation(),
            new Fragment_variation(),
            new Fragment_gallery()
    };

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        fragments[0] = new Fragment_generation();

        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragments[0])
                .commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                for (int i = 0; i < 3; i++) {
                    if (i == position) {
                        if (fragments[position] == null) {
                            fragments[position] = newFragments[position];
                            fragmentManager
                                    .beginTransaction()
                                    .add(R.id.frame, fragments[position])
                                    .commit();
                        }
                        else fragmentManager
                                .beginTransaction()
                                .show(fragments[position])
                                .commit();
                    }
                    else {
                        if (fragments[i] != null) fragmentManager
                                .beginTransaction()
                                .hide(fragments[i])
                                .commit();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public static Context ApplicationContext(){
        return MainActivity.context;
    }
}