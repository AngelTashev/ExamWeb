package com.softuni.exam.web;

import com.softuni.exam.model.binding.ProductAddBindingModel;
import com.softuni.exam.model.entity.CategoryName;
import com.softuni.exam.model.entity.Product;
import com.softuni.exam.model.service.CategoryServiceModel;
import com.softuni.exam.model.service.ProductServiceModel;
import com.softuni.exam.service.CategoryService;
import com.softuni.exam.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ModelMapper modelMapper;

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ModelMapper modelMapper, ProductService productService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String getProductAddForm(Model model, HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("productAddBindingModel")) {
            model.addAttribute("productAddBindingModel",new ProductAddBindingModel());
        }
        return "product-add";
    }

    @PostMapping("/add")
    public String confirmProductAddForm(@Valid @ModelAttribute("productAddBindingModel")
                                        ProductAddBindingModel productAddBindingModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes,
                                        HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors() || this.productService.findByName(productAddBindingModel.getName()) != null) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            return "redirect:add";
        }

        ProductServiceModel product = this.modelMapper.map(productAddBindingModel, ProductServiceModel.class);
        product.setCategory(this.categoryService.findByName(CategoryName.valueOf(productAddBindingModel.getCategory())));
        this.productService.addProduct(product);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") String id, HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        this.productService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/delete/all")
    public String deleteAllItems(HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        this.productService.deleteAllItems();
        return "redirect:/";
    }
}
