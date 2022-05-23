package adapter;

import java.util.*;

import bll.*;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;

import java.io.*;

public class StatisticAdapter {
    private static int nr = 0;
    private MovieBLL movieBLL;

    public StatisticAdapter(MovieBLL movieBLL) {
        this.movieBLL = movieBLL;
    }

    public void deleteFile(String nameFile) {

        File f = new File(nameFile);

        f.delete();


    }

    public void generateStatistics(List<String> typeOfType, List<String> typeOfCategory, String nbMovies, String movieCategory, String movieType) throws IOException {
        int pp = nr - 1;
        deleteFile("chart_column" + pp + ".jpg");
        deleteFile("chart_column2" + pp + ".jpg");
        deleteFile("chart_radial" + pp + ".jpg");
        deleteFile("chart_radial2" + pp + ".jpg");
        deleteFile("chart_inelar" + pp + ".jpg");
        deleteFile("chart_inelar2" + pp + ".jpg");
        var dataset = new DefaultCategoryDataset();
        for (String s : typeOfType
        ) {
            dataset.setValue(this.movieBLL.select_type(s), nbMovies, s);


        }

        var dataset1 = new org.jfree.data.category.DefaultCategoryDataset();
        for (String s : typeOfCategory) {
            dataset1.setValue(this.movieBLL.select_category(s), nbMovies, s);

        }
        var dataset2 = new DefaultPieDataset();
        for (String s : typeOfType) {
            dataset2.setValue(s, this.movieBLL.select_type(s));

        }
        var dataset3 = new DefaultPieDataset();
        for (String s : typeOfCategory) {
            dataset3.setValue(s, this.movieBLL.select_category(s));

        }

        JFreeChart barChart = org.jfree.chart.ChartFactory.createBarChart(
                movieType,
                "",
                nbMovies,
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
        JFreeChart barChart1 = ChartFactory.createBarChart(
                movieCategory,
                "",
                nbMovies,
                dataset1,
                PlotOrientation.VERTICAL,
                false, true, false);
        JFreeChart pieChart = ChartFactory.createPieChart(
                movieType,
                dataset2,
                false, true, false);
        JFreeChart pieChart1 = ChartFactory.createPieChart(
                movieCategory,
                dataset3,
                false, true, false);


        JFreeChart chart = ChartFactory.createRingChart(movieType, dataset2, true, true, false);
        JFreeChart chart1 = ChartFactory.createRingChart(movieCategory, dataset3, true, true, false);

        try {
            ChartUtils.saveChartAsJPEG(new File("chart_column" + nr + ".jpg"),
                    barChart, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_column2" + nr + ".jpg"),
                    barChart1, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_radial" + nr + ".jpg"),
                    pieChart, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_radial2" + nr + ".jpg"),
                    pieChart1, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_inelar" + nr + ".jpg"),
                    chart, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_inelar2" + nr + ".jpg"),
                    chart1, 400, 270);
        } catch (IOException e) {
            System.out.println("Problem in creating chart.");
        }
        nr++;
    }


}
