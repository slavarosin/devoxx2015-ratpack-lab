package lab01;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.util.Objects;

public class Lab01 {

  final static Logger log = LoggerFactory.getLogger(Lab01.class);

  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> {
        ratpackServerSpec
          .serverConfig(s -> s.baseDir(BaseDir.find()))
          .handlers(chain ->
            chain.
              get("user/:username?", c -> {
                String username = c.getPathTokens().get("username");
                if (username == null) {
                  c.render("user");
                } else {
                  c.render("user/" + username);
                }
              }).
              get("user/:username?/:operation?", c -> {
                c.render("user/" + c.getPathTokens().get("username") + "/" + c.getPathTokens().get("operation"));
              }).
              all(c -> c.render("Hello Devoxx!"))
          );
      }
    );
  }
}
