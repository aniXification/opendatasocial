package com.aniXification.opendatasocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AppInfoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_info, menu);
        return true;
    }
}
