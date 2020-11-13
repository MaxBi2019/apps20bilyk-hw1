package ua.edu.ucu.tempseries;

public final class TempSummaryStatistics
{
    private final double avgTemp;
    private final double devTemp;
    private final double minTemp;
    private final double maxTemp;

    public TempSummaryStatistics(double avgTemp,
                                 double devTemp,
                                 double maxTemp,
                                 double minTemp)
    {
        this.avgTemp = avgTemp;
        this.devTemp = devTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public double getMaxTemp()
    {
        return maxTemp;
    }

    public double getDevTemp()
    {
        return devTemp;
    }

    public double getAvgTemp()
    {
        return avgTemp;
    }

    public double getMinTemp()
    {
        return minTemp;
    }

    public String toString()
    {
    return    "avgTemp: " + avgTemp + "\n"
            + "devTemp: " + devTemp + "\n"
            + "maxTemp: " + maxTemp + "\n"
            + "minTemp: " + minTemp + "\n";
    }
}