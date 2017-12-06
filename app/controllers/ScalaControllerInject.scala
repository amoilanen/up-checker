package controllers
import javax.inject.Inject

import play.api.db.DBApi
import play.api.mvc._

class ScalaControllerInject @Inject()(dbApi: DBApi, val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action {
    var outString = "Number is "
    val conn = dbApi.databases().head.getConnection()

    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT 9 as testkey ")

      while (rs.next()) {
        outString += rs.getString("testkey")
      }
    } finally {
      conn.close()
    }
    Ok(outString)
  }

}
