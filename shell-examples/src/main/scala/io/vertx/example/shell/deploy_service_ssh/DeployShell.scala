package io.vertx.example.shell.deploy_service_ssh

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.example.util.Runner

/** Launch this program, then type: {@code ssh -p 3000 admin@localhost} password is {@code password}.
 * @see <a href="https://github.com/vert-x3/vertx-examples/tree/master/shell-examples">shell examples</a>
 * @author <a href="mailto:mslinn@micronauticsresearch.com">Mike Slinn</a> (who converted Julien Viet's original Java version) */
object DeployShell extends App {
  Runner.runExample(classOf[DeployShell])
}

class DeployShell extends AbstractVerticle {
  def start(startFutureFuture[Unit]): Unit = {
    val options = new JsonObject.put("sshOptions",
      new JsonObject.
        put("host", "localhost").
        put("port", 3000).
        put("keyPairOptions", new JsonObject.
            put("path", "keystore.jks").
            put("password", "wibble")).
        put("authOptions", new JsonObject.put("provider", "shiro").put("config",
                new JsonObject.put("properties_path", "auth.properties"))

        )
    )
    vertx.deployVerticle("service:io.vertx.ext.shell", new DeploymentOptions.setConfig(options), ar => {
      if (ar.succeeded) {
        startFuture.succeeded
      } else {
        startFuture.fail(ar.cause)
      }
    })
  }
}
