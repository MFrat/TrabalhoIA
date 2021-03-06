package com.mfratane.checkersinterface.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mfratane.checkersinterface.R;
import com.mfratane.checkersinterface.fragment.BotVsBotFragment;
import com.mfratane.checkersinterface.fragment.GameFragment;
import com.mfratane.checkersinterface.fragment.HumanVsBotFragment;
import com.mfratane.checkersinterface.fragment.HumanVsHumanFragment;

import static com.mfratane.checkersinterface.util.Constants.BOTVSBOT;
import static com.mfratane.checkersinterface.util.Constants.BOTVSHUMAN;
import static com.mfratane.checkersinterface.util.Constants.GAME_MODE;
import static com.mfratane.checkersinterface.util.Constants.HUMANVSHUMAN;

/**
 * Activity que segura o fragment que controla um jogo.
 * {@link HumanVsBotFragment}
 * {@link HumanVsHumanFragment}
 */
public class GameAcitivty extends AppCompatActivity {
    /**
     * Tipo do jogo.
     * {@link com.mfratane.checkersinterface.util.Constants}
     */
    private int gameMode;

    public static final String PECA_JOGADOR1 = "pecaJogador1";
    public static final String PECA_JOGADOR2 = "pecaJogador2";
    public static final String DAMA_JOGADOR1 = "damaJogador1";
    public static final String DAMA_JOGADOR2 = "damaJogador2";

    /**
     * Factory method que retorna uma instancia de Intent.
     * @param context Instancia de Context.
     * @param gameMode Tipo de jogo.
     * @return instancia de Intent.
     */
    public static Intent factoryIntent(Context context, int gameMode, int pecaJogador1,
                                       int pecaJogador2, int damaJogador1, int damaJogador2){
        Intent intent = new Intent(context, GameAcitivty.class);

        Bundle bundle = new Bundle();
        bundle.putInt(GAME_MODE, gameMode);
        bundle.putInt(PECA_JOGADOR1, pecaJogador1);
        bundle.putInt(PECA_JOGADOR2, pecaJogador2);
        bundle.putInt(DAMA_JOGADOR1, damaJogador1);
        bundle.putInt(DAMA_JOGADOR2, damaJogador2);

        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            gameMode = bundle.getInt(GAME_MODE);
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if(fragment == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, getFragment(gameMode)).commit();
        }
    }

    /**
     * Retorna um Fragment de acordo com o tipo de jogo.
     * @param gameMode modo de jogo.
     * @return instancia de {@link Fragment}.
     */
    private Fragment getFragment(int gameMode){
        Bundle bundle = getIntent().getExtras();
        switch (gameMode){
            case BOTVSBOT:
                return BotVsBotFragment.factory(bundle, GameFragment.DIFICIL, GameFragment.DIFICIL);
            case HUMANVSHUMAN:
                return HumanVsHumanFragment.factory(bundle);
        }

        return HumanVsBotFragment.factory(bundle);
    }
}
