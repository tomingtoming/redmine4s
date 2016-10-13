package redmine4s.internal

import org.scalatest.{FlatSpec, Matchers}
import NestedSet._

class NestedSetSpec extends FlatSpec with Matchers {
  val normalSampleTree = Map(
    1 -> None, 2 -> Some(1), 3 -> Some(1), 4 -> Some(1), 5 -> Some(2), 6 -> Some(2), 7 -> Some(4), 8 -> Some(4), 9 -> Some(7), 10 -> None
  )
  val deepSampleTree = Map(
    1 -> None, 2 -> Some(1), 3 -> Some(2), 4 -> Some(3), 5 -> Some(4), 6 -> Some(5), 7 -> Some(6), 8 -> Some(7), 9 -> Some(8), 10 -> None
  )
  val emptySampleTree = Map.empty[Int, Option[Int]]

  "ChildrenToParents.generateNodes" should "satisfies specifications with normal sample tree" in {
    val nodes = normalSampleTree.generateNodes
    assert(nodes.find(_.id == 0) === None)
    assert(nodes.find(_.id == 1) === Some(Node(1, 1, 1, 18)))
    assert(nodes.find(_.id == 2) === Some(Node(2, 1, 2, 7)))
    assert(nodes.find(_.id == 3) === Some(Node(3, 1, 8, 9)))
    assert(nodes.find(_.id == 4) === Some(Node(4, 1, 10, 17)))
    assert(nodes.find(_.id == 5) === Some(Node(5, 1, 3, 4)))
    assert(nodes.find(_.id == 6) === Some(Node(6, 1, 5, 6)))
    assert(nodes.find(_.id == 7) === Some(Node(7, 1, 11, 14)))
    assert(nodes.find(_.id == 8) === Some(Node(8, 1, 15, 16)))
    assert(nodes.find(_.id == 9) === Some(Node(9, 1, 12, 13)))
    assert(nodes.find(_.id == 10) === Some(Node(10, 10, 19, 20)))
    assert(nodes.find(_.id == 11) === None)
  }
  it should "satisfies specifications with deep sample tree" in {
    val nodes = deepSampleTree.generateNodes
    assert(nodes.find(_.id == 0) === None)
    assert(nodes.find(_.id == 1) === Some(Node(1, 1, 1, 18)))
    assert(nodes.find(_.id == 2) === Some(Node(2, 1, 2, 17)))
    assert(nodes.find(_.id == 3) === Some(Node(3, 1, 3, 16)))
    assert(nodes.find(_.id == 4) === Some(Node(4, 1, 4, 15)))
    assert(nodes.find(_.id == 5) === Some(Node(5, 1, 5, 14)))
    assert(nodes.find(_.id == 6) === Some(Node(6, 1, 6, 13)))
    assert(nodes.find(_.id == 7) === Some(Node(7, 1, 7, 12)))
    assert(nodes.find(_.id == 8) === Some(Node(8, 1, 8, 11)))
    assert(nodes.find(_.id == 9) === Some(Node(9, 1, 9, 10)))
    assert(nodes.find(_.id == 10) === Some(Node(10, 10, 19, 20)))
    assert(nodes.find(_.id == 11) === None)
  }
  it should "satisfies specifications with empty tree" in {
    val nodes = emptySampleTree.generateNodes
    assert(nodes.isEmpty)
  }
}
