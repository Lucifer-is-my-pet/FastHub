package com.fintech.fasthub;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.SearchCondition;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class FastHubPage {

    private static final String PACKAGE = "com.fastaccess.github";
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(5);
    private final UiDevice device;
    private final Context context;

    public FastHubPage(UiDevice device) {
        this.device = device;
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    void open() {
        String launcherPackage = device.getLauncherPackageName();
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIMEOUT);
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), TIMEOUT);
    }

    void clickMenuItem(String itemTitle, String submenuTitle) {
        openMenu();
        device.findObject(By.res("com.fastaccess.github:id/design_menu_item_text").text(itemTitle)).click();
        assertTrue(device.wait(Until.hasObject(title(submenuTitle)), TIMEOUT));
    }

    void clickMenuItem(String itemTitle) {
        clickMenuItem(itemTitle, itemTitle);
    }

    private void openMenu() {
        device.findObject(By.clazz("android.widget.ImageButton")).click();
    }

    void clickApply() {
        List<UiObject2> buttons = device.findObjects(By.res("com.fastaccess.github:id/apply"));
        buttons.get(buttons.size() - 1).click();
    }

    void clickSubmit() {
        int last = device.findObjects(By.res("com.fastaccess.github:id/submit")).size() - 1;
        device.findObjects(By.res("com.fastaccess.github:id/submit")).get(last).click();
    }

    void clickObjectWithText(String text) {
        device.findObject(By.text(text)).click();
    }

    void scrollForward(int steps) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();
        appViews.scrollForward(steps);
    }

    void scrollToItem(String elementText) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text(elementText));
    }

    boolean hasObjectWithText(String text) {
        return device.wait(Until.hasObject(By.text(text)), TIMEOUT);
    }

    void setTextToField(String text, String fieldTitle) {
        device.findObject(By.text(fieldTitle)).findObject(By.clazz("android.widget.EditText")).setText(text);
    }

    boolean textFieldContainsText(String fieldName, String text) {
        return device.findObject(By.res("com.fastaccess.github:id/" + fieldName.toLowerCase())).getText().contains(text);
    }

    private BySelector title(String title) {
        return By.clazz("android.widget.TextView").text(title);
    }

}
