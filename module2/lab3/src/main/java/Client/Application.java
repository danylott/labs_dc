package Client;

import DTO.VertexDTO;
import DTO.PolygonDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.util.List;

public class Application extends JFrame {
    private static JFrame frame;

    private static Client client;

    private static PolygonDTO currentPolygon = null;
    private static VertexDTO currentVertex = null;

    private static boolean editMode = false;
    private static boolean polygonMode = true;

    private static JButton btnAddPolygon = new JButton("Add Polygon");
    private static JButton btnAddVertex = new JButton("Add Vertex");
    private static JButton btnEdit = new JButton("Edit Data");
    private static JButton btnBack = new JButton("Back");
    private static JButton btnSave = new JButton("Save");
    private static JButton btnDelete = new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box vertexPanel = Box.createVerticalBox();
    private static Box polygonPanel = Box.createVerticalBox();

    private static JComboBox comboPolygon = new JComboBox();
    private static JComboBox comboVertex = new JComboBox();

    private static JTextField textPolygonName = new JTextField(30);
    private static JTextField textVertexName = new JTextField(30);
    private static JTextField textVertexPolygonName = new JTextField(30);
    private static JTextField textVertexAngle = new JTextField(30);

    private Application() {
        super("Graphics");
        frame = this;
        frame.setPreferredSize(new Dimension(400, 500));
        frame.setMaximumSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                client.disconnect();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();
        sizeAllElements();
        frame.setLayout(new FlowLayout());

        // Menu
        menuPanel.add(btnAddPolygon);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddPolygon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                polygonMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                polygonPanel.setVisible(true);
                vertexPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddVertex);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddVertex.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                polygonMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                polygonPanel.setVisible(false);
                vertexPanel.setVisible(true);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        menuPanel.add(Box.createVerticalStrut(20));
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                polygonPanel.setVisible(false);
                vertexPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Polygon:"));
        comboPanel.add(comboPolygon);
        comboPanel.add(Box.createVerticalStrut(20));
        comboPolygon.addActionListener(e -> {
            currentPolygon = client.polygonFindByName((String) comboPolygon.getSelectedItem());
            polygonMode = true;
            polygonPanel.setVisible(true);
            vertexPanel.setVisible(false);
            fillPolygonFields();
            pack();
        });
        comboPanel.add(new JLabel("Vertex:"));
        comboPanel.add(comboVertex);
        comboPanel.add(Box.createVerticalStrut(20));
        comboVertex.addActionListener(e -> {
            currentVertex = client.vertexFindByName((String) comboVertex.getSelectedItem());
            polygonMode = false;
            polygonPanel.setVisible(false);
            vertexPanel.setVisible(true);
            fillVertexFields();
            pack();
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        // Vertex Fields
        vertexPanel.add(new JLabel("Name:"));
        vertexPanel.add(textVertexName);
        vertexPanel.add(Box.createVerticalStrut(20));
        vertexPanel.add(new JLabel("Polygon Name:"));
        vertexPanel.add(textVertexPolygonName);
        vertexPanel.add(Box.createVerticalStrut(20));
        vertexPanel.add(new JLabel("Angle:"));
        vertexPanel.add(textVertexAngle);
        vertexPanel.add(Box.createVerticalStrut(20));
        vertexPanel.setVisible(false);

        // Polygon Fields
        polygonPanel.add(new JLabel("Name:"));
        polygonPanel.add(textPolygonName);
        polygonPanel.add(Box.createVerticalStrut(20));
        polygonPanel.setVisible(false);

        // Action Bar		
        actionPanel.add(btnSave);
        actionPanel.add(Box.createVerticalStrut(20));
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createVerticalStrut(20));
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                delete();
            }
        });
        actionPanel.add(btnBack);
        actionPanel.add(Box.createVerticalStrut(20));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                polygonPanel.setVisible(false);
                vertexPanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(polygonPanel);
        box.add(vertexPanel);
        box.add(actionPanel);
        setContentPane(box);
        //pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddPolygon.setMaximumSize(dimension);
        btnAddVertex.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddPolygon.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddVertex.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        vertexPanel.setPreferredSize(panelDimension);
        polygonPanel.setPreferredSize(panelDimension);

        comboPolygon.setPreferredSize(dimension);
        comboVertex.setPreferredSize(dimension);

        textVertexPolygonName.setPreferredSize(dimension);
        textVertexName.setPreferredSize(dimension);
        textVertexAngle.setPreferredSize(dimension);
        textPolygonName.setPreferredSize(dimension);
    }

    private static void save() {
        if (editMode) {
            if (polygonMode) {
                currentPolygon.setName(textPolygonName.getText());
                if (!client.polygonUpdate(currentPolygon)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            } else {
                currentVertex.setName(textVertexName.getText());
                currentVertex.setAngle(Long.parseLong(textVertexAngle.getText()));

                PolygonDTO polygon = client.polygonFindByName(textVertexPolygonName.getText());
                if (polygon == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such polygon!");
                    return;
                }
                currentVertex.setPolygonName(polygon.getId());

                if (!client.vertexUpdate(currentVertex)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            }
        } else {
            if (polygonMode) {
                PolygonDTO polygon = new PolygonDTO();
                polygon.setName(textPolygonName.getText());

                if (!client.polygonInsert(polygon)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }

                comboPolygon.addItem(polygon.getName());
            } else {
                VertexDTO vertex = new VertexDTO();
                vertex.setName(textVertexName.getText());
                vertex.setAngle(Long.parseLong(textVertexAngle.getText()));

                PolygonDTO polygon = client.polygonFindByName(textVertexPolygonName.getText());
                if (polygon == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such polygon!");
                    return;
                }
                vertex.setPolygonName(polygon.getId());

                if (!client.vertexInsert(vertex)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }

                comboVertex.addItem(vertex.getName());
            }
        }
    }

    private static void delete() {
        if (editMode) {
            if (polygonMode) {
                List<VertexDTO> list = client.vertexFindByPolygonId(currentPolygon.getId());
                assert list != null;
                for (VertexDTO vertex : list) {
                    comboVertex.removeItem(vertex.getName());
                    client.vertexDelete(vertex);
                }
                client.polygonDelete(currentPolygon);
                comboPolygon.removeItem(currentPolygon.getName());

            } else {
                client.vertexDelete(currentVertex);
                comboVertex.removeItem(currentVertex.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboPolygon.removeAllItems();
        comboVertex.removeAllItems();
        List<PolygonDTO> polygons = client.polygonAll();
        List<VertexDTO> vertexes = client.vertexAll();
        for (PolygonDTO polygon : polygons) {
            comboPolygon.addItem(polygon.getName());
        }
        for (VertexDTO vertex : vertexes) {
            comboVertex.addItem(vertex.getName());
        }
    }

    private static void clearFields() {
        textPolygonName.setText("");
        textVertexName.setText("");
        textVertexPolygonName.setText("");
        textVertexAngle.setText("");
        currentPolygon = null;
        currentVertex = null;
    }

    private static void fillPolygonFields() {
        if (currentPolygon == null) {
            return;
        }
        textPolygonName.setText(currentPolygon.getName());
    }

    private static void fillVertexFields() {
        if (currentVertex == null) {
            return;
        }
        PolygonDTO polygon = client.polygonFindById(currentVertex.getPolygonName());
        assert polygon != null;
        textVertexPolygonName.setText(polygon.getName());
        textVertexName.setText(currentVertex.getName());
        textVertexAngle.setText(String.valueOf(currentVertex.getAngle()));
    }

    public static void main(String[] args) throws IOException {
        client = new Client("localhost",8082);
        JFrame myWindow = new Application();
        myWindow.setVisible(true);
    }
}
