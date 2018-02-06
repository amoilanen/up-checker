package controllers

import context.Context
import daos.GroupDao
import models.Group
import models.GroupReadWrites._
import org.scalatestplus.play.PlaySpec
import play.api.mvc.{ControllerComponents, Result, Results}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class GroupControllerSpec extends PlaySpec with Results with MockitoSugar {

  import GroupControllerSpec._

  "GroupController" should {

    "should list existing groups" in {
      val dao = mock[GroupDao]
      when(dao.list).thenReturn(Future.successful(existingGroups))
      val context = mock[Context]
      when(context.dbOperations).thenReturn(implicitly[ExecutionContext])
      val controllerComponents = mock[ControllerComponents]

      val controller = new GroupsController(dao, context, controllerComponents)
      val result: Future[Result] = controller.list.apply(FakeRequest())

      status(result) mustBe OK
      contentAsJson(result).validate[List[Group]] mustBe existingGroups
    }
  }
}

object GroupControllerSpec {
  val existingGroups = List(
    Group(1, "group1"),
    Group(2, "group2"),
    Group(3, "group3")
  )
}