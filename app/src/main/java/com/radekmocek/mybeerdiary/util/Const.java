package com.radekmocek.mybeerdiary.util;

public final class Const {

    private Const() {
    }

    public static final String[] BREWERIES = new String[]{
            "Agent", "Albrecht", "Antonín", "Antoš", "Bad Flash", "Beer Factory", "Beran", "Bezák", "Bitches Brew", "Bizon", "Born", "Brewteam", "Břevnov", "Budulínek", "Clock", "Cobolis", "Crazy Clown", "Cvikov", "Čestmír", "Černá Hora", "Černý Potoka", "Dobruška", "Dobřany", "Dráteník", "Duck and Dog", "Falkenštejn", "Falkon", "Fenetra", "Frankies", "GARP", "Hákův parní pivovar", "Hauskrecht", "Hellstork", "Hendrych", "Hoppy Dog", "Hostomice", "Hroch", "Hubertus", "Chomout", "Chotěšov", "Chotoviny", "Chroust", "ISAO", "Jungberg", "Kamenice", "Klášter", "Klenot", "Knajzl", "Kočovný Kozi", "Kosa Prérií", "Kostelec", "Kotouč", "Kounice", "Kousek Piva", "Kozlíček Dubenky", "Krasavec", "Kristýna", "Kročehlavy", "Krušnohor", "Křemenný Vrch", "Křikloun", "Kvasar", "Labuť", "Lípák", "Lomnice", "Mad Cat", "Malešov", "Matuška", "Mazák", "Máša", "Medvěd", "Mlýn", "Moravia", "Mordýř", "Muflon", "Nachmelená Opice", "Nepomucen", "Nomád", "Novopacké", "Nový rybník", "Nozib", "Obora", "Olešná", "Opat", "O.SKAR", "Ovipistán", "Paskov", "Perlová Voda", "Pinta", "Pioneer", "Pivečka", "Plzeňský Bandita", "Podřipské", "Podklášterní Urban", "Polička", "Potmehúd", "Poutník", "Purkmistr", "Radouš", "Raven", "Rockmill", "Rudohor", "Řeporyje", "Sibeeria", "SQBRU", "Stín", "Strahov", "Svatý Ján", "Svatý Petr", "Šepťák", "Švihov", "The Barn Beer co.", "Továrna", "Trilobit", "Třebonický Rukodělný", "U Fleků", "U Medvídků", "U Vacků", "Únětice", "Vagabund", "Valášek", "Valteřickej Strojník", "Veselka", "Vik", "Vinohradský Démon", "Volt", "Všerad", "Welzl", "Wywar", "Záhora", "Zdislavka", "Zhůřák", "Zichovec", "Zloun z Loun", "Zlosyn z Losin", "Znojemský Městský", "Zvíkov", "Žatec", "Židovice", "Žlebské Chvalovice",
            "Bakalář", "Bernard", "Braník", "Březňák", "Budweiser Budvar", "Gambrinus", "Heineken", "Holba", "Kozel", "Krušovice", "Litovel", "Lobkowicz", "Ostravar", "Pardál", "Pernštejn", "Platan", "Pilsner Urquell", "Plzeň", "Prazdroj", "Proud", "Primátor", "Postřižiny", "Radegast", "Rebel", "Rohozec", "Samson", "Starobrno", "Staropramen", "Stella Artois", "Svijany", "Zlatopramen", "Zlatý Bažant", "Zubr"
    };

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
