package meuprojeto.controller;

import jakarta.servlet.http.HttpSession;
import meuprojeto.model.Usuario;
import meuprojeto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "index-login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "index-cadastro";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String username,
                              @RequestParam String senha,
                              HttpSession session) {

        Usuario usuario = usuarioService.autenticar(username, senha);

        if (usuario != null) {
            session.setAttribute("usuario", usuario);

            if (usuario.getRole().equals("admin")) {
                return "redirect:/admin/dashboard";
            } else if (usuario.getRole().equals("gerente")) {
                return "redirect:/gerente/dashboard";
            } else {
                return "redirect:/colaborador/home";
            }
        }

        return "redirect:/login?erro=true";
    }

    @PostMapping("/cadastro")
    public String fazerCadastro(@RequestParam String nome,
                                 @RequestParam String cpf,
                                 @RequestParam String email,
                                 @RequestParam String cargo,
                                 @RequestParam String username,
                                 @RequestParam String senha,
                                 @RequestParam String role) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setCargo(cargo);
        usuario.setUsername(username);
        usuario.setSenha(senha);
        usuario.setRole(role);

        usuarioService.salvar(usuario);

        return "redirect:/login";
    }

    @GetMapping("/gerente/dashboard")
    public String gerenteDashboard() {
        return "dashboard-gerente";
    }

    @GetMapping("/colaborador/home")
    public String colaboradorHome() {
        return "dashboard-colaborador";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}