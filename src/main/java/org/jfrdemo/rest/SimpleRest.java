package org.jfrdemo.rest;

import lombok.RequiredArgsConstructor;
import org.jfrdemo.annot.Measure;
import org.jfrdemo.serv.EasyServ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest")
public class SimpleRest {

    private final EasyServ easyServ;

    @GetMapping("/fast")
    public String fast() {
        return "fast OK";
    }

    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        return easyServ.hello();
    }
}
