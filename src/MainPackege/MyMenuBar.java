package MainPackege;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

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

    public MyMenuBar(GraphicsDisplay display, boolean visible){
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

        setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == loadItem) {
                if(fileChooser==null){
                    fileChooser=new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));

                    if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                        openGraphics(fileChooser.getSelectedFile());
                    }
                }

                setVisible(true);
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
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String line;
            String[] strings = null;
            ArrayList<Double[]> graphicsData = new ArrayList<>();

            while((line = reader.readLine()) != null){
                strings = line.split(" ");
            }

            Double x = 0d;
            Double y = 0d;
            for (int  i = 0; i < strings.length;i++){
                if (i%2==0) {
                    x = Double.parseDouble(strings[i]);
                }
                if (i%2!=0){
                    y = Double.parseDouble(strings[i]);
                    graphicsData.add(new Double[]{x,y});
                }
            }

            _display.showGraphics(graphicsData);

            reader.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Указанный файл не найден",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения координат точек из файла",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
            return;
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения координат точек из файла",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    public void setVisible(boolean visible){
        showAxes.setEnabled(visible);
        showPoints.setEnabled(visible);
        saveItem.setEnabled(visible);
    }
}
