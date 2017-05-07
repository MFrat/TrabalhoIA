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

        BoardView boardView = (BoardView) view.findViewById(R.id.board);


        //Implementa o listener das regras.
        regras.setOnBoardChangedListener(new Regras.BoardChangedListener() {
            @Override
            public void onPieceMoved(Posicao posicao, Posicao posicao1) {
                boardView.movePiece(new BoardView.Pos(posicao.getI(), posicao.getJ()), new BoardView.Pos(posicao1.getI(), posicao1.getJ()));
            }

            @Override
            public void onGameFinished(int i, int i1) {
                Toast.makeText(getContext(), R.string.end_game, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPieceRemoved(Posicao posicao) {
                boardView.removePiece(posicao.getI(), posicao.getJ());
            }

            @Override
            public void onKing(int i, int i1, int i2) {
                boardView.changePieceImage(i, i1, i2 == Regras.JOGADOR_UM? R.drawable.psdb_dama : R.drawable.pt_dama_2);
            }
        });

        //Implementa o listener do tabuleiro.
        boardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onClickPiece(BoardView.Pos pos) {
                List<Posicao> posicaoList = jogador.getPosPossiveis(new Posicao(pos.getI(), pos.getJ()));
                List<BoardView.Pos> posList = new ArrayList<>();

                for(Posicao posicao : posicaoList){
                    posList.add(new BoardView.Pos(posicao.getI(), posicao.getJ()));
                }

                boardView.markTiles(posList);
            }

            @Override
            public void onClickTile(BoardView.Pos pos, BoardView.Pos pos1) {
                jogador.realizarJogada(pos.getI(), pos.getJ(), pos1.getI(), pos1.getJ());
            }
        });

        setBoard(boardView, jogador);

        return view;
    }
}
