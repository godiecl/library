package cl.ucn.disc.arqsist.library.controller;

import cl.ucn.disc.arqsist.library.model.Member;
import cl.ucn.disc.arqsist.library.service.MemberService;
import io.javalin.config.JavalinConfig;

public final class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    public void register(JavalinConfig config) {
        config.routes.get("/members", ctx -> ctx.json(service.findAll()));
        config.routes.post("/members", ctx -> ctx.json(service.register(ctx.bodyAsClass(Member.class))));
    }
}
