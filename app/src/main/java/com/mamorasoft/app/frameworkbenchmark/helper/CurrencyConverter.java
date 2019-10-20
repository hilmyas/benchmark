package com.mamorasoft.app.frameworkbenchmark.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyConverter {
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        String tmp = formatter.format(Double.parseDouble(amount));
        tmp = tmp.replace(',', 'a');
        tmp = tmp.replace('.', ',');
        tmp = tmp.replace('a', '.');
        return tmp;
    }

    public static String decimalZeroRemover(double amount){
        DecimalFormat format = new DecimalFormat("#.###########");
        return format.format(amount);
    }

    static final long MILLION = 1000000L;
    static final long BILLION = 1000000000L;
    static final long TRILLION = 1000000000000L;

    public static String truncateNumber(double x) {
        return x < MILLION ?  String.valueOf(x) :
                x < BILLION ?  decimalZeroRemover(x / MILLION) + " Juta" :
                        x < TRILLION ? decimalZeroRemover(x / BILLION) + " Milyar" :
                                decimalZeroRemover(x / TRILLION) + " Trilyiun";
    }
}
