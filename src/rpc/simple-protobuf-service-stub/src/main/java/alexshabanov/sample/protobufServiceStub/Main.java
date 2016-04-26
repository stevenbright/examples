package alexshabanov.sample.protobufServiceStub;

import alexshabanov.sample.protobufServiceStub.model.Bookstore;
import alexshabanov.sample.protobufServiceStub.support.ClientRpcController;
import alexshabanov.sample.protobufServiceStub.support.MockBlockingRpcChannel;
import com.google.protobuf.ServiceException;

import static alexshabanov.sample.protobufServiceStub.model.Bookstore.BookService;
import static alexshabanov.sample.protobufServiceStub.model.Bookstore.GetBookByIdReply;

/**
 * Entry point.
 */
public final class Main {

  public static void main(String[] args) {
    System.out.println("Hello");
    demo1();
  }

  public static void demo1() {
    final MockBlockingRpcChannel rpcChannel = new MockBlockingRpcChannel();
    rpcChannel.addMockEntry("GetBookById", null, GetBookByIdReply.newBuilder()
        .setBook(Bookstore.Book.newBuilder()
            .setId(1L)
            .setTitle("title")
            .build())
        .build());

    final BookService.BlockingInterface bookService = BookService.newBlockingStub(rpcChannel);

    try {
      final GetBookByIdReply reply = bookService.getBookById(new ClientRpcController(), Bookstore.GetBookByIdRequest.newBuilder()
          .setBookId(1L)
          .build());
      System.out.println("Got reply=" + reply);
    } catch (ServiceException e) {
      System.out.println("RPC call error:");
      e.printStackTrace(System.out);
    }
  }
}
