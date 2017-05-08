package com.mfratane.checkersinterface.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.activity.GameAcitivty;

import java.util.ArrayList;
import java.util.List;

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
     * Id da imagem da peça do jogadore 1.
     */
    protected int player1Piece;

    /**
     * Id da imagem da peça do jogadore 2.
     */
    protected int player2Piece;

    /**
     * id da imagem da dama dos jogador 1.
     */
    protected int player1King;

    /**
     * id da imagem da dama dos jogador 1.
     */
    protected int player2King;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        regras = new Regras();

        if(bundle != null){
            player1Piece = bundle.getInt(GameAcitivty.PECA_JOGADOR1);
            player1King = bundle.getInt(GameAcitivty.DAMA_JOGADOR1);
            player2Piece = bundle.getInt(GameAcitivty.PECA_JOGADOR2);
            player2King = bundle.getInt(GameAcitivty.DAMA_JOGADOR2);
        }
    }

    /**
     * Monta o tabuleiro a partir de uma regra.
     * @param board Instancia de {@link BoardView}.
     */
    protected void setBoard(BoardView board, Jogador jogador){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                int pos = jogador.consultarPosicao(i, j);

                if(pos == Tabuleiro.PECA_TIME1){
                    board.setPiece(i, j, player1Piece);
                }

                if(pos == Tabuleiro.PECA_TIME2){
                    board.setPiece(i, j, player2Piece);
                }
            }
        }
    }

    /**
     * Implementa o listener das regras.
     * @return instancia de interface {@link regradejogo.Regras.BoardChangedListener}
     */
    protected Regras.BoardChangedListener getRegrasListener(){
        return new Regras.BoardChangedListener() {
            @Override
            public void onPieceMoved(Posicao posicao, Posicao posicao1) {
                boardView.movePiece(new BoardView.Pos(posicao.getI(), posicao.getJ()),
                        new BoardView.Pos(posicao1.getI(), posicao1.getJ()));
            }

            @Override
            public void onGameFinished(int i, int i1) {
                endGameCallback(i, i1);
            }

            @Override
            public void onPieceRemoved(Posicao posicao) {
                boardView.removePiece(posicao.getI(), posicao.getJ());
            }

            @Override
            public void onKing(int i, int i1, int i2) {
                boardView.changePieceImage(i, i1, i2 == Regras.JOGADOR_UM? player2King : player1King);
            }
        };
    }

    /**
     * Implementa o listener do tabuleiro.
     * @param jogador Instancia de Jogador.
     * @return instancia da interface {@link com.mfratane.boardview.BoardView.BoardListener}.
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

    protected abstract void endGameCallback(int i, int j);

}
