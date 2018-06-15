package Spark.Utils

import org.scalatest._
import org.mockito.Mockito._

class StringProcessingSpec extends FlatSpec {

  "The strings firstname & listname" should "not contains spaces on the left" in {
    val firstname = "  mehdi"
    val lastname = "  tazi"
    val ltrimedFirstname = StringProcessing.ltrim(firstname)
    val ltrimedLastname = StringProcessing.ltrim(lastname)
    assert(!ltrimedFirstname.startsWith(" "))
    assert(!ltrimedLastname.startsWith(" "))

  }

  "The strings firstname & listname" should "not contains spaces on the right" in {
    val firstname = "  mehdi"
    val lastname = "  tazi"
    val rtrimedFirstname = StringProcessing.rtrim(firstname)
    val rtrimedLastname = StringProcessing.rtrim(lastname)
    assert(!rtrimedFirstname.endsWith(" "))
    assert(!rtrimedLastname.endsWith(" "))

  }

  it should "makes the keywords as hashtags" in {
    val keywords = List ("keyword1","keyword2","keyword3")
    val expectedHashtag = List ("#keyword1","#keyword2","#keyword3")
    val hashtags = StringProcessing.stringsToHashTags(keywords)
    assert(hashtags.equals(expectedHashtag))
  }

  it should "makes the keywords as usersid" in {
    val keywords = List ("keyword1","keyword2","keyword3")
    val expectedUsersID = List ("@keyword1","@keyword2","@keyword3")
    val usersID = StringProcessing.stringsToUsersId(keywords)
    assert(usersID.equals(expectedUsersID))
  }

}