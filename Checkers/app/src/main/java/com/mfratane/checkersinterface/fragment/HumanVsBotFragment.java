package com.mfratane.checkersinterface.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;

import regradejogo.Bot;
import regradejogo.Humano;
import regradejogo.Jogador;
import regradejogo.Regras;


/**
 * Fragment que implementa as funcionalidades da interface de jogo de um Humano contra um bot.
 */
public class HumanVsBotFragment extends GameFragment {
    private Humano jogador;
    private Bot bot;


    public HumanVsBotFragment() {
        // Required empty public constructor
    }

    public static HumanVsBotFragment factory(Bundle bundle){
        HumanVsBotFragment humanVsBotFragment = new HumanVsBotFragment();

        humanVsBotFragment.setArguments(bundle);

        return humanVsBotFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jogador = new Humano(regras, Regras.JOGADOR_UM);
        bot = new Bot(regras, Bot.Dificuldade.DIFÍCIL, Regras.JOGADOR_DOIS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        boardView = (BoardView) view.findViewById(R.id.board);

        jogador.setJogadorListener(getJogadorListener());

        regras.setOnBoardChangedListener(getRegrasListener());

        boardView.setBoardListener(getBoardListener(jogador));

        setBoard(boardView, jogador);

        return view;
    }

    /**
     * Implementa o listener {@link regradejogo.Jogador.JogadorListener}.
     * @return instancia de {@link regradejogo.Jogador.JogadorListener}.
     */
    private Jogador.JogadorListener getJogadorListener(){
        return new Jogador.JogadorListener() {
            @Override
            public void jogadaFinalizada() {
                //Assim que a jogador termina a jogada, o bot joga.

                //TODO adicionar delay.
                //TODO colocar jogada do bot numa thread.
                if(!regras.isJogoFinalizado()) {
                    bot.jogar();
                }
            }
        };
    }

    @Override
    protected void endGameCallback(int i, int j) {
        toastShort("O jogo acabou!");
    }
}
