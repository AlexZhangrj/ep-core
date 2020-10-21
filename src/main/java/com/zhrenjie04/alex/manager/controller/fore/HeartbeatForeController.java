package com.zhrenjie04.alex.manager.controller.fore;

import com.zhrenjie04.alex.core.*;
import com.zhrenjie04.alex.core.exception.PrerequisiteNotSatisfiedException;
import com.zhrenjie04.alex.manager.dao.UserDao;
import com.zhrenjie04.alex.manager.service.UserService;
import com.zhrenjie04.alex.util.JsonUtil;
import com.zhrenjie04.alex.util.RedisUtil;
import com.zhrenjie04.alex.util.SessionUtil;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author 张人杰
 */
@Controller
@RequestMapping("/user/fore")
@Permission("fore")
public class HeartbeatForeController {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatForeController.class);

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Permission("do-heartbeat")
    @ResponseJson
    public JsonResult doHeartbeat(HttpServletRequest request, HttpServletResponse response) {
        return JsonResult.success();
    }
}
