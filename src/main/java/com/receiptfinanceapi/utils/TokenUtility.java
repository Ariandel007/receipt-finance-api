package com.receiptfinanceapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public final class TokenUtility {
    public static Long getIdUserFromToken(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").replace("Bearer ", "");

        /*obtendremos sin la firma porque solo queremos descomponer el token para saber la id, la validación
        ya se realizo en el filtro de WebSecurity*/
        int firstPartJwtIndex = jwt.lastIndexOf('.');
        String jwtWithoutSignature = jwt.substring(0, firstPartJwtIndex+1);
        Jwt<Header, Claims> simpleDecodeToken = Jwts.parser().parseClaimsJwt(jwtWithoutSignature);
        //obtenemos el jti que es la id del usuario como definimos antes
        Long idUser = Long.parseLong(simpleDecodeToken.getBody().getId());
        return idUser;
    }
}
