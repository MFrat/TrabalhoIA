/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteIntegração;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import regradejogo.Humano;
import regradejogo.Jogada;
import regradejogo.Jogador;
import regradejogo.Peca;
import regradejogo.Posicao;
import regradejogo.Regras;

/**
 *
 * @author Max
 */
public class JogadasPossíveisDama {
    private Regras regras;
    private List<Jogada> oraculo;
    private Jogador jogador;
    private String damaComJogada = "./testes/testesMovimentacaoDama/DamaComJogada/";
    private String damaSemJogada = "./testes/testesMovimentacaoDama/DamaSemJogada/";
    
    public JogadasPossíveisDama() {
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
    
     /**
     * Cenários quando a dama não possui nenhuma jogada possível.
     */
    @Test
    public void damaSemJogada() throws IOException{
        /**
         * Cenário 1
         * Dama do time 1 sem jogadas. Cercada por peças do time 2.
         */
        regras = new Regras(damaSemJogada + "testeDama1.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
        
        /**
         * Cenário 2
         * Dama do time 2 sem jogadas. Cercada por peças do proprio time.
         */
        regras = new Regras(damaSemJogada + "testeDama2.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
        
        /**
         * Cenário 3
         * Dama do time 1 sem jogadas. Cercada por peças do time 2.
         */
        regras = new Regras(damaSemJogada + "testeDama3.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(0, 0)).toString());
        
        /**
         * Cenário 4
         * Dama do time 1 sem jogadas. Cercada por peças do proprio time.
         */
        regras = new Regras(damaSemJogada + "testeDama4.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
        
        /**
         * Cenário 5
         * Dama do time 2 sem jogadas. Cercada por peças do time 2.
         */
        regras = new Regras(damaSemJogada + "testeDama5.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(0, 0)).toString());
        
        /**
         * Cenário 6
         * Dama do time 2 sem jogadas. Cercada por peças do time adversário.
         */
        regras = new Regras(damaSemJogada + "testeDama6.txt");
        oraculo = new ArrayList<>();
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
    }
    
    /**
     * Cenários nos quais a dama possui alguma jogada.
     */
    @Test
    public void damaComJogada() throws IOException{
         /**
         * Cenário 1
         * Dama do time 1 com jogadas. Cercada por peças inimigas.
         */
        regras = new Regras(damaComJogada + "testeDama1.txt");
        oraculo = oraculo1(Regras.JOGADOR_DOIS, Regras.JOGADOR_UM);
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
        
        /**
         * Cenário 2
         * Dama do time 2 com jogadas. Cercada por peças inimigas.
         */
        regras = new Regras(damaComJogada + "testeDama2.txt");
        oraculo = oraculo1(Regras.JOGADOR_UM, Regras.JOGADOR_DOIS);
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        jogador = new Humano(regras, Regras.JOGADOR_DOIS);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
        
        /**
         * Cenário 3
         * Dama do time 1 com jogadas. Captura limitada por peça inimiga.
         */
        regras = new Regras(damaComJogada + "testeDama3.txt");
        oraculo = oraculo2(Regras.JOGADOR_DOIS, Regras.JOGADOR_UM);
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(0, 0)).toString());
        
        /**
         * Cenário 4
         * Dama do time 1 com jogadas. Inimigas em todas as direções. Todas inimigos logo na casa seguinte. Captura limitada por peça inimiga.
         */
        regras = new Regras(damaComJogada + "testeDama4.txt");
        oraculo = oraculo3(Regras.JOGADOR_DOIS, Regras.JOGADOR_UM);
        jogador = new Humano(regras, Regras.JOGADOR_UM);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
        
        /**
         * Cenário 5
         * Dama do time 2 com jogadas. Captura limitada por peça inimiga.
         */
        regras = new Regras(damaComJogada + "testeDama5.txt");
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        oraculo = oraculo2(Regras.JOGADOR_UM, Regras.JOGADOR_DOIS);
        jogador = new Humano(regras, Regras.JOGADOR_DOIS);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(0, 0)).toString());
        //System.out.println(jogador.getJogadasPossiveis(new Posicao(0, 0)).toString());
        
        /**
         * Cenário 6
         * Dama do time 2 com jogadas. Mistura de todos os casos.
         */
        regras = new Regras(damaComJogada + "testeDama6.txt");
        regras.setJogadorAtual(Regras.JOGADOR_DOIS);
        oraculo = oraculo4(Regras.JOGADOR_UM, Regras.JOGADOR_DOIS);
        jogador = new Humano(regras, Regras.JOGADOR_DOIS);
        assertEquals(oraculo.toString(), jogador.getJogadasPossiveis(new Posicao(3, 3)).toString());
    }
    
    /**
     * Stubs simulandoo o usuário clickando em uma casa e retornando todas as jogadas possíveis.
     * @param time1
     * @param time2
     * @return 
     */
    private List<Jogada> oraculo1(int time1, int time2){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(5,1)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(6,0)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(1,5)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(0,6)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(5,5)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(6,6)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(7,7)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(1,1)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(0,0)));
        
        return jogadas;
    }
    
    private List<Jogada> oraculo2(int time1, int time2){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(0, 0), new Posicao(2, 2)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(0, 0), new Posicao(3, 3)));
        
        return jogadas;
    }
    
    private List<Jogada> oraculo3(int time1, int time2){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(5, 1)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(1, 5)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(5, 5)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(1, 1)));
        
        return jogadas;
    }
    
    private List<Jogada> oraculo4(int time1, int time2){
        List<Jogada> jogadas = new ArrayList<>();
        
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(5, 1)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(6, 0)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(6, 6)));
        jogadas.add(new Jogada(new Peca(time1, false), new Peca(time2, true), new Posicao(3, 3), new Posicao(7, 7)));
        
        return jogadas;
    }
}
