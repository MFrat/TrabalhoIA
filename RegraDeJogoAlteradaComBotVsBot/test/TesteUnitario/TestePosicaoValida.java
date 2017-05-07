/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteUnitario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import regradejogo.Peca;
import regradejogo.Posicao;
import regradejogo.Regras;
import regradejogo.Tabuleiro;

/**
 *
 * @author Max
 */
public class TestePosicaoValida {
    
    private Tabuleiro tabuleiro;
    private List<Posicao> pos;
    
    public TestePosicaoValida() {
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
    public void posicaoVálida(){
        /**
         * Caso 1
         * Posicao inválida.
         */
        boolean resultado = posicaoValida(new Posicao(9, 9), Regras.JOGADOR_UM, getPeca1());
        assertEquals(resultado, false);
        
        /**
         * Caso 2
         * Nenhuma peça na posição, jogada válida.
         */
        resultado = posicaoValida(new Posicao(0, 0), Regras.JOGADOR_UM, getPeca5());
        assertEquals(resultado, true);
        
        /**
         * Caso 3
         * Peça, que está na posição, do mesmo time.
         */
        resultado = posicaoValida(new Posicao(0, 0), Regras.JOGADOR_UM, getPeca1());
        assertEquals(resultado, false);
        
        /**
         * Caso 4
         * pos.getI() == Tabuleiro.DIMEN - 1
         */
        resultado = posicaoValida(new Posicao(7, 0), 2, getPeca1());
        assertEquals(resultado, false);
        
        /**
         * Caso 5
         * pos.getI() == 0.
         */
        resultado = posicaoValida(new Posicao(0, 0), 2, getPeca1());
        assertEquals(resultado, false);
        
        /**
         * Caso 6
         * pos.getJ() == Tabuleiro.DIMEN - 1
         */
        resultado = posicaoValida(new Posicao(1, 7), 2, getPeca1());
        assertEquals(resultado, false);
        
        /**
         * Caso 7
         * pos.getj() == 0.
         */
        resultado = posicaoValida(new Posicao(1, 0), 2, getPeca1());
        assertEquals(resultado, false);
        
        /**
         * Caso 8
         * Posicao válida.
         */
        resultado = posicaoValida(new Posicao(5, 5), 2, getPeca1());
        assertEquals(resultado, true);
    }
    
    protected boolean posicaoValida(Posicao pos, int time, Peca peca){
        if(!posValida(pos)){//1
            return false;//2
        }
        
        //Se não tem nenhuma peça na posição, jogada é válida.
        if(peca == null){//3
            return true;//4
        }
        
        //Se a peça na posição for do seu time, jogada inválida.
        if(peca.getTime() == time){//5
            return false;//6
        }
        
        //Caso a peça esteja na borda do tabuleiro.
        if((pos.getI() == Tabuleiro.DIMEN - 1) || (pos.getI() == 0)){//7, 8
            return false;//9
        }
        
        if((pos.getJ() == Tabuleiro.DIMEN - 1) || (pos.getJ() == 0)){//10, 11
            return false;//12
        }
        
        return true;//13
    }
    
    private Peca getPeca1(){
        return new Peca(1, false);
    }
    
    private Peca getPeca2(){
        return new Peca(1, true);
    }
    
    private Peca getPeca3(){
        return new Peca(2, false);
    }
    
    private Peca getPeca4(){
        return new Peca(2, true);
    }
    
    private Peca getPeca5(){
        return null;
    }
    
    private boolean posValida(Posicao posicao){
        if(posicao.getI() > 7 || posicao.getI() < 0){
            return false;
        }
        
        if(posicao.getJ() > 7 || posicao.getJ() < 0){
            return false;
        }
        
        return true;
    }

}
