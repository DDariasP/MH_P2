package mh;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica extends JFrame {

    public Grafica(Lista<Integer> datos, String nombre) {
        //crear la grafica
        XYPlot plot = new XYPlot();

        //crear funcion
        XYDataset funcion = createDataset(datos,nombre);
        //caracteristicas de funcion
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, true);
        renderer.setSeriesShape(0, new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0));
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        //añadir funcion a la grafica
        plot.setDataset(0, funcion);
        plot.setRenderer(0, renderer);

        //crear y añadir los ejes
        ValueAxis domain = new NumberAxis("Evaluación (n/5k)");
        ValueAxis range = new NumberAxis("Coste");
        plot.setDomainAxis(0, domain);
        plot.setRangeAxis(0, range);

        //crear el area de trazado
        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        //crear la ventana 
        ChartPanel panel = new ChartPanel(chart);
        panel.setDomainZoomable(true);
        panel.setRangeZoomable(true);
        setContentPane(panel);
    }

    private XYDataset createDataset(Lista<Integer> datos, String nombre) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(nombre);
        for (int i = 0; i < datos.size(); i++) {
            series.add(i, datos.get(i));
        }
        dataset.addSeries(series);
        return dataset;
    }
}
