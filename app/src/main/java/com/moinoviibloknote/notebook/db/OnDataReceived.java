package com.moinoviibloknote.notebook.db;

import com.moinoviibloknote.notebook.Adapter.Listitem;

import java.util.List;

public interface OnDataReceived {
    void onReceived(List<Listitem> list);
}
