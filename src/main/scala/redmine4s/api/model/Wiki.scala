package redmine4s.api.model

import org.joda.time.DateTime
import redmine4s.Redmine

case class WikiIndex(title: String, parent: Option[String], version: Int, createdOn: DateTime, updatedOn: DateTime, projectId: Long, redmine: Redmine) extends WikiBase

case class Wiki(title: String, parent: Option[String], version: Int, createdOn: DateTime, updatedOn: DateTime, text: String, author: (Long, String), comments: String, attachments: Option[Seq[Attachment]], projectId: Long, redmine: Redmine) extends WikiBase

sealed trait WikiBase {
  val projectId: Long
  val title: String
  val redmine: Redmine

  /** Getting a wiki page */
  def show(): Wiki = redmine.showWiki(projectId, title)

  /** Getting an old version of a wiki page */
  def showOld(version: Int): Wiki = redmine.showOldWiki(projectId, title, version)

  /** Creating or updating a wiki page */
  def update(text: Option[String] = None,
             comments: Option[String] = None,
             version: Option[Int] = None,
             uploadFiles: Option[Seq[UploadFile]] = None): Wiki = redmine.updateWiki(projectId, title, text, comments, version, uploadFiles)

  /** Deleting a wiki page */
  def delete(): Unit = redmine.deleteWiki(projectId, title)
}
