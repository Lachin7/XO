package client.panels;

import client.GameFrame;
import client.customComponents.MyButton;
import client.customComponents.MyJLabel;
import client.customComponents.MyPanel;
import controller.PlayerController;
import models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static resLoader.json.PlayerJson.getAllPlayers;
import static resLoader.json.PlayerJson.jsonTofilePlayer;

public class MenuPanel extends MyPanel {
    private MyButton play, exit, replay;
    private MyPanel scoreBoard;
    private MyJLabel welcome, scores;
    private JScrollPane scrollBar;

    public MenuPanel(){
        this.backGroundFile = configLoader.readString("accountPanelBg");
        width = configLoader.readInteger("menuPanelWidth");
        height = configLoader.readInteger("menuPanelHeight");
        setLayout(null);
        setPreferredSize(new Dimension(width,height));

        setScoreBoard();

        play = new MyButton("play","blueCrystal150.png",this,null,width/2-75,525);
        play.addActionListener(actionEvent -> {
            Thread savor = new Thread(() -> GameFrame.getInstance().goToPlay());
            savor.start();
        });
        exit = new MyButton("exit", "blueCrystal150.png", this, actionEvent -> {
            try {
                PlayerController.getInstance().getCurrentPlayer().setOnline(false);
                jsonTofilePlayer(PlayerController.getInstance().getCurrentPlayer());
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, width / 2 , 600);
        replay = new MyButton("replay", "blueCrystal150.png", this, actionEvent -> GameFrame.getInstance().goToPanel(new ReplayPanel()), 25, 600);
        Player player = PlayerController.getInstance().getCurrentPlayer();
        welcome = new MyJLabel("welcome back "+player.getName(),new Color(62, 7, 99),25,this,50,30,300,30);
        scores = new MyJLabel("wins : "+ player.getWins()+" "+"loses : "+ player.getLoses()+" "+"score : "+ player.getScore(),Color.BLACK,20,this,50,60,width,25);
    }

    private void setScoreBoard(){
        ArrayList<Player> players = getAllPlayers();
        scoreBoard = new MyPanel(null,true,new GridLayout(70,1),this);
//        MyPanel panel = new MyPanel(null, false,null,scrollBar);
//        scoreBoard.add(panel);
//        panel.setSize(new Dimension(330,30));
        new MyJLabel("  Name           OnlineStatus         Score",Color.BLUE,15,scoreBoard,0,0,330,30);
//        new MyJLabel("  Name", Color.BLUE,15,scoreBoard,0,0,110,30);
//        new MyJLabel("OnlineStatus",Color.magenta,15,scoreBoard,110,0,110,30);
//        new MyJLabel(" Score",Color.magenta,15,scoreBoard,220,0,110,30);
        int i =1 ;
        for(Player player : players){
//            MyPanel myPanel = new MyPanel(null, false,null,scrollBar);
//            myPanel.setSize(new Dimension(330,30));
            new MyJLabel(" "+player.getName()+emptyString(25-player.getName().length())+player.isOnline()+emptyString(25)+player.getScore(),new Color(92, 7, 149),11,scoreBoard,0,i*30,330,30);
//            new MyJLabel(" "+player.getName(), Color.BLUE,11,scoreBoard,0,0,110,30);
//            new MyJLabel(player.isOnline()+"",Color.magenta.darker(),11,scoreBoard,110,0,110,30);
//            new MyJLabel(player.getScore()+"",new Color(92, 7, 149),11,scoreBoard,220,0,110,30);
////            scoreBoard.add(myPanel);
            i++;
        }
        scrollBar = new JScrollPane(scoreBoard,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollBar);
        scrollBar.setBounds(25, 100, 330, 400);
        JButton refresh = new JButton("refresh");
        refresh.addActionListener(actionEvent -> GameFrame.getInstance().goToPanel(new MenuPanel()));
        scoreBoard.add(refresh);
    }

    private String emptyString(int i){
        String result = " ";
        for (int j = 0; j < i ; j++) result+=" ";
        return result;
    }

}
