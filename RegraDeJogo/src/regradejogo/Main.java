package regradejogo;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //Regras regras = new Regras(".\\testes\\testePosPossiveisPeca\\testePosPossiveis7.txt");
        Regras regras = new Regras();//"testeCaptura1.txt");//"testeCaptura1.txt");
        
        regras.setOnBoardChangedListener(new Regras.BoardChangedListener() {
            @Override
            public void onPieceMoved(Posicao posInicial, Posicao posFinal) {
               
            }

            @Override
            public void onGameFinished(int vencedor, int causa) {
                System.out.println("Jogo acabou!");
            }

            @Override
            public void onPieceRemoved(Posicao posicao) {
                
            }

            @Override
            public void onKing(int i, int j, int time) {
                
            }
        });
        regras.setJogadorAtual(Regras.JOGADOR_UM);

        jogarHumanoVsBot(regras);
    }

/*    public static void jogarHumanoVsHumano(Regras regras) {
        Humano humanoPl1 = new Humano(regras, Regras.JOGADOR_UM);
        Humano humanoPl2 = new Humano(regras, Regras.JOGADOR_DOIS);

        while (!regras.isJogoFinalizado()) {
            System.out.println(regras.getTabuleiro().toString());
            if (regras.getJogadorAtual() == humanoPl1.getTime()) {
                humanoJoga(humanoPl1, regras);
            } else {
                humanoJoga(humanoPl2, regras);
            }
        }
    }*/

    private static void humanoJoga(Humano humanoPl, Regras regras) {
        Scanner in = new Scanner(System.in);
        System.out.println("vez do jogador " +humanoPl.getTime()+": digite a posição da peça separada por ; ");
        String posPeca = in.next();
        String[] strPos = posPeca.split(";");
        int xPeca = Integer.parseInt(strPos[0]);
        int yPeca = Integer.parseInt(strPos[1]);
        Peca peca = null;
        try{
            peca = regras.getPeca(new Posicao(xPeca, yPeca));
            System.out.println("Sugestão de jogadas");
            System.out.println(humanoPl.getPosPossiveis(regras.getTabuleiro().getPosicao(peca)).toString());
            System.out.println("digite a posição de destino da peça separada por ; ");
            String posDestino = in.next();
            String[] strPosDestino = posDestino.split(";");
            int xDestino = Integer.parseInt(strPosDestino[0]);
            int yDestino = Integer.parseInt(strPosDestino[1]);
            try {
                humanoPl.realizarJogada(xPeca, yPeca, xDestino, yDestino);
            } catch (Exception e) {
                System.out.println(e);
            }
        }catch(Exception e){
            System.out.println("deu ruim br");
            return;
        }
        
    }

    public static void jogarHumanoVsBot(Regras regras) {
        Bot botPl2 = new Bot(regras, Bot.Dificuldade.CEM_CONTO_QUE_ALINE_NAO_GANHA, Regras.JOGADOR_DOIS);
        Humano humanoPl1 = new Humano(regras, Regras.JOGADOR_UM);
        //Humano humanoPl2 = new Humano(regras, Regras.JOGADOR_DOIS);

        while (!regras.isJogoFinalizado()) {
            System.out.println(regras.getTabuleiro().toString());
            if (regras.getJogadorAtual() == humanoPl1.getTime()) {
                humanoJoga(humanoPl1, regras);
            } else {
                botPl2.Jogar();
                //humanoJoga(humanoPl2, regras);
            }
        }

    }

}
