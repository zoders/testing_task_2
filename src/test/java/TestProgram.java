import java.net.MalformedURLException;
import org.junit.Test;


public class TestProgram {
    @Test
    public void iCanDownloadThirdPic() throws InterruptedException, MalformedURLException {
        GooglePage gp = new GooglePage("chrome", true);
        gp.searchFor("Александр Филипповский");
        Thread.sleep(1000);
        gp.switchToTab("Картинки");
        // нумерация на атрибуте data-ri c 0
        gp.selectAndDownloadPicByNumber(2);
    }
}
