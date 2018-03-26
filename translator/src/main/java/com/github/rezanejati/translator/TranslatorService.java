package com.github.rezanejati.translator;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;

import com.github.rezanejati.translator.model.Trans;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public class TranslatorService {
    private final int REQ_CODE_SPEECH_INPUT = 100;
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

    public void translateFromSpeech(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, getLanguageString(originLanguage));
        try {
            activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.e("Text", result.get(0));
                    createTranslationService(result.get(0));
                }
                break;
            }
        }
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
