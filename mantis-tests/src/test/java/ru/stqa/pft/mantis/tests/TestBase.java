package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); //глобальная переменная

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  /**
   * В классе TestBase, от которого наследуются все тесты, необходимо реализовать функцию boolean isIssueOpen(int issueId) ,
   * которая должна через Remote API получать из баг-трекера информацию о баг-репорте с заданным идентификатором,
   * и возвращать значение false или true в зависимости от того, помечен он как исправленный или нет.
   */
  public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mcpt = new MantisConnectLocator().
            //getMantisConnectPort(new URL("http://localhost/mantisbt-2.25.1/api/soap/mantisconnect.php"));
    getMantisConnectPort(new URL("web.baseUrl"+"/api/soap/mantisconnect.php"));
    IssueData fixedIssueData = mcpt.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
    if (fixedIssueData.getResolution().getName().equals("fixed")) {
      return false;
    } else {
      return true;
    }
  }

  public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }


}
