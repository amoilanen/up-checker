package controllers

import javax.inject.Inject

import play.api._
import play.api.mvc._
import models.{Group, GroupRequest, GroupDao}
import models.GroupReadWrites._
import play.api.libs.json.{JsValue, Json}

class GroupsController @Inject()(dao: GroupDao, cc:ControllerComponents) extends AbstractController(cc)  {

  def list = Action {
    Ok(Json.toJson(dao.list()))
  }

  def create = Action(parse.json) { request: Request[JsValue]  =>
    val jsResult = request.body.validate[GroupRequest]

    jsResult.fold(
      invalid = {
        fieldErrors =>
          BadRequest
      },
      valid = {
        groupRequest => {
          dao.create(groupRequest)
          Ok
        }
      }
    )
  }
}