package redmine4s.api.model

import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import redmine4s.api.json.JsonHelper._

import scala.io.Source

case class Role(id: Long, name: String, inherited: Option[Boolean], permissions: Option[Iterable[String]]) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def show(baseUrl: String): Tracker = {
    val url = s"$baseUrl/roles/$id.json"
    val responseJson = Json.parse(Source.fromURL(url).mkString(""))
    logger.debug(s"url=$url, json=$responseJson")
    (responseJson \ "role").as[Tracker]
  }
}
