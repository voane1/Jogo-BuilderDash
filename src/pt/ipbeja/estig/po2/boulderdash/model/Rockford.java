package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.List;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
public class Rockford extends Mobile{

    //eagle initiation singleton
    // private static Rockford instance = new Rockford();
    private static Rockford instance;


    private Rockford ( String name, Position pos ) {
        super(name, pos);
    }

    public static Rockford getInstance ( String name, Position pos ) {
        //lazy instanciation singleton
        if (instance == null) {
            instance = new Rockford (name, pos);
        }
        return instance;
    }

    /**
     * check if the position of the rockford is the same
     * as the diamond if it is add point
     */

    public  void checkPosRock (){
        List<Diamond> diamonds = GameModel.getDiamonds();
        for (Diamond diamond: diamonds) {
            if (diamond.getPos().equals(getPos()) && diamond.getOnMap().equals(true)){
                diamond.verifyOnMap(false);
                GameModel.setScore(GameModel.getScore() + 200);
                diamond.RemoveMap(new Position(diamond.getPos().getLine(),diamond.getPos().getCol()));
            }
        }
    }

    /**
     * check if the position of the rockford is the same as the gate
     */
    public void checkFlagRockford(){
        if (GameModel.getFlagGame().equals(true)){
            if(getPos().getCol() == GameModel.getGate().getColuna()
                    && getPos().getLine() == GameModel.getGate().getLinha()){
                GameModel.setFlagGame(false);
            }
        }
    }

    @Override
    public Position move(Direction dir, Position pos) {
        switch (dir) {
            case UP:  pos = new Position(pos.getLine() - 1, pos.getCol()); break;
            case DOWN: pos = new Position(pos.getLine() + 1, pos.getCol()); break;
            case LEFT: pos = new Position(pos.getLine(), pos.getCol() - 1); break;
            case RIGHT: pos = new Position(pos.getLine(), pos.getCol() + 1); break;
        }
        return pos;
    }

    @Override
    public void moveToMobile ( Position pos ) {
        if (pos.isInside()){
            if(!pos.noValidPos(pos.getLine(), pos.getCol())){
                Position.setBeginPos(getPos());
                GameModel.lastPos(getPos());
                setPos(pos);
                instance = Rockford.getInstance(getName(),getPos());//não pode ter instancia de rockford
                instance.checkPosRock();

                checkFlagRockford();
            }
        }
    }

    /**
     * Reset instance of Rockford
     */
    public synchronized void resetRockford(){

        instance = null;
    }
}
