/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;
import rule.Rule;

/**
 *0
 * @author Max
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Rule regra = new Rule();
        Bot bot = new Bot(regra);
        Scanner scan = new Scanner(System.in);
        while(true){
            regra.printBoard();
            
            System.out.println("Position to play: ");
            int i = scan.nextInt();
            int j = scan.nextInt();
            
            try{
                regra.play(i, j);
                System.out.println("Vou jogar seu  merda");
                bot.minimax();
            }catch(Exception e){
                System.out.println("Invalid play.");
            }
            
            if(regra.isGameFinished()){
                System.out.println("O jogo acabou!");
                if(regra.playerOneWon()){
                    System.out.println("Jogador 1 ganhou");
                    //break;
                }else{
                    if(regra.draw()){
                        System.out.println("Empatou");
                    }else{
                        System.out.println("Jogador 2 ganhou");
                    }
                }
                //System.out.println(regra.playerOneWon()? "Jogadorumganhou" : "Vai tomar no cu");
                regra.printBoard();
                break;
            }
            
            System.out.println();
        }
    }
    
}
