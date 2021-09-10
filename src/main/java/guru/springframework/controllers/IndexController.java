package guru.springframework.controllers;

import guru.springframework.model.events.PageViewEvent;
import guru.springframework.pageview.PageViewService;
import guru.springframework.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.UUID;


@Controller
public class IndexController {

    private final ProductService productService;
    private final PageViewService pageViewService;


    public IndexController(ProductService productService, PageViewService pageViewService) {
        this.productService = productService;
        this.pageViewService = pageViewService;
    }

    @RequestMapping({"/", "index"})
    public String getIndex(Model model) {

        model.addAttribute("products", productService.listProducts());

        PageViewEvent pageViewEvent = new PageViewEvent();
        pageViewEvent.setPageUrl("springframework.guru/");
        pageViewEvent.setPageViewDate(new Date());
        pageViewEvent.setCorrelationId(UUID.randomUUID().toString());

        pageViewService.sendPageViewEvent(pageViewEvent);


        return "index";
    }

}
