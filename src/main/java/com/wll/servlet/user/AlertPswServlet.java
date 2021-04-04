package com.wll.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.wll.pojo.User;
import com.wll.service.user.UserService;
import com.wll.service.user.UserServiceImpl;
import com.wll.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author wulele
 */
public class AlertPswServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        if(method.equals("pwdmodify")&&method!=null){
            this.pwdModify(req,resp);
        }else if(method.equals("savepwd")&&method!=null){
            this.alertPassword(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    //验证老密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object attribute = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        HashMap<String, String> hashMap = new HashMap<>();
        if(attribute==null){
            //session过期
            hashMap.put("result","sessionerror");
        }else if (oldpassword.equals(null)){
            //输入密码为空
            hashMap.put("result","error");
        }else{
            String userPassword = ((User) attribute).getUserPassword();
            if (userPassword.equals(oldpassword)){
                //密码正确
                hashMap.put("result","true");
            }else {
                //密码错误
                hashMap.put("result","false");
            }
        }
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(hashMap));
        writer.flush();
        writer.close();
    }
    //修改密码
    public void alertPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //登录时user的所有信息保存在了session中
        Object attribute = req.getSession().getAttribute(Constant.USER_SESSION);
        //jsp修改密码页面获取新密码
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;
        if (attribute != null && newpassword != null) {
            UserService userService = new UserServiceImpl();
            flag = userService.alertPsw(((User) attribute).getId(), newpassword);
            if (flag){
                req.setAttribute("message","密码修改成功，请返回重新登录");
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else {
                req.setAttribute("message","密码修改失败");
            }
        }else {
            req.setAttribute("message","新密码有问腿");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
    }
}
