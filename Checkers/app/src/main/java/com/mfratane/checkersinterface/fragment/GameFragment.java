package com.mfratane.checkersinterface.fragment;

import android.support.v4.app.Fragment;

import com.mfratane.boardview.BoardView;
import com.mfratane.checkersinterface.R;

import regradejogo.Jogador;
import regradejogo.Regras;
import regradejogo.Tabuleiro;

/**
 * Created by Max on 07/05/2017.
 */

public abstract class GameFragment extends Fragment {
    protected Regras regras;

    /**
     * Monta o tabuleiro a partir de uma regra.
     * @param board Instancia de BoardView.
     */
    public void setBoard(BoardView board, Jogador jogador){
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
