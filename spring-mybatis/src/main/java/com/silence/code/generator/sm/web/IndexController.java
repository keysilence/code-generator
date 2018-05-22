package com.silence.code.generator.sm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Silence on 2018/5/22.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {

        response.sendRedirect("/admin/mybatisGenerator/list");

    }

}
