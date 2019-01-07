package com.softuni.gamestore.web;

import com.softuni.gamestore.domain.dtos.LoginUserDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.domain.entities.Game;
import com.softuni.gamestore.survice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {
    
    private final UserService userService;
    private String userIsRegister;

    @Autowired
    public GameStoreController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);
        
        while (true){
            String line = scanner.nextLine();
            
            String[] lineToken = line.split("\\|");
            
            switch (lineToken[0]){
                case "RegisterUser":
                    UserRegisterDto userRegisterDto = 
                            new UserRegisterDto(lineToken[1], lineToken[2], lineToken[3], lineToken[4]);

                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    if(userIsRegister == null){
                        LoginUserDto loginUserDto =
                                new LoginUserDto(lineToken[1], lineToken[2]);

                        String loginResult = this.userService.loginUser(loginUserDto);

                        if(loginResult.contains("Successfully logged in")){

                            this.userIsRegister = loginUserDto.getEmail();
                        }

                        System.out.println(loginResult);
                    }else {
                        System.out.println("Session is taken !");
                    }
                    break;
                case "LogoutUser":
                    if(userIsRegister == null){
                        System.out.println("Cannot log out. No user was logged in.");
                    }else {
                        String userName = this.userService.getUsername(userIsRegister);
                        System.out.println(String.format("User %s successfully logged out", userName));
                        userIsRegister = null;
                    }
                    break;
                case "AddGame":
                    
                    if(userIsRegister != null && this.userService.isAdmin(userIsRegister)){
                        String title = lineToken[1];
                        BigDecimal price = new BigDecimal(lineToken[2]);
                        Double size = Double.parseDouble(lineToken[3]);
                        String trailer = lineToken[4];
                        String imageThumbnail =   lineToken[5];
                        String description = lineToken[6];

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String date = lineToken[7];
                        LocalDate releaseDate =  LocalDate.parse(date, formatter);
                        
                        Game newGame = 
                                new Game(title, trailer, imageThumbnail, size, price, description, releaseDate);
                        
                    }  else {
                        System.out.println("Cannot log out. No user was logged in.");
                    }
                    break;
                    
                case "EditGame":
                    break;
                case "DeleteGame":
                    break;
                case "AllGame":
                    break;
                case "DetailsGame":
                    break;
                case "OwnedGames":
                    break;
            }
        }
    }
}
