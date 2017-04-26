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
    
    private int utility(Rule rule){
        if(rule.isWinnerPlayer(player)){
            return 1;
        }else if(rule.draw()){
            return 0;
        }
        return -1;
    }
    
    private int maxValue(Rule rule) {
        if(rule.isGameFinished()){
            return utility(rule);
        }
        
        int value = -499;
        
        for(Position pos : rule.getAvailablePositions()){
            Rule copy = rule.copy();
            int tmp = minValue(copy.play(pos.getI(), pos.getJ()));
            if(tmp > value) {
                value = tmp;
                bestPosition = pos;
            }
        }
        
        return value;
    }
    
    private int minValue(Rule rule) {
        if(rule.isGameFinished()){
            return utility(rule);
        }
        
        int value = 499;
        
        for(Position pos : rule.getAvailablePositions()){
            Rule copy = rule.copy();
            int tmp = maxValue(copy.play(pos.getI(), pos.getJ()));
            if(tmp < value) {
                value = tmp;
                bestPosition = pos;
            }
        }
        
        return value;
    }
    
    private int minimax(Rule rule){
        return maxValue(rule);
    }
}
