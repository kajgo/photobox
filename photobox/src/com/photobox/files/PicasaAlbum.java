package com.photobox.files;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

import android.util.*;

public class PicasaAlbum {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
