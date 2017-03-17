package com.lsy.frescodemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends Activity implements ShowFragment.showFrgInterface {

    FragmentManager fm;
    ShowFragment showFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);    //fresco初始化
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        showFragment = new ShowFragment();
        ft.replace(R.id.frg_show, showFragment);
        ft.commit();
    }

    @Override
    public void resetOnClick() {
        FragmentTransaction ftRemove = fm.beginTransaction();
        ftRemove.remove(showFragment);
        ftRemove.commit();
        FragmentTransaction ftShow = fm.beginTransaction();
        showFragment = new ShowFragment();
        ftShow.replace(R.id.frg_show, showFragment);
        ftShow.commit();
    }
}
