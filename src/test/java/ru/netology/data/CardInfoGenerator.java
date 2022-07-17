package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CardInfoGenerator {
    private static String validCardNumber = "4444444444444441";
    private static String invalidCardNumber = "4444444444444442";

    private static Faker fkr = new Faker(new Locale("en"));

    public static CardInfo getValidCard(){
        String fullName = fkr.name().fullName();
        String month = LocalDate.now().plusDays(365).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().plusDays(365).format(DateTimeFormatter.ofPattern("yy"));
        String cvv = String.format("%03d", fkr.random().nextInt(0,999) );

        return new CardInfo(validCardNumber, month, year, fullName, cvv);
    }

    public static CardInfo getInvalidCard(){
        String fullName = fkr.name().fullName();
        String month = LocalDate.now().plusDays(365).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().plusDays(365).format(DateTimeFormatter.ofPattern("yy"));
        String cvv = String.format("%03d", fkr.random().nextInt(0,999) );

        return new CardInfo(invalidCardNumber, month, year, fullName, cvv);
    }

    public static CardInfo getExpiredCard(){
        String fullName = fkr.name().fullName();
        String month = LocalDate.now().plusYears(-1).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().plusYears(-1).format(DateTimeFormatter.ofPattern("yy"));
        String cvv = String.format("%03d", fkr.random().nextInt(0,999) );

        return new CardInfo(validCardNumber, month, year, fullName, cvv);
    }

    public static CardInfo getCardWithFarFutureExpiration(){
        String fullName = fkr.name().fullName();
        String month = LocalDate.now().plusYears(10).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().plusYears(10).format(DateTimeFormatter.ofPattern("yy"));
        String cvv = String.format("%03d", fkr.random().nextInt(0,999) );

        return new CardInfo(validCardNumber, month, year, fullName, cvv);
    }

}
