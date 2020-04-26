package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 商品管理模块的后台Controller
 *
 * @author PJB
 * @date 2020/4/12
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    /**
     * 处理商品新增或保存的请求，如果参数有productId，执行更新操作，否则执行新增操作
     *
     * @param httpServletRequest httpServletRequest
     * @param product 产品信息
     * @return 返回给前端的商品新增或保存的信息
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest httpServletRequest, Product product) {
        //通过拦截器验证是否登录以及权限
        return iProductService.saveOrUpdateProduct(product);
    }

    /**
     * 处理修改产品上架下架删除的功能
     * @param httpServletRequest httpServletRequest
     * @param productId 产品id
     * @param status  状态参数
     * @return   返回给前端商品状态是否成功改变的信息
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest httpServletRequest, Integer productId, Integer status) {
        //通过拦截器验证是否登录以及权限
        return iProductService.setSaleStatus(productId, status);

    }

    /**
     *  处理获取商品详情的请求
     * @param httpServletRequest httpServletRequest
     * @param productId 商品id
     * @return 返回给前端的商品详情的信息
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest httpServletRequest, Integer productId){
        //通过拦截器验证是否登录以及权限
        return iProductService.manageProductDetail(productId);

    }

    /**
     * 处理后台查询商品列表的请求
     * @param httpServletRequest httpServletRequest
     * @param pageNum 页码 （默认1）
     * @param pageSize 每页的数量（默认10）
     * @return 商品列表
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //通过拦截器验证是否登录以及权限
        return iProductService.getProductList(pageNum,pageSize);

    }

    /**
     * 处理后台搜索商品的请求
     * @param httpServletRequest httpServletRequest
     * @param productName 商品名称
     * @param productId 商品id
     * @param pageNum 页码 （默认1）
     * @param pageSize 每页的数量（默认10）
     * @return  满足搜索条件的商品列表
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest httpServletRequest,String productName,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //通过拦截器验证是否登录以及权限
        return iProductService.searchProduct(productName,productId,pageNum,pageSize);

    }

    /**
     * 处理上传商品图片的请求（前端格式可见 index.jsp 或接口文档）
     * @param httpServletRequest httpServletRequest
     * @param file 上传的文件
     * @param request 前端上传文件的请求
     * @return  文件在ftp服务器的地址
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        //通过拦截器验证是否登录以及权限
        // 相当于在webapp目录下创建了 upload 文件夹
        String path = request.getSession().getServletContext().getRealPath("upload");
        // 上传到ftp服务器
        String targetFileName = iFileService.upload(file,path);
        if(StringUtils.isBlank(targetFileName)){
            return ServerResponse.createByErrorMessage("上传失败");
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        //封装uri 和 url （前端可根据url访问ftp服务器上的图片）
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }

    /**
     * 处理上传商品富文本的请求（前端格式可见 index.jsp 或接口文档）
     * @param httpServletRequest httpServletRequest
     * @param file 上传的文件
     * @param request request
     * @param response response
     * @return 富文本返回值
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
       /* 富文本中对于返回值格式的要求,本项目采用simditor的要求，如下：
        {
            "success": true/false,
             "msg": "error message", # optional
            "file_path": "[real file path]"
        } */
        //通过拦截器验证是否登录以及权限
        // 相当于在webapp目录下创建了 upload 文件夹
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);   // 上传到ftp服务器
        if(StringUtils.isBlank(targetFileName)){
            resultMap.put("success",false);
            resultMap.put("msg","上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name"); //富文本相应要求的相应头
        return resultMap;
    }


}
