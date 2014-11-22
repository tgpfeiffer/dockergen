package net.nablux.dockergen

import scala.collection.mutable

trait DockerImage {
  protected var commands = new mutable.MutableList[List[String]]
  protected var maintainer: Option[String] = None

  def image: String

  def tag: String

  protected def addCommand(which: String, params: String*) = {
    commands += (which :: params.toList)
  }

  def toDockerString: String = {
    val sb = new StringBuffer
    // print header
    sb append s"FROM $image:$tag\n"
    maintainer.foreach(m =>
      sb append s"MAINTAINER $m\n")
    // print all commands now
    commands.foreach(cmd => {
      sb append cmd.mkString(" ")
      sb append "\n"
    })
    sb.toString
  }

  def MAINTAINER(name: String, email: String) = {
    maintainer = Some(s"$name <$email>")
  }

  def ENV(name: String, value: String) = {
    addCommand("ENV", name, value)
  }

  def RUN(cmd: String) = {
    addCommand("RUN", cmd)
  }

  def CMD(cmd: String) = {
    addCommand("CMD", cmd)
  }

  def ##(comment: String) = {
    addCommand("#", comment)
  }
}
