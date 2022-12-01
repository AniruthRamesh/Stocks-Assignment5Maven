package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class LineChart extends JFrame {

  public LineChart(String title, DefaultCategoryDataset data) {
    super(title);
    DefaultCategoryDataset dataset = data;
    JFreeChart chart = ChartFactory.createLineChart(
            "Stock Performance", // Chart title
            "Time Period", // X-Axis Label
            "Value of Stock", // Y-Axis Label
            dataset, PlotOrientation.VERTICAL,
            true, true, false
    );

    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  private DefaultCategoryDataset createDataset() {

    String series1 = "Stocks";

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    dataset.addValue(200, series1, "2016-12-19");
    dataset.addValue(150, series1, "2016-12-20");
    dataset.addValue(100, series1, "2016-12-21");
    dataset.addValue(210, series1, "2016-12-22");
    dataset.addValue(240, series1, "2016-12-23");
    dataset.addValue(195, series1, "2016-12-24");
    dataset.addValue(245, series1, "2016-12-25");

    return dataset;
  }
}  