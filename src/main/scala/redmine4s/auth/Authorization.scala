package redmine4s.auth

import java.util.Base64

import redmine4s.conf.Configuration

object Authorization {
  def fromConfiguration(configuration: Configuration): Option[Authorization] = {
    (configuration.apiKey map { apiKey =>
      new APIKeyAuthorization(apiKey)
    }) orElse (configuration.userPassword map { case (user, password) =>
      new BasicAuthorization(user, password)
    })
  }
}

sealed trait Authorization {
  val header: (String, String)
}

class APIKeyAuthorization(val apiKey: String) extends Authorization {
  override val header: (String, String) = ("X-Redmine-API-Key", apiKey)

  override def toString: String = s"APIKeyAuthorization{apiKey=****************}"
}

class BasicAuthorization(val user: String, val password: String) extends Authorization {
  override val header: (String, String) = {
    val key = "Authorization"
    val value = "Basic " + new String(Base64.getEncoder.encode(s"$user:$password".getBytes))
    (key, value)
  }

  override def toString: String = s"BasicAuthorization{user='$user', password=********}"
}
