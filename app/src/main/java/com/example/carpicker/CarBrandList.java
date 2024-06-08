package com.example.carpicker;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xmlpull.v1.XmlSerializer;

public class CarBrandList {

    ArrayList<CarBrand> cbList = new ArrayList<CarBrand>();

    public CarBrandList(AssetManager assets) {

        try {
            InputStream is = assets.open("records.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("carBrand");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList nameNode = ((Element) node).getElementsByTagName("name").item(0).getChildNodes();
                    String brand =  nameNode.item(0).getNodeValue();
                    NodeList modelsNode = ((Element) node).getElementsByTagName("models").item(0).getChildNodes();
                    String models =  modelsNode.item(0).getNodeValue();
                    cbList.add(new CarBrand(brand, models));
                }
            }

        } catch (Exception e) {e.printStackTrace();}
    }


    public String saveLog(String brand, String model, Date curTime, Context c){
        String filename ="output.log";
        try{
            FileOutputStream fos = c.openFileOutput(filename, Context.MODE_APPEND);
            String logEntry = new String (curTime.toString()+"|"+brand+"|"+model+"\n");
            fos.write(logEntry.getBytes(StandardCharsets.UTF_8));
            fos.close();
            return c.getFilesDir().getPath().toString()+"/"+filename;

        }catch (Exception e){
            return e.toString();
        }
    }

    public String saveXMLLog(String brand, String model, Date curTime, Context c){
        String filename = "output.xml";
        try {
            FileOutputStream fos = c.openFileOutput(filename, Context.MODE_APPEND);
            XmlSerializer s = Xml.newSerializer();
            s.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            s.setOutput(fos, "UTF-8");
            s.startTag("", "log-entry");

            s.startTag("", "Date");
            s.text(curTime.toString());
            s.endTag("", "Date");

            s.startTag("", "Brand");
            s.text(brand);
            s.endTag("", "Brand");

            s.startTag("", "Model");
            s.text(model);
            s.endTag("", "Model");
            s.endTag("", "log-entry");
            s.endDocument();
            s.flush();
            fos.close();

            return c.getFilesDir().getPath().toString()+"/"+filename;
        }catch (Exception e){
            return e.toString();
        }
    }



    public String getAllModelsAsString(String b) {
        String s = "";
        for (int i=0; i<cbList.size(); i++) {
            if (cbList.get(i).hasName(b)) {
                s = cbList.get(i).getAllModelsAsString();
            }
        }
        return s;
    }

    public List<String> getAllModels(String brand) {
        for (int i=0; i<cbList.size(); i++) {
            if (cbList.get(i).hasName(brand)) {
                return cbList.get(i).getAllModels();
            }
        }
        return null;
    }

    public List<String> getAllBrands() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i=0; i<cbList.size(); i++) {
            temp.add(cbList.get(i).getBrandName());
        }
        return temp;
    }
}
