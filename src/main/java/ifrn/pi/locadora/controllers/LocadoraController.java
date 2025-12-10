package ifrn.pi.locadora.controllers;

import ifrn.pi.locadora.models.Filme;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ifrn.pi.locadora.repositories.FilmeRepository;

@Controller
@RequestMapping("/locadora")
public class LocadoraController {

	@Autowired
	private FilmeRepository lr;


	
	
	@GetMapping("/form")
	public ModelAndView form() {
	    ModelAndView mv = new ModelAndView("locadora/formFilme");
	    mv.addObject("filme", new Filme());
	    return mv;
	}

	
	@PostMapping
	public String salvar(Filme filme) {

		System.out.println(filme);
		lr.save(filme);

		return "redirect:/locadora";
	}
	
	
	@GetMapping
	public ModelAndView listar() {
		List<Filme> locadora = lr.findAll();
		ModelAndView mv = new ModelAndView("locadora/lista");
		mv.addObject("locadora", locadora);
		return mv;
	}
	
	@GetMapping("/{id}/detalhes")
    public String detalhes(@PathVariable("id") Long id, Model model) {
        Filme filme = lr.findById(id).orElse(null);
        model.addAttribute("filme", filme);
        return "locadora/detalhes";
    }
	
	@PreAuthorize("hasRole('ATENDENTE')")
	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarEvento(@PathVariable Long id) {
	    ModelAndView md = new ModelAndView();
	    Optional<Filme> opt = lr.findById(id);
	    if(opt.isEmpty()) {
	        md.setViewName("redirect:/locadora");
	        return md;
	    }
	    
	    md.setViewName("locadora/formFilme");
	    Filme locadora = opt.get();
	    md.addObject("filme", locadora);
	    
	    return md;
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/remover")
	public String apagarEvento(@PathVariable Long id) {
		
		Optional<Filme> opt = lr.findById(id);
		
		if(!opt.isEmpty()) {
			Filme locadora = opt.get();
			lr.delete(locadora);
		}
		return "redirect:/locadora";
	}
	
	
}