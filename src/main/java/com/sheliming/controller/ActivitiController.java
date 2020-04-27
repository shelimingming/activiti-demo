package com.sheliming.controller;


import com.sheliming.service.ActivitiService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("activiti")
@Slf4j
public class ActivitiController {
    @Autowired
    private ActivitiService activitiService;

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void image(HttpServletResponse response, String processInstanceId) {
        try {
            InputStream is = activitiService.getDiagram(processInstanceId);
            if (is == null)
                return;

            response.setContentType("image/png");

            BufferedImage image = ImageIO.read(is);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

            is.close();
            out.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
