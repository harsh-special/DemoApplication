
package com.pdfhelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LanguageSelection {

    @SerializedName("languages")
    @Expose
    private List<Language> languages = null;

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }


    public class Language {

        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("filename")
        @Expose
        private String fileName;


        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

}