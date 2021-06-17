package com.receiptfinanceapi.services.impl;

import com.receiptfinanceapi.dtos.UserLoginRequest;
import com.receiptfinanceapi.dtos.UserLoginResponse;
import com.receiptfinanceapi.dtos.UserResponse;
import com.receiptfinanceapi.entities.User;
import com.receiptfinanceapi.repository.IUserRepository;
import com.receiptfinanceapi.services.IUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCrypt;

    @Value("${jwt.secret}")
    private String SECRET;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCrypt) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCrypt = bCrypt;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) throws Exception {
        if(userLoginRequest == null) {
            throw new Exception("userLoginRequest is null");
        }

        if(userLoginRequest.getUsername() == null || userLoginRequest.getPassword() == null
                || userLoginRequest.getUsername().trim().equals("") || userLoginRequest.getPassword().trim().equals("")) {
            throw new Exception("Empty fields not allowed");
        }

        User userWithSameUsername = this.userRepository.findByUsername(userLoginRequest.getUsername());

        if (userWithSameUsername == null) {
            throw new Exception("username doesn't exists");
        }

        boolean passwordMatch = this.isTheSamePassword(userLoginRequest.getPassword(), userWithSameUsername.getPassword());

        if (!passwordMatch) {
            throw new Exception("Insert incorrect password");
        }

        String token = this.generateToken(userWithSameUsername);

        UserLoginResponse userLoginResponse = new UserLoginResponse();

        UserResponse userResponse = this.modelMapper.map(userWithSameUsername, UserResponse.class);
        userLoginResponse.setUser(userResponse);
        userLoginResponse.setToken(token);

        return userLoginResponse;
    }

    @Override
    public UserLoginResponse register(UserLoginRequest userRegisterRequest) throws Exception {
        if(userRegisterRequest == null) {
            throw new Exception("userLoginRequest is null");
        }

        if(userRegisterRequest.getUsername() == null || userRegisterRequest.getPassword() == null
                || userRegisterRequest.getUsername().trim().equals("") || userRegisterRequest.getPassword().trim().equals("")) {
            throw new Exception("Empty fields not allowed");
        }

        User userWithSameUsername = this.userRepository.findByUsername(userRegisterRequest.getUsername());

        if (userWithSameUsername != null) {
            throw new Exception("username already exists");
        }

        String encodePassword = this.bCrypt.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodePassword);

        User userToSave = this.modelMapper.map(userRegisterRequest, User.class);

        User userCreated = this.userRepository.save(userToSave);

        String token = this.generateToken(userCreated);

        UserLoginResponse userLoginResponse = new UserLoginResponse();

        UserResponse userResponse = this.modelMapper.map(userCreated, UserResponse.class);
        userLoginResponse.setUser(userResponse);
        userLoginResponse.setToken(token);

        return userLoginResponse;
    }

    @Override
    public UserResponse findUserById(Long id) throws Exception {

        User userfinded = this.userRepository.findById(id).orElse(null);

        if (userfinded == null) {
            throw new Exception("User with this id doesn't exists");
        }

        UserResponse userResponse = this.modelMapper.map(userfinded, UserResponse.class);

        return userResponse;
    }

    private boolean isTheSamePassword(String passwordInRequest, String passwordEncoded) {
        return this.bCrypt.matches(passwordInRequest, passwordEncoded);
    }

    private String generateToken(User user) {
        //String secretKey = "E3C644A1F4536";

        List<GrantedAuthority> grantedAuthorities = null;

        //SI FUERA A TENER VARIOS ROLES A FUTURO USARIAMOS ESTO:
//        if (user.getRol().equals("ROLE_ADMIN")) {
//            grantedAuthorities = AuthorityUtils
//                    .commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_ADMIN");
//        }
//
//        if (user.getRol().equals("ROLE_USER")) {
//            grantedAuthorities = AuthorityUtils
//                    .commaSeparatedStringToAuthorityList("ROLE_USER");
//        }
        grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        Long idUser = user.getId();

        List<String > authorities = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = Jwts
                .builder()
                .setId(idUser.toString())
                .setSubject(user.getUsername())
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7200000))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

        return "Bearer " + token;
    }
}
