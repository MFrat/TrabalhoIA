/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Max
 */
public class Table {
    public static final int BLANK = 0;
    
    private final int DIMENSION = 3;
    
    private final int[][] gameTable = new int[DIMENSION][DIMENSION];
    
    public Table(){
        
    }
    
    void markPosition(int i, int j, Player player){
        if(isValidPosition(i, j)){
            gameTable[i][j] = player.player;
        }
    }
    
    boolean isValidPosition(int i, int j){
        if(i > 2 || j > 2){
            throw new RuntimeException("This position outsides table's bounds: "
                    + "[" + String.valueOf(i) + "," + String.valueOf(i) +"]");
        }
        
        return true;
    }
    
    boolean isBlankPosition(int i, int j){
        return gameTable[i][j] == BLANK;
    }
    
    int getPositionValue(int i, int j){
        if(isValidPosition(i, j)){
            return gameTable[i][j];
        }
        
        return -1;
    }
    
    public enum Player{
        One(1), TWO(2);
        
        private int player;
        
        Player(int player){
            this.player = player;
        }
        
        int getPlayer(){
            return player;
        }
    }
    
    public int getDimension(){
        return DIMENSION;
    }
    
    List<Position> getPositions(){
        List<Position> positions = new ArrayList<>();
        
        for(int i = 0; i < DIMENSION; i++){
            for(int j= 0; j < DIMENSION; j++){
                if(isBlankPosition(i, j)) {
                    positions.add(new Position(i, j));
                }
            }
        }
        
        return positions;
    }
    
    public Table copy(){
        Table table = new Table();
        
        for(int i = 0; i < DIMENSION; i++){
            for(int j = 0; j < DIMENSION; j++){
                table.gameTable[i][j] = this.gameTable[i][j];
            }
        }
        
        return table;
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
