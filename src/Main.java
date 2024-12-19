import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java YourClassName <file_name>");
            return;
        }
        String fileName = args[0];

        List<City> cities = new ArrayList<>();
        List<Routes> routes = new ArrayList<>();
        List <String> desiredroute = new ArrayList<>();
        String[] values = new String[2];
        CountryMap map = new CountryMap();


        Fileread(fileName,cities, routes,desiredroute);                        // input reading and assigning arraylists


        if (!desiredroute.isEmpty()) {                                  // this line make sures that if we have an error occured in prveous lines while we read the file we dont get an indexoutoufbounds error
            values = desiredroute.get(0).split(" ");
            String from = values[0];
            String to = values[1];

            City startingcity = new City();
            City endingcity = new City();

            for (City city : cities) {
                if (city.getName().equalsIgnoreCase(from)) {
                    startingcity = city;
                } else if (city.getName().equalsIgnoreCase(to)) {
                    endingcity = city;
                }
            }

            for (City city : cities) {
                map.addCity(city);
            }

            for (Routes route : routes) {
                map.addRoute(route);
            }

            System.out.println("               ");
            System.out.println("-----------");

            WayFinder wayFinder = new WayFinder(startingcity, endingcity, map, routes);
        } else {
            System.out.println("Error: No desired route specified in the file.");
        }
                                                                                                             // this part we assign values for cities which we will find the route
        String from = values[0];
        String to = values[1];

        City startingcity = new City();
        City endingcity = new City();

        for (int i = 0; i<cities.size();i++){
            if (cities.get(i).getName().equalsIgnoreCase(from)){
                startingcity=cities.get(i);
            } else if (cities.get(i).getName().equalsIgnoreCase(to)) {
                endingcity=cities.get(i);

            }
        }

        // here on top of this line we print directly the data from input text file
        System.out.println("               ");
        System.out.println("-----------");
        System.out.println("               ");
        // below this line we print possible routes and the fastest one as the result

        WayFinder wayFinder = new WayFinder(startingcity,endingcity,map,routes);

        // here is way you can check if our CountryMap class holds the map's structure or not
        /*System.out.println("********");
        map.displayCities(map);
        System.out.println("*********");
        map.displayCountryRoutes(map);
                                            */

    }

    public static void Fileread(String fileName,List<City> cities, List<Routes> routes, List<String> desired) {
        int numofcities = 0;
        int numofroutes = 0;
        String desiredroute = " ";
        Scanner filereader = null;
        Scanner userInput = new Scanner(System.in);


        int lineNumber = 0;                                                         // with this integer we keep track of linenumber to state the errors line
        boolean hasError = false;                                                   // by this boolean variable we check if there is an error or not

        try {
            filereader = new Scanner(Paths.get(fileName));
            lineNumber++;


            if (filereader.hasNextLine()) {
                try {
                    numofcities = Integer.parseInt(filereader.nextLine());
                    System.out.println("Number of cities: " + numofcities);
                } catch (NumberFormatException e) {
                    System.out.println("Error Line: " + lineNumber + " Invalid number of cities.");
                    hasError = true;
                    return;
                }
            } else {
                System.out.println("Error Line: " + lineNumber + " Missing number of cities.");
                hasError = true;
                return;
            }


            lineNumber++;
            if (filereader.hasNextLine()) {
                String[] cityNames = filereader.nextLine().split(" ");
                System.out.print("Cities: ");
                for (String name : cityNames) {
                    System.out.print(name + " ");
                    City city = new City();
                    city.setName(name);
                    cities.add(city);
                }
            } else {
                System.out.println("Error Line: " + lineNumber + " Missing city names.");
                hasError = true;
                return;
            }


            lineNumber++;
            if (filereader.hasNextLine()) {
                try {
                    numofroutes = Integer.parseInt(filereader.nextLine());
                    System.out.println("\nNumber of routes: " + numofroutes);
                } catch (NumberFormatException e) {
                    System.out.println("Error Line: " + lineNumber + " Invalid number of routes.");
                    hasError = true;
                    return;
                }
            } else {
                System.out.println("Error Line: " + lineNumber + " Missing number of routes.");
                hasError = true;
                return;
            }


            int routecount = 0;
            while (filereader.hasNextLine() && routecount < numofroutes) {
                lineNumber++;
                String[] routefind = filereader.nextLine().split(" ");
                if (routefind.length != 3) {
                    System.out.println("Error Line: " + lineNumber + " Invalid route format.");
                    hasError = true;
                    continue;
                }

                City city1 = findCityByName(routefind[0], cities);
                City city2 = findCityByName(routefind[1], cities);
                int time;
                try {
                    time = Integer.parseInt(routefind[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Error Line: " + lineNumber + " Invalid route time.");
                    hasError = true;
                    continue;
                }

                if (city1 != null && city2 != null) {
                    routes.add(new Routes(city1, city2, time));
                    routecount++;
                } else {
                    System.out.println("Error Line: " + lineNumber + " One of the cities in the route is not defined.");
                    hasError = true;
                }
            }

                                                                                            // display all valid routes
            for (Routes route : routes) {
                System.out.println("Routes: " + route.displayRoute());
            }

                                                                                            // Read the desired route
            if (filereader.hasNextLine()) {
                lineNumber++;
                desiredroute = filereader.nextLine();
                desired.add(desiredroute);
                System.out.println("Desired route is: " + desiredroute);
            }


            if (!hasError) {
                System.out.println("File read is successful!");
            }

        } catch (IOException e) {
            System.out.println("Could not find the file: " + fileName);
            e.printStackTrace();
        } finally {
            if (filereader != null) {
                filereader.close();
            }
        }
    }


    private static City findCityByName(String name, List<City> cities) {
        for (City city : cities) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }
}
