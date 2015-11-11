package lab02;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class SoapActionHandler implements Handler {

  private final String soapAction;
  private final Handler handler;

  public SoapActionHandler(String soapAction, Handler handler) {
    this.soapAction = soapAction;
    this.handler = handler;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    String soapActionHeader = ctx.getRequest().getHeaders().get("SOAPAction");
    if (soapActionHeader != null && soapActionHeader.equals(soapAction)) {
      handler.handle(ctx);
    } else {
      ctx.next();
    }
  }
}
