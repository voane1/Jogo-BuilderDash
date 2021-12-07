package pt.ipbeja.estig.po2.boulderdash.model;

import java.util.Objects;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */


public abstract class AbstractPosition {
    private int linha;
    private int coluna;



    public AbstractPosition ( int linha, int coluna ) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    @Override
    public String toString() {
        return "AbstractPosition{" +
                "linha=" + linha +
                ", coluna=" + coluna +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPosition that = (AbstractPosition) o;
        return linha == that.linha && coluna == that.coluna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(linha, coluna);
    }
}
