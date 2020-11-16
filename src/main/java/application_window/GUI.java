package application_window;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import static application_window.AppRunner.executeFTF;

public class GUI{

    public static JFrame createWindow() {
        JFrame frame = new JFrame("Instagram Manager Bot");
        frame.setSize(500, 465);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        return frame;
    }

    public static JButton createButton(String name) {
        JButton b = new JButton(name);
        JFrame f = createWindow();
        int width = f.getWidth() / 2;
        int height = 40;
        int x = (f.getWidth() / 2) - ((f.getWidth() / 2) / 2);
        int y = (f.getHeight() / 2) - height;
        b.setBounds(x, y, width, height);
        return b;
    }

    static class Action implements ActionListener {
        public void actionPerformed (ActionEvent e){
            try {
                executeFTF();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }
}
