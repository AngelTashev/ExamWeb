package com.softuni.exam.web;

import com.softuni.exam.model.view.ProductViewModel;
import com.softuni.exam.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ModelMapper modelMapper;

    private final ProductService productService;

    @Autowired
    public HomeController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpSession httpSession) {

        // If user is not logged in redirect to index
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        // If user is logged in show all items

        // Getting all lists of items
        List<ProductViewModel> food = this.productService.findAllByCategory("FOOD")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImageSrc(String.format("/img/%s.png", product.getCategory().getName().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());
        List<ProductViewModel> drink = this.productService.findAllByCategory("DRINK")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImageSrc(String.format("/img/%s.png", product.getCategory().getName().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());

        List<ProductViewModel> household = this.productService.findAllByCategory("HOUSEHOLD")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImageSrc(String.format("/img/%s.png", product.getCategory().getName().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());

        List<ProductViewModel> other = this.productService.findAllByCategory("OTHER")
                .stream().map(product -> {
                    ProductViewModel productViewModel = this.modelMapper.map(product, ProductViewModel.class);
                    productViewModel.setImageSrc(String.format("/img/%s.png", product.getCategory().getName().toLowerCase()));
                    return productViewModel;
                })
                .collect(Collectors.toList());

        // Adding all lists of items to the model
        model.addAttribute("food", food);
        model.addAttribute("drink", drink);
        model.addAttribute("household", household);
        model.addAttribute("other", other);
        model.addAttribute("totalPrice", this.productService.getTotalPrice());
        return "home";

    }
}
