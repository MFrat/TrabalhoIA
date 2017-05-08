/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteIntegração;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import regradejogo.Humano;
import regradejogo.Jogada;
import regradejogo.Peca;
import regradejogo.Posicao;
import regradejogo.Regras;

/**
 *
 * @author Max
 */
public class JogadasPossiveisPeca {
    private Regras regras;
    private List<Jogada> oraculo;
    private Humano jogador;
    private String pecaComJogada = "./testes/testeMovimentacaoPeca/ComJogada/";
    private String pecaSemJogada = "./testes/testeMovimentacaoPeca/SemJogada/";
    
    public JogadasPossiveisPeca() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void pecaSemJogada() throws IOException{
        /**
         * Cenário 1
         * Peca do time 1 sem jogadas. Cercada por peças do time 2.
         */
        regras = new Regras(pecaSemJogada + "testeJogada1.txt");
        oraculo = new ArrayList<>();
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(4, 3)).toString());
        
        /**
         * Cenário 2
         * Peca do time 1 sem jogadas. Em uma "quina" do tabuleiro e com uma peca do mesmo time na única posicao possível.
         */
        regras = new Regras(pecaSemJogada + "testeJogada2.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(7, 7)).toString());
        
        /**
         * Cenário 3
         * Peca do time 1 sem jogadas. Em uma "quina" do tabuleiro e com duas pecas do time adversário em sequencia.
         */
        regras = new Regras(pecaSemJogada + "testeJogada3.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(7, 7)).toString());
        
        /**
         * Cenário 4
         * Dama do time 1 sem jogadas. uma jogada travada por ter peca do mesmo time, e outra travada por ter duas do adversário em sequencia.
         */
        regras = new Regras(pecaSemJogada + "testeJogada4.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(7, 3)).toString());
    }
    
    @Test
    public void pecaComJogada() throws IOException{
        /**
         * Cenário 1
         * Captura simples para frente.
         */
        regras = new Regras(pecaComJogada + "testeCaptura1.txt");
        oraculo = stub1();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(4, 3)).toString());
        //System.out.println(jogador.getJogadasPossiveis(new Posicao(4, 3)).toString());
        
        /**
         * Cenário 2
         * Captura dupla para frente.
         */
        regras = new Regras(pecaComJogada + "testeCaptura2.txt");
        oraculo = stub2();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(2, 2)).toString());
        //System.out.println(jogador.getJogadasPossiveis(new Posicao(2, 2)).toString());
        
        /**
         * Cenário 3
         * Captura simples para trás.
         */
        regras = new Regras(pecaComJogada + "testeCaptura3.txt");
        oraculo = stub3();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(2, 2)).toString());
        //System.out.println(jogador.getJogadasPossiveis(new Posicao(4, 3)).toString());
        
        /**
         * Cenário 4
         * Captura dupla para trás.
         */
        regras = new Regras(pecaComJogada + "testeCaptura4.txt");
        oraculo = stub4();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(2, 2)).toString());
        //System.out.println(jogador.getJogadasPossiveis(new Posicao(2, 2)).toString());
    }
    
    private List<Jogada> stub1(){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(2, false), new Peca(1, false), new Posicao(4, 3), new Posicao(2, 5)));
        
        return jogadas;
    }
    
    private List<Jogada> stub2(){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(2, false), new Peca(1, false), new Posicao(2, 2), new Posicao(0, 0)));
        jogadas.add(new Jogada(new Peca(2, false), new Peca(1, false), new Posicao(2, 2), new Posicao(0, 4)));
        
        return jogadas;
    }
    
    private List<Jogada> stub3(){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(2, false), new Peca(1, false), new Posicao(2, 2), new Posicao(4, 0)));
        
        return jogadas;
    }
    
    private List<Jogada> stub4(){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(2, false), new Peca(1, false), new Posicao(2, 2), new Posicao(4, 0)));
        jogadas.add(new Jogada(new Peca(2, false), new Peca(1, false), new Posicao(2, 2), new Posicao(4, 4)));
        
        return jogadas;
    }
    
    
}
