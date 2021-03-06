package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private static final double EPSILON = 0.000000001;
    private static final double LIMIT   = -273;
    private static final double NONE = Double.NaN;

    private double[] temArr;
    private int size;
    private int cap;
    private double dev;
    private double avr;
    private double zer;

    public TemperatureSeriesAnalysis()
    {
        temArr = new double[0];
        size = 0;
        cap = 0;
        cleanData();
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries)
    {
        Arrays.sort(temperatureSeries);
        temArr = temperatureSeries.clone();
        size = temperatureSeries.length;
        cap = size;
        cleanData();
    }

    private boolean isNone(double arg)
    {
        return Double.isNaN(arg);
    }

    private void cleanData()
    {
        dev = NONE;
        avr = NONE;
        zer = NONE;
    }

    private void check()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException();
        }
    }

    private boolean dEquals(double first,
                            double second)
    {
        return Math.abs(first-second) < EPSILON;
    }

    private boolean isEmpty()
    {
        return size == 0;
    }

    private double[] aggregate(double power,
                               double mod)
    {
        double[] aggregated = new double[size];
        for (int ind = size; ind > 0; --ind)
        {
            aggregated[ind - 1] = Math.pow(temArr[ind - 1] - mod, power);
        }
        return aggregated;
    }

    private double totalExt(double power,
                            double mod)
    {
        double sum = 0;
        double[] arr;
        if (power == 1 && mod == 0)
        {
            arr = temArr;
        }
        else
        {
            arr = aggregate(power, mod);
        }
        for (int ind = arr.length; ind > 0; --ind)
        {
            sum += arr[ind - 1];
        }
        return sum;
    }

    private int[] binSearch(double target)
    {
        int last = size - 1;
        int[] boundaries = new int[2];
        int low = 0, high = last;
        int mid = (low + high)/2;
        for (; low < high; mid = (low+high)/2)
        {
            if (dEquals(temArr[mid], target)
                || (temArr[mid] < target && target < temArr[mid+1]))
            {
                break;
            }
            else if (temArr[mid] < target)
            {
                low = mid + 1;
            }
            else
            {
                high = mid - 1;
            }
        }
        if (dEquals(temArr[mid], target))
        {
            if (mid > 0)
            {
                boundaries[0] = mid - 1;
            }
            else
            {
                boundaries[0] = -1;
            }

            if (mid < last)
            {
                boundaries[1] = mid + 1;
            }
            else
            {
                boundaries[1] = -1;
            }
        }
        else
        {
            if (temArr[mid] < target)
            {
                boundaries[0] = mid;
            }
            else
            {
                boundaries[0] = -1;
            }

            if (mid < last && temArr[mid] > target)
            {
                boundaries[1] = mid;
            }
            else if (mid < last)
            {
                boundaries[1] = mid + 1;
            }
            else
            {
                boundaries[1] = -1;
            }
        }
        return boundaries;
    }

    private void copyTem(double[] from,
                         int fromInd,
                         double[] to,
                         int toInd,
                         boolean rightFrom)
    {
        int end = from.length;
        int start, length;
        if (rightFrom)
        {
            start = fromInd;
            length = end-fromInd;
        }
        else
        {
            start = 0;
            length = fromInd+1;
        }
        System.arraycopy(from, start, to, toInd, length);
    }

    private void resize(int inc)
    {
        int newCap = Math.max(cap * 2, inc * 2);
        double[] buffer = new double[newCap];
        copyTem(temArr, 0, buffer, 0, true);
        temArr = buffer;
        cap = newCap;
    }

    private void findAverage()
    {
        check();
        avr = totalExt(1, 0)/size;
    }

    public double average()
    {
        if (isNone(avr))
        {
            findAverage();
        }
        return avr;
    }

    private void findDeviation()
    {
        check();
        dev = Math.pow(totalExt(2, average())/size, 0.5);
    }

    public double deviation()
    {
        if (isNone(dev))
        {
            findDeviation();
        }
        return dev;
    }

    public double min()
    {
        check();
        return temArr[0];
    }

    public double max()
    {
        check();
        return  temArr[size-1];
    }

    private void closestToZero()
    {
        zer = findTempClosestToValue(0);
    }

    public double findTempClosestToZero()
    {
        if (isNone(zer))
        {
            closestToZero();
        }
        return zer;
    }

    public double findTempClosestToValue(double tempValue)
    {
        check();
        int[] bounds = binSearch(tempValue);
        double result;
        if (bounds[0]+1 < temArr.length
            && dEquals(temArr[bounds[0]+1], tempValue))
        {
            result = temArr[bounds[0]+1];
        }
        else if (bounds[0] != -1 && bounds[1] != -1)
        {
            double dif = Math.abs(temArr[bounds[0]]-tempValue)
                         - Math.abs(temArr[bounds[1]]-tempValue);

            if (dif > 0 || dEquals(dif, 0))
            {
                result = temArr[bounds[1]];
            }
            else
            {
                result = temArr[bounds[0]];
            }
        }
        else
        {
            if (bounds[0] != -1)
            {
                result = temArr[bounds[0]];
            }
            else
            {
                result = temArr[bounds[1]];
            }
        }
        return result;
    }

    public double[] findTempsLessThen(double tempValue)
    {
        if (isEmpty())
        {
            return new double[0];
        }
        int[] bounds = binSearch(tempValue);
        if (bounds[0] == -1)
        {
            return new double[0];
        }
        double[] arr = new double[bounds[0]+1];
        copyTem(temArr, bounds[0], arr, 0, false);
        return arr;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (isEmpty())
        {
            return new double[0];
        }
        int start = findTempsLessThen(tempValue).length;
        double[] arr = new double[size-start];
        copyTem(temArr, start, arr, 0, true);
        return arr;
    }

    public TempSummaryStatistics summaryStatistics()
    {
        check();
        return new TempSummaryStatistics(average(),
                                         deviation(),
                                         max(),
                                         min());
    }

    public int addTemps(double... temps)
    {
        int appSize = temps.length;
        for (int ind = appSize; ind > 0; --ind)
        {
            if (temps[ind-1] <= LIMIT)
            {
                throw new InputMismatchException();
            }
        }
        if (cap - size < appSize)
        {
            resize(appSize);
        }
        copyTem(temps, 0, temArr, size, true);
        cleanData();
        size += appSize;
        Arrays.sort(temArr, 0, size);
        return size;
    }
}
