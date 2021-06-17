package com.receiptfinanceapi.controllers;

import com.receiptfinanceapi.dtos.ReceiptToCreateRequest;
import com.receiptfinanceapi.dtos.UserLoginRequest;
import com.receiptfinanceapi.dtos.UserLoginResponse;
import com.receiptfinanceapi.entities.ReceiptData;
import com.receiptfinanceapi.services.IReceiptService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("receipt")
public class ReceiptController {

    private final IReceiptService receiptService;

    @Autowired
    public ReceiptController(IReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createReceipt(@RequestBody ReceiptToCreateRequest receiptToCreateRequest,
                                           HttpServletRequest request) {
        try {
            String jwt = request.getHeader("Authorization").replace("Bearer ", "");

            /*obtendremos sin la firma porque solo queremos descomponer el token para saber la id, la validación
            ya se realizo en el filtro de WebSecurity*/
            int firstPartJwtIndex = jwt.lastIndexOf('.');
            String jwtWithoutSignature = jwt.substring(0, firstPartJwtIndex+1);
            Jwt<Header,Claims> simpleDecodeToken = Jwts.parser().parseClaimsJwt(jwtWithoutSignature);
            //obtenemos el jti que es la id del usuario como definimos antes
            Long idUser = Long.parseLong(simpleDecodeToken.getBody().getId());

            ReceiptData receiptToCreateResponse = this.receiptService.createReceipt(receiptToCreateRequest, idUser);
            return ResponseEntity.status(HttpStatus.OK).body(receiptToCreateResponse);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
