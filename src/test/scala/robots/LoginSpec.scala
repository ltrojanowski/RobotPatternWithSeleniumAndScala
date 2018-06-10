package robots

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.{BeforeAndAfter, FunSuite}

class LoginSpec extends FunSuite with BeforeAndAfter with LoginRobot {

  /*
  * TODO: You need to manually enter the user and password for the account you wish to perform this test for.
  * */
  val user: String = ???
  val password: String = ???

  import LoginRobotSyntax._

  implicit var driver: WebDriver = null
  before {
    driver = new FirefoxDriver()
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    driver.manage().window().maximize()
    driver.get("https://twitter.com/?logged_out=1&lang=en")
  }

  after {
    driver.close()
  }

  test("Login with user 'yourUser' and password 'yourPassword' shouldSucceed") {
    LoginRobot(user = user, password = password).login.shouldSucceed
  }

  test("Using builder - Login with user 'invalidUser' and password 'invalidPassword' shouldFail") {
    login( using user "invalidUser" password "invalidPassword") shouldFail
  }

  test("Login with user 'invalidUser' and password 'invalidPassword' shouldFail") {
    LoginRobot(user = "invalidUser", password = "invalidPassword").login.shouldFail
  }

}
