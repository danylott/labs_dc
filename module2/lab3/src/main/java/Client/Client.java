package Client;

import DTO.VertexDTO;
import DTO.PolygonDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private static final String split = "#";

    Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public PolygonDTO polygonFindById(Long id) {
        String query = "PolygonFindById" + split + id.toString();
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return new PolygonDTO(id, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public VertexDTO vertexFindByName(String name) {
        String query = "VertexFindByName" + split + name;
        out.println(query);
        String response = "";
        try {
            response = in.readLine();
            String[] fields = response.split(split);
            long id = Long.parseLong(fields[0]);
            long polygonId = Long.parseLong(fields[1]);
            long angle = Long.parseLong(fields[3]);
            return new VertexDTO(id, polygonId, name, angle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PolygonDTO polygonFindByName(String name) {
        String query = "PolygonFindByName" + split + name;
        out.println(query);
        try {
            long response = Long.parseLong(in.readLine());
            return new PolygonDTO(response, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean vertexUpdate(VertexDTO vertex) {
        String query = "VertexUpdate" + split + vertex.getId() +
                split + vertex.getPolygonName() + split + vertex.getName()
                + split + vertex.getAngle();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean polygonUpdate(PolygonDTO polygon) {
        String query = "PolygonUpdate" + split + polygon.getId() +
                split + polygon.getName();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean vertexInsert(VertexDTO vertex) {
        String query = "VertexInsert" +
                split + vertex.getPolygonName() + split + vertex.getName()
                + split + vertex.getAngle();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean polygonInsert(PolygonDTO polygon) {
        String query = "PolygonInsert" +
                split + polygon.getName();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean polygonDelete(PolygonDTO polygon) {
        String query = "PolygonDelete" + split + polygon.getId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean vertexDelete(VertexDTO vertex) {
        String query = "VertexDelete" + split + vertex.getId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PolygonDTO> polygonAll() {
        String query = "PolygonAll";
        out.println(query);
        ArrayList<PolygonDTO> list = new ArrayList<PolygonDTO>();
        try {
            String response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 2) {
                long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                list.add(new PolygonDTO(id, name));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VertexDTO> vertexAll() {
        String query = "VertexAll";
        return getVertexDTOS(query);
    }

    public List<VertexDTO> vertexFindByPolygonId(Long polygonId) {
        String query = "VertexFindByPolygonId" + split + polygonId.toString();
        return getVertexDTOS(query);
    }

    private List<VertexDTO> getVertexDTOS(String query) {
        out.println(query);
        ArrayList<VertexDTO> list = new ArrayList<VertexDTO>();
        try {
            String response = in.readLine();
            String[] fields = response.split(split);
            for (int i = 0; i < fields.length; i += 4) {
                long id = Long.parseLong(fields[i]);
                long polygonid = Long.parseLong(fields[i + 1]);
                String name = fields[i + 2];
                long angle = Long.parseLong(fields[i + 3]);
                list.add(new VertexDTO(id, polygonid, name, angle));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
