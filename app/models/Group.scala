package models

import javax.inject.Inject

import play.api.libs.json._
import play.api.libs.functional.syntax._
import anorm._
import anorm.SqlParser._
import play.api.db.Database

case class Group(id: Int, name: String)

case class GroupRequest(name: String)

object GroupReadWrites {

  implicit val groupFormat = Json.format[Group]
  implicit val groupRequestFormat = Json.format[GroupRequest]
}