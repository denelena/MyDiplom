package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.CardInfo;
import ru.netology.data.CardInfoGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


public class ApplicationPage {

    ElementsCollection allButtons;

    public ApplicationPage() {
        allButtons = $$("#root .button");
    }

    public CardPage showCardFormWithBuyBtn() {
        return makeCardFormVisible("Купить", "Оплата по карте");
    }

    public CardPage showCardFormWithCreditBtn() {
        return makeCardFormVisible("Купить в кредит", "Кредит по данным карты");
    }

    private CardPage makeCardFormVisible(String btnText, String expectedHeader) {
        SelenideElement btnToClick = findButton(btnText);
        btnToClick.click();
        return new CardPage(expectedHeader);
    }

    private SelenideElement findButton(String btnText) {
        return allButtons.find(Condition.exactText(btnText));
    }
}
