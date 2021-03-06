package com.fdy.controller;

import com.fdy.entity.*;
import com.fdy.exception.NotFoundException;
import com.fdy.exception.ServiceException;
import com.fdy.filestore.Qiniustore;
import com.fdy.service.AccountService;
import com.fdy.service.PetsService;
import com.fdy.util.AjaxResponseData;
import com.fdy.util.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**客户端控制器
 * @author fdy
 */
@Controller
@RequestMapping("/client")
public class ClientController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private PetsService petsService;
    @Autowired
    private ShiroUtil shiroUtil;
    @Autowired
    private Qiniustore qiniustore;

    /**跳转到公告详情页
     * @param model
     * @return
     */
    @GetMapping("/notice/{id:\\d+}")
    public String looknotice(@PathVariable Integer id, Model model){
        Notice notice = petsService.findNoticeById(id);
        if(notice == null){
            throw new NotFoundException();
        }
        model.addAttribute("notice",notice);
        return "clientPage/noticelook";
    }

    /**跳转到宠物
     * @return
     */
    @GetMapping("/pet")
    public String petManager(Model model){
        List<Pets> petsList = petsService.findAllPets();
        List<Userpet> userpetList = petsService.findAllUserPets();
        model.addAttribute("userpetList",userpetList);
        model.addAttribute("petsList",petsList);
        return "clientPage/pet";
    }

    /**跳转到流浪宠物详情页
     * @param model
     * @return
     */
    @GetMapping("/pet/{id:\\d+}")
    public String look(@PathVariable Integer id, Model model){
        Pets pets = petsService.findById(id);
        if(pets == null){
            throw new NotFoundException();
        }
        model.addAttribute("pets",pets);
        return "clientPage/petlook";
    }

    /**跳转到用户宠物详情页
     * @param model
     * @return
     */
    @GetMapping("/userpet/{id:\\d+}")
    public String lookUserpet(@PathVariable Integer id, Model model){
        Userpet userpet = petsService.findUserPetById(id);
        if(userpet == null){
            throw new NotFoundException();
        }
        Cliam cliam = petsService.findBymobileAndname(userpet.getPetname(),userpet.getMobile());
        model.addAttribute("userpet",userpet);
        model.addAttribute("mobile",shiroUtil.getCurrAcc().getMobile());
        model.addAttribute("cliam",cliam);
        return "clientPage/userpet";
    }

    /**跳转到宠物领养申请页面
     * @return
     */
    @GetMapping("/cliam")
    public String cliam(){
        return "clientPage/cliam";
    }

    /**保存宠物认领申请记录
     * @param cliam 认领申请记录对象
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/cliam")
    public String saveCliam(Cliam cliam, RedirectAttributes redirectAttributes){
        try{
            accountService.saveCliam(shiroUtil.getCurrAcc(),cliam);
            redirectAttributes.addFlashAttribute("message", "提交成功");
        }catch (ServiceException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/client/cliam";
    }

    /**查看领养申请状态
     * @param model
     * @return
     */
    @GetMapping("/cliam/state")
    public String state(Model model){
        Account account = shiroUtil.getCurrAcc();
        //根据当前登录账户的姓名获取申请记录对象
        List<Cliam> cliamList = accountService.findCliamByAccName(account.getUsername());
        model.addAttribute("cliamList",cliamList);
        return "clientPage/cliamstate";
    }

    /**查看用户信息
     * @return
     */
    @GetMapping("/user")
    public String usermanager(Model model){
        Account account = accountService.findById(shiroUtil.getCurrAcc().getId());
        model.addAttribute("account",account);
        return "clientPage/user";
    }

    /**跳转到关于我们页面
     * @return
     */
    @GetMapping("/about")
    public String about(){
        return "clientPage/about";
    }

    /**跳转到留言板页面
     * @return
     */
    @GetMapping("/words")
    public String words(Model model){
        List<Words> wordsList = accountService.findWordsByAcc(shiroUtil.getCurrAcc().getUsername());
        model.addAttribute("wordsLists",wordsList);
        return "clientPage/words";
    }

    /**查看回复内容
     * @return
     */
    @GetMapping("/{id:\\d+}/lookreply")
    public String lookreply(@PathVariable Integer id, Model model){
        Words words = accountService.findWordsById(id);
        if(words != null){
            List<Reply> replyList = accountService.findALLReply(id);
            model.addAttribute("replyList",replyList);
        }
        model.addAttribute("words",words);
        return "clientPage/lookreply";
    }

    /**保存留言信息
     * @return
     */
    @PostMapping("/words")
    public String savewords(Words words, RedirectAttributes redirectAttributes){
        accountService.savewords(words,shiroUtil.getCurrAcc());
        redirectAttributes.addFlashAttribute("words",words);
        redirectAttributes.addFlashAttribute("message","留言成功");
        return "redirect:/client/words";
    }

    /**跳转到修改密码页面
     * @return
     */
    @GetMapping("/repassword")
    public String repassword(Model model){
        return "clientPage/repassword";
    }

    /**修改密码
     * @param oldpass 原密码
     * @param newpass 新密码
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/repassword")
    public String repassword(String oldpass,String newpass,RedirectAttributes redirectAttributes){
        try{
            //获取当前的登陆对象
            Account account = shiroUtil.getCurrAcc();
            accountService.repassword(account,oldpass,newpass);
            redirectAttributes.addFlashAttribute("message","修改成功");
            return "redirect:/logout";
        }catch (ServiceException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/client/repassword";
        }
    }

    /** 跳转到个人信息修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id:\\d+}/edit")
    public String editUser(@PathVariable Integer id, Model model){
        Account account = accountService.findById(id);

        model.addAttribute("account",account);
        return "clientPage/edit";
    }

    /** 保存更新后的用户信息
     * @return
     */
    @PostMapping("/{accountId:\\d+}/edit")
    public String updateUser(@PathVariable Integer accountId,Account account,RedirectAttributes redirectAttributes){
        try{
            accountService.updateAcc(accountId,account);
            redirectAttributes.addFlashAttribute("message","修改成功");
        }catch (ServiceException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/client/user";
    }

    /**跳转到宠物发布页面
     * @return
     */
    @GetMapping("/publish")
    public String publish(Model model){
        String token = qiniustore.getUploadToken();
        model.addAttribute("token",token);
        return "clientPage/publish";
    }

    /**保存新发布的宠物信息
     * @return
     */
    @PostMapping("/publish")
    public String savepets(Userpet userpet,RedirectAttributes redirectAttributes){
        try {
            petsService.saveUserpets(userpet,shiroUtil.getCurrAcc().getUsername());
            redirectAttributes.addFlashAttribute("message", "发布宠物成功");
        }catch (ServiceException e){
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/client/publish";
    }

    /**确认领养
     * @return
     */
    @GetMapping("/pet/{id:\\d+}/confirm")
    @ResponseBody
    public AjaxResponseData add(@PathVariable Integer id,String val){
        petsService.confirm(id,shiroUtil.getCurrAcc(),val);
        return AjaxResponseData.success();
    }
}
