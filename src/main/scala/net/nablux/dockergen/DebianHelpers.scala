package net.nablux.dockergen

import net.nablux.dockergen.distributions.Debian

trait DebianHelpers {
  self: Debian with DockerImage =>

  def aptGetUpdate() = {
    RUN("apt-get update")
  }

  def aptGetDistUpgrade() = {
    RUN("DEBIAN_FRONTEND=noninteractive apt-get -y dist-upgrade")
  }

  def aptGetInstall(packages: String*) = {
    RUN("DEBIAN_FRONTEND=noninteractive apt-get install -y " +
      packages.map("'" + _ + "'").mkString(" "))
  }
}
