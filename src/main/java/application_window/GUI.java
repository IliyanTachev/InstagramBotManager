package application_window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import static application_window.AppRunner.executeFTF;

public class GUI implements ActionListener{
    public JFrame frame;
    public JButton button;

    public JFrame createWindow(String name, int width, int height) {
        this.frame = new JFrame(name);
        this.frame.setSize(width, height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(null);
        return this.frame;
    }

    public JButton createButton(String name, int x, int y, int width, int height) {
        this.button = new JButton(name);
        this.button.setBounds(x, y, width, height);
        return this.button;
    }

    public void constructApplication(){
        int widthFrame = 500;
        int heightFrame = 465;

        int widthButton = widthFrame / 2;
        int heightButton = 40;
        int xButton = (widthFrame / 2) - ((widthFrame / 2) / 2);
        int yButton = (heightFrame / 2) - heightButton;

        JFrame window = createWindow("Instagram Manager Bot", widthFrame, heightFrame);
        JButton button = createButton("Follow for Follow", xButton, yButton, widthButton, heightButton);
        window.add(button);
        button.addActionListener(this);
        window.setVisible(true);
    }

    public void actionPerformed (ActionEvent e){
        if(e.getSource() == button){
            try {
                executeFTF();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }
}
