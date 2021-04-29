<%@ page import="com.example.Servlet_OOP.ConnectionPool" %>
<%@ page import="com.example.Servlet_OOP.Database" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.Servlet_OOP.Polygon" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html style="height: 100vh">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title> Polygons </title>
</head>

    <body style="background-color: whitesmoke; height: 100vh">
        <div class="w3-center w3-content" style="margin-top: 30vh;" >
            <div><h2>Page for polygon Table presentation:</h2><br></div>
            <%
                ConnectionPool connectionPool = new ConnectionPool();
                Database database = new Database(connectionPool);
                ArrayList<Polygon> polygons = database.getInformation();

                String createdTable = "";

                for (Polygon polygon : polygons) {
                    createdTable += "<div class='w3-round w3-card-4 w3-margin w3-third' style='height: 250px; width: 30%'><table class='w3-table-all' width=\"100%\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\">\n";
                    createdTable += "<caption>Polygon: " + polygon.getName() + "</caption>\n";
                    createdTable += "<tr>\n<th>Vertex</th><th>Angle</th>\n</tr>\n";
                    for (int i = 0; i < polygon.getVertices().size(); i++) {
                        createdTable += "<tr>\n<td>" + polygon.getVertices().get(i).getName() + "</td><td>" + polygon.getVertices().get(i).getAngle() + "</td></tr>\n";
                    }
                    createdTable += "</table></div>\n";
                }

                System.out.println(createdTable);
            %>
            <%=createdTable%>
        </div>
    </body>
</html>