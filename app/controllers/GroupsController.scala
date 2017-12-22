package controllers

import javax.inject.Inject

import play.api._
import play.api.mvc._
import models.{Group, GroupDao}
import models.GroupReadWrites._
import play.api.libs.json.Json

class GroupsController @Inject()(dao: GroupDao, cc:ControllerComponents) extends AbstractController(cc)  {

  def list = Action {
    Ok(Json.toJson(dao.list()))
  }
}