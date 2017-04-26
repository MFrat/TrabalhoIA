/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rule;

import java.util.List;
import rule.Table.Player;
import rule.Table.Position;

/**
 *
 * @author Max
 */
public class Rule {
    private Table table = new Table();
    
    private Player TURN_PLAYER = Table.Player.One;
    
    private boolean gameFinished = false;
    
    private boolean isDraw = false;
    
    public Rule(){
        
    }
    
    public Rule Rule(Rule rule) {
        this.TURN_PLAYER = rule.TURN_PLAYER;
        this.gameFinished = rule.gameFinished;
        this.table = rule.table;
        this.isDraw = rule.isDraw;
        
        return null;
    }
    
    public Rule play(int i, int j){
        if(!table.isBlankPosition(i, j)){
            throw new RuntimeException("This position has already been marked!");
        }
        
        table.markPosition(i, j, TURN_PLAYER);
        
        if(verifyEndGame(TURN_PLAYER.getPlayer())){
            gameFinished = true;
        }
        
        changePlayerTurn();
        
        return this;
    }
    
    private void changePlayerTurn(){
        TURN_PLAYER = TURN_PLAYER == Table.Player.One? Table.Player.TWO : Table.Player.One;
    }
    
    public void printBoard(){
        for(int i = 0; i < table.getDimension(); i++){
            for(int j = 0; j < table.getDimension(); j++){
                System.out.print(table.getPositionValue(i, j));
            }
            System.out.println();
        }
    }
    
    private boolean verifyEndGame(int player){
        return verifyColumns(player) || verifyRows(player) 
                || verifyDiag(player) || verifyDiag2(player) || verifyDraw();
    }
    
    private boolean verifyDraw(){
        for(int i = 0; i < table.getDimension(); i++){
            for(int j = 0; j < table.getDimension(); j++){
                if(table.getPositionValue(i, j) == table.BLANK){
                    return false;
                }
            }
        }
        
        isDraw = true;
        return true;
    }
    
    private boolean verifyRows(int player){
        for(int i = 0; i < table.getDimension(); i++){
            boolean winner = true;
            for(int j = 0; j < table.getDimension(); j++){
                if(table.getPositionValue(i, j) != player){
                    winner = false;
                }
            }
            
            if(winner){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean verifyColumns(int player){
        for(int i = 0; i < table.getDimension(); i++){
            boolean winner = true;
            for(int j = 0; j < table.getDimension(); j++){
                if(table.getPositionValue(j, i) != player){
                    winner = false;
                }
            }
            
            if(winner){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean verifyDiag(int player){
        for(int i = 0; i < table.getDimension(); i++){
            for(int j = 0; j < table.getDimension(); j++){
                if(i == j){
                    if(table.getPositionValue(j, i) != player){
                        //isDraw = true;
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    private boolean verifyDiag2(int player){
        for(int j = 0; j < table.getDimension(); j++){
            int a = j;
            int b = (table.getDimension() - 1) - j;
            if(table.getPositionValue(a, b) != player){
                return false;
            }
        }
        
        return true;
    }
    
    public boolean playerOneWon(){
        return gameFinished && (TURN_PLAYER == Table.Player.TWO);
    }
    
    public boolean draw(){
        return isDraw;
    }
    
    public boolean isGameFinished(){
        return gameFinished;
    }
    
    public Table getTable(){
        return table;
    }
    
    public boolean isTurnPlayer(Table.Player player){
        return TURN_PLAYER == player;
    }
    
    public List<Position> getAvailablePos(){
        return table.getPositions();
    }
    
    public Rule copy(){
        Rule rule = new Rule();
        
        rule.TURN_PLAYER = this.TURN_PLAYER;
        rule.gameFinished = this.gameFinished;
        rule.isDraw = this.isDraw;
        rule.table = this.table.copy();
        
        return rule;
    }
}