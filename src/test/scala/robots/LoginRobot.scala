package robots
import org.openqa.selenium.{By, WebDriver, WebElement}

trait LoginRobot {
  case class LoginRobot(private val user: String = "", private val password: String = "")(implicit val driver: WebDriver) {

    private def enterUser(user: String) = {
      val loginField: WebElement = driver.findElement(By.xpath("//*[@id=\"doc\"]/div/div[1]/div[1]/div[1]/form/div[1]/input"))
      loginField.sendKeys(user)
    }
    private def enterPassword(password: String) = {
      val passwordField: WebElement = driver.findElement(By.xpath("//*[@id=\"doc\"]/div/div[1]/div[1]/div[1]/form/div[2]/input"))
      passwordField.sendKeys(password)
    }
    private def performLogin() = {
      val loginButton: WebElement = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/div[1]/form/input[1]"))
      loginButton.click()
    }

    def login(): LoginResult = {
      enterUser(user)
      enterPassword(password)
      performLogin()
      LoginResult()
    }

  }

  case class LoginResult()(implicit val driver: WebDriver) {
    def shouldSucceed = {
      val homePageButton: WebElement = driver.findElement(By.id("global-nav-home"))
      if (homePageButton == null) {
        throw new NullPointerException("couldn't obtain mainPage home button")
      }
    }
    def shouldFail = {
      val failureDialog: WebElement = driver.findElement(By.xpath("//*[@id=\"message-drawer\"]/div/div/span"))
      if (failureDialog == null) {
        throw new NullPointerException("couldn't obtain failure dialog")
      }
    }
  }

  object LoginRobotSyntax {

    def login(loginRobot: LoginRobot): LoginResult = loginRobot.login()
    def using()(implicit driver: WebDriver) = LoginRobot()
    implicit class LoginRobotOps(loginRobot: LoginRobot) {
      def user(user: String)(implicit driver: WebDriver): LoginRobot = loginRobot.copy(user = user)
      def password(password: String)(implicit driver: WebDriver): LoginRobot = loginRobot.copy(password = password)
    }

  }
}