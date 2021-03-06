package com.fdy.controller;

import com.fdy.entity.Fodder;
import com.fdy.exception.ServiceException;
import com.fdy.service.PetsService;
import com.fdy.util.AjaxResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 饲料管理控制器
 * @author fdy
 */
@Controller
@RequestMapping("/fodder")
public class FodderController {

    @Autowired
    private PetsService petsService;

    /**跳转到饲料管理首页
     * @return
     */
    @GetMapping
    public String home(Model model,@RequestParam(required = false)String type){
        Map<String,Object> selectMap = new HashMap<>();
        selectMap.put("type",type);

        List<Fodder> fodderList = petsService.findAllFooder(selectMap);
        model.addAttribute("fodderList",fodderList);
        return "fodder/home";
    }

    /**跳转到饲料管理新增页
     * @return
     */
    @GetMapping("/new")
    public String newfodder(){
        return "fodder/new";
    }

    @PostMapping("/new")
    public String saveFodder(Fodder fodder, RedirectAttributes redirectAttributes){
        try {
            petsService.saveFodder(fodder);
            redirectAttributes.addFlashAttribute("message", "新增饲料使用情况成功");
        }catch (ServiceException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/fodder";
    }

    /**添加
     * @return
     */
    @GetMapping("/{id:\\d+}/add")
    @ResponseBody
    public AjaxResponseData add(@PathVariable Integer id,String val){
        petsService.addFodder(id,val);
        return AjaxResponseData.success();
    }

    /**减少
     * @return
     */
    @GetMapping("/{id:\\d+}/reduce")
    @ResponseBody
    public AjaxResponseData reduce(@PathVariable Integer id,String val){
         try {
             petsService.reduceFodder(id, val);
             return AjaxResponseData.success();
         }catch (ServiceException e){
                 return AjaxResponseData.error(e.getMessage());
             }
    }
	
	/**删除饲料库存
     * @return
     */
    @GetMapping("/{id:\\d+}/del")
    @ResponseBody
    public AjaxResponseData del(@PathVariable Integer id){
        try{
            petsService.delFodder(id);
            return AjaxResponseData.success();
        }catch (ServiceException e){
            return AjaxResponseData.error(e.getMessage());
        }
    }
}
