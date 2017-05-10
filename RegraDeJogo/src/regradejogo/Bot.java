/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regradejogo;

import java.util.List;

/**
 *
 * @author Max
 */
public class Bot extends Jogador {
    private int numero_iteracoes;
    private Jogada proximaJogada;
    private int possibilidades;
    private int time;

    public Bot(Regras regras, Dificuldade dificuldade, int time) {
        super(regras);
        
        this.time = time;

        switch (dificuldade) {
            case FACIL:
                numero_iteracoes = 2;
                break;
            case MEDIO:
                numero_iteracoes = 4;
                break;
            case DIFÍCIL:
                numero_iteracoes = 6;
                break;
        }
    }

    public enum Dificuldade {
        FACIL, MEDIO, DIFÍCIL
    }

    public Jogada jogar() {
        Regras regra_auxiliar;
        proximaJogada = null;
        regra_auxiliar = regras.copia();
        possibilidades = 0;
        minMax(regra_auxiliar, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        //System.out.println(possibilidades);
        //regras.moverPeca(proximaJogada.getPosInicial(), proximaJogada.getPosFinal());
        
        return proximaJogada;
    }
    
    public int goku_mode(Regras regra) { //UTILITY COM PESO PARA DAMA
        int genkindama = 0;
        List<Peca> pecasAptas = regra.getPecasAptasDoJogadorAtual();
        for (Peca peca : pecasAptas) {
            if(peca.isDama()) genkindama++;
        }
        return genkindama + regra.getnPecasJogador2() - regra.getnPecasJogador1();
    }
    
    public int utility(Regras regra) {
        return regra.getnPecasJogador2() - regra.getnPecasJogador1();
    }

    private int minMax(Regras regra, int alpha, int beta, int iteracao) {
        if ((iteracao == numero_iteracoes) || regra.isJogoFinalizado()) {
            return goku_mode(regra); //USAR GOKU_MODE OU UTILITY
        }

        Regras regra_auxiliar;
        Jogada jogadaCandidata;
        if (regra.getJogadorAtual() == time) {
            int value = Integer.MIN_VALUE;
            List<Peca> pecasAptas = regra.getPecasAptasDoJogadorAtual();
            for (Peca peca : pecasAptas) {
                List<Jogada> jogadasPossiveis;

                if (peca.isDama()) {
                    jogadasPossiveis = regra.jogadasPossiveisDama(peca);
                } else {
                    jogadasPossiveis = regra.jogadasPossiveis(peca);
                }

                for (Jogada jogada : jogadasPossiveis) {
                    regra_auxiliar = regra.copia();
                    possibilidades++;

                    try {
                        regra_auxiliar.moverPeca(jogada.getPosInicial(), jogada.getPosFinal());
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(jogada.toString());
                        System.out.println(regra.getTabuleiro().toString());
                        System.out.println("predição da rodada :" + iteracao);
                    }
                    int tmp = minMax(regra_auxiliar, alpha, beta, iteracao + 1);
                    if (tmp > value) {
                        value = tmp;
                    }
                    if (value > alpha) {
                        alpha = value;
                        jogadaCandidata = jogada;

                        if (iteracao == 1) {
                            proximaJogada = jogadaCandidata;
                        }
                    }

                    if (beta <= alpha) {
                        break;
                    }

                }
            }

            return value;
        } else {
            int value = Integer.MAX_VALUE;
            List<Peca> pecasAptas = regra.getPecasAptasDoJogadorAtual();
            for (Peca peca : pecasAptas) {
                List<Jogada> jogadasPossiveis;
                if (peca.isDama()) {
                    jogadasPossiveis = regra.jogadasPossiveisDama(peca);
                } else {
                    jogadasPossiveis = regra.jogadasPossiveis(peca);
                }

                for (Jogada jogada : jogadasPossiveis) {
                    regra_auxiliar = regra.copia();
                    possibilidades++;
                    try {
                        regra_auxiliar.moverPeca(jogada.getPosInicial(), jogada.getPosFinal());
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println(jogada.toString());
                        System.out.println(regra.getTabuleiro().toString());
                        System.out.println("predição da rodada :" + iteracao);
                    }

                    int tmp = minMax(regra_auxiliar, alpha, beta, iteracao + 1);
                    if (tmp < value) {
                        value = tmp;
                    }
                    if (value < beta) {
                        beta = value;

                    }
                    if (beta <= alpha) {
                        break;
                    }

                }

            }
            return value;
        }
    }

}
