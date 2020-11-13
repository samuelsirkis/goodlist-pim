package com.goodlist.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goodlist.api.response.ResponseResult;
import com.goodlist.api.service.UserService;
import com.goodlist.api.view.LoginInput;
import com.goodlist.api.view.RegisterInput;
import com.goodlist.api.view.TokenModel;
import com.goodlist.api.view.UserModel;
import com.goodlist.core.jwt.JwtToken;
import com.goodlist.domain.events.RecurseEvent;
import com.goodlist.domain.models.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

  @Autowired
  private UserService service;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private JwtToken jwtToken;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ApplicationEventPublisher publisher;

  @PostMapping(value = "/register")
  public ResponseEntity<?> save(@Valid @RequestBody RegisterInput user, HttpServletResponse resp) {
    User userCreated = service.save(toEntity(user));
    publisher.publishEvent(new RecurseEvent(this, resp, userCreated.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<ResponseResult<TokenModel>> login(@Valid @RequestBody LoginInput input)
      throws AuthenticationException {

    ResponseResult<TokenModel> responseResult = new ResponseResult<>();

    Authentication authentication = authManager
        .authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = service.loadUserByUsername(input.getEmail());
    String token = jwtToken.getToken(userDetails);

    Optional<User> user = service.findByEmail(input.getEmail());
    responseResult.setData(new TokenModel(token, toModel(user.orElse(null))));
    return ResponseEntity.ok(responseResult);
  }

  @GetMapping(value = "/whoami")
  public ResponseEntity<ResponseResult<UserModel>> whoami() {
    ResponseResult<UserModel> responseResult = new ResponseResult<>();
    Optional<User> user = service.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    responseResult.setData(toModel(user.orElse(null)));
    return ResponseEntity.ok().body(responseResult);
  }

  private UserModel toModel(User model) {
    return modelMapper.map(model, UserModel.class);
  }

  private User toEntity(RegisterInput input) {
    return modelMapper.map(input, User.class);
  }
}
