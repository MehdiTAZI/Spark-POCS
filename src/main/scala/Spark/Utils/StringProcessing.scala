package Spark.Utils

object StringProcessing {

  def ltrim(string: String) = string.replaceAll("^\\s+", "")

  def rtrim(string: String) = string.replaceAll("\\s+$", "")

  def trim(string: String) = string.trim

  def stringsToHashTags(strings: List[String]) = strings.map(x => new StringBuilder("#").append(x).toString())

  def stringsToUsersId(strings: List[String]) = strings.map(x => new StringBuilder("@").append(x).toString())

  def stringsToWholeWords(strings: List[String]) = strings.map(x => new StringBuilder(" ").append(x).append(" ").toString())

  def stringsToWholeWordHashTags(strings: List[String]) = stringsToWholeWords(stringsToHashTags(strings))

  def stringsToWholeWordUsersId(strings: List[String]) = stringsToWholeWords(stringsToUsersId(strings))

}
