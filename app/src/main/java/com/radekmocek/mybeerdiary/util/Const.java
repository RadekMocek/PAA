package com.radekmocek.mybeerdiary.util;

public final class Const {

    private Const() {
    }

    public static final String[] BREWERIES = new String[]{"Agent", "Albrecht", "Antonín", "Antoš", "Bad Flash", "Bakalář", "Beer Factory", "Beran", "Bernard", "Bezák", "Bitches Brew", "Bizon", "Born", "Braník", "Brewteam", "Břevnov", "Březňák", "Budulínek", "Budweiser Budvar", "Clock", "Cobolis", "Crazy Clown", "Cvikov", "Čestmír", "Černá Hora", "Černý Potoka", "Dobruška", "Dobřany", "Dráteník", "Duck and Dog", "Excelent", "Falkenštejn", "Falkon", "Fenetra", "Frankies", "Gambrinus", "GARP", "Hákův parní pivovar", "Hauskrecht", "Heineken", "Hellstork", "Hendrych", "Holba", "Holendr", "Hoppy Dog", "Hostomice", "Hroch", "Hubertus", "Chomout", "Chotěšov", "Chotoviny", "Chroust", "ISAO", "Jungberg", "Kamenice", "Klasik", "Klášter", "Klenot", "Knajzl", "Kočovný Kozi", "Kosa Prérií", "Kostelec", "Kotouč", "Kounice", "Kousek Piva", "Kozel", "Kozlíček Dubenky", "Krasavec", "Kristýna", "Kročehlavy", "Krušnohor", "Krušovice", "Křemenný Vrch", "Křikloun", "Kvasar", "Labuť", "Litovel", "Lípák", "Lobkowicz", "Lomnice", "Mad Cat", "Malešov", "Matuška", "Master", "Mazák", "Máša", "Medvěd", "Mlýn", "Moravia", "Mordýř", "Muflon", "Nachmelená Opice", "Nepomucen", "Nomád", "Novopacké", "Nový rybník", "Nozib", "Obora", "Olešná", "Opat", "O.SKAR", "Ostravar", "Ovipistán", "Pardál", "Paskov", "Perlová Voda", "Pernštejn", "Pilsner Urquell", "Pinta", "Pioneer", "Pivečka", "Platan", "Plzeň", "Plzeňský Bandita", "Podklášterní Urban", "Podřipské", "Polička", "Postřižiny", "Potmehúd", "Poutník", "Prazdroj", "Proud", "Primátor", "Primus", "Purkmistr", "Radegast", "Radouš", "Raven", "Rebel", "Rockmill", "Rohozec", "Rudohor", "Řeporyje", "Samson", "Sibeeria", "SQBRU", "Starobrno", "Stín", "Staropramen", "Stella Artois", "Strahov", "Svatý Ján", "Svatý Petr", "Svijany", "Šepťák", "Švihov", "The Barn Beer co.", "Továrna", "Trilobit", "Třebonický Rukodělný", "U Fleků", "U Medvídků", "U Vacků", "Únětice", "Vagabund", "Valášek", "Valteřickej Strojník", "Veselka", "Vik", "Vinohradský Démon", "Volt", "Všerad", "Welzl", "Wywar", "Záhora", "Zdislavka", "Zhůřák", "Zichovec", "Zlatopramen", "Zlatý Bažant", "Zloun z Loun", "Zlosyn z Losin", "Znojemský Městský", "Zubr", "Zvíkov", "Žatec", "Židovice", "Žlebské Chvalovice"};

    public static final double DEFAULT_EPM = 12d;
    public static final double DEFAULT_ABV = 4.4d;
    public static final float DEFAULT_SLIDER_LITRES = 0.4f;

    public static final int MAX_EDIT_BEER_DIALOG_FRAGMENT_HEADER_CHARACTERS = 33;

    public static final String PREFS_NAME = "UserSettings";
    public static final String PREFS_KEY_IS_MALE = "isMale";
    public static final boolean PREFS_DEFAULT_IS_MALE = true;
    public static final String PREFS_KEY_WEIGHT = "weight";
    public static final int PREFS_DEFAULT_WEIGHT = 80;

    public static final String INTENT_EXTRAS_KEY_PUB_VISIT_ID = "pubVisitID";
    public static final String INTENT_EXTRAS_KEY_PUB_VISIT_NAME = "pubVisitName";
    public static final String INTENT_EXTRAS_KEY_PUB_VISIT_RV_POS = "pubVisitRvPos";

    public static final String PREFS2_NAME = "ReturnFromBeersActivityUpdateHack";
    public static final String PREFS2_KEY_IS_UPDATE_NECESSARY = "isUpdateNecessary";
    public static final String PREFS2_KEY_PUB_VISIT_RV_POS = "pubVisitRvPos";
    public static final String PREFS2_KEY_TOTAL_BEERS = "totalBeers";
    public static final String PREFS2_KEY_TOTAL_COST = "totalCost";
}
