package blog;

import com.linecorp.armeria.server.docs.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;


public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static Server newServer(int port) {
        ServerBuilder sb = Server.builder();
        DocService docService =
                DocService.builder()
                        .exampleRequests(BlogService.class,
                                "createBlogPost",
                                "{\"title\":\"My first blog\", \"content\":\"Hello Armeria!\"}")
                        .build();
        return sb.http(port)
                .annotatedService(new BlogService())
                .serviceUnder("/docs", docService)  //doc 서비스추가
                .build();
    }

//    public static void main(String[] args) throws Exception {
//        Server server = newServer(8080);
//
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            server.stop().join();
//            logger.info("Server has been stopped.");
//        }));
//
//        server.start().join();
//
//        logger.info("Server has been started. Serving DocService at http://127.0.0.1:{}/docs",
//                server.activeLocalPort());
//    }

}

