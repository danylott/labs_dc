import DTO.VertexDTO;
import DTO.PolygonDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IBackend extends Remote {
    public PolygonDTO polygonFindById(Long id) throws RemoteException;

    public VertexDTO vertexFindByName(String name) throws RemoteException;

    public PolygonDTO polygonFindByName(String name) throws RemoteException;

    public boolean vertexUpdate(VertexDTO vertex) throws RemoteException;

    public boolean polygonUpdate(PolygonDTO polygon) throws RemoteException;

    public boolean vertexInsert(VertexDTO vertex) throws RemoteException;

    public boolean polygonInsert(PolygonDTO polygon) throws RemoteException;

    public boolean polygonDelete(PolygonDTO polygon) throws RemoteException;

    public boolean vertexDelete(VertexDTO vertex) throws RemoteException;

    public List<PolygonDTO> polygonAll() throws RemoteException;

    public List<VertexDTO> vertexAll() throws RemoteException;

    public List<VertexDTO> vertexFindByPolygonId(Long idc) throws RemoteException;

    List<VertexDTO> vertexFindByPolygonId(long id) throws RemoteException;
}
