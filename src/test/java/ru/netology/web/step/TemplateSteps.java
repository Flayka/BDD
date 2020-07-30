package ru.netology.web.step;

import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Тогда;
import lombok.val;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.CardPage;
import ru.netology.web.page.CardPageReplenish;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertEquals;
import static ru.alfabank.tests.core.helpers.PropertyLoader.loadProperty;
import static ru.netology.web.data.DataHelper.getCardInfoFromCard2;

public class TemplateSteps {
    private final AkitaScenario scenario = AkitaScenario.getInstance();

    @Пусть("^пользователь залогинен с именем \"([^\"]*)\" и паролем \"([^\"]*)\"$")
    public void loginWithNameAndPassword(String login, String password) {
        val loginUrl = loadProperty("loginUrl");
        open(loginUrl);

        scenario.setCurrentPage(page(LoginPage.class));
        val loginPage = (LoginPage) scenario.getCurrentPage().appeared();
        val authInfo = new DataHelper.AuthInfo(login, password);
        scenario.setCurrentPage(loginPage.validLogin(authInfo));

        val verificationPage = (VerificationPage) scenario.getCurrentPage().appeared();
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        scenario.setCurrentPage(verificationPage.validVerify(verificationCode));

        scenario.getCurrentPage().appeared();
    }

    @Когда("^он переводит \"([^\"]*)\" рублей с карты с номером \"([^\"]*)\" на свою \"([^\"]*)\" карту с главной страницы;$")
    public void replenish5000(String sum, String cardFrom, String cardTo) {
        val cardPage = (CardPage) scenario.getCurrentPage().appeared();
        scenario.setCurrentPage(cardPage.card1Replenish());

        val cardPageReplenish = (CardPageReplenish) scenario.getCurrentPage().appeared();
        scenario.setCurrentPage(cardPageReplenish.replenishCard1ToCard2(getCardInfoFromCard2(), "5000"));
    }

    @Тогда("^баланс его \"([^\"]*)\" карты из списка на главной странице должен стать \"([^\"]*)\" рублей\\.$")
    public void card1Balance(String card1, String sumAfter) {
        val cardPage = (CardPage) scenario.getCurrentPage().appeared();
        int card1Balance = cardPage.getCard1Balance();
        assertEquals(15000, card1Balance);
    }
}
