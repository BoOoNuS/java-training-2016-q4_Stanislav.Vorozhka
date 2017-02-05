package ua.nure.vorozhka.SummaryTask4.db.model.constant;

import java.io.Serializable;

/**
 * Created by Stanislav on 22.01.2017.
 */
public enum Language implements Serializable {

    EN("English"), RU("Russian");

    String fullLangName;

    Language(String fullLangName) {
        this.fullLangName = fullLangName;
    }

    public static Language getLangByName(String fullLangName){
        for (Language language : Language.values()) {
            if(language.getFullLangName().equals(fullLangName)){
                return language;
            }
        }
        return null;
    }

    public String getFullLangName() {
        return fullLangName;
    }

    public String getName(){
        return this.name().toLowerCase();
    }
}
