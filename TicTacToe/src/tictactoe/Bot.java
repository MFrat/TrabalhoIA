/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.List;
import rule.Rule;
import rule.Table;
import rule.Table.Position;

/**
 *
 * @author Max
 */
public class Bot {
    private Rule rule;
    private Position bestPosition = null;
    
    public Bot(Rule rule){
        this.rule = rule;
    }
    
    public Position play(){
        
        
        throw new RuntimeException("Not implemented yet");
    }

    private int utility(Rule rule) {
        if(rule.draw()) return 0;
        if(rule.playerOneWon()) return -1;
        return 1;
    }
    
    private int maxValue(Rule rule) {
        if(rule.isGameFinished()) return utility(rule);
        int value = -499;
        for(Position pos : rule.getAvailablePos()){
            int tmp = minValue(rule.play(pos.getI(), pos.getJ()));
            //rule.printBoard(); System.out.println(tmp);
            if(tmp > value) {
                value = tmp;
                bestPosition = pos;
            }
        }
        System.out.println("maxValue" + value);
        return value;
    }
    
    private int minValue(Rule rule) {
        if(rule.isGameFinished())
            return utility(rule);
        int value = 499;
        Rule copy = rule.copy();
        List<Position> umVetor = copy.getAvailablePos();
        for(Position pos : umVetor){
            int tmp = maxValue(copy.play(pos.getI(), pos.getJ()));
            if(tmp < value) {
                value = tmp;
                //bestPosition = pos;
            }
        }
        System.out.println("minValue" + value);
        return value;
    }
    
    public Rule minimax(){
        maxValue(rule);
        rule.play(bestPosition.getI(), bestPosition.getJ());
        
        return rule;
    }
}
