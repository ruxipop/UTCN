package com.bianca.controller;

import com.bianca.dao.*;
import com.bianca.model.*;
import com.bianca.util.*;
import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private FlowerDAO flowerDAO;

    private String filePath="E:\\1.UTCN\\Anul_3\\Sem_I\\IS\\apache-tomcat-9.0.54\\bin\\Receipt.pdf";


    @GetMapping(value="/cart")
    public String findFlower(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Flower> flowers = new ArrayList<Flower>();
        List<Integer> quantity = new ArrayList<Integer>();
        int maxQuantity = 0;
        float sumT = 0;
        List<Cart> cartList = cartDAO.loadCarts();
        for (Cart c : cartList) {

            if (c.getUsername().equals(authentication.getName())) {
                Flower flower = flowerDAO.getFlowerById(c.getCartItem());
                flowers.add(flower);
                quantity.add(c.getQuantity());


                if(flower.getePromotie()) {
                    sumT = (float) (sumT + flower.getPretNou() * c.getQuantity());
                }
                else{
                    sumT = (float) (sumT + flower.getPret() * c.getQuantity());
                }

                maxQuantity = c.getQuantity() + maxQuantity;
            }

        }
        model.addAttribute("user", new User());
        model.addAttribute("cont", authentication.getName());
        model.addAttribute("flowers", flowers);
        model.addAttribute("cartList", cartList);
        model.addAttribute("maxQuantity", maxQuantity);
        model.addAttribute("sumT", sumT);

        File pdf = new File(filePath);
        model.addAttribute("file", pdf);
        return "cart";
    }

    @PostMapping(value="/sterge-cart")
    public String stergeCart(@RequestParam("id") int id, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Cart c= cartDAO.getCartByCartItemAndUsername(id, authentication.getName());


        model.addAttribute("cart", c);
        if(c.getQuantity()==1){
            cartDAO.deleteCart(id, authentication.getName());
        }
        else {
            cartDAO.updateCart(c);
        }

        return "redirect:/cart";
    }

    @PostMapping(value="/process-cart")
    public String processCart(@RequestParam("id") int id, @RequestParam("page") String page){
        // save the data from the url (data binding)
        // Flower f = flowerDAO.getFlowerById(id);
        Cart c = new Cart();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        c.setCartItem(id);
        c.setQuantity(c.getQuantity()+1);
        c.setUsername(authentication.getName());

        cartDAO.insertCart(c);

        if(page.equals("home")){
            return "redirect:/";
        }
        if(authentication.getName().equals("admin")){
            return "redirect:/catalog-admin?nume=";
        }
        return "redirect:/catalog?nume=";
    }

    @PostMapping(value="/process-cart1")
    public String processCart1(@RequestParam("id") int id){
        // save the data from the url (data binding)
        // Flower f = flowerDAO.getFlowerById(id);
        Cart c = new Cart();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        c.setCartItem(id);
        c.setQuantity(c.getQuantity()+1);
        c.setUsername(authentication.getName());

        cartDAO.insertCart(c);

        return "redirect:/wish";
    }

    @RequestMapping(value="/process-order")
    public String processOrder(@RequestParam("flexRadioDefault") String option,
                                @RequestParam("cardNo") String cardNo,
                                @RequestParam("nume") String name,
                                @RequestParam("expDate") String date){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Cart> carts = cartDAO.getCartsByUsername(authentication.getName());
        if(carts.size() == 0){
            return "redirect:/cart?gol";
        }

        try {
            Date date1 = new SimpleDateFormat("MM/yy").parse(date);
            Date todayDate = new Date();
            if(date1.before(todayDate)){
                return "redirect:/cart?expired";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        StringBuilder sb = new StringBuilder();
        sb.append("Factura\n");
        sb.append("Nume: " + name + "\n");
        sb.append("--------------------------------------\n");


        double pretTotal = 0;
        for(Cart c : carts){
            Flower f = flowerDAO.getFlowerById(c.getCartItem());
            if(f.getePromotie()) {
                sb.append(f.getNume() + ", " + c.getQuantity() + ", " + f.getPretNou() + " X " + c.getQuantity() + " = " + f.getPretNou() * c.getQuantity() + " RON \n");
                pretTotal += f.getPretNou() * c.getQuantity();
            }
            else {
                sb.append(f.getNume() + ", " + c.getQuantity() + ", " + f.getPret() + " X " + c.getQuantity() + " = " + f.getPret() * c.getQuantity() + " RON \n");
                pretTotal += f.getPret() * c.getQuantity();
            }

            cartDAO.deleteCart(c.getCartItem(), authentication.getName());
        }

        sb.append("--------------------------------------\n");
        sb.append("Pret total: " + pretTotal + " RON\n");
        sb.append("Plata efectuata cu cardul " + cardNo.substring(0, 3) + "xxxxxxxxx\n");
        if(option.equals("By courier")){
            sb.append("Produsele vor fi livrate prin: curier \n");
        }
        else{
            sb.append("Produsele vor fi livrate prin: pick up \n");
        }

        sb.append("Thank you for buying from California Flowers!\n");


        try {
            new WriteReceiptToPDF(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return "redirect:/cart?ordered";

    }


}
