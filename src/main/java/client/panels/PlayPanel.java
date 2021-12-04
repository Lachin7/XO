package client.panels;

import client.customComponents.MyButton;
import client.customComponents.MyJLabel;
import client.customComponents.MyPanel;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends MyPanel {


    private MyPanel boardPanel;
    private MyJLabel messageLabel, mark, opponentName;

    public PlayPanel() {
        setSizeConfig();
        boardPanel = new MyPanel(null,true,new GridLayout(7, 7, 2, 2),this);
        boardPanel.setBounds(25,25,configLoader.readInteger("tileButtonWidth")*7,configLoader.readInteger("tileButtonHeight")*7);
        messageLabel = new MyJLabel("...",new Color(231, 108, 226, 255),20,this,60,580,400,25);
        mark = new MyJLabel("", Color.GRAY,30,this,10,575,45,45);
        opponentName = new MyJLabel("",new Color(62, 7, 99),20,this,25,650,400,30);
    }
    private void setSizeConfig(){
//        this.backGroundFile = configLoader.readString("PlayPanelBg");
        width = configLoader.readInteger("PlayPanelWidth");
        height = configLoader.readInteger("PlayPanelHeight");
        setLayout(null);
        setPreferredSize(new Dimension(width,height));
    }

    public MyJLabel getMark() {
        return mark;
    }

    public MyJLabel getOpponentName() {
        return opponentName;
    }

    public MyPanel getBoardPanel() {
        return boardPanel;
    }

    public MyJLabel getMessageLabel() {
        return messageLabel;
    }
}
