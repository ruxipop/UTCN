package com.bianca.controller;

import com.bianca.dao.*;
import com.bianca.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class WishController {

    @Autowired
    private WishDAO wishDAO;

    @Autowired
    private FlowerDAO flowerDAO;

    @GetMapping(value="/wish")
    public String wish(Model model){
        // call the DAO method to get the data
        List<Flower> flowers= new ArrayList<Flower> ();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Wish> wishList = wishDAO.loadWishs();
        for(Wish w:wishList){
            if(w.getUsername().equals(authentication.getName()))
                flowers.add(flowerDAO.getFlowerById(w.getWishItem()));
        }

        model.addAttribute("cont", authentication.getName());
        model.addAttribute("wishs", wishList);
        model.addAttribute("flowers", flowers);
        return "wish";
    }




    @PostMapping(value="/process-wish")
    public String processWish(@RequestParam("id") int id, @RequestParam("page") String page){


        Wish w = new Wish();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        w.setWishItem(id);

        w.setUsername(authentication.getName());

        wishDAO.insertWish(w);

        if(page.equals("home")){
            return "redirect:/";
        }

        if(authentication.getName().equals("admin")){

            return "redirect:/catalog-admin?nume=";
        }
        return "redirect:/catalog?nume=";
    }


    @PostMapping(value="/deleteWish")
    public String deleteWish(@RequestParam("id") int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Wish w = wishDAO.getWishByIdAndUsername(id, authentication.getName());
        model.addAttribute("wish", w);
        wishDAO.deleteWish(id, authentication.getName());
        return "redirect:/wish";
    }
}
