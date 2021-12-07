package pt.ipbeja.estig.po2.boulderdash.model;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
public class Gate extends AbstractPosition{

    public Gate ( int linha, int coluna ) {
        super(linha, coluna);
    }

    @Override
    public boolean equals ( Object o ) {
        return super.equals(o);
    }

    @Override
    public int hashCode () {
        return super.hashCode();
    }

    @Override
    public String toString () {
        return "Gate{}";
    }
}
