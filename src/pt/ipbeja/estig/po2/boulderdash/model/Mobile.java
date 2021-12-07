package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.Objects;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
public abstract class Mobile {
    private String name;
    private Position pos;

    /**
     * class created for the movements of the moving objects
     */
    public Mobile(String name, Position pos) {
        this.name = name;
        this.pos = pos;
    }
    public String getName() {
        return name;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }


    /**
     * object movements
     */
    public abstract Position move (Direction dir, Position pos);

    /**
     * performs the movements of the object
     */
    public abstract void moveToMobile ( Position pos);

    /**
     * removes moving objects from the map
     */
    public void RemoveMap(Position pos) {

        this.pos = pos;
    }

    @Override
    public String toString () {
        return "Mobile{" +
                "name='" + name + '\'' +
                ", pos=" + pos.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mobile mobile = (Mobile) o;
        return Objects.equals(name, mobile.name) && Objects.equals(pos, mobile.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pos);
    }
}
