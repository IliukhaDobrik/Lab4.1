package MainPackege;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMenuBar extends JMenuBar implements ActionListener {
    JMenu fileMenu = new JMenu("Файл");
    JMenu graphicMenu = new JMenu("График");

    JMenuItem loadItem = new JMenuItem("Загрузить");
    JMenuItem saveItem = new JMenuItem("Сохранить");
    JMenuItem exitItem = new JMenuItem("Выход");

    JCheckBoxMenuItem showAxes = new JCheckBoxMenuItem("Показать оси координат",true);
    JCheckBoxMenuItem showPoints = new JCheckBoxMenuItem("Показать маркеры точек",true);

    public MyMenuBar(){
        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(e ->  System.exit(0));

        showPoints.addActionListener(this);
        showAxes.addActionListener(this);

        graphicMenu.add(showAxes);
        graphicMenu.add(showPoints);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        add(fileMenu);
        add(graphicMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == loadItem) {
                System.out.println("Пока так...");
            }
            if (e.getSource() == saveItem) {
                System.out.println("Пока так...");
            }
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}
