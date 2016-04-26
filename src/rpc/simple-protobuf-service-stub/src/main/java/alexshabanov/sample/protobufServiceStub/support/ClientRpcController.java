package alexshabanov.sample.protobufServiceStub.support;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample RPC controller.
 */
public class ClientRpcController implements RpcController {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void reset() {
    log.info("reset");
  }

  @Override
  public boolean failed() {
    log.info("failed");
    return false;
  }

  @Override
  public String errorText() {
    log.info("errorText");
    return null;
  }

  @Override
  public void startCancel() {
    log.info("startCancel");
  }

  @Override
  public void setFailed(String s) {
    log.info("setFailed({})", s);
  }

  @Override
  public boolean isCanceled() {
    log.info("isCanceled");
    return false;
  }

  @Override
  public void notifyOnCancel(RpcCallback<Object> rpcCallback) {
    log.info("notifyOnCancel");
  }
}
