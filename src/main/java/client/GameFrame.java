package client;

import client.panels.PlayPanel;
import controller.PlayerController;
import resLoader.ConfigLoader;

import javax.swing.*;

public class GameFrame extends JFrame {

    private static  GameFrame myGameFrame = null;
    private GameClient gameClient;
    public static GameFrame getInstance(){
        if(myGameFrame == null) myGameFrame = new GameFrame();
        return myGameFrame;
    }

    public GameFrame(){
        this.setTitle("XO");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
    }

    public void goToPanel(JPanel panel){
        this.getContentPane().setVisible(false);
        this.setContentPane(panel);
        setVisible(true);
        pack();
    }

    public void goToPlay() {
        try {
            PlayPanel playPanel = new PlayPanel();
            GameClient gameClient = new GameClient(playPanel.getBoardPanel(),playPanel.getMessageLabel(),playPanel.getMark(),playPanel.getOpponentName());
            GameFrame.getInstance().goToPanel(playPanel);
            this.gameClient = gameClient;
            gameClient.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    //    public void goToPlay(){
//        this.getContentPane().setVisible(false);
//        this.setContentPane(panel);
//    }

}
