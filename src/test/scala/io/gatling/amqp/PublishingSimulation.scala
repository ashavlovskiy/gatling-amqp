package io.gatling.amqp


import com.rabbitmq.client.AMQP.BasicProperties
import io.gatling.amqp.Predef._
import io.gatling.amqp.config._
import io.gatling.amqp.data._
import io.gatling.amqp.request.builder.AmqpRequestBuilder
import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.config.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.util.Random

class PublishingSimulation extends Simulation {
  implicit val amqpProtocol: AmqpProtocol = amqp
    .host("192.168.255.156")
    .port(5672)
    .auth("guest", "victoriabrides15672")
    .poolSize(10)
    .confirmMode()

  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .baseURL("https://victoriahearts.com")
    .wsBaseURL("wss://victoriahearts.com")


  //  def someFunc(f: String) = f + " this is string"

  // () => {}
  // b :=> A
  // b: A

  // A <: B
  // A >: B

  //
  //  def someAnonFunc(arg: BasicProperties.Builder): Unit = {
  //    arg.appId("autotest")
  //    arg.contentType("application/json")
  //    arg.deliveryMode(2)
  //  }

  val HexFeeder = Iterator.continually {
    Map(
      "RandomHash" ->
        "1b4baef96a23c0c6cf875ac868ad4e34",
      //      string2hex(kestrel(Array.fill[Byte](16)(0))(Random.nextBytes).toString),

      "RandomText" ->
        ("autotest" + Random.alphanumeric.take(20).mkString)
    )
  }

  //  def getHex: String = {
  //        string2hex(kestrel(Array.fill[Byte](16)(0))(Random.nextBytes).toString)
  //      }

  def string2hex(str: String): String = {
    str.toList.map(_.toInt.toHexString).mkString
  }

  def kestrel[A](x: A)(f: A => Unit): A = {
    f(x)
    x
  }

  def getRegistrationBody: String = {
    //    JSONObject(Map("method" -> "register",
    //      "params" -> JSONObject(Map("key" -> "${RandomHash}")))).toString()
    "{\"method\":\"register\",\"params\":{\"key\":\"${RandomHash}\"}}"
  }

