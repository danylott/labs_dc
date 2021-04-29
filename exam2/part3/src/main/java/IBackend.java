import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IBackend extends Remote {
    public List<CitizenTypeDTO> findByCityIdAndLanguage(long cityId, String language) throws RemoteException;

    public List<CityDTO> findByCitizenTypeName(String citizenTypeName) throws RemoteException;

    public CityDTO findByTotalPopulationDetailed(long totalPopulation) throws RemoteException;

    public CitizenTypeDTO findOldest()  throws RemoteException;
}
