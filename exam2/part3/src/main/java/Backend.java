import lombok.var;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Backend extends UnicastRemoteObject implements IBackend {

    protected Backend() throws RemoteException {
        super();
    }

    @Override
    public List<CitizenTypeDTO> findByCityIdAndLanguage(long cityId, String language) throws RemoteException {
        return CitizenTypeDAO.findByCityIdAndLanguage(cityId, language);
    }

    @Override
    public List<CityDTO> findByCitizenTypeName(String citizenTypeName) throws RemoteException {
        return CityDAO.findByCitizenTypeName(citizenTypeName);
    }

    @Override
    public CityDTO findByTotalPopulationDetailed(long totalPopulation) throws RemoteException {
        return CityDAO.findByTotalPopulationDetailed(totalPopulation);
    }

    @Override
    public CitizenTypeDTO findOldest() throws RemoteException {
        return CitizenTypeDAO.findOldest();
    }

    public static void main(String[] args) throws RemoteException {
        Backend backend = new Backend();
        Registry r = LocateRegistry.createRegistry(8085);
        r.rebind("map", backend);
    }
}
