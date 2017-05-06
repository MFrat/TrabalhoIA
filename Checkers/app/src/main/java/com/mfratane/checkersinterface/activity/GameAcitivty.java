package com.mfratane.checkersinterface.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mfratane.checkersinterface.R;
import com.mfratane.checkersinterface.fragment.HumanVsBotFragment;

public class GameAcitivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if(fragment == null){
            fragment = new HumanVsBotFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
