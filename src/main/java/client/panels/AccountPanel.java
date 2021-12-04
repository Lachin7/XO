package client.panels;

import client.customComponents.MyButton;
import client.customComponents.MyJLabel;
import client.customComponents.MyPanel;
import controller.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPanel extends MyPanel {

    private MyJLabel WelcomeText, EnterName, EnterPass, noAccount, CreatName, CreatPass, ErrorSignIn, ErrorSignUp;
    private  JTextField userName, password, CreatedName, CreatedPass;
    private MyButton signIn, signUp;
    private final PlayerController playerController;

    public AccountPanel(){
        this.backGroundFile = configLoader.readString("accountPanelBg");
        width = configLoader.readInteger("accountPanelWidth");
        height = configLoader.readInteger("accountPanelHeight");
        playerController = PlayerController.getInstance();
        setLayout(null);
        setPreferredSize(new Dimension(width,height));
        setSignIn();
        setSignUp();
    }

    private void setSignIn(){
        WelcomeText = new MyJLabel("Welcome back! Let's Sign In! ",new Color(189, 8, 154),25,this,50,25,450,30);
        EnterName = new MyJLabel("Enter your user name : ",new Color(6, 18, 64),15,this,25,104,200,17);
        userName = new JTextField();
        userName.setBounds(250,100,200,30);
        add(userName);
        EnterPass = new MyJLabel("Enter your password : ",new Color(6, 18, 64),15,this,25,154,200,17);
        password = new JPasswordField();
        password.setBounds(250,150,200,30);
        add(password);
        ErrorSignIn = new MyJLabel("",new Color(189, 8, 154),15,this,25,250,450,17);
        signIn = new MyButton("Sign In!", "pinkCrystal100.png", this, actionEvent -> ErrorSignIn.setText(playerController.SignIn(userName.getText(),password.getText())), width / 2 - 50, 280);
    }

    private void setSignUp(){
        noAccount = new MyJLabel("Don't have an account? Sign Up! ",new Color(189, 8, 154),25,this,50,350,450,30);
        CreatName = new MyJLabel("Creat your user name : ",new Color(6, 18, 64),15,this,25,429,200,17);
        CreatedName = new JTextField();
        CreatedName.setBounds(250,425,200,30);
        add(CreatedName);
        CreatPass = new MyJLabel("Creat your password : ",new Color(6, 18, 64),15,this,25,475,200,17);
        CreatedPass = new JPasswordField();
        CreatedPass.setBounds(250,475,200,30);
        add(CreatedPass);
        ErrorSignUp = new MyJLabel("",new Color(189, 8, 154),15,this,25,525,450,17);
        signUp = new MyButton("Sign Up!", "pinkCrystal100.png", this, actionEvent -> ErrorSignUp.setText(playerController.SignUp(CreatedName.getText(),CreatedPass.getText())), width / 2 - 50, 575);
    }

}
