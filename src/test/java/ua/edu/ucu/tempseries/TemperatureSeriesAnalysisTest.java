package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.InputMismatchException;


public class TemperatureSeriesAnalysisTest
{
    private final double epsilon = 0.00001;
    @Test
    public void testAverageWithOneElementArray()
    {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, epsilon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray()
    {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage()
    {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, epsilon);
    }

    @Test
    public void testExtremeVal()
    {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0, 12.2, 12.4, -22.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expMin = -22.1;
        double actualMin = seriesAnalysis.min();
        assertEquals(expMin, actualMin, epsilon);

        double expMax = 12.4;
        double actualMax = seriesAnalysis.max();
        assertEquals(expMax, actualMax, epsilon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithEmptyArray()
    {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithEmptyArray()
    {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.max();
    }

    @Test
    public void testFindTempClosestToValue()
    {
        double[] temperatureSeries = {13.0, -5.0, 5.0, 12.2, 12.4, -22.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double expCase1 = -22.1;
        double actualCase1 = seriesAnalysis.findTempClosestToValue(-300);
        assertEquals(expCase1, actualCase1, epsilon);

        double expCase2 = -22.1;
        double actualCase2 = seriesAnalysis.findTempClosestToValue(-20);
        assertEquals(expCase2, actualCase2, epsilon);

        double expCase3 = 5.0;
        double actualCase3 = seriesAnalysis.findTempClosestToValue(0);
        assertEquals(expCase3, actualCase3, epsilon);
        assertEquals(expCase3, seriesAnalysis.findTempClosestToZero(), epsilon);

        double expCase4 = 13.0;
        double actualCase4 = seriesAnalysis.findTempClosestToValue(200);
        assertEquals(expCase4, actualCase4, epsilon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValueWithEmptyArray()
    {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.findTempClosestToValue(0);
    }

    @Test
    public void testFindTempsLessThen()
    {
        double[] temperatureSeries = {13.0, -5.0, 5.0, 12.2, 12.4, -22.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expCase1 = {-22.1, -5.0, 5.0, 12.2, 12.4, 13.0};
        double[] actualCase1 = seriesAnalysis.findTempsLessThen(300);
        assertArrayEquals(expCase1, actualCase1, 0.0);

        double[] expCase2 = {};
        double[] actualCase2 = seriesAnalysis.findTempsLessThen(-300);
        assertArrayEquals(expCase2, actualCase2, 0.0);

        double[] expCase3 = {-22.1, -5.0, 5.0, 12.2};
        double[] actualCase3 = seriesAnalysis.findTempsLessThen(12.4);
        assertArrayEquals(expCase3, actualCase3, 0.0);

        double[] expCase4 = {-22.1, -5.0, 5.0, 12.2, 12.4};
        double[] actualCase4 = seriesAnalysis.findTempsLessThen(12.4000001);
        assertArrayEquals(expCase4, actualCase4, 0.0);

        double[] emptySeries = {};
        TemperatureSeriesAnalysis emptyAnalysis = new TemperatureSeriesAnalysis(emptySeries);
        assertArrayEquals(new double[0], emptyAnalysis.findTempsLessThen(12), 0.0);
    }

    @Test
    public void testFindTempsGreaterThen()
    {
        double[] temperatureSeries = {13.0, -5.0, 5.0, 12.2, 12.4, -22.1};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expCase1 = {-22.1, -5.0, 5.0, 12.2, 12.4, 13.0};
        double[] actualCase1 = seriesAnalysis.findTempsGreaterThen(-200);
        assertArrayEquals(expCase1, actualCase1, 0.0);

        double[] expCase2 = {};
        double[] actualCase2 = seriesAnalysis.findTempsGreaterThen(300);
        assertArrayEquals(expCase2, actualCase2, 0.0);

        double[] expCase3 = {12.4, 13.0};
        double[] actualCase3 = seriesAnalysis.findTempsGreaterThen(12.4);
        assertArrayEquals(expCase3, actualCase3, 0.0);

        double[] expCase4 = {13};
        double[] actualCase4 = seriesAnalysis.findTempsGreaterThen(12.4000001);
        assertArrayEquals(expCase4, actualCase4, 0.0);

        double[] emptySeries = {};
        TemperatureSeriesAnalysis emptyAnalysis = new TemperatureSeriesAnalysis(emptySeries);
        assertArrayEquals(new double[0], emptyAnalysis.findTempsGreaterThen(12), 0.0);
    }

    @Test
    public void testDeviation()
    {
        double[] temperatureSeries = {10, 12, 23, 23, 16, 23, 21, 16};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 4.8989794855664;
        double actualResult = seriesAnalysis.deviation();
        assertEquals(expResult, actualResult, epsilon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithEmptyArray()
    {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.deviation();
    }

    @Test
    public void testSummaryStatistics()
    {
        double[] temperatureSeries = {10, 12, 23, 23, 16, 23, 21, 16};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        TempSummaryStatistics expResult = new TempSummaryStatistics(18,
                                                                    4.8989794855664,
                                                                    23,
                                                                    10);
        TempSummaryStatistics actualResult = seriesAnalysis.summaryStatistics();
        assertEquals(expResult.getAvgTemp(), actualResult.getAvgTemp(), epsilon);
        assertEquals(expResult.getDevTemp(), actualResult.getDevTemp(), epsilon);
        assertEquals(expResult.getMaxTemp(), actualResult.getMaxTemp(), epsilon);
        assertEquals(expResult.getMinTemp(), actualResult.getMinTemp(), epsilon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsWithEmptyArray()
    {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.summaryStatistics();
    }

    @Test
    public void testAddTemps()
    {
        double[] temperatureSeries = {10, 12, 23, 23, 16, 23, 21, 16};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        TempSummaryStatistics expResult1 = new TempSummaryStatistics(18,
                4.8989794855664,
                23,
                10);
        TempSummaryStatistics actualResult1 = seriesAnalysis.summaryStatistics();
        assertEquals(expResult1.getAvgTemp(), actualResult1.getAvgTemp(), epsilon);
        assertEquals(expResult1.getDevTemp(), actualResult1.getDevTemp(), epsilon);
        assertEquals(expResult1.getMaxTemp(), actualResult1.getMaxTemp(), epsilon);
        assertEquals(expResult1.getMinTemp(), actualResult1.getMinTemp(), epsilon);

        int expNum = 11;
        int actualNum = seriesAnalysis.addTemps(1, 2, 100);
        assertEquals(expNum, actualNum, epsilon);

        TempSummaryStatistics expResult2 = new TempSummaryStatistics(22.454545454545453,
                                                                     25.65988604935953,
                                                                     100,
                                                                     1);
        TempSummaryStatistics actualResult2 = seriesAnalysis.summaryStatistics();
        assertEquals(expResult2.getAvgTemp(), actualResult2.getAvgTemp(), epsilon);
        assertEquals(expResult2.getDevTemp(), actualResult2.getDevTemp(), epsilon);
        assertEquals(expResult2.getMaxTemp(), actualResult2.getMaxTemp(), epsilon);
        assertEquals(expResult2.getMinTemp(), actualResult2.getMinTemp(), epsilon);
    }

    @Test(expected = InputMismatchException.class)
    public void testAddTempsWithWrongData()
    {
        double[] temperatureSeries = {10, 12, 23, 23, 16, 23, 21, 16};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.addTemps(1, 2, 100, -280);
    }




    

}
