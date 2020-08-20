package com.ltran.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ltran.study.config.JwtTokenUtil;
import com.ltran.study.model.JwtRequest;
import com.ltran.study.model.JwtResponse;
import com.ltran.study.model.List;
import com.ltran.study.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired 
	private JwtUserDetailsService userDetailsService;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> jsonSample(@RequestBody List listSample) throws Exception {
		return ResponseEntity.ok(new List());
	}
//	private String encryptInternal(Key key) {
//	    JsonWebEncryption jwe = new JsonWebEncryption();
//	    jwe.setPlaintext(claims);
//	    for (Map.Entry<String, Object> entry : headers.entrySet()) {
//	        jwe.getHeaders().setObjectHeaderValue(entry.getKey(), entry.getValue());
//	    }
//	    if (innerSigned && !headers.containsKey("cty")) {
//	        jwe.getHeaders().setObjectHeaderValue("cty", "JWT");
//	    }
//	    String keyAlgorithm = getKeyEncryptionAlgorithm(key);
//	    jwe.setAlgorithmHeaderValue(keyAlgorithm);
//	    jwe.setEncryptionMethodHeaderParameter(getContentEncryptionAlgorithm());
//
//	    if (key instanceof RSAPublicKey && keyAlgorithm.startsWith(KeyEncryptionAlgorithm.RSA_OAEP.getAlgorithm())
//	            && ((RSAPublicKey) key).getModulus().bitLength() < 2048) {
//	        throw ImplMessages.msg.encryptionKeySizeMustBeHigher(keyAlgorithm);
//	    }
//	    jwe.setKey(key);
//	    try {
//	        return jwe.getCompactSerialization();
//	    } catch (org.jose4j.lang.JoseException ex) {
//	        throw ImplMessages.msg.joseSerializationError(ex.getMessage(), ex);
//	    }
//	}
	 
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
