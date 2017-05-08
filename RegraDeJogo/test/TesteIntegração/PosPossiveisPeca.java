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
public class PosPossiveisPeca {
    private Regras regras;
    private List<Posicao> oraculo;
    private Humano jogador;
    private String caminho = "./testes/testePosPossiveisPeca/";
    
    public PosPossiveisPeca() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void posPossiveisPeca() throws IOException{
        /**
         * Cenário 1
         * Movimento livre para frente time 1.
         */
        regras = new Regras(caminho + "testePosPossiveis1.txt");
        oraculo = stub1();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getPosPossiveis(new Posicao(4, 3)).toString());
        
        /**
         * Cenário 2
         * Movimento livre para frente time 2.
         */
        regras = new Regras(caminho + "testePosPossiveis2.txt");
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        oraculo = stub2();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getPosPossiveis(new Posicao(4, 3)).toString());
        
        /**
         * Cenário 3
         * Peca do time 1 na quina inferior esquerda.
         */
        regras = new Regras(caminho + "testePosPossiveis3.txt");
        oraculo = stub3();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getPosPossiveis(new Posicao(7, 0)).toString());
        
        /**
         * Cenário 4
         * Peca do time 2 na quina superior direita.
         */
        regras = new Regras(caminho + "testePosPossiveis4.txt");
        oraculo = stub4();
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getPosPossiveis(new Posicao(0, 7)).toString());
        
        /**
         * Cenário 5
         * Peca do time 1 na lateral do tabuleiro.
         */
        regras = new Regras(caminho + "testePosPossiveis5.txt");
        oraculo = stub5();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getPosPossiveis(new Posicao(4, 7)).toString());
        
        /**
         * Cenário 6
         * Peca do time 2 na lateral do tabuleiro.
         */
        regras = new Regras(caminho + "testePosPossiveis6.txt");
        oraculo = stub6();
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getPosPossiveis(new Posicao(4, 7)).toString());
    }
    
    private List<Posicao> stub1(){
        List<Posicao> jogadas = new ArrayList<>();
        
        jogadas.add(new Posicao(3, 2));
        jogadas.add(new Posicao(3, 4));
        
        return jogadas;
    }
    
    private List<Posicao> stub2(){
        List<Posicao> jogadas = new ArrayList<>();
        
        jogadas.add(new Posicao(5, 2));
        jogadas.add(new Posicao(5, 4));
        
        return jogadas;
    }
    
    private List<Posicao> stub3(){
        List<Posicao> jogadas = new ArrayList<>();
        
        jogadas.add(new Posicao(6, 1));
        
        return jogadas;
    }
    
    private List<Posicao> stub4(){
        List<Posicao> jogadas = new ArrayList<>();
        
        jogadas.add(new Posicao(1, 6));
        
        return jogadas;
    }
    
    private List<Posicao> stub5(){
        List<Posicao> jogadas = new ArrayList<>();
        
        jogadas.add(new Posicao(3, 6));
        
        return jogadas;
    }
    
    private List<Posicao> stub6(){
        List<Posicao> jogadas = new ArrayList<>();
        
        jogadas.add(new Posicao(5, 6));
        
        return jogadas;
    }
    
}
