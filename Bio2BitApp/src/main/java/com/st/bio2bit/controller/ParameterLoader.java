package com.st.bio2bit.controller;

import com.st.bio2bit.utilities.Utils;

/**
 * Created by mathias on 15/03/16.
 */
public class ParameterLoader {

    private String boardName;

    public ParameterLoader(String boardName) {
        if(Utils.isExternalStorageReadable()){
            loadPreferences();
        }
        this.boardName = boardName;
    }

    private void loadPreferences() {

    }
}
