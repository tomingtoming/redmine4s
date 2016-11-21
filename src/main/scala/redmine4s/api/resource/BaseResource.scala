package redmine4s.api.resource

import org.apache.http.client.HttpClient
import org.apache.http.client.methods.{HttpDelete, HttpGet, HttpPost, HttpPut}
import org.apache.http.client.utils.HttpClientUtils
import org.apache.http.entity.{ContentType, StringEntity}
import org.slf4j.Logger
import play.api.libs.json._
import redmine4s.Redmine
import redmine4s.auth.Authorization
import redmine4s.conf.Configuration

trait BaseResource {
  protected val logger: Logger
  protected val redmine: Redmine
  protected val configuration: Configuration
  protected val httpClient: HttpClient = configuration.httpClient
  protected val authorization: Option[Authorization] = Authorization.fromConfiguration(configuration)

  protected def list[T](url: String, targetJsPath: JsPath, params: Map[String, String])(implicit fjs: Reads[T]): Iterator[T] = {
    new Iterator[T] {
      private val limit = 100
      private var offset = 0
      private var (totalCount, fetchedTargets) = _list(offset)

      private def _list(offset: Int): (Int, Iterator[T]) = {
        val requestParameter = (params +("limit" -> limit, "offset" -> offset.toString)) map {
          case (key, value) => key + "=" + value
        } mkString "&"
        val get = new HttpGet(configuration.baseUrl + url + "?" + requestParameter)
        authorization.foreach { auth => get.addHeader(auth.header._1, auth.header._2) }
        val response = httpClient.execute(get)
        try {
          response.getStatusLine.getStatusCode match {
            case 200 =>
              val responseJson = Json.parse(response.getEntity.getContent)
              logger.debug(s"url=$url, responseJson=$responseJson")
              val totalCount = (responseJson \ "total_count").asOpt[Int].getOrElse(0)
              targetJsPath.read[Iterator[T]].reads(responseJson).asEither match {
                case Left(errors) => throw JsResultException(errors)
                case Right(targets) => (totalCount, targets)
              }
            case 403 /*Forbidden*/ =>
              logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
              throw new ForbiddenException(response.getStatusLine.toString)
            case n =>
              logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
              throw new ResourceException(response.getStatusLine.toString)
          }
        } finally {
          HttpClientUtils.closeQuietly(response)
        }
      }

      override def hasNext: Boolean = this.synchronized {
        if (fetchedTargets.hasNext) {
          true
        } else if (limit + offset < totalCount) {
          offset += limit
          fetchedTargets = _list(offset)._2
          this.hasNext
        } else {
          false
        }
      }

      override def next(): T = this.synchronized(fetchedTargets.next)
    }
  }

  protected def show[T](url: String, targetJsPath: JsPath, params: Map[String, String] = Map.empty)(implicit fjs: Reads[T]): T = {
    val requestParameter = params map {
      case (key, value) => key + "=" + value
    } mkString "&"
    val get = new HttpGet(configuration.baseUrl + url + "?" + requestParameter)
    authorization.foreach { auth => get.addHeader(auth.header._1, auth.header._2) }
    val response = httpClient.execute(get)
    try {
      response.getStatusLine.getStatusCode match {
        case 200 =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.debug(s"url=$url, responseJson=$responseJson")
          targetJsPath.read[T].reads(responseJson).asEither match {
            case Left(errors) => throw JsResultException(errors)
            case Right(target) => target
          }
        case 403 /*Forbidden*/ =>
          logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case 404 /*Not Found*/ =>
          logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
          throw new NotFoundException(response.getStatusLine.toString)
        case n =>
          logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }

  protected def add[T](url: String, targetJsPath: JsPath, params: T)(implicit jsw: Writes[T]): Unit = {
    val post = new HttpPost(configuration.baseUrl + url)
    authorization.foreach { auth => post.addHeader(auth.header._1, auth.header._2) }
    val requestJson = Json.toJson(params)
    post.setEntity(new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON))
    val response = httpClient.execute(post)
    try {
      response.getStatusLine.getStatusCode match {
        case 201 /*Created*/ =>
          logger.debug(s"url=$url, requestJson=$requestJson")
        case 403 /*Forbidden*/ =>
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case 422 /*Unprocessable Entity*/ =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}, responseJson=$responseJson")
          throw new UnprocessableEntityException(s"status_line=${response.getStatusLine.toString}, responseJson=$responseJson")
        case n =>
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }

  protected def create[T, U](url: String, targetJsPath: JsPath, params: T)(implicit jsw: Writes[T], jsr: Reads[U]): U = {
    val post = new HttpPost(configuration.baseUrl + url)
    authorization.foreach { auth => post.addHeader(auth.header._1, auth.header._2) }
    val requestJson = Json.toJson(params)
    post.setEntity(new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON))
    val response = httpClient.execute(post)
    try {
      response.getStatusLine.getStatusCode match {
        case 201 /*Created*/ =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.debug(s"url=$url, requestJson=$requestJson, responseJson=$responseJson")
          targetJsPath.read[U].reads(responseJson).asEither match {
            case Left(errors) => throw JsResultException(errors)
            case Right(targets) => targets
          }
        case 403 /*Forbidden*/ =>
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case 422 /*Unprocessable Entity*/ =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}, responseJson=$responseJson")
          throw new UnprocessableEntityException(s"status_line=${response.getStatusLine.toString}, responseJson=$responseJson")
        case n =>
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }

  protected def update[T](url: String, params: T)(implicit jsw: Writes[T]): Unit = {
    val put = new HttpPut(configuration.baseUrl + url)
    authorization.foreach { auth => put.addHeader(auth.header._1, auth.header._2) }
    val requestJson = Json.toJson(params)
    put.setEntity(new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON))
    val response = httpClient.execute(put)
    try {
      response.getStatusLine.getStatusCode match {
        case 200 =>
          logger.debug(s"url=$url, requestJson=$requestJson")
        case 403 /*Forbidden*/ =>
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case 422 /*Unprocessable Entity*/ =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}, responseJson=$responseJson")
          throw new UnprocessableEntityException(s"status_line=${response.getStatusLine.toString}, responseJson=$responseJson")
        case n =>
          logger.error(s"url=$url, requestJson=$requestJson, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }

  protected def delete[T](url: String): Unit = {
    val delete = new HttpDelete(configuration.baseUrl + url)
    authorization.foreach { auth => delete.addHeader(auth.header._1, auth.header._2) }
    val response = httpClient.execute(delete)
    try {
      response.getStatusLine.getStatusCode match {
        case 200 =>
          logger.debug(s"url=$url")
        case 403 /*Forbidden*/ =>
          logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case 422 /*Unprocessable Entity*/ =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
          throw new UnprocessableEntityException(response.getStatusLine.toString)
        case n =>
          logger.error(s"url=$url, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }
}
