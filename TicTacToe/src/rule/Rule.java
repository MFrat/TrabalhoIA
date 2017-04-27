/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rule;

import java.util.ArrayList;
import java.util.List;
import rule.Table.Player;

/**
 *
 * @author Max
 */
public class Rule {
    private Table table = new Table();
    
    private Player TURN_PLAYER = Table.Player.One;
    
    private boolean gameFinished = false;
    
    private boolean isDraw = false;
    
    private int winner;
    
    private Listener listener;
    
    public Rule(){
        
    }
    
    public Rule play(int i, int j){
        if(!table.isBlankPosition(i, j)){
            throw new RuntimeException("This position has already been marked!");
        }
        
        table.markPosition(i, j, TURN_PLAYER);
        
        if(verifyEndGame(Player.One.getPlayer()) || verifyEndGame(Player.TWO.getPlayer())){
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
        if(verifyColumns(player) || verifyRows(player) 
                || verifyDiag(player) || verifyDiag2(player)){
            
            if(listener != null){
                listener.onPlayerWin(player);
            }
            
            gameFinished = true;
            
            winner = player;
            
            return true;
        }
        
        if(verifyDraw()){
            if(listener != null){
                listener.onDraw();
            }
            
            isDraw = true;
            
            return true;
        }
        
        return false;
    }
    
    private boolean verifyDraw(){
        for(int i = 0; i < table.getDimension(); i++){
            for(int j = 0; j < table.getDimension(); j++){
                if(table.getPositionValue(i, j) == table.BLANK){
                    return false;
                }
            }
        }
        
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
    
    public List<Position> getAvailablePositions(){
        List<Position> positions = new ArrayList<>();
        
        for(int i = 0; i < table.getDimension(); i++){
            for(int j = 0; j < table.getDimension(); j++){
                if(table.isBlankPosition(i, j)){
                    positions.add(new Position(i, j));
                }
            }
        }
        
        return positions;
    }
    
    public boolean playerOneWon(){
        return !isDraw && gameFinished && (TURN_PLAYER != Table.Player.One);
    }
    
    public boolean playerTwoWon(){
        return !isDraw && gameFinished && (TURN_PLAYER != Table.Player.TWO);
    }
    
    public boolean isPlayerOneTurn(){
        return TURN_PLAYER == Table.Player.One;
    }
    
    public boolean isPlayerTwoTurn(){
        return TURN_PLAYER == Table.Player.TWO;
    }
    
    public boolean isPlayerTurn(Player player){
        return player == TURN_PLAYER;
    }
    
    public boolean isWinnerPlayer(Player player){
        return player.getPlayer() == winner;
    }
    
    public boolean draw(){
        return isDraw;
    }
    
    public boolean isGameFinished(){
        return gameFinished;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Player getTURN_PLAYER() {
        return TURN_PLAYER;
    }
    
    public Rule copy(){
        Rule rule = new Rule();
        
        rule.table = this.table.copy();
        rule.TURN_PLAYER = this.TURN_PLAYER;
        rule.gameFinished = this.gameFinished;
        rule.isDraw = this.isDraw;
        
        return rule;
    }
    
    public interface Listener{
        void onPlayerWin(int winner);
        void onDraw();
    }
    
    public class Position{
        private int i;
        private int j;
        
        public Position(){
            
        }
        
        public Position(int i, int j){
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }
    }
}
