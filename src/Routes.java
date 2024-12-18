public class Routes {
    private City city1;
    private City city2;
    private int time;

    public Routes(City city1, City city2, int time) {
        this.city1 = city1;
        this.city2 = city2;
        this.time = time;
    }

    public String displayRoute() {
        return city1.getName() + " -> " + city2.getName() + " : " + time;
    }
    public String displayCity1(){
        return city1.getName();
    }
    public String displayCity2(){
        return city2.getName();
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    public int getTime() {
        return time;
    }
}

