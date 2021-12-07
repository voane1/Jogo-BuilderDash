package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.List;
import java.util.Objects;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
final public class Position {

    private final int  line, col;

    public static Position getBeginPos () {
        return beginPos;
    }

    public static void setBeginPos ( Position beginPos ) {
        Position.beginPos = beginPos;
    }

    private static Position beginPos;



    public Position ( int line, int col ) {
        this.line = line;
        this.col = col;

    }

    public int getLine () {
        return line;
    }

    public int getCol () {
        return col;
    }

    @Override
    public String toString () {
        return "Position{" +
                "line=" + line +
                ", col=" + col +
                '}';
    }

    /**
     * inspired by grid--animation
     */
    public boolean isInside()
    {
        return this.isInside(
                this.getLine(), this.getCol());
    }

    /**
     * checks if the movements are within the map
     */
    private  boolean isInside(int line, int col)
    {
        AbstractPosition[][] board = GameModel.getBoardPosition();
        return 0 <= line && line < board.length &&
                0 <= col && col < board[0].length;
    }
    /**
     * check that the next rockford position is not a wall or anvil
     */
    public boolean noValidPos(int line, int col){
        boolean bolR = false;
        List<Anvil> anvils = GameModel.getAnvil();
        AbstractPosition[][] board = GameModel.getBoardPosition();
            if (anvils.size() == 0 ){
                bolR =  board[line][col].equals(new Wall(line, col));
            }else{
                for (Anvil anvil : anvils) {
                    bolR =  board[line][col].equals(new Wall(line, col))  || new Position(line,col).equals(anvil.getPos());
                    if (bolR){
                        break;
                    }
                }
        }
        return bolR;
    }

    public boolean freeSpace (){

        return freeSpace(this.getLine(), this.getCol());
    }
    /**
     * check that the down position diamond is a wall
     */
    private static boolean freeSpace ( int line, int col) {
        AbstractPosition[][] board = GameModel.getBoardPosition();
        if (0 <= line && line < board.length && 0 <= col && col < board[0].length){
            return board[line][col].equals(new Wall(line, col));
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return line == position.line && col == position.col;
    }


    @Override
    public int hashCode() {
        return Objects.hash(line, col);
    }
}
