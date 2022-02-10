package blog;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

public class MyAnnotatedService {

    @Get("/hello/{seq}")
    public HttpResponse hello(@Param("seq") int seq) {
        if (seq % 3 == 0) {
            return HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return HttpResponse.of(HttpStatus.OK, MediaType.PLAIN_TEXT_UTF_8, "Success");
    }
}