package meuprojeto.service;

import meuprojeto.model.Usuario;
import meuprojeto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }
        return null;
    }

    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}