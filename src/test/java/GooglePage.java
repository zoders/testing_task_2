import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GooglePage {
    public void searchFor(String query) {
        open("https://google.com");
        $(By.name("q")).setValue(query).pressEnter();
    }

    public void switchToTab(String tab) {
        $(byText(tab)).click();
    }

    private void downloadPic(String url) throws MalformedURLException {
        URL website = new URL(url);
        try (InputStream in = website.openStream()) {
            Files.copy(in, Paths.get("pic.jpg"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAndDownloadPicByNumber(Integer numPic) throws MalformedURLException {
        //именно на атрибуте data-ri нумерация с 0
        $(byAttribute("data-ri", numPic.toString())).click();
        var picElement = $(byAttribute("data-ri", numPic.toString()));
        String description = picElement
                .findElement(byTagName("a"))
                .findElement(byAttribute("role", "button"))
                .findElement(byTagName("img"))
                .getAttribute("alt");
        // id="islsp" -- это id у правой половинки гугл картинок. У левой -- id="islmp"
        var imgs = $(byId("islsp")).findElements(byTagName("img"));
        if (!imgs.isEmpty()) {
            for (WebElement img: imgs) {
                if (img.getAttribute("alt").equals(description)) {
                    System.out.println("downloading pic...");
                    downloadPic(img.getAttribute("src"));
                    break;
                }
            }
        }
    }

    GooglePage(String browser, Boolean holdBrowserOpen) {
        Configuration.browser = browser;
        Configuration.holdBrowserOpen = holdBrowserOpen;
    }
}
