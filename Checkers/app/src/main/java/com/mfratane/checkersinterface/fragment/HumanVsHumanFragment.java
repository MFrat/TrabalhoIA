package com.mfratane.checkersinterface.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;


import regradejogo.Humano;
import regradejogo.Regras;

/**
 * Fragment que implementa as funcionalidades da interface de jogo de um Humano contra outro humano.
 */
public class HumanVsHumanFragment extends GameFragment {
    private Humano jogador;

    public HumanVsHumanFragment() {
        // Required empty public constructor
    }

    public static HumanVsHumanFragment factory(Bundle bundle){
        HumanVsHumanFragment humanVsHumanFragment = new HumanVsHumanFragment();

        humanVsHumanFragment.setArguments(bundle);

        return humanVsHumanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        jogador = new Humano(regras, Regras.JOGADOR_UM);
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

    @Override
    protected void endGameCallback(int i, int j) {
        toastShort("O jogo acabou!");
    }
}
