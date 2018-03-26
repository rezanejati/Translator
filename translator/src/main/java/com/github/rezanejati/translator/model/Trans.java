
package com.github.rezanejati.translator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public class Trans {

    @SerializedName("sentences")
    @Expose
    private List<Sentence> sentences = null;


    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }


}
