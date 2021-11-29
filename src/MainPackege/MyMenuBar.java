package MainPackege;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MyMenuBar extends JMenuBar implements ActionListener {
    JMenu fileMenu = new JMenu("Файл");
    JMenu graphicMenu = new JMenu("График");

    JMenuItem loadItem = new JMenuItem("Загрузить");
    JMenuItem saveItem = new JMenuItem("Сохранить");
    JMenuItem exitItem = new JMenuItem("Выход");

    JCheckBoxMenuItem showAxes = new JCheckBoxMenuItem("Показать оси координат",true);
    JCheckBoxMenuItem showPoints = new JCheckBoxMenuItem("Показать маркеры точек",true);

    private GraphicsDisplay _display;
    JFileChooser fileChooser = null;

    public MyMenuBar(GraphicsDisplay display){
        _display = display;

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(e ->  System.exit(0));

        showPoints.addActionListener(e -> _display.setShowMarkers(showPoints.getState()));
        showAxes.addActionListener(e -> _display.setShowAxis(showAxes.getState()));

        graphicMenu.add(showAxes);
        graphicMenu.add(showPoints);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        add(fileMenu);
        add(graphicMenu);

        showAxes.setEnabled(false);
        showPoints.setEnabled(false);
        saveItem.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == loadItem) {
                if(fileChooser==null){
                    fileChooser=new JFileChooser();

                    if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                        openGraphics(fileChooser.getSelectedFile());
                    }
                }
            }
            if (e.getSource() == saveItem) {
                System.out.println("Пока так...");
            }
        }
        catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void openGraphics(File file){
        try{
            DataInputStream in = new DataInputStream(new FileInputStream(file.getAbsolutePath()));

            Double[][] graphicsData = new Double[in.available()/(Double.SIZE/8)/2][];

            int i = 0;
            while (in.available()>0) {
                Double x = in.readDouble();
                Double y = in.readDouble();
                graphicsData[i++] = new Double[] {x, y};
            }

            if (graphicsData != null && graphicsData.length>0) {
                _display.showGraphics(graphicsData);
            }

            in.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Указанный файл не найден",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения координат точек из файла",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
}
