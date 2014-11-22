package net.nablux.dockergen

import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class DockerImageSpec
  extends FlatSpec
  with Matchers
  with BeforeAndAfter {

  class MinimalImage extends DockerImage {
    override def image: String = "test.img"

    override def tag: String = "0.1"
  }

  var desc: DockerImage = null

  before {
    desc = new MinimalImage()
  }

  "An empty description" should "create a minimal Dockerfile" in {
    desc.toDockerString shouldBe "FROM test.img:0.1\n"
  }

  "MAINTAINER()" should "set the MAINTAINER" in {
    desc.MAINTAINER("John Doe", "doe@example.net")
    desc.toDockerString should
      include("\nMAINTAINER John Doe <doe@example.net>\n")
  }

  "ENV()" should "add an environment variable" in {
    desc.ENV("LANG", "de_DE.UTF-8")
    desc.toDockerString should
      include("\nENV LANG de_DE.UTF-8\n")
  }

  "RUN()" should "add a RUN command" in {
    desc.RUN("echo hello")
    desc.toDockerString should
      include("\nRUN echo hello\n")
  }

  "CMD()" should "add a CMD command" in {
    desc.CMD("/bin/bash")
    desc.toDockerString should
      include("\nCMD /bin/bash\n")
  }

  "##()" should "add a comment" in {
    desc.##("Helpful comment")
    desc.toDockerString should
      include("\n# Helpful comment\n")
  }
}
