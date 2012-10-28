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

    public List<PicasaAlbum> getAlbums() {
        Document doc = getDocumentAt("https://picasaweb.google.com/data/feed/api/user/" + userId);
        if (doc == null) {
            return null;
        }
        NodeList nodes = filterNodes(doc, "//entry");
        if (nodes == null) {
            return null;
        }
        List<PicasaAlbum> albums = new ArrayList<PicasaAlbum>();
        for (int s = 0; s < nodes.getLength(); s++) {
            PicasaAlbum album = new PicasaAlbum();
            Node currentNode = nodes.item(s);
            populateAlbum(album, currentNode);
            albums.add(album);
        }
        return albums;
    }

    private void populateAlbum(PicasaAlbum album, Node entryNode) {
        album.setName(getFirstNodeText(entryNode, "title"));
        album.setId(getFirstNodeText(entryNode, "id"));
    }

    private String getFirstNodeText(Node parentNode, String nodeName) {
        for (int i = 0; i < parentNode.getChildNodes().getLength(); i++) {
            Node currentNode = parentNode.getChildNodes().item(i);
            if (currentNode.getNodeName().equals(nodeName)) {
                return currentNode.getFirstChild().getNodeValue();
            }
        }
        return "";
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
