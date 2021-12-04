package client;

import client.panels.AccountPanel;
import server.GameServer;

public class Main{

    public static void main(String[] args) {
        GameFrame.getInstance().goToPanel(new AccountPanel());
        GameFrame.getInstance().setVisible(true);

    }

//
//    private static boolean isRunning = true;
//  public static boolean gotoPlay=false;
//   static Thread thread = new Thread(() -> {
//        while (isRunning) if(gotoPlay) {
//            GameFrame.getInstance().goToPlay();
//            isRunning = false;
//        }
//    });

}
