package MainPackege;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMenuBar extends JMenuBar implements ActionListener {
    JMenu FileMenu = new JMenu("Файл");
    JMenu GraphicMenu = new JMenu("График");

    public MyMenuBar(){
        this.add(FileMenu);
        this.add(GraphicMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
