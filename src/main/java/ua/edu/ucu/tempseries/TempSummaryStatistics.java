package ua.edu.ucu.tempseries;

public final class TempSummaryStatistics
{
    private double avgTemp;
    private double devTemp;
    private double minTemp;
    private double maxTemp;

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

    public double getAvgTemp()
    {
        return avgTemp;
    }

    public double getDevTemp()
    {
        return devTemp;
    }

    public double getMaxTemp()
    {
        return maxTemp;
    }

    public double getMinTemp()
    {
        return minTemp;
    }
}