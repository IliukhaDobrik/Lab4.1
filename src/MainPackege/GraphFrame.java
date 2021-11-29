package MainPackege;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;

public class GraphFrame extends JFrame {
    GraphFrame(){
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setExtendedState(MAXIMIZED_BOTH);

        setJMenuBar(new MyMenuBar());

        add(new GraphicsDisplay());

        setVisible(true);
    }
}