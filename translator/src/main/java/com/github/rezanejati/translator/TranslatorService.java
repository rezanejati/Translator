package com.github.rezanejati.translator;

import com.github.rezanejati.translator.model.Trans;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public class TranslatorService {
    private Language originLanguage;
    private Language destLnaguage;
    private OnTranslate listener;
    private RetroClient retroClient;


    public TranslatorService(Language originLanguage, Language destLanguage, OnTranslate listener) {
        this.originLanguage = originLanguage;
        this.destLnaguage = destLanguage;
        this.listener = listener;
        retroClient = ServiceGenerator.createService(RetroClient.class);
    }

    public Language getOriginLanguage() {
        return originLanguage;
    }

    public void setOriginLanguage(Language originLanguage) {
        this.originLanguage = originLanguage;
    }

    public Language getDestLnaguage() {
        return destLnaguage;
    }

    public void setDestLnaguage(Language destLnaguage) {
        this.destLnaguage = destLnaguage;
    }

    public OnTranslate getListener() {
        return listener;
    }

    public void setListener(OnTranslate listener) {
        this.listener = listener;
    }

    public void translate(String text) {
        if (originLanguage == null)
            throw new TranslatorException("Origin Language Must Not Null");

        if (destLnaguage == null)
            throw new TranslatorException("Destination Language Must Not Null");
        if (originLanguage.equals(destLnaguage))
            throw new TranslatorException("Origin Language Must Different Destination Language");

        if (text == null || text.isEmpty())
            throw new TranslatorException("Text is Not Null or Empty");
        createTranslationService(text);


    }



    private void createTranslationService(String text) {

        retroClient.translate("at", "1", "2.0", getLanguageString(originLanguage), getLanguageString(destLnaguage), "UTF-8", "UTF-8", text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Trans>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener == null)
                            return;
                        listener.onError(new TranslatorException(e.getMessage()));
                    }

                    @Override
                    public void onNext(Trans trans) {
                        if (listener == null)
                            return;
                        listener.translateReady(trans.getSentences().get(0).getTrans());

                    }
                });
    }


    private String getLanguageString(Language language) {
        switch (language) {
            case ENGLISH:
                return "en";
            case PERSIAN:
                return "fa";
            default:
                return "";
        }
    }
}
