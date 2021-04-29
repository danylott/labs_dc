import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Application {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String url = "//localhost:8085/map";
        IBackend backend = (IBackend) Naming.lookup(url);

        System.out.println("1. Get citizen types for 1st city (Kyiv) and Russian language");
        System.out.println(backend.findByCityIdAndLanguage(1, "Russian"));

        System.out.println("2. Get cities, which have ukrainets citizen type");
        System.out.println(backend.findByCitizenTypeName("ukrainets"));

        System.out.println("3. Get city and all citizen types for it, where total population = 1500000");
        System.out.println(backend.findByTotalPopulationDetailed(1500000));

        System.out.println("4. Get oldest citizen type");
        System.out.println(backend.findOldest());
    }
}
