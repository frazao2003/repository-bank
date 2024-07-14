package com.banco.banco.controller.rest;

import com.banco.banco.model.dto.LoginRequesDTO;
import com.banco.banco.model.dto.RegisterResquestDTO;
import com.banco.banco.model.dto.ResponseDTO;
import com.banco.banco.model.dto.SaveClientDTO;
import com.banco.banco.model.entity.User;
import com.banco.banco.model.infra.security.TokenService;
import com.banco.banco.model.repository.UserRepository;
import com.banco.banco.model.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.banco.banco.model.entity.Conta;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final ClienteService clienteService;

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO> login (@RequestBody LoginRequesDTO body){
        User user = this.userRepository.findByCpf(body.cpf()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setConta(user.getConta());
            responseDTO.setToken(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterResquestDTO body){
        Optional<User> user = this.userRepository.findByCpf(body.getCpf());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.getPassword()));
            SaveClientDTO clientDTO = new SaveClientDTO();
            clientDTO.setCpf(body.getCpf());
            clientDTO.setNome(body.getNome());
            clientDTO.setEmail(body.getEmail());
            clientDTO.setObservacoes(body.getObservacoes());
            Conta conta = clienteService.saveCliente(clientDTO);
            newUser.setConta(conta);
            newUser.setCpf(conta.getCliente().getCpf());
            this.userRepository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setConta(conta);
            responseDTO.setToken(token);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

}
