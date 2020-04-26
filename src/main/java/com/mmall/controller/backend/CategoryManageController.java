package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 产品分类模块的后台Controller
 *
 * @author PJB
 * @date 2020/4/12
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 处理增加产品种类的请求
     *
     * @param httpServletRequest      httpServletRequest
     * @param categoryName 产品种类名字
     * @param parentId     父产品种类的id （默认 0）
     * @return 返回给是否增加成功的信息
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        //通过拦截器验证是否登录以及权限
        return iCategoryService.addCategory(categoryName, parentId);
    }

    /**
     * 处理修改产品种类名称的请求
     *
     * @param httpServletRequest      httpServletRequest
     * @param categoryId   产品种类id
     * @param categoryName 产品类别新名字
     * @return 返回给前端的修改分类是否成功信息
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest, Integer categoryId, String categoryName) {
        //通过拦截器验证是否登录以及权限，更新categoryName
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    /**
     * 处理 获取产品的子产品分类的请求
     *
     * @param httpServletRequest    httpServletRequest
     * @param categoryId 产品种类的id
     * @return 返回给前端的子产品分类的结果
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //通过拦截器验证是否登录以及权限，查询子产品的category信息
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 处理 获取产品的子产品分类的请求
     *
     * @param httpServletRequest    httpServletRequest
     * @param categoryId 产品种类的id
     * @return 返回给前端的所有子产品分类的id
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //通过拦截器验证是否登录以及权限, 查询当前分类id和子分类的id
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}
