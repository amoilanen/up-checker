package daos

import javax.inject.Inject

import anorm.SqlParser.{int, str}
import anorm.{SQL, ~}
import models.{Group, GroupRequest}
import play.api.db.Database

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

  def create(req: GroupRequest): Option[Long] = {
    db.withConnection(implicit connection => {
      SQL("insert into group_table(name) values ({name})")
        .on('name -> req.name).executeInsert()
    })
  }
}
