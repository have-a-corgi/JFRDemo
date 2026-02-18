package org.jfrdemo.serv;

import org.jfrdemo.annot.Measure;
import org.springframework.stereotype.Service;

@Service
public class EasyServ {

    @Measure
    public String hello() {
        return "hello";
    }

}
