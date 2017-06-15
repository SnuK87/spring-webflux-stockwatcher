package de.snuk.webflux;

// @Configuration
// public class SecurityConfiguration {
//
// // authentication
//
// @Bean
// UserDetailsRepository udr() {
//
// Map<String, List<String>> users = new ConcurrentHashMap<>();
// users.put("peter", Arrays.asList("ADMIN", "USER"));
// users.put("karl", Arrays.asList("USER"));
//
// return username -> {
//
// Optional<User> user = Optional.ofNullable(users.get(username)).map(roles -> {
//
// List<SimpleGrantedAuthority> collect =
// roles.stream().map(SimpleGrantedAuthority::new)
// .collect(Collectors.toList());
//
// return new User(username, "password", collect);
// });
//
// return Mono.justOrEmpty(user.orElseThrow(() -> new
// UsernameNotFoundException("could not find username.")));
// };
// }
//
// @Bean
// ReactiveAuthenticationManager authenticationManger(UserDetailsRepository udr)
// {
// return new UserDetailsRepositoryAuthenticationManager(udr);
// }
//
// // authorization
// @Bean
// WebFilter securityFilterChain(ReactiveAuthenticationManager ram) {
// HttpSecurity http = HttpSecurity.http();
//
// http.httpBasic();
// http.authenticationManager(ram);
// http.authorizeExchange().antMatchers("/**").access((mono, context) ->
// mono.map(authentication -> {
// boolean userIsAdmin = authentication.getAuthorities().stream()
// .anyMatch(ga -> ga.getAuthority().equalsIgnoreCase("ADMIN"));
//
// return new AuthorizationDecision(userIsAdmin);
// }));
//
// return http.build();
// }
// }
