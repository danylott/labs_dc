public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        City London = new City("London", 80);
        City Sofia = new City("Sofia", 45);
        City Kyiv = new City("Kyiv", 60);
        City Moscow = new City("Moscow", 170);
        City Madrid = new City("Madrid", 170);
        graph.addCityAuto(London);
        graph.addCityAuto(Sofia);
        graph.addCityAuto(Kyiv);
        graph.addCityAuto(Moscow);
        graph.addCityAuto(Madrid);
        graph.connectCitiesAuto(London, Sofia);
        graph.connectCitiesAuto(Kyiv, Moscow);

        new AddDeleteCity(graph);
        new AddDeleteRoad(graph);
        new ChangeCost(graph);
        new GetCost(graph, "Kyiv", "Moscow");
        new GetCost(graph, "London", "Sofia");
    }
}
