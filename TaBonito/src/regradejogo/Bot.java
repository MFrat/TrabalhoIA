/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regradejogo;

import regradejogo.Jogador;
import java.util.List;
import regradejogo.Jogada;
import regradejogo.Peca;
import regradejogo.Regras;

/**
 *
 * @author Max
 */
public class Bot extends Jogador {

    private int numero_iteracoes;
    Jogada proximaJogada;
    int possibilidades; 
    
    public Bot(Regras regras, Dificuldade dificuldade, int time) {

        super(regras,time);
        
        
        switch(dificuldade){
            case FACIL:
                numero_iteracoes = 2;
                break;
            case MEDIO:
                numero_iteracoes = 4;
                break;
            case DIFICIL:
                numero_iteracoes = 6;
                break;
        }
    }

    public enum Dificuldade {

        FACIL, MEDIO, DIFICIL

    }
    public void Jogar() {
        Regras regra_auxiliar;
        proximaJogada = null;
        regra_auxiliar = regras.copia();
        possibilidades = 0;
        minMax(regra_auxiliar, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        System.out.println("pos=" + possibilidades + "\t");
        
        regras.moverPeca(proximaJogada.getPosInicial(), proximaJogada.getPosFinal());
    }

    public int heuristica(Regras regra) {
        if (false){//regra.getJogadorAtual() == Regras.JOGADOR_UM) {
            return regra.getnPecasJogador1() - regra.getnPecasJogador2();
        } else {
		if((regra.getnPecasJogador2() - regra.getnPecasJogador1())!=0) System.out.println("Heu= " + (regra.getnPecasJogador2() - regra.getnPecasJogador1()));
            return regra.getnPecasJogador2() - regra.getnPecasJogador1();
        }
    }
    
    
    private int minMax(Regras regra, int alpha, int beta, int iteracao) {
        if ((iteracao == numero_iteracoes) || regra.isJogoFinalizado()) {
            return heuristica(regra);
        } else {            
            Regras regra_auxiliar;            
            int minMax;
            Jogada jogadaCandidata = null;
            if (true){//regra.getJogadorAtual() == time) {INUTIL ESSA MERDA
                
                List<Peca> pecasAptas = regra.getPecasAptasDoJogadorAtual();
                for (Peca peca : pecasAptas) {
                    List<Jogada> jogadasPossiveis;
                    if(peca.isDama())    
                        jogadasPossiveis = regra.jogadasPossiveisDama(peca);
                    else
                        jogadasPossiveis = regra.jogadasPossiveis(peca);
                    
                    for (Jogada jogada : jogadasPossiveis) {                        
                        regra_auxiliar = regra.copia();
                        possibilidades++;
                        
                        
                        try{
                            regra_auxiliar.moverPeca(jogada.getPosInicial(), jogada.getPosFinal());
                        }catch(Exception e){
                            System.out.println(e);
                            System.out.println(jogada.toString());
                            System.out.println(regra.getTabuleiro().toString());
                            System.out.println("predição da rodada :" + iteracao);
                        }
                        int heuristica = minMax(regra_auxiliar, alpha, beta, iteracao + 1);                                                
                        if(heuristica > alpha){
                            alpha = heuristica;
                            jogadaCandidata = jogada;
                            
                            if(iteracao == 1)
                                proximaJogada = jogadaCandidata;                                        
                        }
                        
                        if(alpha >= beta)
                            break;

                    }
                }
                
                return alpha;
            } else {
                List<Peca> pecasAptas = regra.getPecasAptasDoJogadorAtual();
                for (Peca peca : pecasAptas) {
                    List<Jogada> jogadasPossiveis;
                    if(peca.isDama())    
                        jogadasPossiveis = regra.jogadasPossiveisDama(peca);
                    else
                        jogadasPossiveis = regra.jogadasPossiveis(peca);
                    for (Jogada jogada : jogadasPossiveis) {
                        regra_auxiliar = regra.copia();
                        possibilidades++;
                        try{
                            regra_auxiliar.moverPeca(jogada.getPosInicial(), jogada.getPosFinal());
                        }catch(Exception e){
                            System.out.println(e);
                            System.out.println(jogada.toString());
                            System.out.println(regra.getTabuleiro().toString());
                            System.out.println("predição da rodada :" + iteracao);
                        }
                        
                        int heuristica = minMax(regra_auxiliar, alpha, beta, iteracao + 1);                        
                        if(heuristica < beta){
                            beta = heuristica;
                            
                        }
                        
                        if(alpha >= beta)
                            break;

                    }

                }
                return beta;
            }
            
            
        }
    }

}

