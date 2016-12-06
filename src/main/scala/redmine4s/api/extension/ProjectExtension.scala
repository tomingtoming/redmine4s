package redmine4s.api.extension

import redmine4s.api.model.Project

trait ProjectExtension {

  case class ProjectTree(id: Long, name: String, children: Iterable[ProjectTree])

  implicit class RichProjects(projects: Iterable[Project]) {
    def toIdMap: Map[Long, Project] = projects.map(p => (p.id, p)).toMap

    def toProjectTree: Iterable[ProjectTree] = {
      def toProjectTreeSub(parent: Option[Long], projects: Iterable[Project]): Iterable[ProjectTree] = {
        projects.filter(_.parent.map(_._1) == parent).map { project: Project =>
          ProjectTree(project.id, project.name, toProjectTreeSub(Some(project.id), projects))
        }
      }

      toProjectTreeSub(None, projects)
    }

    def toSortedProjectTree[B](order: ProjectTree => B)(implicit ord: Ordering[B]): Seq[ProjectTree] = {
      def toSortedProjectTreeSub(order: ProjectTree => B, projectTrees: Iterable[ProjectTree]): Seq[ProjectTree] = {
        projectTrees.toSeq.sortBy(order).map { projectTree =>
          projectTree.copy(children = toSortedProjectTreeSub(order, projectTree.children))
        }
      }

      toSortedProjectTreeSub(order, toProjectTree)
    }
  }

}
