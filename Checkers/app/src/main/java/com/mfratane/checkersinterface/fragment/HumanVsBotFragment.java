package com.mfratane.checkersinterface.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 */
public class HumanVsBotFragment extends Fragment {

    private Regras regras;
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

        BoardView boardView = (BoardView) view.findViewById(R.id.board);

        jogador.setJogadorListener(new Jogador.JogadorListener() {
            @Override
            public void jogadaFinalizada() {
                if(!regras.isJogoFinalizado()) {
                    bot.jogar();
                }
            }
        });

        regras.setOnBoardChangedListener(new Regras.BoardChangedListener() {
            @Override
            public void onPieceMoved(Posicao posicao, Posicao posicao1) {
                boardView.movePiece(new BoardView.Pos(posicao.getI(), posicao.getJ()), new BoardView.Pos(posicao1.getI(), posicao1.getJ()));
            }

            @Override
            public void onGameFinished(int i, int i1) {
                Toast.makeText(getContext(), "Jogo acabou", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPieceRemoved(Posicao posicao) {
                boardView.removePiece(posicao.getI(), posicao.getJ());
            }

            @Override
            public void onKing(int i, int i1, int i2) {
                //boardView.removePiece(i, i2);
                //boardView.setPiece(i, i1, i2 == Regras.JOGADOR_DOIS? R.drawable.pt_dama_2 : R.drawable.psdb_dama);
            }
        });

        boardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onClickPiece(BoardView.Pos pos) {
                List<Posicao> posicaoList = jogador.getPosPossiveis(new Posicao(pos.getI(), pos.getJ()));
                List<BoardView.Pos> posList = new ArrayList<BoardView.Pos>();

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

        setBoard(boardView);

        return view;
    }

    private void setBoard(BoardView board){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                int pos = jogador.consultarPosicao(i, j);

                if(pos == Tabuleiro.PECA_TIME1){
                    board.setPiece(i, j, R.drawable.pt_peao_2);
                }

                if(pos == Tabuleiro.PECA_TIME2){
                    board.setPiece(i, j, R.drawable.psdb_peao);
                }
            }
        }
    }

}
