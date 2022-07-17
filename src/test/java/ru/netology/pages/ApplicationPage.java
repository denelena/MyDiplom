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
    ElementsCollection allFormSpans;

    public ApplicationPage() {
        allButtons = $$("#root .button");
        allFormSpans = $$("fieldset span");
    }

    public void showCardFormWithBuyBtn() {
        makeCardFormVisible("Купить", "Оплата по карте");
    }

    public void showCardFormWithCreditBtn() {
        makeCardFormVisible("Купить в кредит", "Кредит по данным карты");
    }

    public void raiseValidationErrorEmptyCardNumber() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getValidCard();
        setCardFieldValuesAndClickContinue("", ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());

        SelenideElement cardNumberValidationBox = locateValidationBoxElement("Номер карты");
        cardNumberValidationBox.shouldBe(Condition.visible);
        cardNumberValidationBox.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void raiseValidationErrorEmptyCardMonth() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getValidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), "", ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());

        SelenideElement cardMonthValidationBox = locateValidationBoxElement("Месяц");
        cardMonthValidationBox.shouldBe(Condition.visible);
        cardMonthValidationBox.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void raiseValidationErrorEmptyCardYear() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getValidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), "", ci.getCardOwner(), ci.getCardCvv());

        SelenideElement cardYearValidationBox = locateValidationBoxElement("Год");
        cardYearValidationBox.shouldBe(Condition.visible);
        cardYearValidationBox.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void raiseValidationErrorEmptyCardOwner() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getValidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), "", ci.getCardCvv());

        SelenideElement cardOwnerValidationBox = locateValidationBoxElement("Владелец");
        cardOwnerValidationBox.shouldBe(Condition.visible);
        cardOwnerValidationBox.shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void raiseValidationErrorEmptyCardCvv() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getValidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), "");

        SelenideElement cardCvvValidationBox = locateValidationBoxElement("CVC/CVV");
        cardCvvValidationBox.shouldBe(Condition.visible);
        cardCvvValidationBox.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void enterWrongValueCardMonth() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getValidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), "13", ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());

        SelenideElement cardMonthValidationBox = locateValidationBoxElement("Месяц");
        cardMonthValidationBox.shouldBe(Condition.visible);
        cardMonthValidationBox.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void enterExpiredDate() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getExpiredCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());

        SelenideElement cardYearValidationBox = locateValidationBoxElement("Год");
        cardYearValidationBox.shouldBe(Condition.visible);
        cardYearValidationBox.shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    public void enterFarFutureExpirationDate() {
        SelenideElement btnToClick = findButton("Купить");
        btnToClick.click();

        CardInfo ci = CardInfoGenerator.getCardWithFarFutureExpiration();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());

        SelenideElement cardYearValidationBox = locateValidationBoxElement("Год");
        cardYearValidationBox.shouldBe(Condition.visible);
        cardYearValidationBox.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void enterValidCardBuySuccess() {
        enterCardAndProceed(true, true);
        waitForSuccessPopup(true, 15);
        waitForErrorPopup(false, 0);
    }

    public void enterValidCardCreditSuccess() {
        enterCardAndProceed(true, false);
        waitForSuccessPopup(true, 15);
        waitForErrorPopup(false, 0);
    }

    public void enterInvalidCardBuyFail() {
        enterCardAndProceed(false, true);
        waitForErrorPopup(true, 15);
        waitForSuccessPopup(false, 0);
    }

    public void enterInvalidCardCreditFail() {
        enterCardAndProceed(false, false);
        waitForErrorPopup(true, 15);
        waitForSuccessPopup(false, 0);
    }

    public void enterCardAndProceed(boolean validCard, boolean withBuyBtn) {
        SelenideElement btnToClick = findButton(withBuyBtn ? "Купить" : "Купить в кредит");
        btnToClick.click();

        CardInfo ci = validCard ? CardInfoGenerator.getValidCard() : CardInfoGenerator.getInvalidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());
    }

    private void makeCardFormVisible(String btnText, String expectedHeader) {
        SelenideElement btnToClick = findButton(btnText);
        btnToClick.click();
        waitCardFormVisible(expectedHeader);
    }

    private SelenideElement getCardNumberElement() {
        return locateInputElement("Номер карты");
    }

    private SelenideElement getCardMonthElement() {
        return locateInputElement("Месяц");
    }

    private SelenideElement getCardYearElement() {
        return locateInputElement("Год");
    }

    private SelenideElement getCardOwnerElement() {
        return locateInputElement("Владелец");
    }

    private SelenideElement getCardCvcElement() {
        return locateInputElement("CVC/CVV");
    }

    private SelenideElement locateInputElement(String anchor) {
        SelenideElement parentSpan = allFormSpans.find(Condition.exactText(anchor));
        return parentSpan.$(".input__control");
    }

    private SelenideElement locateValidationBoxElement(String anchor) {
        SelenideElement parentSpan = allFormSpans.find(Condition.exactText(anchor)).parent();
        return parentSpan.$(".input__sub");
    }

    private void setCardFieldValuesAndClickContinue(String cardNumber, String cardMonth, String cardYear, String cardOwner, String cardCvv) {
        setInputFieldString("Номер карты", cardNumber);
        setInputFieldString("Месяц", cardMonth);
        setInputFieldString("Год", cardYear);
        setInputFieldString("Владелец", cardOwner);
        setInputFieldString("CVC/CVV", cardCvv);

        SelenideElement btnContinue = findButton("Продолжить");
        btnContinue.click();
    }

    private void setInputFieldString(String fieldName, String textToSet) {
        SelenideElement inputField = locateInputElement(fieldName);
        inputField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        inputField.setValue(textToSet);
    }

    private void waitCardFormVisible(String formHeaderText) {
        ElementsCollection headers3 = $$("#root h3");
        SelenideElement formHeaderElement = headers3.find(Condition.exactText(formHeaderText));
        formHeaderElement.shouldBe(Condition.visible);

        SelenideElement cardNumber = getCardNumberElement();
        SelenideElement cardMonth = getCardMonthElement();
        SelenideElement cardYear = getCardYearElement();
        SelenideElement cardOwner = getCardOwnerElement();
        SelenideElement cardCvc = getCardCvcElement();

        cardNumber.shouldBe(Condition.visible);
        cardMonth.shouldBe(Condition.visible);
        cardYear.shouldBe(Condition.visible);
        cardOwner.shouldBe(Condition.visible);
        cardCvc.shouldBe(Condition.visible);
    }

    private void waitForSuccessPopup(boolean expectVisible, int timeoutSeconds) {
        ElementsCollection notificationTitleElements = $$("#root .notification__title");
        ElementsCollection notificationContentElements = $$("#root .notification__content");

        SelenideElement successTitleElement = notificationTitleElements.find(Condition.exactText("Успешно"));
        SelenideElement successContentElement = notificationContentElements.find(Condition.exactText("Операция одобрена Банком."));

        Condition expectedCondition = expectVisible ? Condition.visible : Condition.hidden;
        if (timeoutSeconds == 0) {
            successTitleElement.shouldBe(expectedCondition);
            successContentElement.shouldBe(expectedCondition);
        } else {
            successTitleElement.shouldBe(expectedCondition, Duration.ofSeconds(timeoutSeconds));
            successContentElement.shouldBe(expectedCondition, Duration.ofSeconds(timeoutSeconds));
        }
    }

    private void waitForErrorPopup(boolean expectVisible, int timeoutSeconds) {
        ElementsCollection notificationTitleElements = $$("#root .notification__title");
        ElementsCollection notificationContentElements = $$("#root .notification__content");

        SelenideElement errorTitleElement = notificationTitleElements.find(Condition.exactText("Ошибка"));
        SelenideElement errorContentElement = notificationContentElements.find(Condition.exactText("Ошибка! Банк отказал в проведении операции."));

        Condition expectedCondition = expectVisible ? Condition.visible : Condition.hidden;
        if (timeoutSeconds == 0) {
            errorTitleElement.shouldBe(expectedCondition);
            errorContentElement.shouldBe(expectedCondition);
        } else {
            errorTitleElement.shouldBe(expectedCondition, Duration.ofSeconds(timeoutSeconds));
            errorContentElement.shouldBe(expectedCondition, Duration.ofSeconds(timeoutSeconds));
        }
    }


    private SelenideElement findButton(String btnText) {
        return allButtons.find(Condition.exactText(btnText));
    }
}
