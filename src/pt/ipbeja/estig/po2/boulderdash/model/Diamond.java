package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.Objects;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */

public class Diamond extends Mobile {
    private Boolean onMap ;

    public Diamond(String name, Position pos ) {
        super(name, pos);
        this.onMap = true;
    }
    public Boolean getOnMap() {
        return onMap;
    }
    /**
     * checks whether the diamond is on the map or not
     */
    public void verifyOnMap ( Boolean onMap) {

        this.onMap = onMap;
    }

    /**
     * checks if the diamond can fall
     */
    void checkFreeDown (){
        AbstractPosition[][] board = GameModel.getBoardPosition();
        if (getOnMap().equals(true)){
        while (board[getPos().getLine()+1][getPos().getCol()].equals(new FreeTunnel(getPos().getLine()+1,getPos().getCol()))
         && getPos().getLine()+1 < board.length ){
            moveToMobile(move(Direction.DOWN, getPos()));
            if (getPos().getLine()+1 == board.length){
                break;
            }
        }
        }
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Diamond diamond = (Diamond) o;
        return Objects.equals(onMap, diamond.onMap);
    }

    @Override
    public int hashCode () {
        return Objects.hash(super.hashCode(), onMap);
    }

    @Override
    public Position move (Direction dir,Position pos) {
        if (dir == Direction.DOWN) {
            pos = new Position(pos.getLine() + 1, pos.getCol());
        }
        return pos;}

    @Override
    public void moveToMobile ( Position pos ) {
        if ( pos.freeSpace()){
            System.out.println();
        }else{
            setPos(pos);
        }
    }
}
