package net.nablux.dockergen

import net.nablux.dockergen.distributions.Debian

trait BaseImage extends Debian {
  self: DockerImage =>

  // See http://phusion.github.io/baseimage-docker/ for documentation.
  override def image: String = "phusion/baseimage"
  // See https://github.com/phusion/baseimage-docker/blob/master/Changelog.md
  // for version information.
  override def tag: String = "0.9.15"

  // Set correct environment variables.
  ENV("HOME", "/root")

  // Regenerate SSH host keys.
  RUN("/etc/my_init.d/00_regen_ssh_host_keys.sh")

  // Use baseimage-docker's init system.
  CMD("[\"/sbin/my_init\"]")
}
