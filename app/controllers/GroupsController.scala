package controllers

import javax.inject.Inject

import context.Context
import daos.GroupDao
import play.api.mvc._
import models.GroupRequest
import models.GroupReadWrites._
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

class GroupsController @Inject()(dao: GroupDao, cc: ControllerComponents)(implicit executionContext: ExecutionContext) extends AbstractController(cc)  {

  def list = Action.async {
    dao.list.map(
      groups => Ok(Json.toJson(groups))
    )
  }

  def create = Action.async(parse.json) { request =>
    request.body.validate[GroupRequest].fold(
      invalid = _ => Future.successful(BadRequest),
      valid = groupRequest => {
        dao.create(groupRequest).map(groupId => {
          Ok(Json.toJson(groupId))
        })
      }
    )
  }

  def update(groupId: Long) = Action.async(parse.json) { request =>
    request.body.validate[GroupRequest].fold(
      invalid = _ => Future.successful(BadRequest),
      valid = groupRequest => {
        dao.update(groupId, groupRequest).map(updatedSuccessfully => {
          if (updatedSuccessfully) {
            Ok
          } else {
            Conflict
          }
        })
      }
    )
  }
}