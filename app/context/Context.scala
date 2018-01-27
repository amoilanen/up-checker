package context

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

@Singleton
class Context @Inject()(akkaSystem: ActorSystem) {

  implicit val dbOperations: ExecutionContext = akkaSystem.dispatchers.lookup("contexts.db-operations")
}