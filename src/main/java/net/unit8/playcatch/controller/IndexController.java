package net.unit8.playcatch.controller;

import enkan.data.HttpResponse;
import kotowari.component.TemplateEngine;
import net.unit8.bouncr.sign.JsonWebToken;
import net.unit8.bouncr.sign.JwtHeader;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static enkan.util.BeanBuilder.builder;

public class IndexController {
    @Inject
    private TemplateEngine template;

    @Inject
    private JsonWebToken jwt;

    public HttpResponse index() {
        String message = null;
        if (!Objects.equals(System.getProperty("enkan.env"), "production")) {
            Map<String, Object> claim = new HashMap<>();
            claim.put("sub", "kawasima");
            JwtHeader header = builder(new JwtHeader())
                    .set(JwtHeader::setAlg, "none")
                    .build();
            message = jwt.sign(claim, header, null);
        }
        return template.render("index",
                "credential", message);
    }
}
