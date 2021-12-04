package client.customComponents;

import resLoader.ConfigLoader;
import resLoader.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    protected ConfigLoader configLoader;
    protected ImageLoader imageLoader;
    protected String backGroundFile;
    protected int width, height;
    protected Boolean grayBackground = false;

    public MyPanel(){
        imageLoader = ImageLoader.getInstance();
        configLoader = new ConfigLoader("guiConfig");
    }

    public MyPanel(String backGroundFile, Boolean grayBackground, LayoutManager layoutManager, Container container){
        this();
        if(backGroundFile!=null) this.backGroundFile = backGroundFile;
        this.grayBackground = grayBackground;
        this.setLayout(layoutManager);
        this.setOpaque(false);
        if(grayBackground)this.setBorder(BorderFactory.createLineBorder(new Color(0x092980),3,true));
        if(container!=null)container.add(this);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(backGroundFile!= null) g.drawImage(imageLoader.loadImage(backGroundFile),0,0,null);
        if(grayBackground) {
            g.setColor(new Color(164, 151, 215));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
        }
    }

}