  def getMessageBody(hash: String, message: String): String = {
    //    "{\"data\":{\"method\":\"send_interaction\",\"params\":{\"pushId\":[\"${RandomHash}" +
    "{\"data\":{\"method\":\"send_interaction\",\"params\":{\"pushId\":[\"" + hash +
      "\"],\"events\":{\"message\":{\"message\":{\"id_user_to\":209386,\"id_user_from\":268924,\"type\":\"message\",\"time\":1506071738,\"content\":\"{\\\"id\\\":0,\\\"message\\\":\\\"" +
      message + "\\\"}\",\"id\":11332,\"date_created\":\"2017-09-28 15:15:38\",\"id_dialog\":4805,\"status\":\"unread\"},\"profiles\":{\"268924\":{\"avatar_xxs\":\"\\/img\\/defaults\\/avatar\\/dc5a01.png\",\"avatar_xl\":\"\\/img\\/defaults\\/avatar\\/5154fa.png\",\"avatar_large\":\"\\/img\\/defaults\\/avatar\\/c788bb.png\",\"avatar_small\":\"\\/img\\/defaults\\/avatar\\/7ff124.png\",\"audioEnabled\":0,\"name\":\"misha.molokov\",\"description\":\"\",\"id\":189586,\"id_user\":268924,\"age\":19,\"count_photos\":0,\"count_videos\":0,\"credits_premium\":2611,\"credits_free\":463,\"occupation\":\"\",\"country\":\"Ukraine\",\"city\":\"Kiev\",\"gender\":\"male\",\"is_online\":true,\"is_premium\":false,\"id_partner\":\"0\"},\"209386\":{\"avatar_xxs\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r35x35.5e08e5ca.jpg\",\"avatar_xl\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r220x200.0316fab8.jpg\",\"avatar_large\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r100x100.7e439b0b.jpg\",\"avatar_small\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r50x50.56374d4a.jpg\",\"audioEnabled\":1,\"name\":\"Alinka-malinka\",\"description\":\"In my spare time I like going to the movies, watching mostly comedies.I love to cook, I have finished courses of culinary. I am fond of sea, it is very important for me, because there I can do yoga in the morning in the fresh air and also I enjoy swimming and sunbathing. I haven&#039;t travelled a lot, I have been to Crimea and I adore Yalta and Bakhchisarai. I also go in for sports, running. I remember when I won the race at a distance of 5 km.\",\"id\":130895,\"id_user\":209386,\"age\":40,\"count_photos\":4,\"count_videos\":0,\"credits_premium\":0,\"credits_free\":0,\"occupation\":\"\\teconomist\",\"country\":\"Ukraine\",\"city\":\"Pavlograd\",\"gender\":\"female\",\"is_online\":false,\"is_premium\":true,\"id_partner\":\"0\"}}}}}}}"

    //    "{\"data\":{\"method\":\"send_interaction\",\"params\":{\"pushId\":[\"1b4baef96a23c0c6cf875ac868ad4e34" +
    //      "\"],\"events\":{\"message\":{\"message\":{\"id_user_to\":209386,\"id_user_from\":268924,\"type\":\"message\",\"time\":1506071738,\"content\":\"{\\\"id\\\":0,\\\"message\\\":\\\"" + "${RandomText}" + "\\\"}\",\"id\":11332,\"date_created\":\"2017-09-28 15:15:38\",\"id_dialog\":4805,\"status\":\"unread\"},\"profiles\":{\"268924\":{\"avatar_xxs\":\"\\/img\\/defaults\\/avatar\\/dc5a01.png\",\"avatar_xl\":\"\\/img\\/defaults\\/avatar\\/5154fa.png\",\"avatar_large\":\"\\/img\\/defaults\\/avatar\\/c788bb.png\",\"avatar_small\":\"\\/img\\/defaults\\/avatar\\/7ff124.png\",\"audioEnabled\":0,\"name\":\"misha.molokov\",\"description\":\"\",\"id\":189586,\"id_user\":268924,\"age\":19,\"count_photos\":0,\"count_videos\":0,\"credits_premium\":2611,\"credits_free\":463,\"occupation\":\"\",\"country\":\"Ukraine\",\"city\":\"Kiev\",\"gender\":\"male\",\"is_online\":true,\"is_premium\":false,\"id_partner\":\"0\"},\"209386\":{\"avatar_xxs\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r35x35.5e08e5ca.jpg\",\"avatar_xl\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r220x200.0316fab8.jpg\",\"avatar_large\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r100x100.7e439b0b.jpg\",\"avatar_small\":\"https:\\/\\/ni.onthe.io\\/shpzkl28if82hu5plg.r50x50.56374d4a.jpg\",\"audioEnabled\":1,\"name\":\"Alinka-malinka\",\"description\":\"In my spare time I like going to the movies, watching mostly comedies.I love to cook, I have finished courses of culinary. I am fond of sea, it is very important for me, because there I can do yoga in the morning in the fresh air and also I enjoy swimming and sunbathing. I haven&#039;t travelled a lot, I have been to Crimea and I adore Yalta and Bakhchisarai. I also go in for sports, running. I remember when I won the race at a distance of 5 km.\",\"id\":130895,\"id_user\":209386,\"age\":40,\"count_photos\":4,\"count_videos\":0,\"credits_premium\":0,\"credits_free\":0,\"occupation\":\"\\teconomist\",\"country\":\"Ukraine\",\"city\":\"Pavlograd\",\"gender\":\"female\",\"is_online\":false,\"is_premium\":true,\"id_partner\":\"0\"}}}}}}}"

  }

  val x = Exchange("amq.topic", "direct")

  def getRequest(hash: String, message: String) = PublishRequest(exchange = x, routingKey = "vb_push", body = getMessageBody(hash, message))
    .persistent
    .withProps[BasicProperties.Builder](
    _
      .appId("autotest")
      .contentType("application/json")
      .deliveryMode(2)
  )

  //  }

  //  object BasicConnection {

