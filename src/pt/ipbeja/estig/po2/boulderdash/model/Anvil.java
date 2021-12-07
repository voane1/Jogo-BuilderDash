package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Viviane Candeias and Ivan Viegas
 * @version 22/06/2021
 */
public class Anvil extends Mobile {


    public Anvil(String name, Position pos ) {
        super(name, pos);
    }

    @Override
    public Position move(Direction dir, Position pos) {
        return null;
    }

    @Override
    public void moveToMobile ( Position pos ) {

    }


}
