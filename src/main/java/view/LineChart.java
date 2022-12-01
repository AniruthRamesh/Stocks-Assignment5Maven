package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;

/**
 * It creates a line chart using the data in the dataset passed to it.
 */
public class LineChart extends JFrame {

  /**
   * Constructor for line chart.
   * @param title Title of chart
   * @param data data to be displayed
   */
  public LineChart(String title, DefaultCategoryDataset data) {
    super(title);
    JFreeChart chart = ChartFactory.createLineChart(
            "Stock Performance",
            "Time Period",
            "Value of Stock",
            data, PlotOrientation.VERTICAL,
            true, true, false
    );
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }
}  