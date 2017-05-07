package com.mfratane.checkersinterface.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;

import java.util.ArrayList;
import java.util.List;

import regradejogo.Bot;
import regradejogo.Jogada;
import regradejogo.Jogador;
import regradejogo.Posicao;
import regradejogo.Regras;
import regradejogo.Tabuleiro;


/**
 * Fragment que implementa as funcionalidades da interface de jogo de um Humano contra um bot.
 */
public class HumanVsBotFragment extends GameFragment {
    private Jogador jogador;
    private Bot bot;


    public HumanVsBotFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        regras = new Regras();
        jogador = new Jogador(regras, Regras.JOGADOR_UM);
        bot = new Bot(regras, Bot.Dificuldade.MEDIO, Regras.JOGADOR_DOIS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        boardView = (BoardView) view.findViewById(R.id.board);

        //Implemento listener do jogador.
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
}