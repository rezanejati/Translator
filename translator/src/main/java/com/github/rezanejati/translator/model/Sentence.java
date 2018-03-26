
package com.github.rezanejati.translator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public class Sentence {

    @SerializedName("trans")
    @Expose
    private String trans;
    @SerializedName("orig")
    @Expose
    private String orig;


    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "trans='" + trans + '\'' +
                ", orig='" + orig + '\'' +
                '}';
    }
}
