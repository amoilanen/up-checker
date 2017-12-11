package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Group(name: String)

object GroupReadWrites {

  implicit val groupWrites = new Writes[Group] {
    def writes(group: Group) = Json.obj(
      "name" -> group.name
    )
  }
}