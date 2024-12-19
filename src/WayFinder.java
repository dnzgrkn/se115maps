import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class WayFinder {
    private CountryMap country;
    private City startCity;
    private City endCity;

    public WayFinder(City from, City to, CountryMap country, List<Routes> routes) {
        this.startCity = from;
        this.endCity = to;
        this.country = country;


        List<List<City>> allPaths = new ArrayList<>();                                // storing all valid paths    // storing total times for each path
        List<Integer> totalTimes = new ArrayList<>();

        for (Routes route : routes) {                                                 // for reverse route here i check if there is a direct route or not
            if (route.displayCity2().equalsIgnoreCase(startCity.getName()) && route.displayCity1().equalsIgnoreCase(endCity.getName())) {
                List<City> directPath = new ArrayList<>();
                directPath.add(startCity);
                directPath.add(endCity);
                allPaths.add(directPath);
                totalTimes.add(route.getTime());
            }
        }


        List<Routes> startingRoutes = new ArrayList<>();                              // finding all routes starting with startCity
        for (Routes route : routes) {
            if (route.displayCity1().equalsIgnoreCase(startCity.getName())) {
                startingRoutes.add(route);
            }
        }

        // Move on from each starting route
        for (Routes route : startingRoutes) {
            List<City> currentPath = new ArrayList<>();
            int currentTime = route.getTime();                                      // Initialize the total time with the time of the starting route
            currentPath.add(startCity);                                             // starting with the startCity
            findRoutes(route, routes, currentPath, allPaths, currentTime, totalTimes, true);
        }
        if (allPaths.isEmpty()) {
            System.out.println("No possible routes found from " + startCity.getName() + " to " + endCity.getName() + ".");
            return;
        }

        int fastest = totalTimes.get(0);                                                // here we calculate the fastest route
        int fastestindex = 0;
        for (int i = 0; i < totalTimes.size(); i++) {
            if (totalTimes.get(i) < fastest) {
                fastest = totalTimes.get(i);
                fastestindex = i;
            }
        }


        System.out.println("All possible paths from " + startCity.getName() + " to " + endCity.getName() + ":");
        if (allPaths.isEmpty()) {
            System.out.println("No paths found.");
        } else {
            for (int i = 0; i < allPaths.size(); i++) {
                List<City> path = allPaths.get(i);
                int totalTime = totalTimes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    System.out.print(path.get(j).getName());
                    if (j < path.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println(" | Total Time: " + (totalTime));
            }
        }


        List<City> fastestRoute = allPaths.get(fastestindex);                   // here we display the fastestroutes cities by index of fastesttime
        System.out.print("Fastest route: ");
        for (int i = 0; i < fastestRoute.size(); i++) {
            System.out.print(fastestRoute.get(i).getName());
            if (i < fastestRoute.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println(" | Total Time: " + fastest);
        writeToFile(fastestRoute, fastest, "outputtext.txt");

    }


    private void findRoutes(Routes currentRoute, List<Routes> allRoutes, List<City> currentPath,
                            List<List<City>> allPaths, int currentTime, List<Integer> totalTimes, boolean isFirstRoute) {

        City nextCity = currentRoute.getCity2();


        if (!isFirstRoute) {                                                    // here i encountered a strange problem in my code the first route between 2 cities in route was adding twice so i take care of it like this it may be a bit strange but it works
            currentTime += currentRoute.getTime();
        }

                                                                                // adding the next city to the current path
        currentPath.add(nextCity);

        // If we've reached the end city, store the path and total time
        if (nextCity.getName().equalsIgnoreCase(endCity.getName())) {
            allPaths.add(new ArrayList<>(currentPath));
            totalTimes.add(currentTime);
            return;
        }

        for (Routes route : allRoutes) {
                                                                                            // We will now check both directions (A -> B and B -> A)
            if (route.displayCity1().equalsIgnoreCase(nextCity.getName()) && !currentPath.contains(route.getCity2())) {
                findRoutes(route, allRoutes, new ArrayList<>(currentPath), allPaths, currentTime, totalTimes, false);
            }

                                                                                          // adding the reverse route if it exists (B -> A if A -> B exists)
            if (route.displayCity2().equalsIgnoreCase(nextCity.getName()) && !currentPath.contains(route.getCity1())) {
                Routes reverseRoute = new Routes(route.getCity2(), route.getCity1(), route.getTime());
                findRoutes(reverseRoute, allRoutes, new ArrayList<>(currentPath), allPaths, currentTime, totalTimes, false);
            }
        }
    }

     private void writeToFile(List<City> fastestRoute, int totalTime, String fileName) {
        FileWriter fileWriter = null;
        Formatter formatter = null;

        try {

            fileWriter = new FileWriter(fileName);
            formatter = new Formatter(fileWriter);


            formatter.format("Fastest Way: ");
            for (int i = 0; i < fastestRoute.size(); i++) {
                formatter.format("%s", fastestRoute.get(i).getName());
                if (i < fastestRoute.size() - 1) {
                    formatter.format(" -> ");
                }
            }
            formatter.format("\nTotal Time: %d min\n", totalTime);

            System.out.println("Data has been successfully written to " + fileName);
        } catch (IOException e) {
            System.err.println("Something went wrong while writing to the file.");
            e.printStackTrace();
        } finally {
            if (formatter != null) {
                formatter.close();
            }
        }
    }
}







// HERE THIS WAS MY FIRST TRY OF IT

/* import java.util.ArrayList;
import java.util.List;

public class WayFinder {
    private CountryMap country;
    private City startCity;
    private City endCity;

    public WayFinder(City from,City to , CountryMap country,List<Routes>routes) {
        City startcity =from;
        City endcity = to;
        CountryMap map = country;

        System.out.println(map.displayCountryRoutesasString(map));
        System.out.println(startcity.getName() +" far away from " + endcity.getName());

        List<Routes> possibleroutes = new ArrayList<>();
        List<Routes> possiblestarts = new ArrayList<>();
        City starting = new City();
        City ending = new City();

        for (int i = 0 ; i<routes.size();i++){
            if (routes.get(i).displayCity1().equalsIgnoreCase(startcity.getName()) && routes.get(i).displayCity2().equalsIgnoreCase(endcity.getName())){
                System.out.println("SHORTEST ROUTE IS: " + startcity.getName()+ " to " + endcity.getName());
                possibleroutes.add(routes.get(i));
            }
        }
        for (int i = 0; i<routes.size();i++){
            if (routes.get(i).displayCity1().equalsIgnoreCase(startcity.getName())){
                possiblestarts.add(routes.get(i));
            }
        }
        while (true){
            for (int i = 0; i< possiblestarts.size();i++){
                starting=possiblestarts.get(i).getCity1();
                ending=possiblestarts.get(i).getCity2();

            }


        }


    }
}

 */