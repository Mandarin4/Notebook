package com.prilozeniebloknotdlyazapicei.notebook.db;

import com.prilozeniebloknotdlyazapicei.notebook.Adapter.ListItem;

import java.util.List;

public interface OnDataReceived {
    void onReceived(List<ListItem> list);
}
