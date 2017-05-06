/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.List;
import rule.Rule;
import rule.Rule.Position;
import rule.Table.Player;

/**
 *
 * @author Max
 */
public class Bot {
    private Position bestPosition;
    private Player player;
    
    public Bot(Player player){
        this.player = player;
    }
    
    public Position play(Rule rule){
        minimax(rule);
        
        return bestPosition;
        
    }

    private int utility(Rule rule, int depth){
        //System.out.println("Jogador um venceu: " + rule.playerOneWon() + " Jogador dois venceu: " + rule.playerTwoWon());
        //rule.printBoard();
        if(rule.isWinnerPlayer(player)){
            return 10 - depth;
        }else if(rule.draw()){
            return 0;
        }
        return depth - 10;
    }
    
    private int maxValue(Rule rule, int depth) {
        if(rule.isGameFinished()){
            return utility(rule, depth);
        }
        
        depth++;
        
        int value = -999999;
        
        for(Position pos : rule.getAvailablePositions()){
            Rule copy = rule.copy();
            int tmp = minValue(copy.play(pos.getI(), pos.getJ()), depth);
            if(tmp > value) {
                value = tmp;
                bestPosition = pos;
            }
        }
        System.out.println("maxValue" + value);
        return value;
    }
    
    private int minValue(Rule rule, int depth) {
        if(rule.isGameFinished()){
            return utility(rule, depth);
        }
        
        depth++;
        
        int value = 999999;
        
        for(Position pos : rule.getAvailablePositions()){
            Rule copy = rule.copy();
            int tmp = maxValue(copy.play(pos.getI(), pos.getJ()), depth);
            if(tmp < value) {
                value = tmp;
                //bestPosition = pos;
            }
        }
        System.out.println("minValue" + value);
        return value;
    }
    
    private int minimax(Rule rule){
        return maxValue(rule, 0);
    }
}
