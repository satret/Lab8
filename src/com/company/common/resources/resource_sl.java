package com.company.common.resources;

import java.util.ListResourceBundle;

public class resource_sl extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"LanguageName", "Ime jezika"},
                    {"Language", "Jezik"},
                    {"Login", "Vpiši se"},
                    {"Register", "Registriraj se"},
                    {"Exit", "Izhod"},
                    {"AccountName", "Ime računa"},
                    {"Password", "Geslo"},
                    {"PasswordAgain", "Ponovno Geslo"},
                    {"Cancel", "Prekliči"},
                    {"Save", "Shrani"},
                    {"Back", "Nazaj"},

                    {"VisualizationArea", "Območje Vizualizacije"},
                    {"BrowseCollection", "Zbirka brskalnikov"},
                    {"ExecuteScript", "Izvršilni nalog"},
                    {"Information", "Informacije"},
                    {"ReplaceIf", "ReplaceIf"},
                    {"Owner", "Lastnik"},
                    {"Id", "Id"},
                    {"CreationDate", "Datum nastanka"},
                    {"Name", "Ime"},
                    {"Distance", "Razdalja"},
                    {"CoordinateX", "Koordinateks"},
                    {"CoordinateY", "Koordinacija"},
                    {"FromX", "Iz KS"},
                    {"FromY", "Od In"},
                    {"FromName", "Iz Imena"},
                    {"ToX", "Toksikologija"},
                    {"ToY", "Igrača"},
                    {"ToName", "Za Ime"},
                    {"FieldsCantBeEmpty", "Polja Ne Morejo Biti Prazna"},
                    {"Add", "Dodaj"},
                    {"Remove", "Odstrani"},
                    {"Clear", "Jasno"},
                    {"Delete", "Izbriši"},
                    {"Create", "Ustvari"},
                    {"ChooseFile", "Izberi Datoteko"},
                    {"ReplaceIfGreater", "Zamenjaj, Če Je Večja"},
                    {"ReplaceIfLower", "Zamenjaj, Če Je Nižje"},
                    {"Drop", "Spusti"},
                    {"StartsWith", "Začetek"},
            };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
