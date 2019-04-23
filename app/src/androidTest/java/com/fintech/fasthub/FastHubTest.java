package com.fintech.fasthub;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FastHubTest {

    private static FastHubPage app;

    @BeforeClass
    public static void login() {
        app = new FastHubPage(UiDevice.getInstance(getInstrumentation()));
        app.open();
        //залогиниться, если ещё не
    }

    @Before
    public void before() {
        // открыть главное меню
//        device = UiDevice.getInstance(getInstrumentation());
//        assertThat(device, notNullValue());
    }

    /**
     * Приоритет: Высокий
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkTrendingPage() {
        app.clickMenuItem("Trending");
    }

    /**
     * Приоритет: Низкий
     * Предусловие – Пользователь залогинен в приложение
     * Ожидаемый результат: тема сменираль на черную
     */
    @Test
    public void checkThemeChange() throws UiObjectNotFoundException {
        app.clickMenuItem("Settings");
        app.clickObjectWithText("Theme");
        assertTrue(app.hasObjectWithText("FastHub Premium Features"));
        app.scrollForward(2);
        app.clickApply();
        assertTrue(app.hasObjectWithText("If the theme didn't apply properly, please kill and open the app manually."));
    }

    /**
     * Приоритет: Низкий
     * Предусловие – Пользователь залогинен в приложение
     * Шаги:
     * Ожидаемый результат: отправился Intent(activity, CheckPurchaseActivity::class.java)
     */
    @Test
    public void checkRestorePurchases() throws UiObjectNotFoundException {
        app.clickMenuItem("Restore Purchases");
    }

    /**
     * Приоритет: Средний
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkReportAnIssue() throws InterruptedException {
        app.clickMenuItem("Report an issue", "Submit feedback");
        app.setTextToField("hello", "Title");
        app.clickObjectWithText("Description");
        app.clickSubmit();
        assertTrue(app.hasObjectWithText("Submit feedback"));
        assertTrue(app.textFieldContainsText("Description", "Xiaomi"));
        assertTrue(app.textFieldContainsText("Description", "Redmi 3"));
        app.clickSubmit();
        assertTrue(app.hasObjectWithText("Successfully submitted"));
    }

    /**
     * Приоритет: Средний
     * Предусловие – Пользователь залогинен в приложение
     * Шаги:
     * Ожидаемый результат: На второй позиции отображается текст «Changelog». Иконка R.drawable.ic_track_changes
     * блок About, res com.fastaccess.github:id/mal_list_card_title
     * Changelog второй в списке res com.fastaccess.github:id/mal_item_text
     */
    @Test
    public void checkMarkup() throws UiObjectNotFoundException {
        app.clickMenuItem("About", "FastHub");
        app.scrollToItem("About");
    }
}
