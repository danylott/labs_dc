package com.company;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.net.URL;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class checkXML {
    public static void main(String[] args) {
        File schemaFile = new File("/home/danylott/university/course3/RO/module2/lab1/database.xsd");
        Source xmlFile = new StreamSource(new File("/home/danylott/university/course3/RO/module2/lab1/database.xml"));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        }
        assert schema != null;
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
        } catch (SAXException | IOException saxException) {
            saxException.printStackTrace();
        }
        System.out.println(xmlFile.getSystemId() + " is valid");
    }

}
