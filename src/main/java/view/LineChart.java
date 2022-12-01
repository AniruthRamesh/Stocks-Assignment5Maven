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
            "Stock Performance",
            "Time Period",
            "Value of Stock",
            dataset, PlotOrientation.VERTICAL,
            true, true, false
    );
    JLabel label = new JLabel("Enter the ticker symbol:");
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }
}  