package com.mfratane.checkersinterface.fragment;

import android.support.v4.app.Fragment;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;

import java.util.ArrayList;
import java.util.List;

import regradejogo.Jogada;
import regradejogo.Jogador;
import regradejogo.Posicao;
import regradejogo.Regras;
import regradejogo.Tabuleiro;

/**
 * Classe abstrata que agrupa funcionalidades e atributos dos fragments de jogo.
 * {@link HumanVsBotFragment}
 * {@link HumanVsHumanFragment}
 */
public abstract class GameFragment extends BaseFragment {
    /**
     * Instancia das regras.
     */
    protected Regras regras;

    /**
     * Instancia do tabuleiro da interface.
     */
    protected BoardView boardView;

    /**
     * Monta o tabuleiro a partir de uma regra.
     * @param board Instancia de BoardView.
     */
    protected void setBoard(BoardView board, Jogador jogador){
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

    /**
     * Implementa o listener das regras.
     * @return instancia de interface BoardChangedListener
     */
    protected Regras.BoardChangedListener getRegrasListener(){
        return new Regras.BoardChangedListener() {
            @Override
            public void onPieceMoved(Posicao posicao, Posicao posicao1) {
                boardView.movePiece(new BoardView.Pos(posicao.getI(), posicao.getJ()), new BoardView.Pos(posicao1.getI(), posicao1.getJ()));
            }

            @Override
            public void onGameFinished(int i, int i1) {
                toastShort(getContext().getString(R.string.end_game));
            }

            @Override
            public void onPieceRemoved(Posicao posicao) {
                boardView.removePiece(posicao.getI(), posicao.getJ());
            }

            @Override
            public void onKing(int i, int i1, int i2) {
                boardView.changePieceImage(i, i1, i2 == Regras.JOGADOR_UM? R.drawable.psdb_dama : R.drawable.pt_dama_2);
            }
        };
    }

    /**
     * Implementa o listener do tabuleiro.
     * @param jogador Instancia de Jogador.
     * @return instancia da interface BoardListener.
     */
    protected BoardView.BoardListener getBoardListener(Jogador jogador){
        return new BoardView.BoardListener() {
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
        };
    }

}
