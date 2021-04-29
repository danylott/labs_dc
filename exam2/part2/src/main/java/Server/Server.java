package Server;

import DTO.CitizenTypeDTO;
import DTO.CityDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
                CitizenTypeDTO country;
                List<CitizenTypeDTO> citizenTypes;
                List<CityDTO> cities;
                CitizenTypeDTO city;
                StringBuilder str;

                switch (action) {
                    case "findCityByCitizenTypeName":
                        String citizenTypeName = fields[1];
                        cities = CityDAO.findByCitizenTypeName(citizenTypeName);
                        str = new StringBuilder();
                        assert cities != null;
                        for (CityDTO cit : cities) {
                            str.append(cit.getId());
                            str.append(split);
                            str.append(cit.getName());
                            str.append(split);
                            str.append(cit.getFoundationYear());
                            str.append(split);
                            str.append(cit.getArea());
                            str.append(split);
                        }
                        response = str.toString();
                        out.println(response);
                        break;
                    case "findCitizenTypeByCityIdAndLanguage":
                        long cityId = Long.parseLong(fields[1]);
                        String language = fields[2];
                        citizenTypes = CitizenTypeDAO.findByCityIdAndLanguage(cityId, language);
                        str = new StringBuilder();
                        assert citizenTypes != null;
                        for (CitizenTypeDTO citizenType : citizenTypes) {
                            str.append(citizenType.getId());
                            str.append(split);
                            str.append(citizenType.getCityId());
                            str.append(split);
                            str.append(citizenType.getName());
                            str.append(split);
                            str.append(citizenType.getLanguage());
                            str.append(split);
                            str.append(citizenType.getPopulation());
                            str.append(split);
                        }
                        response = str.toString();
                        out.println(response);
                        break;

                    case "findCitizenTypeOldest":
                        citizenTypes = new ArrayList<>();
                        citizenTypes.add(CitizenTypeDAO.findOldest());
                        str = new StringBuilder();
                        for (CitizenTypeDTO citizenType : citizenTypes) {
                            str.append(citizenType.getId());
                            str.append(split);
                            str.append(citizenType.getCityId());
                            str.append(split);
                            str.append(citizenType.getName());
                            str.append(split);
                            str.append(citizenType.getLanguage());
                            str.append(split);
                            str.append(citizenType.getPopulation());
                            str.append(split);
                        }
                        response = str.toString();
                        out.println(response);
                        break;

                    case "findByTotalPopulationDetailed":
                        long population = Long.parseLong(fields[1]);
                        cities = new ArrayList<>();
                        cities.add(CityDAO.findByTotalPopulationDetailed(population));
                        str = new StringBuilder();
                        for (CityDTO cit : cities) {
                            str.append(cit.getId());
                            str.append(split);
                            str.append(cit.getName());
                            str.append(split);
                            str.append(cit.getFoundationYear());
                            str.append(split);
                            str.append(cit.getArea());
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

    private void citiesToStr(StringBuilder str, List<CitizenTypeDTO> citiesList) {
        for (CitizenTypeDTO currCity : citiesList) {
            str.append(currCity.getId());
            str.append(split);
            str.append(currCity.getCityId());
            str.append(split);
            str.append(currCity.getName());
            str.append(split);
            str.append(currCity.getPopulation());
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
