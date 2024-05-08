package com.silence.ht.web;

import com.silence.code.generator.sm.SqlParser;
import com.silence.code.generator.sm.model.Class;
import com.silence.code.generator.sm.model.Column;
import com.silence.code.generator.sm.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/ht", method = {RequestMethod.GET, RequestMethod.POST})
public class HtController {

    private static final Logger logger = LoggerFactory.getLogger(HtController.class);

    @Autowired
    private SqlParser sqlParser;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "breed", defaultValue = "") String breed,
                       @RequestParam(value = "id", required = false) Long id,
                       Model view) {

        return "admin/ht/list";

    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String search(@RequestParam(value = "breed", defaultValue = "") String breed,
                         Model view) {

        return "admin/ht/list";

    }

}
