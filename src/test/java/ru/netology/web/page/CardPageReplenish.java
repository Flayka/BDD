package ru.netology.web.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;
import ru.netology.web.data.DataHelper;

@Name("Перевод")
public class CardPageReplenish extends AkitaPage {
    @FindBy(css = "[data-test-id='amount'] .input__control")
    @Name("Сумма")
    private SelenideElement sumField;
    @FindBy(css = "[data-test-id='from'] .input__control")
    @Name("Перевод с карты")
    private SelenideElement cardFrom;
    @FindBy(css = "[data-test-id='action-transfer']")
    @Name("Пополнить")
    private SelenideElement replenish;

    public CardPage replenishCard1ToCard2(DataHelper.CardInfo cardInfo, String sum) {
        sumField.setValue(sum);
        cardFrom.setValue(cardInfo.getCardFrom());
        replenish.click();
        return Selenide.page(CardPage.class);
    }
}
