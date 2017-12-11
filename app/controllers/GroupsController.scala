package controllers

import javax.inject.Inject

import play.api._
import play.api.mvc._
import models.Group
import models.GroupReadWrites._
import play.api.libs.json.Json

class GroupsController @Inject()(cc:ControllerComponents) extends AbstractController(cc)  {

  def list = Action {
    val groups = List(
      Group("group1"),
      Group("group2"),
      Group("group3")
    )

    Ok(Json.toJson(groups))
  }
}