package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.{UploadFile, Wiki, WikiIndex}

/**
  * Wiki Pages
  * http://www.redmine.org/projects/redmine/wiki/Rest_WikiPages
  */
trait WikiResource extends BaseResource {
  /** Getting the pages list of a wiki */
  def indexWikis(projectId: Long): Iterable[WikiIndex] = {
    import redmine4s.api.json.JsonHelper.wikiIndexReads
    list[WikiIndex](s"/projects/$projectId/wiki/index.json", __ \ "wiki_pages", Map.empty).map {
      wikiIndex => wikiIndex.copy(projectId = projectId, redmine = redmine)
    }.toIterable
  }

  /** Getting a wiki page */
  def showWiki(projectId: Long, title: String): Wiki = {
    import redmine4s.api.json.JsonHelper.wikiReads
    val wiki = show[Wiki](s"/projects/$projectId/wiki/$title.json", __ \ "wiki_page", Map("include" -> "attachments"))
    wiki.copy(projectId = projectId, redmine = redmine, attachments = wiki.attachments.map(_.map(_.copy(redmine = redmine))))
  }

  /** Getting an old version of a wiki page */
  def showOldWiki(projectId: Long, title: String, version: Int): Wiki = {
    import redmine4s.api.json.JsonHelper.wikiReads
    val wiki = show[Wiki](s"/projects/$projectId/wiki/$title/$version.json", __ \ "wiki_page", Map("include" -> "attachments"))
    wiki.copy(projectId = projectId, redmine = redmine, attachments = wiki.attachments.map(_.map(_.copy(redmine = redmine))))
  }

  /** Creating or updating a wiki page */
  def updateWiki(projectId: Long,
                 title: String,
                 text: Option[String] = None,
                 comments: Option[String] = None,
                 version: Option[Int] = None,
                 uploadFiles: Option[Seq[UploadFile]] = None): Wiki = {
    import redmine4s.api.json.JsonHelper.{wikiReads, wikiUpdateWrites}
    create(s"/projects/$projectId/wiki/$title.json", __ \ 'wiki_page, (text, comments, version, uploadFiles.map(_.map(redmine.upload))))
  }

  /** Deleting a wiki page */
  def deleteWiki(projectId: Long, title: String): Unit = delete(s"/projects/$projectId/wiki/$title.json")

  /** UNDOCUMENTED IN OFFICIAL: Getting a main wiki page */
  def showMainWiki(projectId: Long): Wiki = {
    import redmine4s.api.json.JsonHelper.wikiReads
    val wiki = show[Wiki](s"/projects/$projectId/wiki.json", __ \ "wiki_page", Map("include" -> "attachments"))
    wiki.copy(projectId = projectId, redmine = redmine, attachments = wiki.attachments.map(_.map(_.copy(redmine = redmine))))
  }
}
