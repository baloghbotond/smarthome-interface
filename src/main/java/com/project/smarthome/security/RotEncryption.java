package com.project.smarthome.security;

import org.springframework.stereotype.Component;

@Component
public class RotEncryption {

	public String encryption(String message) {
        String encryptedMessage = "";
        String regex = "\\d*";
        int max, min;
        StringBuilder stringBuilder = new StringBuilder(encryptedMessage);
        
        if(message.matches(regex)) {
            encryptedMessage = Integer.toString(Integer.parseInt(message) + 13);
        } else {
            for(int i = 0; i < message.length(); i++) {
                char letter = message.charAt(i);
                if((letter > 90 && letter < 97) || (letter < 65 || letter > 122)) {
                    stringBuilder.append(letter);
                } else {
                    if(letter > 64 && letter < 91) {
                        max = 91;
                        min = 65;
                    } else {
                        max = 123;
                        min = 97;
                    }
                    
                    int temp = (letter + 13) % max;
                    if(temp < min) {
                        temp = temp + min;
                    }
                    letter = (char) temp;
                    stringBuilder.append(letter);
                }
            }
            encryptedMessage = stringBuilder.toString();
        }
        return encryptedMessage;
    }
    
    public String decryption(String message) {
        String decryptedMessage = "";
        String regex = "\\d*";
        int max, min;
        StringBuilder stringBuilder = new StringBuilder(decryptedMessage);
        
        if(message.matches(regex)) {
            decryptedMessage = Integer.toString(Integer.parseInt(message) - 13);
        } else {
            for(int i = 0; i < message.length(); i++) {
                char letter = message.charAt(i);
                if((letter > 90 && letter < 97) || (letter < 65 || letter > 122)) {
                    stringBuilder.append(letter);
                } else {
                    if(letter > 64 && letter < 91) {
                        max = 91;
                        min = 65;
                    } else {
                        max = 123;
                        min = 97;
                    }
                    
                    int temp = (letter - 13) % max;
                    if(temp < min) {
                        temp = temp + min;
                    }
                    
                    letter = (char) temp;
                    stringBuilder.append(letter);
                }
            }
            decryptedMessage = stringBuilder.toString();
        }
        return decryptedMessage;
    }
}
