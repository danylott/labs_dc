import DTO.VertexDTO;
import DTO.PolygonDTO;

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
    public PolygonDTO polygonFindById(Long id) throws RemoteException {
        return PolygonDAO.findById(id);
    }

    @Override
    public VertexDTO vertexFindByName(String name) throws RemoteException {
        return VertexDAO.findByName(name);
    }

    @Override
    public PolygonDTO polygonFindByName(String name) throws RemoteException {
        return PolygonDAO.findByName(name);
    }

    @Override
    public boolean vertexUpdate(VertexDTO vertex) throws RemoteException {
        return VertexDAO.update(vertex);
    }

    @Override
    public boolean polygonUpdate(PolygonDTO polygon) throws RemoteException {
        return PolygonDAO.update(polygon);
    }

    @Override
    public boolean vertexInsert(VertexDTO vertex) throws RemoteException {
        return VertexDAO.insert(vertex);
    }

    @Override
    public boolean polygonInsert(PolygonDTO polygon) throws RemoteException {
        return PolygonDAO.insert(polygon);
    }

    @Override
    public boolean polygonDelete(PolygonDTO polygon) throws RemoteException {
        return PolygonDAO.delete(polygon);
    }

    @Override
    public boolean vertexDelete(VertexDTO vertex) throws RemoteException {
        return VertexDAO.delete(vertex);
    }

    @Override
    public List<PolygonDTO> polygonAll() throws RemoteException {
        return PolygonDAO.findAll();
    }

    @Override
    public List<VertexDTO> vertexAll() throws RemoteException {
        return VertexDAO.findAll();
    }

    @Override
    public List<VertexDTO> vertexFindByPolygonId(Long polygonId) throws RemoteException {
        return VertexDAO.findByPolygonId(polygonId);
    }

    @Override
    public List<VertexDTO> vertexFindByPolygonId(long id) throws RemoteException {
        return VertexDAO.findByPolygonId(id);
    }

    public static void main(String[] args) throws RemoteException {
        Backend backend = new Backend();
        Registry r = LocateRegistry.createRegistry(8085);
        r.rebind("graphics", backend);
    }
}
