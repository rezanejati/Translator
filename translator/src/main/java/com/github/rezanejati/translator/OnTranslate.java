package com.github.rezanejati.translator;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public interface OnTranslate {
    void translateReady(String translation);

    void onError(TranslatorException e);
}
