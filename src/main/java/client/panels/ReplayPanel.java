package client.panels;

import client.GameFrame;
import client.customComponents.MyButton;
import client.customComponents.MyPanel;
import client.customComponents.TileButton;
import javafx.util.Pair;
import models.TileType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplayPanel extends MyPanel {

    private MyPanel boardPanel;
    private TileButton[][] buttons;
    private MyButton next, back;
    private int times = 0;


    public ReplayPanel(){
        this.backGroundFile = configLoader.readString("PlayPanelBg");
        width = configLoader.readInteger("PlayPanelWidth");
        height = configLoader.readInteger("PlayPanelHeight");
        setLayout(null);
        setPreferredSize(new Dimension(width,height));
        boardPanel = new MyPanel(null,true,new GridLayout(7, 7, 2, 2),this);
        boardPanel.setBounds(25,25,configLoader.readInteger("tileButtonWidth")*7,configLoader.readInteger("tileButtonHeight")*7);
        buttons = new TileButton[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7 ; j++) {
                final int ii = i;
                final int jj = j;
                buttons[i][j] = new TileButton();
                boardPanel.add(buttons[i][j]);
            }
        }
        next = new MyButton("next", "blueCrystal150.png", this, (ActionListener) actionEvent -> nextFillUp(),300,570);
        back = new MyButton("back2menu", "blueCrystal150.png", this, (ActionListener) actionEvent -> GameFrame.getInstance().goToPanel(new MenuPanel()),25,570);

    }

    private void nextFillUp() {
        times++;
        if (GameFrame.getInstance().getGameClient().getReplay()!=null &&
                GameFrame.getInstance().getGameClient().getReplay().size() > 0) {
            int i = 0;
            for (Pair pair : GameFrame.getInstance().getGameClient().getReplay() ){
                if (i<times+1) {
                    if (i % 2 == 0) buttons[(int) pair.getKey()][(int) pair.getValue()].setType(TileType.X);
                    else buttons[(int) pair.getKey()][(int) pair.getValue()].setType(TileType.O);
                    i++;
                }
            }
        }
    }


}
