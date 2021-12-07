package pt.ipbeja.estig.po2.boulderdash.gui;

import pt.ipbeja.estig.po2.boulderdash.model.Position;

/**
 * @author Ivan Viegas and Viviane Candeias
 * @version 22/06/2021
 */
public interface View {
    void notifyView(Position newPosition, Boolean gamewin);
}
