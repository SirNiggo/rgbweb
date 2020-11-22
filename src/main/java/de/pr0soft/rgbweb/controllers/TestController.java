package de.pr0soft.rgbweb.controllers;

import de.pr0soft.rgbweb.entities.RGBBody;
import de.pr0soft.rgbweb.entities.RGBResponse;
import de.pr0soft.rgbweb.exceptions.InvalidByteArrayLengthException;
import de.pr0soft.rgbweb.exceptions.InvalidChannelBrightnessException;
import de.pr0soft.rgbweb.exceptions.InvalidPwmValueException;
import de.pr0soft.rgbweb.exceptions.SpiDeviceNotInitializedException;
import de.pr0soft.rgbweb.services.SimpleRGBHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    SimpleRGBHandler simpleRGBHandler;

    @PostMapping(path = "/rgb", produces = "application/json")
    public RGBResponse setRGB(@RequestBody RGBBody rgbBody) throws IOException, InvalidPwmValueException, InvalidChannelBrightnessException, SpiDeviceNotInitializedException, InvalidByteArrayLengthException {
        simpleRGBHandler.setColor(rgbBody.r, rgbBody.g, rgbBody.b);
        RGBResponse response = new RGBResponse();
        response.message = "OK";
        return response;
    }
}
