package com.wll.servlet.user;

import com.wll.pojo.User;
import com.wll.service.user.UserService;
import com.wll.service.user.UserServiceImpl;
import com.wll.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wulele
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);
        if(user!=null){
            //登录保存session
            req.getSession().setAttribute(Constant.USER_SESSION,user);
            //重定向地址发生变化，项目地址为 smbms
            resp.sendRedirect("/smbms/jsp/frame.jsp");
            //resp.sendRedirect(req.getContextPath()+"/jsp/frame.jsp");
        }else {
            //错误信息只在一次请求中有效
            req.setAttribute("error","账号或密码有误");
            //请求转发，地址不发生变化，request对象未销毁，error信息保留
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
