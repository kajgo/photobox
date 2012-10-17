package com.photobox.files;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import android.util.*;

public class PicasaApi {

    private String userId;

    public PicasaApi(String userId) {
        this.userId = userId;
    }

    public List<String> getAlbums() {
        Document doc = getDocumentAt("https://picasaweb.google.com/data/feed/api/user/" + userId);
        if (doc != null) {
            List<String> albums = new ArrayList<String>();
            NodeList nodeLst = doc.getElementsByTagName("title");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                String albumTitle = fstNode.getFirstChild().getNodeValue();
                albums.add(albumTitle);
            }
            return albums;
        }
        return null;
    }

    public List<String> getPhotoUrlsForAlbum(String albumId) {
        Document doc = getDocumentAt("https://picasaweb.google.com/data/feed/api/user/" + userId + "/albumid/" + albumId);
        if (doc != null) {
            List<String> urls = new ArrayList<String>();
            NodeList nodeLst = doc.getElementsByTagName("content");
            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                String imageUrl = fstNode.getAttributes().getNamedItem("src").getNodeValue();
                urls.add(imageUrl);
            }
            return urls;
        }
        return null;
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
