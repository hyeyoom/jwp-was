package http.controller;

import http.requests.RequestContext;
import http.responses.HttpStatus;
import http.responses.ResponseContext;
import model.User;
import model.UserParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TemplateReader;

import java.io.FileNotFoundException;

/**
 * 그냥 컨트롤러라고 하면 밋밋해서 으메이징이라고 붙임. 다른 의미로 으미에징해질거 같지만 일단 모르는척..
 */
public class AmazingController {

    private static final Logger log = LoggerFactory.getLogger(AmazingController.class);

    public static ResponseContext dispatch(RequestContext requestContext) {
        final String branch = buildBranchString(requestContext);

        log.debug("branch: {}", branch);

        switch (branch) {
            case "[POST]/user/create":  // 하드코딩 실화니.. =ㅁ=..
                return signUpHandler(requestContext);
            default:
                return defaultHandler(requestContext);
        }
    }

    private static ResponseContext signUpHandler(RequestContext requestContext) {
        final User user = UserParser.parse(requestContext);
        log.debug("user: {}", user);
        return ResponseContext
                .builder()
                    .status(HttpStatus.FOUND)
                    .addHeader("Location", "/index.html")
                .build();
    }

    private static ResponseContext defaultHandler(RequestContext requestContext) {
        final byte[] rawBody = convertFileToByte(requestContext.getPath());
        return ResponseContext
                .builder()
                    .status(HttpStatus.OK)
                    .addHeader("Content-Type", "text/html;charset=utf-8")
                    .addHeader("Content-Length", String.valueOf(rawBody.length))
                    .body(rawBody)
                .build();
    }

    private static byte[] convertFileToByte(String path) {
        try {
            return TemplateReader.read(path);
        } catch (FileNotFoundException e) {
            return "Hello World".getBytes();
        }
    }

    /**
     * URI로만 분기하면 method를 구분할 수 없어서 분기용으로 일단 이렇게 만듬
     * 
     * @param ctx http 요청 컨텍스트
     * @return 분기 문자열
     */
    private static String buildBranchString(RequestContext ctx) {
        return String.format("[%s]%s", ctx.getMethod(), ctx.getPath());
    }
}
