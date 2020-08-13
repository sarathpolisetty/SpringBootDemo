package com.demo.integrate;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.integrate.entities.Product;
import com.demo.integrate.entities.User;
import com.demo.integrate.jwt.JwtUtil;
import com.demo.integrate.models.AuthenticationRequest;
import com.demo.integrate.models.AuthenticationResponse;
import com.demo.integrate.repos.UserRepo;
import com.demo.integrate.services.MyUserDetailsService;
import com.demo.integrate.services.ProductService;

@RestController
public class ApiController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	MyUserDetailsService userService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	PasswordEncoder encoder;
	
	@PostMapping("/insertuser")
	public ResponseEntity<?> insertUser(@RequestBody User user){
		if(userRepo.getUserByUsername(user.getUsername())==null) {
			user.setPassword(encoder.encode(user.getPassword()));
			userService.insertUser(user);
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@GetMapping("/demo")
	public String welcome() {
		return "Welcome";
	}
	
	@GetMapping("/products")
	public List<Product> getAll(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> get(@PathVariable Integer id) {
		try {
			Product product = productService.getById(id);
			return new ResponseEntity<Product>(product,HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/insertproduct")
	public ResponseEntity<Product> save(@RequestBody Product product) {
		productService.insert(product);
		return new ResponseEntity<>(product,HttpStatus.OK);
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<Product> update(@RequestBody Product product,@PathVariable Integer id){
		try {
			Product dbProduct = productService.getById(id);
			//System.out.println("testing");
//			service.delete(id);
//			Product obj=new Product();
//			obj.setId(product.getId());
//			obj.setName(product.getName());
//			obj.setPrice(product.getPrice());
			productService.insert(product);
			
			return new ResponseEntity<>(product,HttpStatus.OK);
			
		}
		catch(NoSuchElementException e) {
			//service.insert(product);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> delete(@PathVariable Integer id){
		try {
			Product product = productService.getById(id);
			productService.delete(id);
			return new ResponseEntity<Product>(product,HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}

	

}
