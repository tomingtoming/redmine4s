package redmine4s.conf

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClients

object Configuration {
  def default: Configuration = new Configuration {
    override val baseUrl: String = sys.env.getOrElse("REDMINE_URL", throw new ConfigurationNotFoundException("""Environment variable not found "REDMINEURL""""))
    override val userPassword: Option[(String, String)] = for {
      user <- sys.env.get("REDMINE_USER")
      password <- sys.env.get("REDMINE_PASSWORD")
    } yield (user, password)
    override val apiKey: Option[String] = sys.env.get("REDMINE_APIKEY")
  }
}

trait Configuration {
  def baseUrl: String
  def userPassword: Option[(String, String)] = None
  def apiKey: Option[String] = None
  def httpClient: HttpClient = HttpClients.createDefault()
}
