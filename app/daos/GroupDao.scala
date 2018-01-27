package daos

import javax.inject.Inject

import anorm.SqlParser.{int, str}
import anorm.{SQL, ~}
import context.Context
import models.{Group, GroupRequest}
import play.api.db.Database

import scala.concurrent.Future

class GroupDao @Inject()(db: Database, context: Context) {

  implicit val executionContext = context.dbOperations

  val groupParser =
    int("id") ~
    str("name") map {
      case id ~ name => Group(id, name)
    }

  def list(): Future[Seq[Group]] = Future {
    db.withConnection(implicit connection => {
      SQL("select * from group_table").as(groupParser.*)
    })
  }

  def create(groupRequest: GroupRequest): Future[Option[Long]] = Future {
    db.withConnection(implicit connection => {
      SQL("insert into group_table(name) values ({name})")
        .on('name -> groupRequest.name).executeInsert()
    })
  }

  def update(groupId: Long, groupRequest: GroupRequest): Future[Boolean] = Future {
    db.withConnection(implicit connection => {
      val updatedRowNumber = SQL(
        """
          | update group_table
          | set name = {name}
          | where id = {id}
        """.stripMargin)
        .on(
          'id -> groupId,
          'name -> groupRequest.name
        ).executeUpdate()
      updatedRowNumber > 0
    })
  }
}
