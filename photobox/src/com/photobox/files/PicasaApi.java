package com.photobox.files;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

import android.util.*;

public class PicasaApi {

    private String userId;

    public PicasaApi(String userId) {
        this.userId = userId;
    }

    public List<String> getAlbums() {
        Document doc = getDocumentAt("https://picasaweb.google.com/data/feed/api/user/" + userId);
        if (doc == null) {
            return null;
        }
        NodeList nodes = filterNodes(doc, "//entry/title");
        if (nodes == null) {
            return null;
        }
        List<String> albums = new ArrayList<String>();
        for (int s = 0; s < nodes.getLength(); s++) {
            Node currentNode = nodes.item(s);
            String albumTitle = currentNode.getFirstChild().getNodeValue();
            albums.add(albumTitle);
        }
        return albums;
    }

    public List<String> getPhotoUrlsForAlbum(String albumId) {
        Document doc = getDocumentAt("https://picasaweb.google.com/data/feed/api/user/" + userId + "/albumid/" + albumId);
        if (doc == null) {
            return null;
        }
        NodeList nodes = filterNodes(doc, "//content");
        if (nodes == null) {
            return null;
        }
        List<String> urls = new ArrayList<String>();
        for (int s = 0; s < nodes.getLength(); s++) {
            Node currentNode = nodes.item(s);
            String imageUrl = currentNode.getAttributes().getNamedItem("src").getNodeValue();
            urls.add(imageUrl);
        }
        return urls;
    }

    private NodeList filterNodes(Document doc, String xpathExpression) {
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xpathExpression);
            NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
            return nodes;
        } catch (Exception e) {
            Log.d("PicasaApi", "failed to filter nodes: " + e.toString(), e);
            return null;
        }
    }

    private Document getDocumentAt(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(connection.getInputStream());
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            Log.d("PicasaApi", "failed to getDocumentAt: " + e.toString(), e);
            return null;
        }
    }

}
