package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.News

trait NewsResource extends BaseResource {
  def listNews(): Iterable[News] = {
    import redmine4s.api.json.NewsJsonHelper.newsReads
    list("/news.json", __ \ "news", Map.empty).toIterable
  }

  def listNews(projectId: Long): Iterable[News] = {
    import redmine4s.api.json.NewsJsonHelper.newsReads
    list(s"/projects/$projectId/news.json", __ \ "news", Map.empty).toIterable
  }
}
