package com.xxdai.filter;

import com.xxdai.client.CXF_Factory;
import com.xxdai.external.user.ws.User;
import com.xxdai.filter.ws.UserDimensionCXFService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wanggang on 2017/2/16.
 */
public class UserIdTemp {
    private static final Logger logger = LoggerFactory.getLogger(UserIdTemp.class);
    static boolean sign = false;
    static Set userIdSet = new HashSet();
    UserDimensionCXFService userDimensionCXFService = (UserDimensionCXFService) CXF_Factory.getFactory(UserDimensionCXFService.class, com.xxdai.util.Configuration.getInstance().getValue("webService_url") + "/userDimensionWebService").create();

    /**
     * 将登录状态中的用户userid存进redis中去，用于统计用户维度
     * @param req
     */
    public void putUserIdToredis(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("loginUser");
        if( user != null){
            Long userId = user.getUserId();
            userIdSet.add(userId);
        }
        if(!sign) {
            sign = true;
            Thread thread = new SendUserIdToRedis();
            thread.start();
        }
    }

    class SendUserIdToRedis extends Thread{
        public void run() {
            while (true){
                if(userIdSet != null && userIdSet.size()>0){
                    String userIdSetStr = "";
                    synchronized (userIdSet){
                        userIdSetStr = userIdSet.toString();
                        userIdSet.clear();
                    }
                    //插入redis
                    userDimensionCXFService.putUserIdToRedis(userIdSetStr);
                }
                try {
                    Thread.sleep(30*1000l);
                    logger.debug("webApp端每三十秒将登录用户放入redis");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
