package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json);
    }

    private static List<Employee> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
        // Создается построитель документа.
//        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        // Создается дерево DOM документа из файла.
//        Document document = documentBuilder.parse(fileName);
//        // Получаем корневой элемент.
//        Node root = document.getDocumentElement();
//        // Просматриваем все подэлементы корневого.
//        NodeList elements = root.getChildNodes();
//        for (int i = 0; i < elements.getLength(); i++) {
//            Node element = elements.item(i);
//            // Если нода не текст, то это сотрудник - заходим внутрь.
//            if (element.getNodeType() != Node.TEXT_NODE) {
//                NodeList attrName = element.getChildNodes();
//                for(int j = 0; j < attrName.getLength(); j++) {
//                    Node attrValue = attrName.item(j);
//                    // Если нода не текст, то это один из параметров.
//                    if (attrValue.getNodeType() != Node.TEXT_NODE) {
//                        System.out.println(attrValue.getNodeName() + " : " + attrValue.getChildNodes().item(0).getTextContent());
//                    }
//                }
//                System.out.println("-------------------");
//            }
//        }

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(fileName);
        NodeList nodeList = document.getElementsByTagName("employee");
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                employees.add(new Employee(id, firstName, lastName, country, age));
            }
        }
        employees.forEach(System.out::println);
        return employees;
    }

    private static String listToJson(List<Employee> list) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(list);
        System.out.println(json);
        return json;
    }

    private static void writeString(String json) {
        try (FileWriter file = new FileWriter("data.json")) {
            file.write(json);
            file.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}