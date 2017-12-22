package models

import javax.inject.Inject

import play.api.libs.json._
import play.api.libs.functional.syntax._
import anorm._
import anorm.SqlParser._
import play.api.db.Database

case class Group(id: Int, name: String)

object GroupReadWrites {

  implicit val groupWrites = new Writes[Group] {
    def writes(group: Group) = Json.obj(
      "name" -> group.name
    )
  }
}

class GroupDao @Inject()(db: Database) {

  val groupParser =
    int("id") ~
    str("name") map {
      case id ~ name => Group(id, name)
    }

  def list(): Seq[Group] =
    db.withConnection(implicit connection => {
      SQL("select * from group_table").as(groupParser.*)
    })
}