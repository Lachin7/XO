package client;

import client.customComponents.MyJLabel;
import client.customComponents.TileButton;
import client.panels.MenuPanel;
import controller.PlayerController;
import javafx.util.Pair;
import models.TileType;
import resLoader.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static resLoader.json.PlayerJson.jsonTofilePlayer;

public class GameClient {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private ConfigLoader configLoader;
    private MyJLabel messageLabel, mark, opponentName;
    private TileButton[][] buttons;
    private TileButton currentTile;
    private ArrayList<Pair<Integer,Integer>> replay;

    public GameClient( JPanel boardPanel, MyJLabel messageLabel, MyJLabel mark,MyJLabel opponentName) throws Exception {
        configLoader = new ConfigLoader("serverConfig");
        socket = new Socket(configLoader.readString("host"), configLoader.checkIfIsEmpty("port") ? 8000 : configLoader.readInteger("port"));
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        this.messageLabel = messageLabel;
        this.mark = mark;
        this.opponentName = opponentName;
        messageLabel.setBackground(Color.lightGray);
        replay = new ArrayList<>();
        buttons = new TileButton[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7 ; j++) {
                final int ii = i;
                final int jj = j;
                buttons[i][j] = new TileButton();
                buttons[i][j].setRow(i);
                buttons[i][j].setColumn(j);
                buttons[i][j].addActionListener(actionEvent -> {
                    currentTile = buttons[ii][jj];
                    out.println("MOVE " + ii+ jj);
                });
                boardPanel.add(buttons[i][j]);
            }
        }
    }

    public void play() throws Exception {
        try {
            var response = in.nextLine();
            var mark = response.charAt(8);
            TileType tileType = TileType.X;
            if(mark == 'O') tileType = TileType.O;
            this.mark.setText(mark+"");
            while (in.hasNextLine()) {
                response = in.nextLine();
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                    currentTile.setType(tileType);
                    currentTile.repaint();
                    replay.add(new Pair<>(currentTile.getRow(),currentTile.getColumn()));
                } else if (response.contains("GET_ENEMY_NAME")) {
                    System.out.println("here");
                    out.println("ENEMY_NAME"+PlayerController.getInstance().getCurrentPlayer().getName());
                } else if (response.startsWith("ENEMY_NAME")){
                    opponentName.setText("opponent name :"+response.substring(10));
                }else if (response.startsWith("OPPONENT_MOVED")) {
                    var i = Integer.parseInt(response.substring(15,16));
                    var j = Integer.parseInt(response.substring(17,18));
                    buttons[i][j].setType(getReversedTile(tileType));
                    buttons[i][j].repaint();
                    messageLabel.setText("Opponent moved, your turn");
                    replay.add(new Pair(i,j));
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                } else if (response.startsWith("VICTORY")) {
                    JOptionPane.showMessageDialog(null, "Winner Winner");
                    PlayerController.getInstance().getCurrentPlayer().setWins(PlayerController.getInstance().getCurrentPlayer().getWins()+1);
                    jsonTofilePlayer(PlayerController.getInstance().getCurrentPlayer());
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    JOptionPane.showMessageDialog(null, "Sorry you lost");
                    PlayerController.getInstance().getCurrentPlayer().setLoses(PlayerController.getInstance().getCurrentPlayer().getLoses()+1);
                    jsonTofilePlayer(PlayerController.getInstance().getCurrentPlayer());

                    break;
                } else if (response.startsWith("TIE")) {
                    JOptionPane.showMessageDialog(null, "Tie");
                    break;
                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    JOptionPane.showMessageDialog(null, "Other player left");
                    break;
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
            GameFrame.getInstance().goToPanel(new MenuPanel());
        }
    }

    private TileType getReversedTile(TileType tileType){
        if(tileType==TileType.X)return TileType.O;
        return TileType.X;
    }

    public ArrayList<Pair<Integer, Integer>> getReplay() {
        return replay;
    }
}
