package lab05;

import ratpack.guice.Guice;
import ratpack.handlebars.HandlebarsModule;
import ratpack.handlebars.Template;
import ratpack.render.Renderer;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Lab05 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .serverConfig(s -> s.baseDir(BaseDir.find()))
      .registry(Guice.registry(bindingsSpec -> bindingsSpec
          .bind(BookRepository.class, DefaultBookRepository.class)
          .bind(BookService.class, DefaultBookService.class)
          .bind(BookRenderer.class)
          .module(HandlebarsModule.class)
      ))
      .handlers(chain -> {
        chain
          .get(c -> c.render("Hello Devoxx!"))
          .get("welcome", c -> c.render(Template.handlebarsTemplate("index",
            m -> m.put("welcomeMessage", "Hello Devoxx!"))))
          .get("api/book/:isbn", c -> {
            BookService bookService = c.get(BookService.class);
            c.render(bookService.getBook(c.getPathTokens().get("isbn")));
          });
      })
    );
  }
}
