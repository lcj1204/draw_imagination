package com.example.draw_imagination;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ViewFlipper;

@SuppressWarnings("deprecation")
//public class MainActivity extends AppCompatActivity {
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("GENERATION").setIndicator("생성");
        tabSpec1.setContent(R.id.tab1);
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("VARIATION").setIndicator("변환");
        tabSpec2.setContent(R.id.tab2);
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("GALLERY").setIndicator("갤러리");
        tabSpec3.setContent(R.id.tab3);
        tabHost.addTab(tabSpec3);

        tabHost.setCurrentTab(0);

        Button G_btnPrev, G_btnNext;
        final ViewFlipper G_viewFlipper;

        G_btnPrev = (Button) findViewById(R.id.G_btnPrev);
        G_btnNext = (Button) findViewById(R.id.G_btnNext);
        G_viewFlipper = (ViewFlipper) findViewById(R.id.G_viewFlipper);

        G_btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G_viewFlipper.showPrevious();
            }
        });

        G_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G_viewFlipper.showNext();
            }
        });

    }
}