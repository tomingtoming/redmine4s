package redmine4s.internal

object NestedSet {
  case class Node[T](id: T, root: T, left: Long, right: Long)

  type ParentsToChildren[T] = Map[T, Set[T]]
  implicit class RichParentsToChildren[T](parentsToChildren: ParentsToChildren[T]) {
    def generateNodes(id: T, root: T, left: Long): (List[Node[T]], Long) = {
      val (subNodes, right) = parentsToChildren.get(id) match {
        case Some(subTree) =>
          subTree.foldLeft((List.empty[Node[T]], left)) {
            case ((headNodes, lft), child) =>
              val (tailNodes, rgt) = generateNodes(child, root, lft + 1)
              (headNodes ++ tailNodes, rgt)
          }
        case None => (Nil, left)
      }
      (Node(id, root, left, right + 1) :: subNodes, right + 1)
    }

    def generateNodes(roots: List[T]): List[Node[T]] = {
      roots.foldLeft(List.empty[Node[T]], 1L) {
        case ((tailNodes, left), root) =>
          val (headNodes, right) = generateNodes(root, root, left)
          (headNodes ++ tailNodes, right + 1)
      }._1 // take List[Node[T]]
    }
  }

  type ChildrenToParents[T] = Map[T, Option[T]]
  implicit class RichChildrenToParent[T](childrenToParents: ChildrenToParents[T]) {
    def convertToParentsToChildren: ParentsToChildren[T] = {
      def _convertToParentsToChildren(root: Option[T], childrenToParents: ChildrenToParents[T]): ParentsToChildren[T] = {
        val children: Set[T] = childrenToParents.filter(_._2 == root).keySet
        val atThisParent: ParentsToChildren[T] = root match {
          case Some(rootId) => Map(rootId -> children)
          case None => Map.empty[T, Set[T]]
        }
        children.foldLeft(atThisParent) { (parentsToChildren, childId) =>
          parentsToChildren ++ _convertToParentsToChildren(Some(childId), childrenToParents)
        }
      }
      _convertToParentsToChildren(None, childrenToParents)
    }

    def generateNodes()(implicit cmp: Ordering[T]): List[Node[T]] = {
      val roots = childrenToParents.filter(_._2.isEmpty).keys.toList.sorted
      childrenToParents.convertToParentsToChildren.generateNodes(roots)
    }
  }
}
