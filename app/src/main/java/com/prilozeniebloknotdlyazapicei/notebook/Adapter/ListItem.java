package com.prilozeniebloknotdlyazapicei.notebook.Adapter;

import java.io.Serializable;

public class ListItem implements Serializable {
    private String title;
    private String descrip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
