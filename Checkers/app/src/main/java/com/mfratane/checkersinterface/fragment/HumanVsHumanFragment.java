package com.mfratane.checkersinterface.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;

import java.util.ArrayList;
import java.util.List;

import regradejogo.Bot;
import regradejogo.Jogador;
import regradejogo.Posicao;
import regradejogo.Regras;

/**
 * Fragment que implementa as funcionalidades da interface de jogo de um Humano contra outro humano.
 */
public class HumanVsHumanFragment extends GameFragment {
    private Jogador jogador;

    public HumanVsHumanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        regras = new Regras();
        jogador = new Jogador(regras, Regras.JOGADOR_UM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        boardView = (BoardView) view.findViewById(R.id.board);

        regras.setOnBoardChangedListener(getRegrasListener());

        boardView.setBoardListener(getBoardListener(jogador));

        setBoard(boardView, jogador);

        return view;
    }
}
