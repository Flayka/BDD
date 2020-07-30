package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;

import static com.codeborne.selenide.Selenide.$$;

@Name("Дашбоард")
public class CardPage extends AkitaPage {

    @FindBy(css = "[data-test-id='dashboard']")
    private SelenideElement cardPage;

    @FindBy(css = ".list__item [data-test-id=action-deposit]")
    @Name("Пополнить Карту 1")
    private SelenideElement replenishCard1;

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceEnd = " р.";

    public CardPageReplenish card1Replenish() {
        replenishCard1.click();
        return Selenide.page(CardPageReplenish.class);
    }

    public int getCard1Balance() {
        val text = cards.first().text();
        return extractBalanceCard1(text);
    }

    private int extractBalanceCard1(String text) {
        val start = text.indexOf(balanceStart);
        val end = text.indexOf(balanceEnd);
        val value = text.substring(start + balanceStart.length(), end);
        return Integer.parseInt(value);
    }
}
