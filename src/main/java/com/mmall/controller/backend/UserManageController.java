package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**用户模块的门户Controller
 * @author PJB
 * @date 2020/4/12
 */
@Controller
@RequestMapping("/manage/user/")
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    /**
     * 处理后台管理员登录的请求
     *
     * @param username 用户名
     * @param password 密码
     * @param session  session
     * @return 返回给前端的登录结果
     */
    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session,HttpServletResponse httpServletResponse){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                //新增redis共享cookie，session的方式
                CookieUtil.writeLoginToken(httpServletResponse,session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisKeyExpires.USER_LOGIN_TOKEN);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }
}
