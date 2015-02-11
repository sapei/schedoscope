package com.ottogroup.bi.soda.bottler

import akka.actor.Props
import akka.actor.Actor
import com.ottogroup.bi.soda.dsl.transformations.sql.HiveQl
import com.ottogroup.bi.soda.bottler.driver.HiveDriver
import scala.concurrent._
import akka.event.Logging
import java.sql.SQLException
import org.joda.time.LocalDateTime

class HiveActor(jdbcUrl: String) extends Actor {
  val hiveDriver = HiveDriver.apply(jdbcUrl)
  import context._
  val ec = ExecutionContext.global
  val log = Logging(system, this)
  var startTime = LocalDateTime.now()

  def running(sql: String): Receive = {
    case "tick" =>
    case _: GetStatus => sender() ! new HiveStatusResponse("executing query", self, ProcessStatus.RUNNING, sql, startTime)
    case CommandWithSender(_: KillAction, s) => s ! new InternalError("can't kill hive queries yet")
  }

  override def receive: Receive = {
    case WorkAvailable => sender ! PollCommand("hive")
    case CommandWithSender(h: HiveQl, s) => {
      val actionsRouter = sender
      val requester = s
      val f = future {
        startTime = LocalDateTime.now()
        hiveDriver.runAndWait(h)
      }(ec)
      f.onSuccess {
        case true => {
          requester ! new HiveSuccess
          finish(receive, actionsRouter)
        }
        case false => {
          log.error("hive driver returned false")
          requester ! new HiveError
          finish(receive, actionsRouter)
        }
      }
      f.onFailure {
        case e => {
          log.error(e, "got exception from hivedriver")
          requester ! new HiveError
          finish(receive, actionsRouter)
        }
      }
      become(running(h.sql.head))
    }
    case _: GetStatus => sender ! HiveStatusResponse("idle", self, ProcessStatus.IDLE, "", startTime)

  }

  private def finish(receive: => HiveActor.this.Receive, actionsRouter: akka.actor.ActorRef): Unit = {
    unbecome
    become(receive)
    startTime = LocalDateTime.now()
    actionsRouter ! PollCommand("hive")
  }

}

object HiveActor {
  def props(url: String) = Props(new HiveActor(url))
}