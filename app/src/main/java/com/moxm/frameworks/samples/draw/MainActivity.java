package com.moxm.frameworks.samples.draw;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RelativeLayout;

import com.moxm.frameworks.samples.draw.view.SampleSurfaceView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new SampleSurfaceView(this));
        setContentView(R.layout.item_main);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(new SampleSurfaceView(this));
    }

}
