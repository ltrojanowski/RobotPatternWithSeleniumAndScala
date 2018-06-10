package robots

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.{BeforeAndAfter, FunSuite}

class TweetingSpec extends FunSuite with BeforeAndAfter with LoginRobot with TweetingRobot {

  /*
  * TODO: You need to manually enter the user and password for the account you wish to perform this test for.
  * This test suite will send a tweet with a message to the article on medium.
  * Feel free to write more tests of your own and expand the robots.
  * */
  val user: String = ???
  val password: String = ???

  implicit var driver: WebDriver = null
  before {
    driver = new FirefoxDriver()
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
    driver.manage().window().maximize()
    driver.get("https://twitter.com/?logged_out=1&lang=en")
    LoginRobot(user = user, password = password).login.shouldSucceed
  }

  after {
    driver.close()
  }

  test("Tweet with message 'Checkout the robot pattern for UI testing.' should succeed") {
    TweetingRobot(message = "Checkout the robot pattern for UI testing.").tweet().shouldSucceed
  }
}
