package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rest.domain.Account;
import rest.domain.DTO.AccountDTO;
import rest.domain.DTO.JwtAuthenticationRequest;
import rest.domain.DTO.UserTokenState;
import rest.exception.ResourceConflictException;
import rest.service.AccountService;
import rest.utils.TokenUtils;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        Account account = (Account) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(account.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
        public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountRequest, UriComponentsBuilder ucBuilder) throws Exception {
        AccountDTO existUser = this.accountService.findByEmail(accountRequest.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(accountRequest.getId(), "Username already exists");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        accountRequest.setPassword(encoder.encode(accountRequest.getPassword()));

        AccountDTO accountDTO = this.accountService.insert(accountRequest);

        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }
}
