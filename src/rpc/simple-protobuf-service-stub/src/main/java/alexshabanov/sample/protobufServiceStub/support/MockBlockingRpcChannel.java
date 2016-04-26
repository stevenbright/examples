package alexshabanov.sample.protobufServiceStub.support;

import com.google.protobuf.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of simple in-proc blocking RPC channel.
 */
public class MockBlockingRpcChannel implements BlockingRpcChannel {

  private List<MockEntry> mockEntries = new ArrayList<>();

  public void addMockEntry(@Nullable String methodName,
                           @Nullable Message request,
                           @Nonnull Message response) {
    mockEntries.add(new MockEntry(methodName, request, response));
  }

  @Override
  public Message callBlockingMethod(Descriptors.MethodDescriptor method,
                                    RpcController controller,
                                    Message request,
                                    Message responsePrototype) throws ServiceException {
    //noinspection Convert2streamapi
    for (final MockEntry entry : mockEntries) {
      if ((entry.methodName == null || entry.methodName.equals(method.getName())) &&
          (entry.request == null || entry.request.equals(request))) {
        try {
          return responsePrototype.getParserForType().parseFrom(entry.response.toByteArray());
        } catch (InvalidProtocolBufferException e) {
          throw new ServiceException("Unable to convert message", e);
        }
      }
    }

    throw new ServiceException("Not implemented");
  }

  public static final class MockEntry {
    @Nullable final String methodName;
    @Nullable final Message request;
    @Nonnull final Message response;

    public MockEntry(@Nullable String methodName, @Nullable Message request, @Nonnull Message response) {
      this.methodName = methodName;
      this.request = request;
      this.response = response;
    }
  }
}
