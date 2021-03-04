public class Main {
    public static void main(String[] args) {
        Garden garden = new Garden();
        new GardenKeeper(garden);
        new Nature(garden);
        new Monitor1(garden);
        new Monitor2(garden);
    }
}
