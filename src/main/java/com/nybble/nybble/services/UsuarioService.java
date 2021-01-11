package com.nybble.nybble.services;

import com.nybble.nybble.model.Usuario.Usuario;
import com.nybble.nybble.model.Usuario.UsuarioDao;
import com.nybble.nybble.model.Usuario.UsuarioResponse;
import com.nybble.nybble.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService
{

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioDao findUserByEmail(String email)
    {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioResponse findUserById(int id)
    {
        UsuarioDao usuarioDao = usuarioRepository.findById(id);
        if (usuarioDao == null) {
            return null;
        } else {
            UsuarioResponse usuario = new UsuarioResponse();
            usuario.setNombre(usuarioDao.getNombre());
            usuario.setApellido(usuarioDao.getApellido());
            usuario.setEmail(usuarioDao.getEmail());
            usuario.setRol(usuarioDao.getRol());
            usuario.setId(usuarioDao.getId());
            return usuario;
        }
    }

    public UsuarioDao entityToDao(UsuarioResponse usuario) {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.setNombre(usuario.getNombre());
        usuarioDao.setEmail(usuario.getEmail());
        usuarioDao.setId(usuario.getId());
        usuarioDao.setApellido(usuario.getApellido());
        usuarioDao.setRol(usuario.getRol());

        return usuarioDao;
    }

    public UsuarioResponse daoToEntity(UsuarioDao usuarioDao) {
        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setId(usuarioDao.getId());
        usuario.setEmail(usuarioDao.getEmail());
        usuario.setApellido(usuarioDao.getApellido());
        usuario.setNombre(usuarioDao.getNombre());
        usuario.setRol(usuarioDao.getRol());
        return usuario;
    }

    public UsuarioDao authenticate(String email, String passWord) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            UsuarioDao user = findUserByEmail(email);
            if (null != user) {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                digest.reset();
                digest.update(passWord.getBytes("utf8"));
                String sha1 = String.format("%040x", new BigInteger(1, digest.digest()));

                if (user.getPassword().equals(sha1)) {
                    return user;
                }
            }
            return null;
        } catch (NoSuchAlgorithmException exp) {
            throw new NoSuchAlgorithmException(exp.getMessage());
        } catch (UnsupportedEncodingException exp) {
            throw new UnsupportedEncodingException(exp.getMessage());
        }
    }

    public Usuario saveUser(Usuario usuario) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.setNombre(usuario.getNombre());
        usuarioDao.setApellido(usuario.getApellido());

        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(usuario.getPassWord().getBytes("utf8"));
        String sha1 = String.format("%040x", new BigInteger(1, digest.digest()));

        usuarioDao.setPassword(sha1);
        usuarioDao.setEmail(usuario.getEmail());

        usuarioDao.setRol(usuario.getRol());

        UsuarioDao usuarioSave = usuarioRepository.save(usuarioDao);
        usuario.setId(usuarioSave.getId());
        return usuario;
    }
}