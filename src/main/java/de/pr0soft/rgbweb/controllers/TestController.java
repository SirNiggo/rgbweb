package de.pr0soft.rgbweb.controllers;

import de.pr0soft.rgbweb.entities.RGBResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @PostMapping(path = "/rgb", produces = "application/json")
    public RGBResponse setRGB() {
        RGBResponse response = new RGBResponse();
        response.test = "ABC";
        return response;
    }
}
