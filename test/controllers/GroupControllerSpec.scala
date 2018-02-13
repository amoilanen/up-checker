package controllers

import context.Context
import daos.GroupDao
import models.{Group, GroupRequest}
import models.GroupReadWrites._
import org.scalatestplus.play.PlaySpec
import play.api.mvc.{ControllerComponents, Result, Results}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.libs.json.Json
import play.api.test.{FakeHeaders, FakeRequest}
import play.api.test.Helpers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GroupControllerSpec extends PlaySpec with GuiceOneServerPerSuite with MockitoSugar {

  import GroupControllerSpec._

  trait TestCase {
    val dao = mock[GroupDao]
    val controllerComponents = app.injector.instanceOf[ControllerComponents]
    val controller = new GroupsController(dao, controllerComponents)
  }

  "GroupController" should {

    "should list existing groups" in new TestCase {
      when(dao.list).thenReturn(Future.successful(existingGroups))

      val result = controller.list(FakeRequest())

      status(result) mustBe OK
      contentAsJson(result).validate[Seq[Group]].get mustBe existingGroups
    }

    "create a group" in new TestCase {
      val groupRequest = GroupRequest("group1")
      val createdGroupId = 1l
      when(dao.create(groupRequest)).thenReturn(Future.successful(Some(createdGroupId)))
      val request = FakeRequest(POST, "/groups", FakeHeaders(), Json.toJson(groupRequest))

      val result = controller.create(request)

      status(result) mustBe OK
      contentAsJson(result).validate[Long].get mustBe createdGroupId
    }
  }
}

object GroupControllerSpec {
  val existingGroups = Seq(
    Group(1, "group1"),
    Group(2, "group2"),
    Group(3, "group3")
  )
}
