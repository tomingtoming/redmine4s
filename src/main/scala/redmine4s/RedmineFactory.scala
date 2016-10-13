package redmine4s

import redmine4s.conf.Configuration

object RedmineFactory {
  /** Returns default singleton Redmine instance. */
  lazy val getSingleton: Redmine = new Redmine(Configuration.default)
  /** Returns new created Redmine instance. */
  def getInstance(conf: Configuration): Redmine = new Redmine(conf)
}
