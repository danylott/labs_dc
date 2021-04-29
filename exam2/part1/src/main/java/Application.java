public class Application {
    public static void main(String[] args) {
        System.out.println("1. Get citizen types for 1st city (Kyiv) and Russian language");
        System.out.println(CitizenTypeDAO.findByCityIdAndLanguage(1, "Russian"));

        System.out.println("2. Get cities, which have ukrainets citizen type");
        System.out.println(CityDAO.findByCitizenTypeName("ukrainets"));

        System.out.println("3. Get city and all citizen types for it, where total population = 1500000");
        System.out.println(CityDAO.findByTotalPopulationDetailed(1500000));

        System.out.println("4. Get oldest citizen type");
        System.out.println(CitizenTypeDAO.findOldest());
    }
}
