package com.example.medcheckb8.db.utill;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateUtil {
    private static final String API_KEY = "AIzaSyBFLVyZQLsyYMndC9fhrCLoBP3NK6BHitA";

    public String translateMethod(String request, String targetLang, String sourceLang){
        Translate translate = TranslateOptions.newBuilder().setApiKey(API_KEY).build().getService();
        Translation translation = translate.translate(request,
                Translate.TranslateOption.targetLanguage(targetLang),
                Translate.TranslateOption.sourceLanguage(sourceLang));
        return translation.getTranslatedText();
    }
}
