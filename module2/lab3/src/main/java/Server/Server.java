package Server;

import DTO.VertexDTO;
import DTO.PolygonDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static final String split = "#";

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    private boolean processQuery() {
        String response;
        try {
            String query = in.readLine();
            if (query == null) {
                return false;
            }

            String[] fields = query.split(split);
            if (fields.length == 0) {
                return true;
            } else {
                String action = fields[0];
                PolygonDTO polygon;
                VertexDTO vertex;

                switch (action) {
                    case "PolygonFindById":
                        long id = Long.parseLong(fields[1]);
                        polygon = PolygonDAO.findById(id);
                        response = polygon.getName();
                        out.println(response);
                        break;
                    case "VertexFindByPolygonId":
                        id = Long.parseLong(fields[1]);
                        List<VertexDTO> list = VertexDAO.findByPolygonId(id);
                        StringBuilder str = new StringBuilder();
                        vertexesToStr(str, list);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "VertexFindByName":
                        String name = fields[1];
                        vertex = VertexDAO.findByName(name);
                        assert vertex != null;
                        response = vertex.getId() + split + vertex.getPolygonName() + split + vertex.getName() + split + vertex.getAngle();
                        out.println(response);
                        break;
                    case "PolygonFindByName":
                        name = fields[1];
                        polygon = PolygonDAO.findByName(name);
                        assert polygon != null;
                        response = polygon.getId() + "";
                        out.println(response);
                        break;
                    case "VertexUpdate":
                        id = Long.parseLong(fields[1]);
                        long polygonId = Long.parseLong(fields[2]);
                        name = fields[3];
                        long angle = Long.parseLong(fields[4]);
                        vertex = new VertexDTO(id, polygonId, name, angle);
                        if (VertexDAO.update(vertex))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "PolygonUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        polygon = new PolygonDTO(id, name);
                        if (PolygonDAO.update(polygon)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "VertexInsert":
                        polygonId = Long.parseLong(fields[1]);
                        name = fields[2];
                        angle = Long.parseLong(fields[3]);
                        vertex = new VertexDTO(0, polygonId, name, angle);
                        if (VertexDAO.insert(vertex)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "PolygonInsert":
                        name = fields[1];
                        polygon = new PolygonDTO();
                        polygon.setName(name);

                        System.out.println(name);

                        if (PolygonDAO.insert(polygon)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "VertexDelete":
                        id = Long.parseLong(fields[1]);
                        vertex = new VertexDTO();
                        vertex.setId(id);
                        if (VertexDAO.delete(vertex)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "PolygonDelete":
                        id = Long.parseLong(fields[1]);
                        polygon = new PolygonDTO();
                        polygon.setId(id);
                        if (PolygonDAO.delete(polygon)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "VertexAll":
                        List<VertexDTO> vertexesList = VertexDAO.findAll();
                        str = new StringBuilder();
                        assert vertexesList != null;
                        vertexesToStr(str, vertexesList);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "PolygonAll":
                        List<PolygonDTO> polygonsList = PolygonDAO.findAll();
                        str = new StringBuilder();
                        for (PolygonDTO currPolygon : polygonsList) {
                            str.append(currPolygon.getId());
                            str.append(split);
                            str.append(currPolygon.getName());
                            str.append(split);
                        }
                        response = str.toString();
                        out.println(response);
                        break;
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void vertexesToStr(StringBuilder str, List<VertexDTO> vertexesList) {
        for (VertexDTO currVertex : vertexesList) {
            str.append(currVertex.getId());
            str.append(split);
            str.append(currVertex.getPolygonName());
            str.append(split);
            str.append(currVertex.getName());
            str.append(split);
            str.append(currVertex.getAngle());
            str.append(split);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
