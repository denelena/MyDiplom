package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.CardInfo;
import ru.netology.data.CardInfoGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CardPage {

    ElementsCollection allButtons;
    ElementsCollection allFormSpans;

    public CardPage(String expectedHeader) {
        allButtons = $$("#root .button");
        allFormSpans = $$("fieldset span");

        waitCardFormVisible(expectedHeader);
    }

    public void raiseValidationError(CardInfo ci, String validationElementAnchor, String expectedErrorText) {
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());
        SelenideElement validationBox = locateValidationBoxElement(validationElementAnchor);
        validationBox.shouldBe(Condition.visible);
        validationBox.shouldHave(Condition.exactText(expectedErrorText));
    }

    public void enterValidCardSuccess() {
        enterCardAndProceed(true);
        waitForSuccessPopup(true, 15);
        waitForErrorPopup(false, 0);
    }

    public void enterInvalidCardFail() {
        enterCardAndProceed(false);
        waitForErrorPopup(true, 15);
        waitForSuccessPopup(false, 0);
    }

    public void enterCardAndProceed(boolean validCard) {
        CardInfo ci = validCard ? CardInfoGenerator.getValidCard() : CardInfoGenerator.getInvalidCard();
        setCardFieldValuesAndClickContinue(ci.getCardNumber(), ci.getCardMonth(), ci.getCardYear(), ci.getCardOwner(), ci.getCardCvv());
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
