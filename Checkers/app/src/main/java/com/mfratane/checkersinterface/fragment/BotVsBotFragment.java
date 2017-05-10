package com.mfratane.checkersinterface.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;
import com.mfratane.checkersinterface.util.Constants;

import regradejogo.Bot;
import regradejogo.Humano;
import regradejogo.Jogada;
import regradejogo.Posicao;
import regradejogo.Regras;

/**
 * Fragment que implementa as funcionalidades da interface de jogo de um Bot contra outro bot.
 */
public class BotVsBotFragment extends GameFragment {
    private Bot bot1;
    private Bot bot2;

    public BotVsBotFragment() {
        // Required empty public constructor
    }

    public static BotVsBotFragment factory(Bundle bundle){
        BotVsBotFragment humanVsHumanFragment = new BotVsBotFragment();

        humanVsHumanFragment.setArguments(bundle);

        return humanVsHumanFragment;
    }

    public static BotVsBotFragment factory(Bundle bundle, int dificuldadeBot1, int dificuldadeBot2){
        BotVsBotFragment humanVsHumanFragment = new BotVsBotFragment();

        bundle.putInt(Constants.DIFICULDADE_BOT1, dificuldadeBot1);
        bundle.putInt(Constants.DIFICULDADE_BOT2, dificuldadeBot2);

        humanVsHumanFragment.setArguments(bundle);

        return humanVsHumanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        int dificuldade1 = getDefaultDificulty();
        int dificuldade2 = getDefaultDificulty();

        if(bundle != null){
            dificuldade1 = bundle.getInt(Constants.DIFICULDADE_BOT1);
            dificuldade2 = bundle.getInt(Constants.DIFICULDADE_BOT2);
        }else{
            warnLog("Nenhuma dificuldade selecionada. Dificuldade padrão setada.");
        }

        bot1 = new Bot(regras, getDificuldade(dificuldade1), Regras.JOGADOR_UM);
        bot2 = new Bot(regras, getDificuldade(dificuldade2), Regras.JOGADOR_DOIS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_botxbot, container, false);

        Button button = (Button) view.findViewById(R.id.play);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jogada jogada = regras.getJogadorAtual() == Regras.JOGADOR_UM? bot1.jogar() : bot2.jogar();
                Posicao posIni = jogada.getPosInicial();
                Posicao posFim = jogada.getPosFinal();
                bot1.realizarJogada(posIni.getI(), posIni.getJ(), posFim.getI(), posFim.getJ());
            }
        });

        boardView = (BoardView) view.findViewById(R.id.board);

        regras.setOnBoardChangedListener(getRegrasListener());

        boardView.setBoardListener(getBoardListener(bot1));

        setBoard(boardView, bot1);

        return view;
    }

    @Override
    protected void endGameCallback(int i, int j) {
        toastShort("O jogo acabou!");
    }
}
