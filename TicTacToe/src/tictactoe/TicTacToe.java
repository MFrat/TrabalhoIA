/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;
import rule.Rule;
import rule.Rule.Position;
import rule.Table.Player;

/**
 *
 * @author Max
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final Rule regra = new Rule();
        Bot bot = new Bot(Player.TWO);
        
        regra.setListener(new Rule.Listener() {
            @Override
            public void onPlayerWin(int winner) {
                System.out.println("O jogador " + winner + " ganhou");
                regra.printBoard();
            }

            @Override
            public void onDraw() {
                System.out.println("O jogo empatou");
                regra.printBoard();
            }
        });
        
        Scanner scan = new Scanner(System.in);
        while(true){
            regra.printBoard();
            System.out.println("Position to play: ");
            int i = scan.nextInt();
            int j = scan.nextInt();
            
            try{
                regra.play(i, j);
            }catch(Exception e){
                System.out.println("Invalid play.");
            }
            
            if(regra.isGameFinished()){
                break;
            }
            
            System.out.println("Vou jogar");
            Position pos = bot.play(regra);
            regra.play(pos.getI(), pos.getJ());
            System.out.println();
        }
    }
    
}
