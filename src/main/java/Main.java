import ua.edu.ucu.tempseries.*;

import java.util.Arrays;

public class Main
{
    public static void main(String[] args) {
        double[] temperatureSeries = {3.0, -1, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        TempSummaryStatistics statistics = seriesAnalysis.summaryStatistics();

        System.out.println(statistics.getAvgTemp() + "\n" + statistics.getDevTemp() + "\n" + statistics.getMaxTemp() + "\n" + statistics.getMinTemp());
        seriesAnalysis.addTemps(1.1, 12.2);
    }
}
