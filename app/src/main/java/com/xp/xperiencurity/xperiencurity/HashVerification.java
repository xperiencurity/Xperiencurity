package com.xp.xperiencurity.xperiencurity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class HashVerification extends TabActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_verification);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        String sTextTabTitle = getString(R.string.tab_text);
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, TextActivity.class);
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost
                .newTabSpec("text")
                .setIndicator(sTextTabTitle,
                        res.getDrawable(R.drawable.ic_tab_text))
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        String sFileTabTitle = getString(R.string.tab_file);
        intent = new Intent().setClass(this, FileActivity.class);
        spec = tabHost
                .newTabSpec("file")
                .setIndicator(sFileTabTitle,
                        res.getDrawable(R.drawable.ic_tab_file))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, CompareActivity.class);
        String sCompareTabTitle = getString(R.string.tab_compare);
        spec = tabHost
                .newTabSpec("compare")
                .setIndicator(sCompareTabTitle,
                        res.getDrawable(R.drawable.ic_tab_compare))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }
}
