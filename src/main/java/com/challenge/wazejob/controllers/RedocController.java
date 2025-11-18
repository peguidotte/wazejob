package com.challenge.wazejob.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redoc")
@RequiredArgsConstructor
@Slf4j
@Hidden
public class RedocController {

    private final ServletContext servletContext;


    @GetMapping(produces = "text/html")
    public String getRedocDocumentation() {

        String contextPath = servletContext.getContextPath();
        String redocUrl = contextPath + "/v3/api-docs";
        log.debug("contextPath for redoc is: {}", contextPath);
        log.debug("Redoc Documentation SPEC Url is: {}", redocUrl);


        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>API Docs</title>\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap\" rel=\"stylesheet\">\n" +
                "\n" +
                "  <style>\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      font-family: 'Inter', sans-serif;\n" +
                "      background-color: #fafafa;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <redoc spec-url='" + redocUrl + "'></redoc>\n" +
                "  <script src=\"https://cdn.jsdelivr.net/npm/redoc@latest/bundles/redoc.standalone.js\"></script>\n" +
                "</body>\n" +
                "</html>";
    }
}