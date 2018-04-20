import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    static ArrayList<RouteAndDistance> routeAndDistances=new ArrayList<>();
    static ArrayList<RoadParameters> roadParameters=new ArrayList<>();
    static int pumps[]=new int[10];


    // recursive dfs
    private static void dfs_rec(ArrayList<ArrayList<Integer>> adjLists, boolean[] visited, int v, int d,
                                List<Integer> path,int[] e,int[][] c,int[] p,int[] arr) {
        int a[] = new int[10];

        int k=-1,min=0;

        visited[v] = true;
        path.add(v);
        if (v == d) {

            for (int i = 0; i < path.size(); i++) {
                a[i]=path.get(i);
            }

            int m=path.size();
            //Call Two Dimensional Matix
            twoD(a,m,c,min,e,p,arr);
        }

        else {
            for (int w : adjLists.get(v)) {
                if (!visited[w]) {
                    dfs_rec(adjLists, visited, w, d, path,e,c,p,arr);
                }

            }
        }

        path.remove(path.size() - 1);
        visited[v] = false;

    }

    public static void twoD(int a[], int m,int c[][],int min,int[] e,int[] p,int[] arr) {
        int b[][]= new int[m-1][2];
        int nopp=0;
        int count=0;
// calculating the distance
        for(int i=0;i<m-1;i++) {
            b[i][0]=a[i];
            b[i][1]=a[i+1];
            if(b[i][0]==0) {
                if(b[i][1]==1) {
                    count=count+2*400;
                    nopp+=2;
                }


                if(b[i][1]==4) {
                    nopp+=1;
                    count=count+5*400;
                }

            }
            if(b[i][0]==4) {
                if(b[i][1]==3) {
                    nopp+=3;
                    count=count+3*400;
                }
                if(b[i][1]==1) {
                    nopp+=1;
                    count=count+2*400;
                }
                if(b[i][1]==0){
                    nopp+=3;
                    count=count+5*400;
                }
            }
            if(b[i][0]==2) {
                if(b[i][1]==3) {
                    nopp+=4;
                    count=count+1*400;
                }
                if(b[i][1]==1){
                    nopp+=0;
                    count=count+3*400;
                }
            }
            if(b[i][0]==1) {
                if(b[i][1]==2){
                    nopp+=0;
                    count=count+3*400;
                }
                if(b[i][1]==4){
                    nopp+=1;
                    count=count+2*400;
                }
                if(b[i][1]==0){
                    nopp+=2;
                    count=count+2*400;
                }
            }
            if(b[i][0]==3) {
                if(b[i][1]==4){
                    nopp+=3;
                    count=count+3;
                }
                if(b[i][1]==2){
                    nopp+=4;
                    count=count+1;
                }
            }


        }
        pumps[e[0]]=nopp;

        arr[e[0]]=count;
        for(int j=0;j<m;j++) {
            c[e[0]][j]=a[j];
        }

        p[e[0]]=m; // length of each route
        e[0]++;    //number of routes
        routeAndDistances.removeAll(routeAndDistances);
        for(int i=0;i<e[0];i++)
        {
            String routeCharString="";
            for(int j=0;j<p[i];j++)
            {
                String temp= String.valueOf(c[i][j]);
                routeCharString=routeCharString.concat(temp);
            }

            routeAndDistances.add(new RouteAndDistance(routeCharString,arr[i]));
        }

    }

    public static void dfs(ArrayList<ArrayList<Integer>> adjLists, int s, int d,int[] e,int[][] c,int[] p,int[] arr,int[] pumps) {
        int n = adjLists.size();
        boolean[] visited = new boolean[n];

        List<Integer> path = new ArrayList<Integer>();
        dfs_rec(adjLists, visited, s, d, path,e,c,p,arr);
    }

    public static void main(String[] args) {

        int[] e = {0};   //to store count of number of routes
        int c[][] = new int[10][10];    //to display all routes in a matrix
        int p[]=new int[5];    //to store length of each route
        int arr[]= new int[10];   //to store distance of each route
        ArrayList<ArrayList<Integer>> adjLists;
        final int n = 12;  //size

        adjLists=CreateRoutes(n);
        String startString,endString;
        String finalRoute ="";

        int start=0,end=0;
        float averageVelocity=0,mileage=0,tankCapacity=0;

        while(true) {
            try {
                System.out.println("Enter Start and End points");
                Scanner sc = new Scanner(System.in);
                startString =sc.next();
                endString = sc.next();
                if(startString.equalsIgnoreCase("mumbai")) start=0;
                else if(startString.equalsIgnoreCase("delhi")) start=1;
                else if(startString.equalsIgnoreCase("chennai")) start=2;
                else if(startString.equalsIgnoreCase("kolkata")) start=3;
                else if(startString.equalsIgnoreCase("jaipur")) start=4;

                if(endString.equalsIgnoreCase("mumbai")) end=0;
                else if(endString.equalsIgnoreCase("delhi")) end=1;
                else if(endString.equalsIgnoreCase("chennai")) end=2;
                else if(endString.equalsIgnoreCase("kolkata")) end=3;
                else if(endString.equalsIgnoreCase("jaipur")) end=4;

                System.out.println("Enter the average velocity (km/hr):");
                averageVelocity = sc.nextFloat();
               // averageVelocity /= 400;
                System.out.println("Enter the average mileage of the car (metres/litre):");
                mileage = sc.nextFloat();
                System.out.println("Enter the tank capacity (litres):");
                tankCapacity = sc.nextFloat();
                break;
            }catch (Exception ex){
                System.out.println("Please enter valid input!");
            }
        }
        System.out.print("-------------------------------------------------------------------------------------------------------");
        System.out.print("------------------------------------------------------------------------------\n");

        dfs(adjLists, start,end,e,c,p,arr,pumps);

        System.out.println("Possible Routes are:");
        for (int i=0;i<routeAndDistances.size();i++)
        {
            for(int j=0;j<routeAndDistances.get(i).route.length();j++) {
                if(routeAndDistances.get(i).route.charAt(j)=='0') finalRoute=finalRoute.concat("->Mumbai");
                if(routeAndDistances.get(i).route.charAt(j)=='1') finalRoute=finalRoute.concat("->Delhi");
                if(routeAndDistances.get(i).route.charAt(j)=='2') finalRoute=finalRoute.concat("->Chennai");
                if(routeAndDistances.get(i).route.charAt(j)=='3') finalRoute=finalRoute.concat("->Kolkata");
                if(routeAndDistances.get(i).route.charAt(j)=='4') finalRoute=finalRoute.concat("->Jaipur");

            }
            System.out.printf("Route:%s\n",finalRoute);
            finalRoute="";
        }

        ArrayList<PredictionforEachRoute> predictions=fetchPredictionOfEachRoute(averageVelocity);
        System.out.println("\nPredictions:");
        String route[]=new String[predictions.size()];
        float damage[]=new float[predictions.size()];
        float time[]=new float[predictions.size()];
        for (int i=0;i<predictions.size();i++)
        {
            damage[i]=Float.parseFloat(predictions.get(i).damage);
            route[i]=(routeAndDistances.get(i).route);
            time[i]=Float.parseFloat(predictions.get(i).time);

        }

        for (int i=0;i<predictions.size()-1;i++)
        {

            for(int j=i+1;j<predictions.size();j++)
            {
                float op1=(67*200*averageVelocity*time[i]/mileage)+((damage[i])* 1500);
                float op2=(67*200*averageVelocity*time[j]/mileage)+((damage[j])* 1500);
                if(op1 > op2)
                {
                    String tmp=route[i];
                    route[i]=route[j];
                    route[j]=tmp;

                    float tmp1=time[i];
                    time[i]=time[j];
                    time[j]=tmp1;

                    int tmp2=pumps[i];
                    pumps[i]=pumps[j];
                    pumps[j]=tmp2;

                    float tmp3=damage[i];
                    damage[i]=damage[j];
                    damage[j]=tmp3;
                }
            }



        }
        for (int i=0;i<predictions.size();i++)
        {
            for(int j=0;j<route[i].length();j++) {
                if(route[i].charAt(j)=='0') finalRoute=finalRoute.concat("->Mumbai");
                if(route[i].charAt(j)=='1') finalRoute=finalRoute.concat("->Delhi");
                if(route[i].charAt(j)=='2') finalRoute=finalRoute.concat("->Chennai");
                if(route[i].charAt(j)=='3') finalRoute=finalRoute.concat("->Kolkata");
                if(route[i].charAt(j)=='4') finalRoute=finalRoute.concat("->Jaipur");

            }
            //System.out.println("Route:"+finalRoute+"\t\tTime:"+time[i]+"\t\tDamage:"+damage[i]+"\t\tFuel Required:"+200*averageVelocity*time[i]/mileage+"\t\tTank Capacity:"+tankCapacity+"\t\tFuel Available:"+tankCapacity*(pumps[i]+2)/2);
            System.out.format("Route:%-50sTime:%-15fDamage:%-15fFuel Required:%-15fTank Capacity:%-15fFuel Available:%-15f\n",finalRoute,time[i],damage[i],200*averageVelocity*time[i]/mileage,tankCapacity,tankCapacity*(pumps[i]+2)/2);
            finalRoute="";
        }
        System.out.print("-------------------------------------------------------------------------------------------------------");
        System.out.print("------------------------------------------------------------------------------\n");

        boolean flag=false;
        for (int i=0;i<predictions.size();i++)
        {
            if((tankCapacity*(pumps[i]+2)/2)>200*averageVelocity*time[i]/mileage)
            {
                for(int j=0;j<route[i].length();j++) {
                    if(route[i].charAt(j)=='0') finalRoute=finalRoute.concat("->Mumbai");
                    if(route[i].charAt(j)=='1') finalRoute=finalRoute.concat("->Delhi");
                    if(route[i].charAt(j)=='2') finalRoute=finalRoute.concat("->Chennai");
                    if(route[i].charAt(j)=='3') finalRoute=finalRoute.concat("->Kolkata");
                    if(route[i].charAt(j)=='4') finalRoute=finalRoute.concat("->Jaipur");

                }
                System.out.print("\nOptimized Path:"+finalRoute+"\tTime:"+time[i]+"\n(Obtained after optimizing the path based on time taken and damage to the engine)");
                flag=true;

                //Below function for printing output instructions
                System.out.println("\n\nFollow below instructions to reach destination:");
                PrintingInstructions(route[i]);
            }
            if(flag)
                break;

        }
        if(flag==false)
            System.out.println("No Path is possible");



    }

    static ArrayList<ArrayList<Integer>> CreateRoutes(int n)
    {
        ArrayList<ArrayList<Integer>> adjLists=new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjLists.add(new ArrayList<Integer>());
        }

        adjLists.get(0).add(1);
        adjLists.get(0).add(4);
        adjLists.get(1).add(0);
        adjLists.get(1).add(2);
        adjLists.get(1).add(4);
        adjLists.get(2).add(1);
        adjLists.get(2).add(3);
        adjLists.get(3).add(2);
        adjLists.get(3).add(4);
        adjLists.get(4).add(0);
        adjLists.get(4).add(1);
        adjLists.get(4).add(3);

        roadParameters.add(new RoadParameters("01",800,"low","many","1","dry","good"));
        roadParameters.add(new RoadParameters("04",2000,"low","none","1","wet","good"));
        roadParameters.add(new RoadParameters("12",1200,"low","many","1","dry","good"));
        roadParameters.add(new RoadParameters("14",800,"low","some","1","wet","good"));
        roadParameters.add(new RoadParameters("23",400,"low","some","1","dry","good"));
        roadParameters.add(new RoadParameters("34",1200,"low","none","1","dry","good"));

        roadParameters.add(new RoadParameters("10",800,"low","many","1","dry","good"));
        roadParameters.add(new RoadParameters("40",2000,"low","none","1","wet","good"));
        roadParameters.add(new RoadParameters("21",1200,"low","many","1","dry","good"));
        roadParameters.add(new RoadParameters("41",800,"low","some","1","wet","good"));
        roadParameters.add(new RoadParameters("32",400,"low","some","1","dry","good"));
        roadParameters.add(new RoadParameters("43",1200,"low","none","1","dry","good"));

        return adjLists;
    }

    static ArrayList<PredictionforEachRoute> fetchPredictionOfEachRoute(double maxspeed)
    {
        ArrayList<PredictionforEachRoute> predictionforEachRoutes=new ArrayList<>();

        for (int routeindex=0;routeindex<routeAndDistances.size();routeindex++)
        {
            String route=routeAndDistances.get(routeindex).route;
            double timeforroute=0;
            double totaldamagewithweight=0;

            for (int index=0;index<=route.length()-2;index++)
            {
                String path=route.substring(index,index+2);
                RoadParameters currentParameters = null;

                for (int roadparameterindex=0;roadparameterindex<roadParameters.size();roadparameterindex++)
                {
                    if (path.equals(roadParameters.get(roadparameterindex).path))
                    {
                        currentParameters=roadParameters.get(roadparameterindex);
                        break;
                    }
                }

                int distance=currentParameters.distance;
                double timeforpath=distance/(double)maxspeed;

                String testinginstanceString=GetTestingInstanceStringForTime(currentParameters,0);
                double multiplicationfactor = GetMultiplicationFactorByTesting(testinginstanceString,0);

                timeforpath=timeforpath*multiplicationfactor;
                timeforroute=timeforroute+timeforpath;

                String testinginstanceString2=GetTestingInstanceStringForTime(currentParameters,1);
                double damagefactor = GetMultiplicationFactorByTesting(testinginstanceString2,1);
                totaldamagewithweight=totaldamagewithweight+damagefactor*distance;
            }
            double adjusteddamagefactor = totaldamagewithweight/(double)routeAndDistances.get(routeindex).distance;
            predictionforEachRoutes.add(new PredictionforEachRoute(String.valueOf(timeforroute),String.valueOf(adjusteddamagefactor)));

        }
        return predictionforEachRoutes;
    }

    static String GetTestingInstanceStringForTime(RoadParameters parameters,int condition)
    {
        String s="";
        String t="";

        if (condition==0) {
            s="@relation CarAutomationDamage\n" +
                    "\n" +
                    "@attribute traffic {high,medium,low}\n" +
                    "@attribute potholes {many,some,none}\n" +
                    "@attribute signals {1,2,3}\n" +
                    "@attribute weather {wet,dry}\n" +
                    "@attribute tyregrip {good,medium,bad}\n" +
                    "@attribute multiplicationfactor numeric\n" +
                    "\n" +
                    "@data\n";
            t = parameters.traffic + "," + parameters.potholes + "," + parameters.signals + ","
                    + parameters.weather + "," + parameters.tyregrip + ",?";
        } else {
            s="@relation CarAutomationDamage\n" +
                    "\n" +
                    "@attribute potholes {many,some,none}\n" +
                    "@attribute weather {wet,dry}\n"  +
                    "@attribute damage numeric\n" +
                    "\n"+
                    "@data\n";
            t = parameters.potholes + "," +parameters.weather +",?";
        }
        s=s.concat(t);
        return s;
    }

    static double GetMultiplicationFactorByTesting(String testing,int condition)
    {
        BufferedReader reader=null;

        try {

            if(condition==0) {
                reader = new BufferedReader(new FileReader("timetrainingdata.arff"));
            } else {
                reader = new BufferedReader(new FileReader("damagetrainingdata.arff"));
            }
            Instances trainingdata = new Instances(reader);
            reader.close();

            reader = new BufferedReader(new StringReader(testing));
            Instances testingdata = new Instances(reader);
            reader.close();

            trainingdata.setClassIndex(trainingdata.numAttributes() - 1);
            testingdata.setClassIndex(testingdata.numAttributes()-1);

            Classifier classifier = new MultilayerPerceptron();
            classifier.buildClassifier(trainingdata);

            Evaluation evaluation=new Evaluation(trainingdata);
            evaluation.evaluateModel(classifier,testingdata);

            ArrayList<Prediction> predictions=evaluation.predictions();

            for(Prediction p:predictions)
            {
                double factor= (double) p.predicted();
                return factor;
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        return 1;
    }

    static void PrintingInstructions(String route)
    {
        String allinstruction="";
        for (int i=0;i<=route.length()-2;i++)
        {
            String smallroute=route.substring(i,i+2);

            RoadParameters currentParameters = null;

            for (int roadparameterindex=0;roadparameterindex<roadParameters.size();roadparameterindex++)
            {
                if (smallroute.equals(roadParameters.get(roadparameterindex).path))
                {
                    currentParameters=roadParameters.get(roadparameterindex);
                    break;
                }
            }
            int checker=0;
            String smallRouteString="";
            for(int j=0;j<smallroute.length();j++) {
                if(smallroute.charAt(j)=='0') smallRouteString=smallRouteString.concat("->mumbai");
                if(smallroute.charAt(j)=='1') smallRouteString= smallRouteString.concat("->delhi");
                if(smallroute.charAt(j)=='2')  smallRouteString=smallRouteString.concat("->chennai");
                if(smallroute.charAt(j)=='3') smallRouteString= smallRouteString.concat("->kolkata");
                if(smallroute.charAt(j)=='4') smallRouteString= smallRouteString.concat("->jaipur");;

            }
            String instruction="Subroute "+smallRouteString+"\n";

            if (currentParameters.potholes.equals("many") || currentParameters.potholes.equals("some"))
            {
                instruction=instruction.concat("\tMight contain potholes. Drive slowly with caution\n");
                checker=1;
            }

            if (currentParameters.traffic.equals("high") || currentParameters.traffic.equals("medium"))
            {
                instruction=instruction.concat("\tIs High Traffic zone. Be calm and drive slowly\n");
                checker=1;
            }

            if (currentParameters.weather.equals("wet"))
            {
                instruction=instruction.concat("\tThere's chance of rain. Keep headlamps on and take caution\n");
                checker=1;
            }

            if (checker==1)
            {
                allinstruction=allinstruction.concat(instruction);
            }else{
                allinstruction=allinstruction.concat(instruction+"\tIdeal driving conditions. You can speed up on this road\n");
            }
        }
        System.out.println(allinstruction);
    }
}

class RouteAndDistance
{
    String route;
    int distance;

    RouteAndDistance(String route,int distance)
    {
        this.route=route;
        this.distance=distance;
    }

}

class RoadParameters
{
    String path,traffic,potholes,signals,weather,tyregrip;
    int distance=0;
    RoadParameters(String path,int distance,String traffic,String potholes,String signals,String weather,String tyregrip)
    {
        this.path=path;
        this.distance=distance;
        this.traffic=traffic;
        this.potholes=potholes;
        this.signals=signals;
        this.weather=weather;
        this.tyregrip=tyregrip;
    }
}

class PredictionforEachRoute
{
    String time,damage;

    PredictionforEachRoute(String time,String damage)
    {
        this.time=time;
        this.damage=damage;
    }
}
