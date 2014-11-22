package net.nablux.dockergen

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class BaseImageSpec
  extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  class MinimalImage extends DockerImage with BaseImage

  var desc: DockerImage = null

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
}
