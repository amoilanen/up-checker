package controllers

import javax.inject.Inject

import daos.GroupDao
import play.api.mvc._
import models.GroupRequest
import models.GroupReadWrites._
import play.api.libs.json.{JsValue, Json}

class GroupsController @Inject()(dao: GroupDao, cc:ControllerComponents) extends AbstractController(cc)  {

  def list = Action {
    Ok(Json.toJson(dao.list()))
  }

  def create = Action(parse.json) { request =>
    request.body.validate[GroupRequest].fold(
      invalid = _ => BadRequest,
      valid = groupRequest => {
        dao.create(groupRequest)
        Ok
      }
    )
  }
}