  object chat {
    def sendMessage(amqpString: String)(implicit session: Session) = {
      var hash = session("RandomHash").as[String]
      var text = session("RandomText").as[String]
      amqp(amqpString)
        .publish(getRequest(hash, text))
      println("brbrbr")
      println(hash)
      println(text)
    }
    var hash = "1b4baef96a23c0c6cf875ac868ad4e34"
    var text = "ddddddd"

    val start: ChainBuilder =
      feed(HexFeeder)
        .exec(
          http("HTTP REST get info for WS")
            .get("/push/info")
            .check(status is 200)
        )
        .exec(
          ws("Open connection to WS")
            .open("/push/websocket")
        )
        .exec(
          ws("Registration in WS")
            //            .sendText(_ => getRegistrationBody)
            .sendText(getRegistrationBody)
            .check(wsAwait.within(10).until(1).jsonPath("$.result.register"))
        )
        .pause(5)
        .repeat(1) {
          repeat(1) {
            exec {
              session => {
                hash = session("RandomHash").as[String]
                text = session("RandomText").as[String]
                println(hash, text)

                session
              }
            }.
              //                      exec {

              //                                    session =>
              //                        println("11111111111")
              //            //              println(getMessageBody(session("RandomHash").as[String], session("RandomText").as[String]))
              //            //              amqp("Publish message to Rabbitmq")
              //            //                .publish(getRequest(session("RandomHash").as[String], session("RandomText").as[String]))
              //            println(StringBody("${RandomHash}").bytes.toString())
              //
              //
              //            var hash = session("RandomHash").as[String]
              //                                      var text = session("RandomText").as[String]
              //                                      println(hash)
              //                                      println(text)
              //                        amqp("Publish message to Rabbitmq")
              //                          .publish("vb_push_queue", getMessageBody("1b4baef96a23c0c6cf875ac868ad4e34", "texttexttext"))

              //
              //            //              session.set(StringBody(getMessageBody("${RandomHash"},"${RandomText}")).toString"message_text", getMessageBody(session("RandomHash").as[String], session("RandomText").as[String]))
              //                      }
              //            sendMessage
              //            group("send message") {
              //              var text = "ttt"
              //              val x = Exchange("amq.topic", "direct")
              //              exec { session =>
              //                text = session("RandomHash").as[String]
              //                session
              //              }
              //              val session: Session = Session("MySCN", "123")
              //              val test2 = session("foo").as[String]
              //
              //              println(test2)
              ////              exec (_.get("RandomHash").as[String]))
              //              exec(amqp("Publish message to Rabbitmq")
              //                .publish(PublishRequest(
              //                  exchange = x
              //                  , routingKey = "vb_push"
              //                  , body = getMessageBody("1b4baef96a23c0c6cf875ac868ad4e34", text)
              //                ).persistent
              //                  .withProps[BasicProperties.Builder](
              //                  _
              //                    .appId("autotest")
              //                    .contentType("application/json")
              //                    .deliveryMode(2)
              //                )))
              //            }
              //            exec(amqp("Publish message to Rabbitmq").publish(getRequest("1b4baef96a23c0c6cf875ac868ad4e34", "texttexttext")))
              //                          .exec(amqp("Publish message to Rabbitmq").publish(getRequest("1b4baef96a23c0c6cf875ac868ad4e34", "texttexttext"))
              //                          )
              exec(amqp("SOME STRING")
              .publish(getRequest(hash, text)))
              .
                exec(
                  ws("Check message in WS")
                    .check(wsAwait.within(20).until(1).regex(".*").saveAs("RESPONSE_DATA"))
                )
            //            .pause(Random.nextInt(5))
          }
          //          .pause(20 + Random.nextInt(20))
        }
        //      .pause(5)
        .exec(ws("Close WS").close)

  }

  val theWSScenarioBuilder: ScenarioBuilder = scenario("Test VB chat via Rabbitmq").repeat(1) {
    exec(chat.start)
  }

  setUp(theWSScenarioBuilder.inject(rampUsers(1) over (10 seconds))).protocols(amqpProtocol, theHttpProtocolBuilder)


}
