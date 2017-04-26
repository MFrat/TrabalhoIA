/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rule;

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
    
    public Table copy(){
        Table table = new Table();
        for(int i = 0; i < DIMENSION; i++){
            System.arraycopy(this.gameTable[i], 0, table.gameTable[i], 0, DIMENSION);
        }
        
        return table;
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
}
