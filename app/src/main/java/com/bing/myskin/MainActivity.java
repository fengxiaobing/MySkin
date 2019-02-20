package com.bing.myskin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.bing.myskin.Utils.SkinUtils;

public class MainActivity extends AppCompatActivity {

    protected SkinFactory mSkinFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinFactory = new SkinFactory();
        LayoutInflater.from(this).setFactory(mSkinFactory);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if(resId>0){
//           int height = getResources().getDimensionPixelSize(resId);
//        }

        SkinManager.getInstance().init(this);


    }

    public void onApply(View view) {
      SkinManager.getInstance().loadSkin("skinapk.apk");
        mSkinFactory.apply();
    }

    public void onApplyDefalt(View view) {
        SkinManager.getInstance().loadSkin("");
        mSkinFactory.apply();
    }
}
