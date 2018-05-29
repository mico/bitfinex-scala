package com.mico.bitfinex

import akka.actor.{ActorSystem, ActorRef}
import akka.{Done, NotUsed}
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.ws._
import com.typesafe.scalalogging.LazyLogging
import spray.json.{JsNumber, JsObject, JsString}

import scala.concurrent.Future

object Main extends App with LazyLogging {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  // print each incoming strict text message
  val printSink: Sink[Message, Future[Done]] =
    Sink.foreach {
      case message: TextMessage.Strict =>
        println(message.text)
    }

  val webSocketFlow: Flow[Message, Message, Future[WebSocketUpgradeResponse]] =
    Http().webSocketClientFlow(WebSocketRequest("wss://api.bitfinex.com/ws/2"))

  val messageSource: Source[Message, ActorRef] =
    Source.actorRef[TextMessage.Strict](bufferSize = 10, OverflowStrategy.fail)

  val ((ws, upgradeResponse), closed) =
    messageSource
      .viaMat(webSocketFlow)(Keep.both)
      .toMat(printSink)(Keep.both)
      .run()

  closed.foreach(_ => println("closed"))

  ws ! TextMessage.Strict(JsObject(
    "event" -> JsString("subscribe"),
    "channel" -> JsString("book"),
    "symbol" -> JsString("ETHBTC"),
    "len" -> JsNumber(100)
  ).toString())
}
