package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.News

/**
  * News
  * http://www.redmine.org/projects/redmine/wiki/Rest_News
  */
trait NewsResource extends BaseResource {
  /** Returns all news across all projects with pagination. */
  def listNews(): Iterable[News] = {
    import redmine4s.api.json.JsonHelper.newsReads
    list("/news.json", __ \ "news", Map.empty).toIterable
  }

  /** Returns all news from project with given id or identifier with pagination. */
  def listNews(projectId: Long): Iterable[News] = {
    import redmine4s.api.json.JsonHelper.newsReads
    list(s"/projects/$projectId/news.json", __ \ "news", Map.empty).toIterable
  }
}
