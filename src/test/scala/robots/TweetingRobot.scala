package robots

import org.openqa.selenium.{By, WebDriver, WebElement}
import scala.collection.JavaConverters._

trait TweetingRobot {

  case class TweetingRobot(private val message: String = "")(implicit driver: WebDriver)  {

    private def enterMessage(message: String) = {
      val tweetEntryField = driver.findElement(By.xpath("//*[@id=\"tweet-box-home-timeline\"]"))
      tweetEntryField.click()
      tweetEntryField.sendKeys(message)
    }

    private def performTweet() = {
      val tweetButton: WebElement = driver.findElement(By.xpath("//*[@id=\"timeline\"]/div[2]/div/form/div[3]/div[2]/button"))
      if (tweetButton == null) {
        throw new NullPointerException("tweet button is null")
      }
      tweetButton.click()
    }

    def tweet(): TweetingResult = {
      enterMessage(message)
      performTweet()
      TweetingResult(message)
    }
  }

  case class TweetingResult(private val message: String)(implicit driver: WebDriver) {

    def shouldSucceed = {
      Thread.sleep(1000)
      val tweetLists: Seq[WebElement] = driver.findElements(By.xpath("//*[@id=\"stream-items-id\"]/li")).asScala
      if (tweetLists.isEmpty) {
        throw new Exception("failed to find tweet list")
      }
      val containsSentTweet = tweetLists.map(tweetElement => tweetElement.findElement(By.className("tweet-text")).getText)
        .contains(message)
      if (!containsSentTweet) {
        throw new Exception("failed to send tweet")
      }
    }

    def shouldFail = {
      val failureDialog: WebElement = driver.findElement(By.xpath("//*[@id=\"message-drawer\"]/div/div/span"))
      if (failureDialog == null) {
        throw new NullPointerException("couldn't obtain failureDialog")
      }
    }

  }

}
