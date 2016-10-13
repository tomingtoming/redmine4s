package redmine4s

import org.scalatest.{DiagrammedAssertions, FlatSpec}
import redmine4s.api.resource.ResourceException
import redmine4s.conf.Configuration

class RedmineSpec extends FlatSpec with DiagrammedAssertions {
  "Redmine" should "project senario" in {
    val redmine = RedmineFactory.getInstance(new Configuration {
      override val baseUrl: String = "http://localhost:3000/"
      override val userPassword: Option[(String, String)] = Some(("admin", "00000000"))
      override val apiKey: Option[String] = None
    })
    assert(redmine.listProjects().size === 0)

    val newProject = redmine.createProject(name = "a", identifier = "a")
    assert(newProject.name === "a")
    assert(newProject.identifier === "a")

    val updatedProject = newProject.update(description = Some("hoge"))
    assert(updatedProject.name === "a")
    assert(updatedProject.identifier === "a")
    assert(updatedProject.description === Some("hoge"))

    val issue = redmine.createIssue("hoge", updatedProject.id)
    val count = (for {
      issueStatus <- redmine.listIssueStatus()
      issuePriorities <- redmine.listIssuePriorities()
    } yield {
      val updatedIssue = issue.update(
        subject = Some(issuePriorities.name),
        description = Some(issuePriorities.name),
        priorityId = Some(issuePriorities.id),
        statusId = Some(issueStatus.id)
      )
      assert(updatedIssue.subject === issuePriorities.name)
      assert(updatedIssue.description === Some(issuePriorities.name))
      assert(updatedIssue.priority._1 === issuePriorities.id)
      assert(updatedIssue.priority._2 === issuePriorities.name)
      assert(updatedIssue.status._1 === issueStatus.id)
      assert(updatedIssue.status._2 === issueStatus.name)
      val shownIssue = redmine.showIssue(issue.id)
      assert(updatedIssue.subject === shownIssue.subject)
      assert(updatedIssue.description === shownIssue.description)
      assert(updatedIssue.priority === shownIssue.priority)
      assert(updatedIssue.status === shownIssue.status)
    }).size

    assert(issue.show.journals.get.size === count)
    issue.show.journals.get foreach { journal =>
      println(s"Journal(${journal.id}, ${journal.user}, ${journal.notes}, ${journal.createdOn}):")
      journal.details foreach { detail =>
        println(s"    ${detail.name}, ${detail.property}, ${detail.oldValue} -> ${detail.newValue}")
      }
    }

    issue.delete()
    assertThrows[ResourceException](redmine.showIssue(issue.id))

    updatedProject.delete()

    assertThrows[ResourceException](redmine.showProject(updatedProject.id))
  }
}
