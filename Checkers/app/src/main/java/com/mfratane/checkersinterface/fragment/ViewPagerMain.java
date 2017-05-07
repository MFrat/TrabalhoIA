package com.mfratane.checkersinterface.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mfratane.checkersinterface.R;
import com.mfratane.checkersinterface.activity.GameAcitivty;

import static com.mfratane.checkersinterface.util.Constants.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerMain extends Fragment {

    private int gameMode;

    public ViewPagerMain() {
        // Required empty public constructor
    }

    /**
     * Factory method. Create a instance of ViewPagerMain.
     * @param gameMode
     * @return instance of ViewPagerMain
     */
    public static ViewPagerMain factory(int gameMode){
        ViewPagerMain viewPagerMain = new ViewPagerMain();

        Bundle bundle = new Bundle();
        bundle.putInt(GAME_MODE, gameMode);

        viewPagerMain.setArguments(bundle);

        return viewPagerMain;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null){
            gameMode = bundle.getInt(GAME_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.view_pager_main, container, false);

        ImageView player1 = (ImageView) view.findViewById(R.id.player1);
        ImageView player2 = (ImageView) view.findViewById(R.id.player2);

        if(gameMode == BOTVSBOT){
            player1.setImageResource(R.drawable.ic_ia);
            player2.setImageResource(R.drawable.ic_ia);
        }else if(gameMode == HUMANVSHUMAN){
            player1.setImageResource(R.drawable.ic_player);
            player2.setImageResource(R.drawable.ic_player);
        }else{
            player1.setImageResource(R.drawable.ic_player);
            player2.setImageResource(R.drawable.ic_ia);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GameAcitivty.factoryIntent(getContext(), gameMode);
                startActivity(intent);
            }
        });

        return view;
    }

    public int getGameMode() {
        return gameMode;
    }
}
