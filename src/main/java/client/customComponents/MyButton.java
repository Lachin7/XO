package client.customComponents;

import resLoader.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyButton extends JButton {
    private final ImageLoader imageLoader;
    private final String IconFile;
    private String name;

    public MyButton(String name, String IconFile, Container container, ActionListener actionListener, int x, int y ){
        imageLoader = ImageLoader.getInstance();
        this.IconFile = IconFile;
        this.name = name;
        Font font = new Font("",Font.BOLD,12);
        BufferedImage image = imageLoader.loadImage(IconFile);
        this.setFont(font);
        this.setText(name);
        this.setIcon(new ImageIcon(image));
        this.setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
        this.setSize(image.getWidth(),image.getHeight());
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setRolloverEnabled(false);
        this.setBorderPainted(false);
        this.setAlignmentX(JButton.CENTER_ALIGNMENT);
        this.setFocusable(false);
        if(container!=null) {
            container.add(this);
            this.setBounds(x,y,this.getWidth(),this.getHeight());
        }
        if(actionListener!= null) this.addActionListener(actionListener);
    }

    public String getIconFile() {
        return IconFile;
    }

    @Override
    public String getName() {
        return name;
    }
}
