package client.customComponents;

import models.TileType;
import resLoader.ConfigLoader;

import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {
    private int row, column;
    private ConfigLoader configLoader;
//    public TileButton(int row, int column){
//        configLoader = new ConfigLoader("guiConfig");
//        setSize(new Dimension(configLoader.readInteger("tileButtonWidth"), configLoader.readInteger("tileButtonHeight")));
//        this.row = row;
//        this.column = column;
//    }
    public TileButton(){
        configLoader = new ConfigLoader("guiConfig");
        setSize(new Dimension(configLoader.readInteger("tileButtonWidth"), configLoader.readInteger("tileButtonHeight")));

    }
    public void setType(TileType type) {
        Font font = new Font("Ariel",Font.BOLD,25);
        setFont(font);
        this.setForeground(type==TileType.X ? Color.BLUE : Color.MAGENTA);
        this.setText(type + "");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
////        Graphics2D g2 = (Graphics2D)g;
//        if(tileType!=null){
//            if(tileType==TileType.X) setText("X");
//            else setText("Y");
//        }
//    }

}
