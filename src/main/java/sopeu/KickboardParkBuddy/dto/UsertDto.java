//package sopeu.KickboardParkBuddy.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import sopeu.KickboardParkBuddy.domain.User;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//public class UsertDto {
//    private String username;
//    private String password;
//    private String nickname;
//
//    public User toEntity() {
//        return User.builder()
//                .username(username)
//                .nickname(nickname)
//                .password(password)
//                .build();
//    }
//
//    public void encodePassword(String encodedPassword) {
//        this.password = encodedPassword;
//    }
//}
