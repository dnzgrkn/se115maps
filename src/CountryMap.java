import java.util.ArrayList;
import java.util.List;

public class CountryMap {
   private List<City>cities;
   private List<Routes>routes;

   public CountryMap() {
      routes = new ArrayList<>();
      cities = new ArrayList<>();

   }

   public  void  addCity(City city) {
      cities.add(city);
   }
   public void addRoute(Routes route) {
      routes.add(route);
   }

   public void displayCities(CountryMap map){
      System.out.print("The cities we can work on are: " + "\n");
      for (int i = 0;i<map.cities.size();i++){
         System.out.print((i+1)+"- " +map.cities.get(i).getName() + "\n");

      }

   }

   public void displayCitynum(CountryMap map) {
      System.out.print("In this map number of cities: ");
      System.out.println(map.cities.size());

   }
   public String displayCountryRoutesasString(CountryMap map) {
      StringBuilder sb = new StringBuilder();
      sb.append("In this map, the number of routes is: ").append(routes.size()).append("\n");

      for (int i = 0; i < routes.size(); i++) {
         sb.append((i + 1)).append("- ").append(routes.get(i).displayRoute()).append("\n");    // in this part I looked for internet to find a way to convert to a string and with stringbuilder and appendmethod it is done
      }

      return sb.toString(); // Return the routes as a String
   }

   public void displayCountryRoutes(CountryMap map) {
      System.out.println("In this map number of routes is: " + routes.size());

      for (int i = 0;i<map.routes.size();i++){
         System.out.print((i+1)+"- "  +map.routes.get(i).displayRoute() + "\n");

      }
   }

}