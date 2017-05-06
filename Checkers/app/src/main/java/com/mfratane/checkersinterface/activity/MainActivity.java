package com.mfratane.checkersinterface.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfratane.checkersinterface.R;
import com.mfratane.checkersinterface.adapter.GenericAdapter;
import com.mfratane.checkersinterface.fragment.ViewPagerMain;
import com.mfratane.checkersinterface.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.mfratane.checkersinterface.util.Constants.BOTVSBOT;
import static com.mfratane.checkersinterface.util.Constants.HUMANVSHUMAN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViewPager();
    }

    private void setUpViewPager(){
        final ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragments = getFragments();
        int size = fragments.size();

        PagerAdapter adapter = new GenericAdapter(getSupportFragmentManager(), fragments, size);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(size);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ViewPagerMain frag = (ViewPagerMain) pager.getAdapter().instantiateItem(pager, pager.getCurrentItem());
                int gameMode = frag.getGameMode();
                setResources(gameMode);
                animPlayersLayouts();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setResources(int gameMode){
        ImageView player1 = (ImageView) findViewById(R.id.player1Image);
        ImageView player2 = (ImageView) findViewById(R.id.player2Image);

        TextView player1Text = (TextView) findViewById(R.id.player1Text);
        TextView player2Text = (TextView) findViewById(R.id.player2Text);

        if(gameMode == BOTVSBOT){
            player1.setImageResource(R.drawable.ic_ia);
            player2.setImageResource(R.drawable.ic_ia);
            player1Text.setText("BOT");
            player2Text.setText("BOT");
        }else if(gameMode == HUMANVSHUMAN){
            player1.setImageResource(R.drawable.ic_player);
            player2.setImageResource(R.drawable.ic_player);
            player1Text.setText("Humano");
            player2Text.setText("Humano");
        }else{
            player1.setImageResource(R.drawable.ic_player);
            player2.setImageResource(R.drawable.ic_ia);
            player1Text.setText("Humano");
            player2Text.setText("BOT");
        }
    }

    private List<Fragment> getFragments(){
        List<Fragment> list = new ArrayList<>();

        list.add(ViewPagerMain.factory(Constants.BOTVSHUMAN));
        list.add(ViewPagerMain.factory(Constants.HUMANVSHUMAN));
        list.add(ViewPagerMain.factory(Constants.BOTVSBOT));

        return list;
    }

    private void animPlayersLayouts(){
        View viewPlayer1 = findViewById(R.id.layoutJogador1);
        View viewPlayer2 = findViewById(R.id.layoutJogador2);

        animatePlayer1(viewPlayer1);
        animatePlayer2(viewPlayer2);


    }

    private void animatePlayer1(View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right);

        view.startAnimation(animation);
    }

    private void animatePlayer2(View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.right_to_left);

        view.startAnimation(animation);
    }
}
