package com.github.takahirom.plaidanimation.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.takahirom.plaidanimation.R;


public class DetailActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
