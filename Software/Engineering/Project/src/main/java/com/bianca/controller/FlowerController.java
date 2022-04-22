package com.bianca.controller;


import com.bianca.dao.*;
import com.bianca.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class FlowerController {

    @Autowired
    private FlowerDAO flowerDAO;


    @GetMapping(value="/")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("flower", new Flower());
        model.addAttribute("cont", authentication.getName());
        List<Flower> flowerList = flowerDAO.loadFlowers();
        List<Flower> fs = new ArrayList<>();
        int count = 0;
        for(Flower f : flowerList){
            if(f.getePromotie() && count < 4){
                count++;
                fs.add(f);
            }
        }
        model.addAttribute("fs", fs);
        return "home";
    }


    @RequestMapping(value="/catalog")
    public String catalog(@RequestParam("nume") String nume, Model model){
        // call the DAO method to get the data
        model.addAttribute("flower", new Flower());
        List<Flower> f = flowerDAO.getFlowerByNume(nume);
        model.addAttribute("flowers", f);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("cont", authentication.getName());
        //model.addAttribute("acc", new Account());

        return "catalog";
    }




    @GetMapping(value="/catalog-admin")
    public String catalogAdmin(@RequestParam("nume") String nume, Model model){

        model.addAttribute("flower", new Flower());
        List<Flower> f = flowerDAO.getFlowerByNume(nume);
        model.addAttribute("flowers", f);
        return "catalog-admin";
    }

    @PostMapping(value="/process-flower")
    public String processFlower(Flower flower){
        // save the data from the url (data binding)

        flowerDAO.insertFlower(flower);

        return "redirect:/catalog-admin?nume=";
    }

    @PostMapping(value="/updateFlower")
    public String updateFlower(@RequestParam("nume") String nume, @RequestParam("id") int id, Model model, Flower flower, Flower fl){
        // we should give the user object who clicked on the update button
        Flower f = flowerDAO.getFlowerById(id);
        model.addAttribute("flower", f);
        flower.setNume(flower.getNume().substring(1)); // weird bug caused by requesting 2 param
        if(flower.getId() == 0){
            flowerDAO.insertFlower(flower);
        }
        else{
            flowerDAO.updateFlower(flower);
        }

        return "redirect:/catalog-admin?nume=";
    }

    @PostMapping(value="/deleteFlower")
    public String deleteFlower(@RequestParam("nume") String nume,@RequestParam("id") int id, Model model, Flower flower){
        Flower f = flowerDAO.getFlowerById(id);
        model.addAttribute("flower", f);
        flowerDAO.deleteFlower(id);
        return "redirect:/catalog-admin?nume=";
    }




    @RequestMapping(value="/process-search")
    public String processSearch(Flower flower){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getName().equals("admin")){
            // admin
            return "redirect:/catalog-admin?nume=" + flower.getNume();
        }
        return "redirect:/catalog?&nume=" + flower.getNume();

    }

    @PostMapping(value="/admin-process-search")
    public String adminProcessSearch(Flower flower){


        return "redirect:/catalog-admin?nume=" + flower.getNume();

    }







}
