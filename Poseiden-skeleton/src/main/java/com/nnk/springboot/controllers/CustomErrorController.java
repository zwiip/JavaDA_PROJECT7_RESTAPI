package com.nnk.springboot.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
public class CustomErrorController implements ErrorController {
    private final Logger logger = Logger.getLogger(CustomErrorController.class.getName());

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Principal principal) {
        model.addAttribute("username", principal.getName());

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            logger.info("Status Code = " + statusCode);

            if (statusCode == 403) {
                model.addAttribute("errorName", "Erreur 403 : Accès Refusé");
                model.addAttribute("errorMsg", "Vous n'êtes pas authorisé.");

            } else if (statusCode == 404) {
                model.addAttribute("errorName", "Erreur 404 : Page non trouvée");
                model.addAttribute("errorMsg", "La page que vous recherchez n'a pas été trouvée.");

            } else {
                model.addAttribute("errorName", "Erreur impromptue.");
                model.addAttribute("errorMsg", "Quelque chose semble ne pas bien fonctionner.");
            }
        } else {
            logger.fine("status est null");
        }
        return "error";
    }
}
