package net.nablux.dockergen

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class DebianHelpersSpec
  extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  class MinimalImage extends DockerImage with BaseImage with DebianHelpers

  var desc: DockerImage with DebianHelpers = null

  before {
    desc = new MinimalImage()
  }

  "An empty description" should "create a minimal Dockerfile" in {
    desc.toDockerString shouldBe
      ("""FROM phusion/baseimage:0.9.15
         |ENV HOME /root
         |RUN /etc/my_init.d/00_regen_ssh_host_keys.sh
         |CMD /sbin/my_init
         |""".stripMargin)
  }

  "aptGetUpdate()" should "include the right apt-get statement" in {
    desc.aptGetUpdate
    desc.toDockerString should
      include("apt-get update")
  }

  "aptGetDistUpgrade()" should "include the right apt-get statement" in {
    desc.aptGetDistUpgrade
    desc.toDockerString should
      include("apt-get -y dist-upgrade")
  }

  "aptGetInstall()" should "include the right apt-get statement" in {
    desc.aptGetInstall("bash")
    desc.toDockerString should
      include("apt-get install -y 'bash'")
  }
}
