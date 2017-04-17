package com.bibliotecaParaiso.prestamos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bibliotecaParaiso.prestamos.business.PrestamoService;

@Controller
public class DevolucionController {
	
	@RequestMapping("/devolucion")
	public String iniciar(Model model){
		
		return "devolucion";
	}
}
