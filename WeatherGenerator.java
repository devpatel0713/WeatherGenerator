/*
 * @author: Dev Patel
 */

public class WeatherGenerator {


    static final int WET = 1; // Use this value to represent a wet day
    static final int DRY = 2; // Use this value to represent a dry day 
    
    // Number of days in each month, January is index 0, February is index 1...
    static final int[] numberOfDaysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    public static void populateArrays(double[][] drywet, double[][] wetwet) {

        StdIn.setFile("drywet.txt");

	for(int i=0; i < drywet.length; i++){
            for(int j=0; j<14; j++){
                drywet[i][j] = StdIn.readDouble();
            }
        }

	StdIn.setFile("wetwet.txt");

	for(int i=0; i < drywet.length; i++){
            for(int j=0; j<14; j++){
                wetwet[i][j] = StdIn.readDouble();
            }
        }
    }
    public static void populateLocationProbabilities( double[] drywetProbability, double[] wetwetProbability, 
                                     double longitude, double latitude, 
                                     double[][] drywet, double[][] wetwet){     
            //drywetProbability = new double[12];
           // wetwetProbability = new double[12];
            for(int x = 0;x< drywet.length;x++){
                if(drywet[x][0] == longitude && drywet[x][1] == latitude){
                    int row = x;
                    for(int i = 0; i< 12 ;i++){
                    drywetProbability[i] = drywet[row][i+2];
                    //System.out.println(drywetProbability[i]);
                    }
                }
            }
            for ( int x = 0; x<wetwet.length;x++){
                if(wetwet[x][0] == longitude && wetwet[x][1] == latitude){
                    int row = x;
                    for(int i = 0; i < 12 ; i++){
                    wetwetProbability[i] = wetwet[row][i + 2];
                    //System.out.println(wetwetProbability[i]);
                }
            }
            }
    }
    public static int[] forecastGenerator( double drywetProbability, double wetwetProbability, int numberOfDays) {
        int [] forecast = new int[numberOfDays];
        double num1 = StdRandom.uniform();
        if(num1< 0.5){
            forecast[0] = WET;
        }else {
            forecast[0] = DRY;
        }

        for( int day = 1; day < forecast.length; day++){
            num1 = StdRandom.uniform();
            if (forecast[day - 1] == WET){
                if(num1 <= wetwetProbability){
                    forecast[day] = WET;
                }else {
                    forecast[day] = DRY;
                }
            }else if (forecast[day - 1] == DRY){
                if (num1 <= drywetProbability){
                    forecast[day] = WET;
                }else {
                    forecast[day] = DRY;
                }
            }
        }
        return forecast;

    } 
    public static int[] oneMonthForecast(int numberOfLocations, int month, double longitude, double latitude ){
        int numDay = numberOfDaysInMonth[month - 1];
        numberOfLocations = 4100;
        double [][] wetwet = new double[numberOfLocations][14];
        double [][] drywet = new double[numberOfLocations][14];
        populateArrays(drywet, wetwet);
        double []drywetProb =  new double[12];
        double [] wetwetProb = new double[12];
        populateLocationProbabilities(drywetProb,wetwetProb,longitude,latitude,drywet,wetwet);
        double drywetProbability = drywetProb[month -1];
        double wetwetProbability = wetwetProb[month -1];
        int [] forecast = forecastGenerator(drywetProbability, wetwetProbability, numDay);
        return forecast;
    }
    public static int numberOfWetDryDays (int[] forecast, int mode) {
        int sum = 0;
        for (int i = 0; i<=forecast.length;i++){
            if (forecast[i] == mode){
                sum+= 1;
            }
        }
        return sum;
    }
    public static int lengthOfLongestSpell (int[] forecast, int mode) {
        int LengthOfMode = 1;
        int sum = 0;
        if(forecast[0] == mode){
            sum++;
        }
        for(int i = 1; i < forecast.length; i++){
            if (forecast[i-1] == forecast[i] && forecast[i] == mode){
                sum++;
                continue;
            }
            if (LengthOfMode < sum && forecast[i-1] == mode){
                LengthOfMode = sum;
            }
        }
        return LengthOfMode;
        }
    public static int bestWeekToTravel(int[] forecast){
        int num = 0;
        int total = 0;
        int sub = 0;
        for(int i = 0; i<forecast.length-6; i++){
                total = (forecast[i] + forecast[i+1] + forecast [i+2] + forecast[i+3] + forecast[i+4] + forecast[i+5] + forecast[i+6]);
                if (total > sub){
                    sub = total;
                    num = i;
                }
            }
            return num;
    }
    public static void main (String[] args) {
        StdRandom.setSeed(1636172170678L);

        int numberOfRows    = 4100; // Total number of locations
        int numberOfColumns = 14;   // Total number of 14 columns in file 

        // File format: longitude, latitude, 12 months of transition probabilities
        double longitude = Double.parseDouble(args[0]);
        double latitude  = Double.parseDouble(args[1]);
        int    month     = Integer.parseInt(args[2]);
        
        int[] forecast = oneMonthForecast( numberOfRows,  month,  longitude,  latitude );
        

        int drySpell = lengthOfLongestSpell(forecast, DRY);
        int wetSpell = lengthOfLongestSpell(forecast, WET);
        int bestWeek = bestWeekToTravel(forecast);

        StdOut.println("There are " + forecast.length + " days in the forecast for month " + month);
        StdOut.println(drySpell + " days of dry spell.");
        StdOut.println("The bestWeekToTravel starts on:" + bestWeek );

        for ( int i = 0; i < forecast.length; i++ ) {
            String weather = (forecast[i] == WET) ? "Wet" : "Dry";  
            StdOut.println("Day " + (i) + " is forecasted to be " + weather);
        }
    }
}