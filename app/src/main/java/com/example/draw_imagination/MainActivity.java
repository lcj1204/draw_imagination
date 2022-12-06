package com.example.draw_imagination;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
//import android.support.design.widget.TabLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Fragment fragment_generation, fragment_variation, fragment_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_generation = new Fragment_generation();
//        fragment_variation = new Fragment_variation();
//        fragment_gallery = new Fragment_gallery();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment_generation).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                /*Fragment selected = null;
                if(position == 0){
                    selected = fragment_generation;
                }else if (position == 1){
                    selected = fragment_variation;
                }else if (position == 2){
                    selected = fragment_gallery;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();*/
                if(position == 0){
                    if (fragment_generation == null) {
                        fragment_generation = new Fragment_generation();
                        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment_generation).commit();
                    }
                    if (fragment_generation != null) getSupportFragmentManager().beginTransaction().show(fragment_generation).commit();
                    if (fragment_variation != null) getSupportFragmentManager().beginTransaction().hide(fragment_variation).commit();
                    if (fragment_gallery != null) getSupportFragmentManager().beginTransaction().hide(fragment_gallery).commit();
                }else if (position == 1){
                    if (fragment_variation == null) {
                        fragment_variation = new Fragment_variation();
                        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment_variation).commit();
                    }
                    if (fragment_generation != null) getSupportFragmentManager().beginTransaction().hide(fragment_generation).commit();
                    if (fragment_variation != null) getSupportFragmentManager().beginTransaction().show(fragment_variation).commit();
                    if (fragment_gallery != null) getSupportFragmentManager().beginTransaction().hide(fragment_gallery).commit();
                }else if (position == 2){
                    if (fragment_gallery == null) {
                        fragment_gallery = new Fragment_gallery();
                        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment_gallery).commit();
                    }
                    if (fragment_generation != null) getSupportFragmentManager().beginTransaction().hide(fragment_generation).commit();
                    if (fragment_variation != null) getSupportFragmentManager().beginTransaction().hide(fragment_variation).commit();
                    if (fragment_gallery != null) getSupportFragmentManager().beginTransaction().show(fragment_gallery).commit();
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
}