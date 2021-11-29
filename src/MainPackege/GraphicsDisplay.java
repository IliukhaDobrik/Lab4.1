package MainPackege;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;

public class GraphicsDisplay extends JPanel {
    private Double[][] _graphicsData;

    private boolean _showAxis = true;
    private boolean _showMarkers = true;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    private double scale;

    private BasicStroke _graphicsStroke;
    private BasicStroke _axisStroke;
    private BasicStroke _markerStroke;

    private Font _axisFont;

    public GraphicsDisplay(){
        setBackground(Color.WHITE);

        _graphicsStroke = new BasicStroke(2f,BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,10f, null,0f);
        _axisStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
        _markerStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
        _axisFont = new Font("Serif", Font.BOLD, 36);
    }

    public void showGraphics(Double[][] graphicsData) {
        _graphicsData = graphicsData;
        repaint();
    }

    public void setShowAxis(boolean showAxis) {
        _showAxis = showAxis;
        repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        _showMarkers = showMarkers;
        repaint();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        if (_graphicsData == null || _graphicsData.length == 0) return;

        minX = _graphicsData[0][0];
        maxX = _graphicsData[_graphicsData.length-1][0];
        minY = _graphicsData[0][1];
        maxY = minY;

        for (int i = 1; i<_graphicsData.length; i++) {
            if (_graphicsData[i][1]<minY) {
                minY = _graphicsData[i][1];
            }
            if (_graphicsData[i][1]>maxY) {
                maxY = _graphicsData[i][1];
            }
        }

        double scaleX = getSize().getWidth() / (maxX - minX);
        double scaleY = getSize().getHeight() / (maxY - minY);

        scale = Math.min(scaleX, scaleY);

        if (scale==scaleX) {
            double yIncrement = (getSize().getHeight()/scale - (maxY - minY))/2;
            maxY += yIncrement;
            minY -= yIncrement;
        }
        if (scale==scaleY) {
            double xIncrement = (getSize().getWidth()/scale - (maxX - minX))/2;
            maxX += xIncrement;
            minX -= xIncrement;
        }

        Graphics2D canvas = (Graphics2D) graphics;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();

        if (_showAxis) paintAxis(canvas);

        paintGraphics(canvas);

        if (_showMarkers) paintMarkers(canvas);

        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }

    private void paintMarkers(Graphics2D canvas) {
        canvas.setStroke(_markerStroke);
        canvas.setColor(Color.RED);
        canvas.setPaint(Color.RED);

        for (Double[] point: _graphicsData) {
            Ellipse2D.Double marker = new Ellipse2D.Double();
            Point2D.Double center = xyToPoint(point[0], point[1]);
            Point2D.Double corner = shiftPoint(center, 5.5, 5.5);
            marker.setFrameFromCenter(center, corner);
            canvas.draw(marker);
            canvas.fill(marker);
        }
    }

    private void paintGraphics(Graphics2D canvas) {
        canvas.setStroke(_graphicsStroke);
        canvas.setColor(Color.RED);

        GeneralPath graphics = new GeneralPath();
        for (int i=0; i<_graphicsData.length; i++) {
            Point2D.Double point = xyToPoint(_graphicsData[i][0], _graphicsData[i][1]);
            if (i>0) {
                graphics.lineTo(point.getX(), point.getY());
            } else {
                graphics.moveTo(point.getX(), point.getY());
            }
        }

        canvas.draw(graphics);
    }

    private void paintAxis(Graphics2D canvas) {
        canvas.setStroke(_axisStroke);
        canvas.setColor(Color.BLACK);
        canvas.setPaint(Color.BLACK);
        canvas.setFont(_axisFont);

        FontRenderContext context = canvas.getFontRenderContext();

        if (minX<=0.0 && maxX>=0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(0, maxY), xyToPoint(0, minY)));

            GeneralPath arrowY = new GeneralPath();
            Point2D.Double lineEnd = xyToPoint(0, maxY);
            arrowY.moveTo(lineEnd.getX(), lineEnd.getY());
            arrowY.lineTo(arrowY.getCurrentPoint().getX()+5, arrowY.getCurrentPoint().getY()+20);
            arrowY.lineTo(arrowY.getCurrentPoint().getX()-10, arrowY.getCurrentPoint().getY());

            //arrow.closePath();
            //canvas.draw(arrow); // Нарисовать стрелку
            //canvas.fill(arrow); // Закрасить стрелку

            Rectangle2D bounds = _axisFont.getStringBounds("Y", context);
            Point2D.Double labelPos = xyToPoint(0, maxY);

            canvas.drawString("Y", (float)labelPos.getX() + 10,
                    (float)(labelPos.getY() - bounds.getY()));
        }

        if (minY<=0.0 && maxY>=0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(minX, 0), xyToPoint(maxX, 0)));

            GeneralPath arrow = new GeneralPath();
            Point2D.Double lineEnd = xyToPoint(maxX, 0);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() - 20, arrow.getCurrentPoint().getY() - 5);
            arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY() + 10);

            //arrow.closePath();
            //canvas.draw(arrow);
            //canvas.fill(arrow);

            Rectangle2D bounds = _axisFont.getStringBounds("X", context);
            Point2D.Double labelPos = xyToPoint(maxX, 0);

            canvas.drawString("X", (float) (labelPos.getX() - bounds.getWidth() - 10),
                    (float) (labelPos.getY() + bounds.getY()));
        }
    }

    private Point2D.Double xyToPoint(double x, double y) {
        double deltaX = x - minX;
        double deltaY = maxY - y;
        return new Point2D.Double(deltaX*scale, deltaY*scale);
    }

    private Point2D.Double shiftPoint(Point2D.Double src, double deltaX, double deltaY) {
        Point2D.Double dest = new Point2D.Double();
        dest.setLocation(src.getX() + deltaX, src.getY() + deltaY);
        return dest;
    }


}
