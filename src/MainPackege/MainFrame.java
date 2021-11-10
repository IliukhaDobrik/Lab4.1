package MainPackege;

import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame(){
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setJMenuBar(new MyMenuBar());

        setVisible(true);
    }
}
