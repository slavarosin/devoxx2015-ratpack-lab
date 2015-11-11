package lab03;

import ratpack.handling.Context;
import ratpack.handling.InjectionHandler;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;

import static ratpack.registry.Registry.single;

public class Lab03 {
  public static void main(String[] args) throws Exception {
    RatpackServer.start(ratpackServerSpec -> ratpackServerSpec
      .handlers(chain -> chain
        .register(single(new DefaultBookService()))
        .prefix("book/:isbn", bookChain ->
          bookChain
            .all(ctx -> {
                String isbn = ctx.getAllPathTokens().get("isbn");
                Book book = ctx.get(BookService.class).getBook(isbn);
                ctx.next(single(book));
              }
            )
            .get("title", ctx -> ctx.getResponse().send(ctx.get(Book.class).title))
            .get("author", ctx -> ctx.getResponse().send(ctx.get(Book.class).author))
        )
      ));
  }
}
