package Client;

import java.io.IOException;


public class Application {


    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 8082);

        System.out.println("1. Get citizen types for 1st city (Kyiv) and Russian language");
        System.out.println(client.findCitizenTypeByCityIdAndLanguage(1L, "Russian"));

        System.out.println("2. Get cities, which have ukrainets citizen type");
        System.out.println(client.findCityByCitizenTypeName("ukrainets"));

        System.out.println("3. Get city, where total population = 1500000");
        System.out.println(client.findByTotalPopulationDetailed(1500000L));

        System.out.println("4. Get oldest citizen type");
        System.out.println(client.findCitizenTypeOldest());
    }
}
