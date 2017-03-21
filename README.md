# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Send HTML email
* Retrieve the same email
* Parse the email

### How do I get set up? ###

* Open application.properties and set below properties: 
  * spring.mail.username
  * spring.mail.password 
  * spring.mail.host
  * spring.mail.port etc.
  which will be used in sending emails from the application
* Open EmailService.java and set the "To" address
  * line: `messageHelper.setTo("me@myemail.com");`
* Open EmailController.java and set below strings:
  * `String host = "imap.gmail.com";`
  * `String mailStoreType = "imap";`
  * `String username = "you@gmail.com";`
  * `String password = "password";`
  for the `check()` and `fetch()` methods. Emails in these accounts will be checked or fetched.

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact
