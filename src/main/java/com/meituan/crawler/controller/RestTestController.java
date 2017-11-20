package com.meituan.crawler.controller;

import com.meituan.crawler.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/20
 * \* Time: 17:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
public class RestTestController {

    @RequestMapping("/owners/{ownerId}/pets/{petId}")
    public String findPet(@PathVariable String ownerId, @PathVariable String petId, HttpServletResponse response) {
        System.out.println("ownerId = " + ownerId);
        System.out.println("pedId = " + petId);
        response.addCookie(new Cookie("ownerId", ownerId));
        response.addCookie(new Cookie("pedId", petId));
        return ownerId + " : " + petId;
    }

    @RequestMapping("/testcookie")
    public String testCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ownerId")) {
                    return "detect cookie ! " + cookie.getName() + ":" + cookie.getValue();
                }
            }
        }
        return "no cookie found!";
    }

    @RequestMapping(value = "/handle41", method = RequestMethod.POST)
    public String handle41(@RequestBody String requestBody) {
        System.out.println(requestBody);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/handle42/{imageId}", method = RequestMethod.GET)
    public byte[] handle42(@PathVariable("imageId") String imageId) throws IOException {
        System.out.println("load image of " + imageId);
        Resource res = new ClassPathResource("/img/image.jpg");
        byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
        return fileData;
    }

    @RequestMapping(path = "/handle43")
    public String handle43(HttpEntity<String> httpEntity) {
        long contentLength = httpEntity.getHeaders().getContentLength();
        System.out.println("contentLength = " + contentLength);
        System.out.println(httpEntity.getBody());
        return httpEntity.getBody();
    }

    @ResponseBody
    @RequestMapping("/handle44/{imageId}")
    public ResponseEntity<byte[]> handle44(@PathVariable("imageId") String imageId) throws IOException {
        Resource resource = new ClassPathResource("/img/image.jpg");
        byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(fileData, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(path = "/handle51")
    public ResponseEntity<User> handle51(HttpEntity<User> requestEntity) {
        User user = requestEntity.getBody();
        user.setAge(10);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
