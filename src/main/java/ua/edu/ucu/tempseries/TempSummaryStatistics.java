package ua.edu.ucu.tempseries;

public final class TempSummaryStatistics
{
    public final double avgTemp;
    public final double devTemp;
    public final double minTemp;
    public final double maxTemp;

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

    public String toString()
    {
    return    "avgTemp: " + avgTemp + "\n"
            + "devTemp: " + devTemp + "\n"
            + "maxTemp: " + maxTemp + "\n"
            + "minTemp: " + minTemp + "\n";
    }
}