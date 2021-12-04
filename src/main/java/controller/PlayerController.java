package controller;

import client.GameFrame;
import client.panels.MenuPanel;
import models.Player;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static resLoader.json.PlayerJson.*;

public class PlayerController {
    private  Logger PlayerLOGGER ;
    private Player currentPlayer;
    private static PlayerController instance;
    public static PlayerController getInstance(){
        if(instance==null) instance = new PlayerController();
        return instance;
    }
    private PlayerController(){
        PlayerLOGGER = Logger.getLogger("PlayerLog");
    }

    public String SignIn(String userName, String pass) {
        String message = "";
        if (getPlayerFiles(userName) == null) message = "Error! There isn't an account in this name , Try again..";
        else {
            try {
                if(!jsonFileReader(userName).getPassword().equals(getHashedPassword(pass)))
                    message = "Error! Wrong password , Try again..";
                else {
                    Player player = null;
                    try {
                        player = jsonFileReader(userName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setCurrentPlayer(player);
                    LogManager.getLogManager().reset();
                    FileHandler fileHandler = null;
                    try {
                        fileHandler = new FileHandler("./src/main/java/logs/" + player.getName() + "-" + player.getId() + ".log", true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fileHandler.setFormatter(new SimpleFormatter());
                    PlayerLOGGER.addHandler(fileHandler);
                    PlayerLOGGER.info("USER  : " + player.getName() + "\nSigned_In AT :" +
                            new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss").format(new Date()) + "\nPASSWORD : " + player.getPassword() + "\n");
                    GameFrame.getInstance().goToPanel(new MenuPanel());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public String SignUp(String CreatedName, String CreatedPass){
        String message = " ";

        if(getPlayerFiles(CreatedName)!=null) message ="Error! Sorry this user name is taken, Try something else..";
        else if(CreatedName.length()<3) message = "Error! your username should contain more than 2 Characters , try again";
        else if(CreatedPass.length()<3) message = "Error! your password should contain more than 2 Characters , try again";
        else {
            Player SignedUpPlayer = new Player();
            SignedUpPlayer.setName(CreatedName);
            SignedUpPlayer.setPassword(getHashedPassword(CreatedPass));
            SignedUpPlayer.setId(System.currentTimeMillis());
            SignedUpPlayer.setLoses(0);
            SignedUpPlayer.setWins(0);
            SignedUpPlayer.setScore(0);
            SignedUpPlayer.setOnline(true);
            setCurrentPlayer(SignedUpPlayer);
             try {
                jsonTofilePlayer(SignedUpPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LogManager.getLogManager().reset();
            FileHandler fileHandler = null;
            try {
                fileHandler = new FileHandler("./src/main/java/logs/"+ SignedUpPlayer.getName()+"-"+SignedUpPlayer.getId()+".log",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileHandler.setFormatter(new SimpleFormatter());
            PlayerLOGGER.addHandler(fileHandler);
            PlayerLOGGER.info("USER  : " + SignedUpPlayer.getName() + "\nCREATED AT :" +
                    new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss").format(new Date()) + "\nPASSWORD : " + SignedUpPlayer.getPassword() + "\n");
            message = "you are signed up successfully!";
            GameFrame.getInstance().goToPanel(new MenuPanel());
        }
        return message;
    }

    public void deleteThePlayer() throws IOException {
        String password = JOptionPane.showInputDialog("if your sure of deleting your account enter you password :");
        if(!getCurrentPlayer().getPassword().equals(getHashedPassword(password))){
            JOptionPane.showMessageDialog(null,"incorrect password!");
        }
        else if(!password.equals("")){
            /** adding deleted to the log file in line 4 */
            File file = new File("src/logs/"+ getCurrentPlayer().getName()+"-"+ getCurrentPlayer().getId()+".log");
            File temp = File.createTempFile("temp-file-name", ".log");
            BufferedReader br = new BufferedReader(new FileReader( file));
            PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                pw.println(line);
                if(lineCount==3){
                    pw.println("PLAYER_DELETED_AT : " +  new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n");
                }
                lineCount++;
            }
            br.close();
            pw.close();
            file.delete();
            temp.renameTo(file);

            getPlayerFiles(getCurrentPlayer().getName()).deleteOnExit();
            System.exit(0);
        }
    }

    public String getHashedPassword(String playerPassword){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(playerPassword.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length ; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
